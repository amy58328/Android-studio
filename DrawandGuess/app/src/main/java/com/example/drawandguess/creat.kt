package com.example.drawandguess

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class creat : AppCompatActivity() {
    private var jsonArray:JsonObjectRequest?=null
    private  val strurl = "http://140.136.149.224:3000/user/login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creat)

<<<<<<< HEAD

        textView = findViewById<View>(R.id.test) as TextView

        val nextPageBtn = findViewById<View>(R.id.test) as TextView
        nextPageBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@creat, user1::class.java)
            startActivity(intent)
        }
    }

    companion object {
        val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"
=======
    }

    fun click(v:View)
    {
        when(v.id)
        {
            R.id.correct -> new_request()
            R.id.cancel->{
                val intent = Intent()
                intent.setClass(this@creat, MainActivity::class.java)
                startActivity(intent)
            }

            R.id.goback_button ->{
                val intent = Intent()
                intent.setClass(this@creat, MainActivity::class.java)
                startActivity(intent)
            }

        }
    }

    fun new_request(){
        var post_text = findViewById(R.id.post_text) as TextView
        var account = findViewById(R.id.text_userid) as EditText
        var password = findViewById(R.id.text_userpassword) as EditText
        var password2 = findViewById(R.id.text_userpassword2) as EditText

        if(password.text.toString() == password2.text.toString())
        {
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
        else{
            Toast.makeText(applicationContext, "The password is not the same twice", Toast.LENGTH_SHORT).show()
            password.text = null
            password2.text = null
        }


>>>>>>> 670d4423402caf5b37a55c6c3c704104b603fa37
    }
}
