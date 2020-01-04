package com.example.drawandguess

import android.app.AlertDialog
import android.content.DialogInterface
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

class MainActivity : AppCompatActivity() {
    private var jsonobject: JsonObjectRequest?=null
    private  val strurl = "http://140.136.149.224:3000/user/login"
    private var account: EditText?=null
    private var password:EditText?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user)
        account = findViewById(R.id.text_userid)
        password = findViewById(R.id.text_userpassword)
    }

    fun new_request(){
        val json = JSONObject()
        json.put("User",account!!.text.toString())
        json.put("Password",password!!.text.toString())

        jsonobject = JsonObjectRequest(
                Request.Method.POST,strurl,json,
                object: Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject?) {
                        val test:String?=response.toString().substring(12,15)

                        if(test.equals("Yes"))
                        {
                            val intent = Intent()
                            intent.setClass(this@MainActivity, Maininterface::class.java)
                            intent.putExtra("account",account!!.text.toString())
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(applicationContext, test, Toast.LENGTH_SHORT).show()
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
        Volley.newRequestQueue(this).add(jsonobject)
    }

    fun isempty():Boolean{
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
        else
        {
            return true
        }
    }
    fun click(v:View)
    {
        when(v.id)
        {
            R.id.login -> {
                if(isempty())
                {
                    new_request()
                }
            }

            R.id.register ->{
                val intent = Intent()
                intent.setClass(this@MainActivity, creat::class.java)
                startActivity(intent)
            }
            R.id.forget ->{
                AlertDialog.Builder(this@MainActivity)
                        .setIcon(R.drawable.ring)
                        .setTitle("你跟我說也沒辦法啊! 去找歐歐")
                        .setPositiveButton("確定", DialogInterface.OnClickListener {

                            dialog, which ->
                           })

                        //.setNegativeButton("", null).create()
                        .show()
            }
        }
    }
}
