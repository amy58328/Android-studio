package com.example.drawandguess

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.creat.*
import kotlinx.android.synthetic.main.game.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

class connect : AppCompatActivity() {
    private lateinit var color_list: List<String>
    private val strurl : String?="http://140.136.149.224:3000/picture/store"
    private var jsonobject: JsonObjectRequest?=null
    private  var account :String? = null
    private var title :String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connect)
        initData()
        account = intent.getStringExtra("account")
        getnewrequest()
    }

    fun getnewrequest()
    {
        val subjecturl = "http://140.136.149.224:3000/subject"
        var lbl_result = findViewById(R.id.subject) as TextView
        val objqueue = Volley.newRequestQueue(this)
        val getRequest =
                StringRequest(subjecturl, Response.Listener { response ->
                    //response，表示是回傳值，就是API要回傳的字串，也可以是JSON字串。
                    title = response
                    lbl_result.setText("subject:" + response)

                }, Response.ErrorListener { error ->
                    //如果發生錯誤，就是回傳VolleyError，可以顯示是什麼錯誤。
                    lbl_result.text = error.message
                })
        objqueue!!.add(getRequest)
    }

    private fun initData() {
        color_list = listOf(getString(R.string.color_black),
                getString(R.string.color_red),
                getString(R.string.color_blue),
                getString(R.string.color_green),
                getString(R.string.color_yellow))
    }


    fun click(v: View) {
        when (v.id) {
            R.id.clean_button ->
                layout_paint_board.Clean()
            R.id.color_button -> change_color()
            R.id.weight_button -> change_weight()
            R.id.eraser_button -> {
                layout_paint_board.pencolorchange("#FFFFFF")
                Toast.makeText(this@connect, "you choose the eraser", Toast.LENGTH_SHORT).show()
            }
            R.id.save_button ->{
                check()

            }
            R.id.goback_button -> {
                val intent = Intent()
                intent.setClass(this@connect, Maininterface::class.java)
                intent.putExtra("account",account)
                startActivity(intent)
            }

            R.id.next_button->getnewrequest()

        }

    }

    private fun check() {
        val item = LayoutInflater.from(this@connect).inflate(R.layout.connect_enter_title, null)
        val ediText = item.findViewById(R.id.enter_title_button) as EditText
        ediText.setText(title)
        AlertDialog.Builder(this@connect)
                .setTitle("Save")
                .setView(item)
                .setPositiveButton("save") { _, _ ->
                    title = ediText.text.toString()
                    if (TextUtils.isEmpty(title)) {
                        Toast.makeText(applicationContext, "Title is empty", Toast.LENGTH_SHORT).show()
                    } else {
                        saveClickHandler(title)
                    }
                }
                .setNegativeButton("cancel") { dia, _ ->
                    dia.dismiss()
                }
                .show()
                .setCanceledOnTouchOutside(false)
    }

    var size = 10
    private fun change_weight() {
        val popDialog = AlertDialog.Builder(this)
        val seek = SeekBar(this)
//        val item1 = TextView(this)
        seek.setMax(100)
        seek.setProgress(size)
        popDialog.setTitle("change the pen weight")
        popDialog.setView(seek)

        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                val temp = i.toString()
                size = i;
                weight_button.text = temp + "f"
//                item1.setText("Value is : "+ temp+"f")
                layout_paint_board.pen_size_change(i)
                popDialog.setMessage("Value is : " + temp + "f")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        popDialog.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        popDialog.create()
        popDialog.show()

    }

    private var singleChoiceIndex = 0 // 初始選取的顏色
    private fun change_color() {
        AlertDialog.Builder(this@connect)
                .setSingleChoiceItems(color_list.toTypedArray(), singleChoiceIndex
                ) { _, which -> singleChoiceIndex = which }
                .setPositiveButton(R.string.confirm) { dialog, _ ->
                    if (singleChoiceIndex == 0) { // black
                        layout_paint_board.pencolorchange("#000000")
                        color_button.setBackgroundResource(R.drawable.black_button)
                    } else if (singleChoiceIndex == 1) { // red
                        layout_paint_board.pencolorchange("#FF0000")
                        color_button.setBackgroundResource(R.drawable.red_circle_button)
                    } else if (singleChoiceIndex == 2) { // blue
                        layout_paint_board.pencolorchange("#0000FF")
                        color_button.setBackgroundResource(R.drawable.blue_circle_button)
                    } else if (singleChoiceIndex == 3) { // green
                        layout_paint_board.pencolorchange("#00FF00")
                        color_button.setBackgroundResource(R.drawable.green_circle_button)
                    } else if (singleChoiceIndex == 4) { // yellow
                        layout_paint_board.pencolorchange("#FFFF00")
                        color_button.setBackgroundResource(R.drawable.yellow_circle_button)
                    }
                    Toast.makeText(this@connect, "你選擇的是" + color_list[singleChoiceIndex], Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .show()
                .setCanceledOnTouchOutside(false)
    }

    private fun checkWritable(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
            return false
        } else {
            return true
        }
    }

    fun new_request(name:String?){

            val json = JSONObject()
            json.put("account",account)
            json.put("subject",name)

            jsonobject = JsonObjectRequest(
                    Request.Method.POST,strurl,json,
                    object: Response.Listener<JSONObject> {
                        override fun onResponse(response: JSONObject?) {
                            Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()
                        }
                    },
                    object : Response.ErrorListener{
                        override fun onErrorResponse(error: VolleyError?) {
                            if (error != null) {
                                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            )


            Volley.newRequestQueue(this).add(jsonobject)
    }

    private fun saveClickHandler(title: String?) {
        if (checkWritable()) {
            try {
                Toast.makeText(this, "enter save", Toast.LENGTH_SHORT).show()
                val fileName = title + ".jpg"
                var file = File(Environment.getExternalStorageDirectory(), fileName)

                val uri: Uri
                uri = file.toUri()

                val stream = FileOutputStream(file)

                if(!account.equals("null"))
                {
                    GlobalScope.launch(Dispatchers.Main) {
                        val job1 = async{
                            layout_paint_board.saveBitmap(stream, uri, this@connect, title,account)
                            stream.flush()
                            stream.close()
                        }
                        job1.await()

                        val job2 = async{
                            new_request(title)
                        }
                        job2.await()
                    }

                    Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show()
                }

                else
                {
                    Toast.makeText(applicationContext, "your account is null please login again", Toast.LENGTH_SHORT).show()
                    val intent = Intent()
                    intent.setClass(this@connect,MainActivity::class.java)
                    startActivity(intent)
                }
            } catch (e: Exception) {
                println(e)
                Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

}
