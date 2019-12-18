package com.example.drawandguess

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user)
    }

    fun click(v:View)
    {
        when(v.id)
        {
            R.id.login -> {
                val intent = Intent()
                intent.setClass(this@MainActivity, Maininterface::class.java)
                startActivity(intent)
            }

            R.id.register ->{
                val intent = Intent()
                intent.setClass(this@MainActivity, creat::class.java)
                startActivity(intent)
            }
        }

    }
    companion object {
        val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"
    }
}
