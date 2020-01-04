package com.example.drawandguess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject

class wait : AppCompatActivity() {

    private var account: String? = null
    private var jsonobject: JsonObjectRequest?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.waiting)

        account = intent.getStringExtra("account")

        GlobalScope.launch(Dispatchers.Main) {
            val job1 = async {
                new_request(1)
                Thread.sleep(500)
            }
            job1.await()
            val job2 = async {
                new_request(0)
                Thread.sleep(500)
            }
            job2.await()
        }
    }

    fun click(v:View)
    {
        when(v.id)
        {
            // 出去要跟後端說 他已經離開了
            R.id.goback_button -> {
                val intent = Intent()
                intent.setClass(this@wait, Maininterface::class.java)
                intent.putExtra("account",account)
                startActivity(intent)
            }

        }
    }

    fun new_request(first:Int){
        if(first == 1)
        {
            val strurl = "http://140.136.149.224:3000/room/in"
            val json = JSONObject()
            json.put("account",account)

            jsonobject = JsonObjectRequest(
                    Request.Method.POST,strurl,json,
                    object: Response.Listener<JSONObject> {
                        override fun onResponse(response: JSONObject?) {
                        }
                    },
                    object : Response.ErrorListener{
                        override fun onErrorResponse(error: VolleyError?) {
                            if (error != null) {
                                Log.e("debug","1-"+error.message.toString())
                            }
                        }
                    }
            )
        }

        else
        {
            val strurl = "http://140.136.149.224:3000/room/wait"
            jsonobject = JsonObjectRequest(
                    Request.Method.GET,strurl,
                    object: Response.Listener<JSONObject> {
                        override fun onResponse(response: JSONObject?) {
                            val a = response.toString()
                            Log.e("debug","2-"+a)
                            if(a == "{\"message\":\"1\"}") // 如果兩個人了 就開始遊戲
                            {
                                val intent = Intent()
                                intent.setClass(this@wait, game::class.java)
                                intent.putExtra("account",account)
                                startActivity(intent)
                            }
                            else
                            {
                                new_request(0)
                            }
                        }
                    },
                    object : Response.ErrorListener{
                        override fun onErrorResponse(error: VolleyError?) {
                            if (error != null) {
                                Log.e("debug",error.message.toString())
                            }
                        }
                    }
            )
        }

        Volley.newRequestQueue(this).add(jsonobject)
    }
}