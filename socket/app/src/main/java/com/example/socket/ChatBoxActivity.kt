package com.example.socket

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.socket.client.IO
import io.socket.client.Manager
import java.net.URISyntaxException


class ChatBoxActivity : AppCompatActivity() {

    private  var messagetext : EditText?=null
    private var send : Button?=null
    private var nickname : String?=null
    private  val socket = IO.socket("http://140.136.149.224:8787")

    override fun onCreate(savedInstanceState: Bundle?) {

        Log.e("debug","go in ")
        super.onCreate(savedInstanceState)
        Log.e("debug","1 ")
        setContentView(R.layout.activity_chat_box)
        Log.e("debug","2 ")

        messagetext = findViewById(R.id.message)
        nickname = intent.getStringExtra("nickname")
        Log.e("debug",nickname)

        try { // 連線server
            socket.connect()
            socket.emit("join",nickname)
        }catch (e:URISyntaxException)
        {
            Log.e("debug",e.toString())

        }
        socket.on("userdisconnect") { args ->
            runOnUiThread {
                val data = args[0] as String
                Toast.makeText(this@ChatBoxActivity, data, Toast.LENGTH_SHORT).show()
            }
        }

        socket.on("userjoinedthechat") { args ->
            runOnUiThread {
                val data = args[0] as String
                Toast.makeText(this@ChatBoxActivity, data, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun click(v: View)
    {
        when(v.id)
        {
            R.id.send ->{
                if(!messagetext!!.text.toString().isEmpty())
                {
                    socket.emit("messagedetection",nickname,messagetext!!.text)
                    messagetext!!.setText("")
                }
            }
        }
    }
}
