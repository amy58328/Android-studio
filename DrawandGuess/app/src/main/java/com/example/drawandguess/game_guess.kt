package com.example.drawandguess

import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import java.io.IOException

class game_guess : AppCompatActivity() {
    private var info : TextView?=null
    private var account:String?=null
    private var jsonobject: JsonObjectRequest?=null
    private val strurl : String?="http://140.136.149.224:3000/guess/back"
    var subject :String ? = null
    var title :String =""
    var ans :EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_guess)

        ans = findViewById(R.id.Dialog_box2)
        account = intent.getStringExtra("account")
        clock()
        new_picture()

    }

    fun click(v:View)
    {
       when(v.id)
       {
           R.id.enter_button->{
               checkanswer()
           }
           R.id.goback_button -> {
               val intent = Intent()
               intent.setClass(this@game_guess, Maininterface::class.java)
               intent.putExtra("account",account)
               startActivity(intent)
           }
       }
    }
    fun clock()
    {
        info = findViewById(R.id.info)
        account = intent.getStringExtra("account")
        object : CountDownTimer(30000, 1000) {
            override fun onFinish() {
                info!!.text = getString(R.string.done)
                android.app.AlertDialog.Builder(this@game_guess)
                        .setIcon(R.drawable.ring)
                        .setTitle("time up")
                        .setPositiveButton("ok", DialogInterface.OnClickListener{
                            dialog, which ->
                            val intent = Intent()
                            intent.setClass(this@game_guess,wait::class.java)
                            intent.putExtra("account",account)
                            startActivity(intent) })
                        .show()
                        .setCanceledOnTouchOutside(false)
            }
            override fun onTick(millisUntilFinished: Long) {
                info!!.text = getString(R.string.remain).plus("${millisUntilFinished/1000}")
            }
        }.start()
    }

    fun checkanswer()
    {
        val answer = ans!!.text.toString()
        if (answer.isEmpty())
        {
            Toast.makeText(this@game_guess, "Your answer is empty", Toast.LENGTH_SHORT).show()
        }
        else{
            if(answer.equals(subject))
            {
                Toast.makeText(this@game_guess, "answer is correct", Toast.LENGTH_SHORT).show()
                ans!!.setText("")
                val intent = Intent()
                intent.setClass(this@game_guess,wait::class.java)
                intent.putExtra("account",account)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this@game_guess, "answer is false", Toast.LENGTH_SHORT).show()
                ans!!.setText("")
            }
        }

    }


    fun new_picture(){
        GlobalScope.launch(Dispatchers.Main) {
            val job1 = async{
                new_request()
                Thread.sleep(500)
            }
            job1.await()

            val job2 = async{
                downloadImage()
            }
            job2.await()
        }
    }

    fun new_request(){
        val json = JSONObject()
        json.put("account",account)
        Log.e("debug",account)

        jsonobject = JsonObjectRequest(
                Request.Method.POST,strurl,json,
                object: Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject?) {
                        Log.e("debug",response.toString())
                        val str = response.toString()
                        val list  = str.split("\"")
                        val player_id = list[3]
                        subject = list[9]

                        title = player_id + "_" + subject
                        Log.e("debug",title)


                    }
                },
                object : Response.ErrorListener{
                    override fun onErrorResponse(error: VolleyError?) {
                        if (error != null) {
                            Log.e("debug",error.message)
                        }
                    }
                }
        )
        Volley.newRequestQueue(this).add(jsonobject)
    }

    fun downloadImage(){
        val photo_view = findViewById<ImageView>(R.id.blink)
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReferenceFromUrl("gs:///databaseimage-e5b6b.appspot.com/images").child(title)
        try {
            val localFile: File = File.createTempFile("images", "jpg")
            storageRef.getFile(localFile)
                    .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot?> {
                        val bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath())
                        photo_view!!.setImageBitmap(bitmap)
                    }).addOnFailureListener(OnFailureListener {
                        Log.e("debug","download fail")
                    })
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}