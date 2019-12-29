package com.example.drawandguess

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
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

class normal : AppCompatActivity() {
    private var downloadView:ImageView?=null
    private  var account :String? = null
    private var jsonobject: JsonObjectRequest?=null
    private  val strurl = "http://140.136.149.224:3000/picture/back"
    private  var title :String?=null
    private var dialog: EditText? = null
    var subject :String ? = null
    var number : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.normal)
        downloadView = findViewById(R.id.blink)
        account = intent.getStringExtra("account")

        dialog = findViewById(R.id.Dialog_box2)
        title = "asd"

        new_picture()
    }

    fun new_picture(){
        number = 0
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
        jsonobject = JsonObjectRequest(
                Request.Method.POST,strurl,
                object: Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject?) {
                        val str = response.toString()
                        val list  = str.split("\"")
                        val player_id = list[3]
                        subject = list[7]

                        title = player_id + "_" + subject
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

    fun click(v:View)
    {
        when(v.id)
        {
            R.id.goback_button ->{
                val intent = Intent()
                intent.setClass(this@normal, Maininterface::class.java)
                intent.putExtra("account",account)
                startActivity(intent)
            }
            R.id.enter_button ->{
                checkanswer()
            }
            R.id.next_button->{new_picture() }
            R.id.answer_button->{
                giveanswer()
            }
            R.id.prompt->{
                number += 1
                prompt(number)
            }

        }
    }

    fun prompt(n : Int)
    {
        var size = subject!!.length
        if(n == 1)
        {
            Toast.makeText(this@normal, "The answer is " + size + " words", Toast.LENGTH_SHORT).show()
        }

        else if(n == 2)
        {
            val string = subject
            var text = string!!.substring(0,1)
            Toast.makeText(this@normal, "The first word is " + text , Toast.LENGTH_SHORT).show()
        }

        else if( n ==3)
        {
            val string = subject
            var text = string!!.substring(size -1 , size)
            Toast.makeText(this@normal, "The last word is " + text , Toast.LENGTH_SHORT).show()
        }

        else
        {
            Toast.makeText(this@normal, "you have no prompt" , Toast.LENGTH_SHORT).show()
        }


    }


    fun giveanswer()
    {
        Toast.makeText(this@normal, "The answer is " + subject, Toast.LENGTH_SHORT).show()
    }


    fun checkanswer()
    {
        val answer = dialog!!.text.toString()
        if (answer.isEmpty())
        {
            Toast.makeText(this@normal, "Your answer is empty", Toast.LENGTH_SHORT).show()
        }
        else{
            if(answer.equals(subject))
            {
                Toast.makeText(this@normal, "answer is correct", Toast.LENGTH_SHORT).show()
                dialog!!.setText("")
                new_picture()
            }
            else
            {
                Toast.makeText(this@normal, "answer is false", Toast.LENGTH_SHORT).show()
                dialog!!.setText("")
            }
        }

    }

    fun downloadImage(){
        val tt = title as String
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReferenceFromUrl("gs:///databaseimage-e5b6b.appspot.com/images").child(tt)
        try {
            val localFile: File = File.createTempFile("images", "jpg")
            storageRef.getFile(localFile)
                    .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot?> {
                        val bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath())
                        downloadView!!.setImageBitmap(bitmap)
                    }).addOnFailureListener(OnFailureListener {
                        Log.e("debug","download fail")
                    })
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}
