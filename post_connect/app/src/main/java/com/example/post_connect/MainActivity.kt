package com.example.post_connect

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private var jsonobject:JsonObjectRequest?=null
    private  val strurl = "http://140.136.149.224:3000/user/login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun new_request(){
        var post_text = findViewById(R.id.post_text) as TextView
        var account = findViewById(R.id.account) as EditText
        var password = findViewById(R.id.password) as EditText

        val json = JSONObject()
        json.put("User",account.text.toString())
        json.put("Password",password.text.toString())


        jsonobject = JsonObjectRequest(
            Request.Method.POST,strurl,json,
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
        Volley.newRequestQueue(this).add(jsonobject)
    }


    fun click(v: View) { //將getRequest物件加入Volley物件的queue中，執行跟API的溝通。
        when(v.id)
        {
            R.id.get_button -> new_request()
        }
    }

}
