package com.example.drawandguess

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

class charts : AppCompatActivity() {
    private var button: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.charts)

        button = findViewById<View>(R.id.button) as Button

        val nextPageBtn = findViewById<View>(R.id.button) as Button
        nextPageBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@charts, MainActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"
    }


}
