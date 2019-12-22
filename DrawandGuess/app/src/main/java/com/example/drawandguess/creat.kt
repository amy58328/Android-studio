package com.example.drawandguess

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    private val strurl = "http://140.136.149.224:3000/user/registed"
    private var account :EditText?=null
    private var password:EditText?=null
    private var password2:EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creat)
        account = findViewById(R.id.text_userid)
        password = findViewById(R.id.text_userpassword)
        password2 = findViewById(R.id.text_userpassword2)
    }

    fun click(v:View)
    {
        when(v.id)
        {
            R.id.correct ->{
                if(isempty())
                {
                    new_request()
                }
            }

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

    fun isempty():Boolean
    {
        if(account!!.text.isEmpty())
        {
            Toast.makeText(applicationContext, "Your account is empty", Toast.LENGTH_SHORT).show()
            return false
        }
        if(password!!.text.isEmpty())
        {
            Toast.makeText(applicationContext, "The password is empty", Toast.LENGTH_SHORT).show()
            return false
        }
        if(password2!!.text.isEmpty())
        {
            Toast.makeText(applicationContext, "Please enter the password twice", Toast.LENGTH_SHORT).show()
            return false
        }
        else
        {
            return true
        }
    }


    fun new_request(){
        if(password!!.text.toString().equals(password2!!.text.toString()) )
        {
            val json = JSONObject()
            json.put("User",account!!.text.toString())
            json.put("Password",password!!.text.toString())

            jsonArray = JsonObjectRequest(
                    Request.Method.POST,strurl,json,
                    object: Response.Listener<JSONObject> {
                        override fun onResponse(response: JSONObject?) {

                            val test:String?=response.toString().substring(12,15)

                            if(test.equals("Yes"))
                            {
                                val intent = Intent()
                                intent.setClass(this@creat, MainActivity::class.java)
                                startActivity(intent)
                            }
                            else
                            {
                                Toast.makeText(applicationContext, "create error", Toast.LENGTH_SHORT).show()
                            }
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
            Volley.newRequestQueue(this).add(jsonArray)
        }
        else{
            Toast.makeText(applicationContext, "The password is not the same twice", Toast.LENGTH_SHORT).show()
            password!!.text = null
            password2!!.text = null
        }


    }
}
