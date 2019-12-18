package com.example.drawandguess

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

class charts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.charts)

<<<<<<< HEAD
        button = findViewById<View>(R.id.button) as Button

        val nextPageBtn = findViewById<View>(R.id.button) as Button
        nextPageBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@charts, MainActivity::class.java)
            startActivity(intent)
        }
=======
>>>>>>> 670d4423402caf5b37a55c6c3c704104b603fa37
    }

    fun click(v:View)
    {
        when(v.id)
        {
            R.id.goback_button ->{
                val intent = Intent()
                intent.setClass(this@charts, Maininterface::class.java)
                startActivity(intent)
            }
        }
    }


}
