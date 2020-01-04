package com.example.drawandguess

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.game.*
import kotlinx.android.synthetic.main.normal.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


class game : AppCompatActivity() {

    private lateinit var color_list: List<String>
    // 跟後端連結的宣告
    private  val strurl = "http://140.136.149.224:3000/subject"
    private var account:String?=null
    private var info :TextView?=null
    var title :String?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        account = intent.getStringExtra("account")

        initData()
        newrequest()
        clock()

    }

    fun clock()
    {
        info = findViewById(R.id.info)
        object : CountDownTimer(10000, 1000) {

            override fun onFinish() {
                info!!.text = getString(R.string.done)
//                saveClickHandler(title)
                android.app.AlertDialog.Builder(this@game)
                        .setIcon(R.drawable.ring)
                        .setTitle("time up")
                        .setPositiveButton("ok", DialogInterface.OnClickListener {

                            dialog, which ->
                            val intent = Intent()
                            intent.setClass(this@game,game_guess::class.java)
                            intent.putExtra("account",account)
                            startActivity(intent)})
                        .show()
            }

            override fun onTick(millisUntilFinished: Long) {
                info!!.text = getString(R.string.remain).plus("${millisUntilFinished/1000}")
            }

        }.start()
    }

    fun newrequest()
    {
        var lbl_result = findViewById(R.id.subject) as TextView
        val objqueue = Volley.newRequestQueue(this)
       val getRequest =
                StringRequest(strurl, Response.Listener { response ->
                    //response，表示是回傳值，就是API要回傳的字串，也可以是JSON字串。
                    title = response
                    lbl_result.setText("subject:" + response)
                }, Response.ErrorListener { error ->
                    //如果發生錯誤，就是回傳VolleyError，可以顯示是什麼錯誤。
                    lbl_result.text = error.message
                })
        objqueue!!.add(getRequest)
    }
    private fun initData()
    {
        color_list = listOf( getString(R.string.color_black),
                getString(R.string.color_red),
                getString(R.string.color_blue),
                getString(R.string.color_green),
                getString(R.string.color_yellow))
    }

    fun click(v : View)
    {
        when(v.id)
        {
            R.id.clean_button ->
                layout_paint_board.Clean()
            R.id.color_button -> change_color()
            R.id.weight_button -> change_weight()
            R.id.eraser_button -> {
                layout_paint_board.pencolorchange("#FFFFFF")
                Toast.makeText(this@game, "you choose the eraser", Toast.LENGTH_SHORT).show()}
            R.id.goback_button -> {
                val intent = Intent()
                intent.setClass(this@game, Maininterface::class.java)
                intent.putExtra("account",account)
                startActivity(intent)
            }
        }

    }

    var size = 10
    private fun  change_weight(){
        val popDialog = AlertDialog.Builder(this)
        val seek = SeekBar(this)
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
                layout_paint_board.pen_size_change(i)
                popDialog.setMessage("Value is : "+ temp+"f")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })



        popDialog.setPositiveButton("OK"){dialog, _ ->
            //            Toast.makeText(this@game, "你選擇的是" + color_list[singleChoiceIndex], Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        popDialog.create()
        popDialog.show()

    }

    private var singleChoiceIndex = 0 // 初始選取的顏色
    private fun change_color() {
        AlertDialog.Builder(this@game)
                .setSingleChoiceItems(color_list.toTypedArray(), singleChoiceIndex
                ) { _, which -> singleChoiceIndex = which }
                .setPositiveButton(R.string.confirm) { dialog, _ ->
                    if(singleChoiceIndex == 0) { // black
                        layout_paint_board.pencolorchange("#000000")
                        color_button.setBackgroundResource(R.drawable.black_button)

                    }
                    else if(singleChoiceIndex == 1){ // red
                        layout_paint_board.pencolorchange("#FF0000")
                        color_button.setBackgroundResource(R.drawable.red_circle_button)
                    }
                    else if(singleChoiceIndex == 2){ // blue
                        layout_paint_board.pencolorchange("#0000FF")
                        color_button.setBackgroundResource(R.drawable.blue_circle_button)
                    }
                    else if(singleChoiceIndex == 3){ // green
                        layout_paint_board.pencolorchange("#00FF00")
                        color_button.setBackgroundResource(R.drawable.green_circle_button)
                    }
                    else if(singleChoiceIndex == 4){ // yellow
                        layout_paint_board.pencolorchange("#FFFF00")
                        color_button.setBackgroundResource(R.drawable.yellow_circle_button)
                    }
                    Toast.makeText(this@game, "你選擇的是" + color_list[singleChoiceIndex], Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .show()
    }

    private fun checkWritable(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
            return false
        } else {
            return true
        }
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
                            layout_paint_board.saveBitmap(stream, uri, this@game, title,account)
                            stream.flush()
                            stream.close()
                        }
                        job1.await()
                    }

                    Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                println(e)
                Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

}
