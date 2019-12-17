package com.example.internet_connect

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.w3c.dom.Text
import kotlin.math.log


//import javax.swing.UIManager.put


class MainActivity : AppCompatActivity() {
    private var jsonArray:JsonArrayRequest?=null
    private  val strurl = "http://140.136.149.224:3000/subject"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        new_request()
   }

    fun new_request(){
        var post_text = findViewById(R.id.post_text) as TextView
        jsonArray = JsonArrayRequest(strurl,
            object: Response.Listener<JSONArray> {
                override fun onResponse(response: JSONArray?) {
                    post_text.text = response.toString()

                }
            },
            object : Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    if (error != null) {
                        post_text.text = error.message
                    }
                }
            }
        )
    }

    fun prc_getmessage(v: View?) { //將getRequest物件加入Volley物件的queue中，執行跟API的溝通。
        Volley.newRequestQueue(this).add(jsonArray)
        new_request()
    }

}
