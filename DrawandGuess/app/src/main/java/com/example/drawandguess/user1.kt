package com.example.drawandguess

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class user1 : AppCompatActivity() {
    private var button: Button? = null
    private var button1: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user)


        button = findViewById<View>(R.id.button1) as Button

        val nextPageBtn = findViewById<View>(R.id.button1) as Button
        nextPageBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@user1, MainActivity::class.java)
            startActivity(intent)
        }
        button1 = findViewById<View>(R.id.button2) as Button

        val nextPageBtn1 = findViewById<View>(R.id.button2) as Button
        nextPageBtn1.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@user1, creat::class.java)
            startActivity(intent)
        }
    }

    companion object {
        val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"
    }
}
