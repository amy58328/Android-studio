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
        Log.e("game_guess","1-1")
        super.onCreate(savedInstanceState)
        Log.e("game_guess","1-2")
        setContentView(R.layout.game_guess)
        Log.e("game_guess","1-3")

        ans = findViewById(R.id.Dialog_box2)
        Log.e("game_guess","1--4")
        account = intent.getStringExtra("account")
        Log.e("game_guess",account)
        Log.e("game_guess","1-5")
//        new_request()
//        title = "妹妹_手"
//        subject = "手"
//            downloadImage()
        clock()
        Log.e("game_guess","1")
        new_picture()
        Log.e("game_guess","2")

    }

    fun click(v:View)
    {
       when(v.id)
       {
           R.id.enter_button->{
               checkanswer()
           }
       }
    }
    fun clock()
    {
        info = findViewById(R.id.info)
        account = intent.getStringExtra("account")
        object : CountDownTimer(10000, 1000) {
            override fun onFinish() {
                info!!.text = getString(R.string.done)
                android.app.AlertDialog.Builder(this@game_guess)
                        .setIcon(R.drawable.ring)
                        .setTitle("time up")
                        .setPositiveButton("ok", DialogInterface.OnClickListener{
                            dialog, which ->
                            Log.e("debug","go wait")
                            val intent = Intent()
                            intent.setClass(this@game_guess,wait::class.java)
                            intent.putExtra("account",account)
                            startActivity(intent) })
                        .show()
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
        Log.e("game_guess","2-2")
        new_request()
        Log.e("game_guess","2-3")
    }

    fun new_request(){
        Log.e("game_guess","2-4")
        val json = JSONObject()
        Log.e("game_guess","2-5")
        json.put("account",account)
        Log.e("game_guess",account)

        jsonobject = JsonObjectRequest(
                Request.Method.POST,strurl,json,
                object: Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject?) {
                        Log.e("debug",response.toString())

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
        Log.e("game_guess","5")
        Volley.newRequestQueue(this).add(jsonobject)
        Log.e("game_guess","5-1")
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