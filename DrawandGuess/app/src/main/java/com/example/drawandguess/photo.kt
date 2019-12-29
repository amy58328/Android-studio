package com.example.drawandguess

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
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


class photo : AppCompatActivity() {

    private var jsonobject: JsonObjectRequest?=null
    private  val strurl = "http://140.136.149.224:3000/picture/find"
    private var account:String?=null
    var data = arrayOf("一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月")
    var title : List<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo)
        account = intent.getStringExtra("account")
        Log.e("debg","photo"+account)

        Log.e("data",data.toString())

        GlobalScope.launch(Dispatchers.Main) {
            val job1 = async{
                new_request()
                Thread.sleep(500)
            }
            job1.await()

            val job2 = async{
                setList()
            }
            job2.await()
        }


    }

    fun new_request(){
        val json = JSONObject()
        json.put("User",account)

        jsonobject = JsonObjectRequest(
                Request.Method.POST,strurl,json,
                object: Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject?) {
                        var string = response!!.getString("out")
                        string = string.substring(2,string.length-2)
                        Log.e("test",string)

                        title = string.split( "\",\""  )
                        Log.e("title",title.toString())

                        for(i in 0..title.size-1)
                        {
                            Log.e("title",title[i].toString())
                        }

                    }
                },
                object : Response.ErrorListener{
                    override fun onErrorResponse(error: VolleyError?) {
                        if (error != null) {
//                            Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
                            Log.e("debug",error.message.toString())
                        }
                    }
                }
        )
        Volley.newRequestQueue(this).add(jsonobject)
    }

    fun setList()
    {

        val listview = findViewById(R.id.listview) as ListView
        val adapter: ArrayAdapter<*> = ArrayAdapter<String?>(this, android.R.layout.simple_list_item_1, title)
        listview.adapter = adapter

        listview.onItemClickListener = OnItemClickListener {
            adapterView, view, i, l -> Toast.makeText(this@photo, listview.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show()
            downloadImage(listview.getItemAtPosition(i).toString())
        }

    }
    fun click(v:View)
    {
        when(v.id)
        {
            R.id.goback_button ->{
                val intent = Intent()
                intent.setClass(this@photo, Maininterface::class.java)
                intent.putExtra("account",account)
                startActivity(intent)
            }

        }
    }

    fun downloadImage(t : String){
        val tt = account + "_" + t
        Log.e("tt",tt)
        val photo_view = findViewById<ImageView>(R.id.photo_view)
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReferenceFromUrl("gs:///databaseimage-e5b6b.appspot.com/images").child(tt)
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
