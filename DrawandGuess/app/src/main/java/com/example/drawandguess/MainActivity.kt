package com.example.drawandguess

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private var button: Button? = null
    private var button1: Button? = null
    private var button2: Button? = null
    private var button3: Button? = null
    private var button4: Button? = null
    private var button5: Button? = null
    private val toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        button = findViewById<View>(R.id.side_menu_button) as Button
        button1 = findViewById<View>(R.id.normal_button) as Button
        button2 = findViewById<View>(R.id.connect_button) as Button
        button3 = findViewById<View>(R.id.photo_button) as Button
        button4 = findViewById<View>(R.id.chart_button) as Button
        button5 = findViewById<View>(R.id.start_button) as Button

        val nextPageBtn = findViewById<View>(R.id.side_menu_button) as Button
        nextPageBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity, user1::class.java)
            startActivity(intent)
        }

        val nextPageBtn1 = findViewById<View>(R.id.normal_button) as Button
        nextPageBtn1.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity, normal::class.java)
            startActivity(intent)
        }
        val nextPageBtn2 = findViewById<View>(R.id.connect_button) as Button
        nextPageBtn2.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity, connect::class.java)
            startActivity(intent)
        }
        val nextPageBtn3 = findViewById<View>(R.id.photo_button) as Button
        nextPageBtn3.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity, photo::class.java)
            startActivity(intent)
        }
        val nextPageBtn4 = findViewById<View>(R.id.chart_button) as Button
        nextPageBtn4.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity, charts::class.java)
            startActivity(intent)
        }
        val nextPageBtn5 = findViewById<View>(R.id.start_button) as Button
        nextPageBtn5.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity, game::class.java)
            startActivity(intent)
        }

    }

    companion object {
        val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"
    }

}



