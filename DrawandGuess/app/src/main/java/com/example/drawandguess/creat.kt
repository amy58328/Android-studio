package com.example.drawandguess

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class creat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creat)

    }

    fun click(v:View)
    {
        when(v.id)
        {
            R.id.correct ->{
                val intent = Intent()
                intent.setClass(this@creat, MainActivity::class.java)
                startActivity(intent)
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

    companion object {
        val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"
    }
}
