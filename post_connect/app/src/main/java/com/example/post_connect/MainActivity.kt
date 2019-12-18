package com.example.post_connect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    val account = findViewById<EditText>(R.id.text_account)
    val password = findViewById<EditText>(R.id.text_password)
    val post_button = findViewById<Button>(R.id.post_button)

    private var jsonArray: JsonArrayRequest?=null
    private  val strurl = "http://140.136.149.224:3000/subject"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        post_data_to_server()

    }

    fun post_data_to_server()
    {
        val server_request = findViewById<TextView>(R.id.server_request)
        jsonArray =object: JsonArrayRequest(strurl,
            object: Response.Listener<JSONArray> {
                override fun onResponse(response: JSONArray?) {
                    server_request.text = response.toString()

                }
            },
            object : Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    if (error != null) {
                        server_request.text = error.message
                    }
                }
            }
        ){
//            override fun getParams(): Map<String, String> {
//                val params = HashMap<String,String>()
//
//                params.put("User",account.getText().toString())
//                params.put("Password",password.getText().toString())
//                return super.getParams()
//
//                return params
//            }
        }
    }

    fun click(v: View)
    {
        Volley.newRequestQueue(this).add(jsonArray)
        post_data_to_server()
    }
}
