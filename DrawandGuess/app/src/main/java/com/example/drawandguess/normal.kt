package com.example.drawandguess

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

class normal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.normal)

    }

    fun click(v:View)
    {
        when(v.id)
        {
            R.id.goback_button ->{
                val intent = Intent()
                intent.setClass(this@normal, Maininterface::class.java)
                startActivity(intent)
            }
        }
    }

}
