package com.example.drawandguess

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var jsonArray: JsonObjectRequest?=null
    private  val strurl = "http://140.136.149.224:3000/user/login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user)
    }

    fun new_request(){
        var post_text = findViewById(R.id.post_text) as TextView
        var account = findViewById(R.id.text_userid) as EditText
        var password = findViewById(R.id.text_userpassword) as EditText

        val json = JSONObject()
        json.put("User",account.text.toString())
        json.put("Password",password.text.toString())


        jsonArray = JsonObjectRequest(
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
        Volley.newRequestQueue(this).add(jsonArray)
    }

    fun click(v:View)
    {
        when(v.id)
        {
            R.id.login -> {
                new_request()
                val intent = Intent()
                intent.setClass(this@MainActivity, Maininterface::class.java)
                startActivity(intent)
            }

            R.id.register ->{
                val intent = Intent()
                intent.setClass(this@MainActivity, creat::class.java)
                startActivity(intent)
            }

        }

    }
}
