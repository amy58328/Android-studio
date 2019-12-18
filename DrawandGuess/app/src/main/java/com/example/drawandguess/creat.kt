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
    private var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creat)


        textView = findViewById<View>(R.id.test) as TextView

        val nextPageBtn = findViewById<View>(R.id.test) as TextView

        nextPageBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@creat, MainActivity::class.java)
            startActivity(intent)
        }
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

        }
    }

    companion object {
        val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"
    }
}
