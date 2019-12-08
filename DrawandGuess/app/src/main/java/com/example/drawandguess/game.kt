package com.example.drawandguess

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.game.*

class game : AppCompatActivity() {
    private var button: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        button = findViewById<View>(R.id.button) as Button

        val nextPageBtn = findViewById<View>(R.id.button) as Button
        nextPageBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@game, MainActivity::class.java)
            startActivity(intent)
        }
        change_weight()
    }

    companion object {
        val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"
    }

    fun click(v : View)
    {
        when(v.id)
        {
            R.id.clean_button ->
                layout_paint_board.Clean()
            R.id.red_button ->
                layout_paint_board.pencolorchange("#FF0000")
            R.id.blue_button ->
                layout_paint_board.pencolorchange("#0000FF")
        }

    }
    fun change_weight(){
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
//                Toast.makeText(applicationContext, "start tracking", Toast.LENGTH_SHORT).show()
                val temp = i.toString()
                paint_size.text = temp + "f"
                layout_paint_board.pen_size_change(i)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }



}
