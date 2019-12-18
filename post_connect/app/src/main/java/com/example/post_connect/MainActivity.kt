package com.example.post_connect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import java.lang.reflect.Method

class MainActivity : AppCompatActivity() {
    private var jsonArray: JsonObjectRequest?=null
    private  val strurl = "http://140.136.149.224:3000/user/login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun new_request(){
        val account = findViewById<EditText>(R.id.account)
        val password = findViewById<EditText>(R.id.password)

        var json = JSONObject()

        json.put("User",account.text.toString())
        json.put("Password",password.text.toString())

        var post_text = findViewById(R.id.post_text) as TextView

        jsonArray = JsonObjectRequest(Request.Method.POST,strurl,json,
            object: Response.Listener<JSONObject> {
                override fun onResponse(response: JSONObject?) {
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
        new_request()
        Volley.newRequestQueue(this).add(jsonArray)

    }
}
