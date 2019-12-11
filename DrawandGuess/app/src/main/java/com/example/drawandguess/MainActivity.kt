package com.example.drawandguess

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    private var mAppBarConfiguration: AppBarConfiguration? = null
    private var button: Button? = null
    private var button1: Button? = null
    private var button2: Button? = null
    private var button3: Button? = null
    private var button4: Button? = null
    private var button5: Button? = null
    private var button6: Button? = null
    private val drawer: DrawerLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        button = findViewById<View>(R.id.toolbar) as Button
        button1 = findViewById<View>(R.id.guess_button) as Button
        button2 = findViewById<View>(R.id.draw_button) as Button
        button3 = findViewById<View>(R.id.photo_button) as Button
        button4 = findViewById<View>(R.id.chart_button) as Button
        button5 = findViewById<View>(R.id.draw_and_guess_button) as Button
        button6 = findViewById<View>(R.id.button_signout) as Button

        val nextPageBtn = findViewById<View>(R.id.toolbar) as Button
        nextPageBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity,MainActivity::class.java)
            startActivity(intent)
        }

        val nextPageBtn1 = findViewById<View>(R.id.guess_button) as Button
        nextPageBtn1.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity,normal::class.java)
            startActivity(intent)
        }
        val nextPageBtn2 = findViewById<View>(R.id.draw_button) as Button
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
        val nextPageBtn5 = findViewById<View>(R.id.draw_and_guess_button) as Button
        nextPageBtn5.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity, game::class.java)
            startActivity(intent)
        }
        val nextPageBtn6 = findViewById<View>(R.id.button_signout) as Button
        nextPageBtn6.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity,user1::class.java)
            startActivity(intent)
        }






    }


    companion object {
        val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"
    }

}




