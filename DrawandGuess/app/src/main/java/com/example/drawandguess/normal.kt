package com.example.drawandguess

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

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
import org.json.JSONObject
import java.io.File
import java.io.IOException

class normal : AppCompatActivity() {
    private var downloadView:ImageView?=null
    private  var account :String? = null
    private var jsonobject: JsonObjectRequest?=null
    private  val strurl = "http://140.136.149.224:3000/user/login"
    private  var title :String?=null
    private var dialog: EditText? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.normal)
        downloadView = findViewById(R.id.blink)
        account = intent.getStringExtra("account")
        Log.e("debg","normal"+account)

        dialog = findViewById(R.id.Dialog_box2)

        new_request()
        title = "測試4"
        downloadImage()
    }

    fun new_request(){
        jsonobject = JsonObjectRequest(
                Request.Method.POST,strurl,
                object: Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject?) {
//                        val string = response.toString()
//                        val list  = string.split("{}:\"=")
//                        val player_id = list.get(1)
//                        val subject = list.get(3)
//
//                        Log.e("debug",player_id)
//                        Log.e("debug",subject)
//
//                        title = player_id + "_" + subject
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
            R.id.next_button->{
                new_request()
                downloadImage()
            }
            R.id.answer_button->{
                giveanswer()
            }

        }
    }

    fun giveanswer()
    {
        Toast.makeText(this@normal, "The answer is " + title, Toast.LENGTH_SHORT).show()
    }


    fun checkanswer()
    {
        val answer = dialog!!.text.toString()
        if (answer.isEmpty())
        {
            Toast.makeText(this@normal, "Your answer is empty", Toast.LENGTH_SHORT).show()
        }
        else{
            if(answer.equals(title))
            {
                Toast.makeText(this@normal, "answer is correct", Toast.LENGTH_SHORT).show()
                dialog!!.setText("")
                new_request()
                downloadImage()
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
