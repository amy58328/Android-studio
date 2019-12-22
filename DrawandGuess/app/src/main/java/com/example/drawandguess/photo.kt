package com.example.drawandguess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

class photo : AppCompatActivity() {

    private var account:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo)
        account = intent.getStringExtra("account")
        Log.e("debg","photo"+account)
    }

    fun click(v:View)
    {
        when(v.id)
        {
            R.id.goback_button ->{
                val intent = Intent()
                intent.setClass(this@photo, Maininterface::class.java)
                intent.putExtra("account",account)
                startActivity(intent)
            }

        }
    }


}
