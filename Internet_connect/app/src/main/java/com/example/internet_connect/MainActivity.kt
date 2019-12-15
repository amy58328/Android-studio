package com.example.internet_connect

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


//import javax.swing.UIManager.put


class MainActivity : AppCompatActivity() {
    private var objqueue:RequestQueue? = null
    private  var getRequest:StringRequest? = null
    private  val strurl = "http://140.136.149.224:3000/subject"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newrequest()
   }

    fun newrequest()
    {
        var lbl_result = findViewById(R.id.post_text) as TextView
        objqueue = Volley.newRequestQueue(this)
        getRequest =
            StringRequest(strurl, Response.Listener { response ->
                //response，表示是回傳值，就是API要回傳的字串，也可以是JSON字串。
                lbl_result.text = response
            }, Response.ErrorListener { error ->
                //如果發生錯誤，就是回傳VolleyError，可以顯示是什麼錯誤。
                lbl_result.text = error.message
            })
    }


    fun prc_getmessage(v: View?) { //將getRequest物件加入Volley物件的queue中，執行跟API的溝通。
        objqueue!!.add(getRequest)
        newrequest()
    }

}
