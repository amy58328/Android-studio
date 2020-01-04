package com.example.socket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.DisplayNameSources.NICKNAME
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private  var btn : Button?= null
    private var nickname:EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.enterchat)
        nickname = findViewById(R.id.nickname)
    }

    fun click(v:View)
    {
        when(v.id)
        {
            R.id.enterchat -> {
                Log.e("debug","click the button ")
                Log.e("debug",nickname!!.text.toString())
                if(!nickname!!.text.toString().isEmpty()) {
                    Log.e("debug","go to ")
                    val intent = Intent()
                    intent.setClass(this@MainActivity, ChatBoxActivity::class.java)
                    intent.putExtra("nickname", nickname!!.text.toString())
                    startActivity(intent)
                }
            }
        }
    }
}
