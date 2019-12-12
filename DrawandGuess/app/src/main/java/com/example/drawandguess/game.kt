package com.example.drawandguess

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.game.*


class game : AppCompatActivity() {
    private var button: Button? = null
    private lateinit var color_list: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        button = findViewById<View>(R.id.goback_button) as Button

        val nextPageBtn = findViewById<View>(R.id.goback_button) as Button
        nextPageBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@game, MainActivity::class.java)
            startActivity(intent)
        }

        initData()
    }

    private fun initData()
    {
        color_list = listOf( getString(R.string.color_black),
                getString(R.string.color_red),
                getString(R.string.color_blue),
                getString(R.string.color_green),
                getString(R.string.color_yellow))
    }


    fun click(v : View)
    {
        when(v.id)
        {
            R.id.clean_button ->
                layout_paint_board.Clean()
            R.id.color_button -> change_color()
            R.id.weight_button -> change_weight()
            R.id.eraser_button -> {layout_paint_board.pencolorchange("#FFFFFF")
            Toast.makeText(this@game, "you choose the eraser", Toast.LENGTH_SHORT).show()}

        }

    }

     var size = 10
    private fun  change_weight(){
        val popDialog = AlertDialog.Builder(this)
        val seek = SeekBar(this)
//        val item1 = TextView(this)
        seek.setMax(100)
        seek.setProgress(size)
        popDialog.setTitle("change the pen weight")
        popDialog.setView(seek)

        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                val temp = i.toString()
                size = i;
                weight_button.text = temp + "f"
//                item1.setText("Value is : "+ temp+"f")
                layout_paint_board.pen_size_change(i)
                popDialog.setMessage("Value is : "+ temp+"f")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })



        popDialog.setPositiveButton("OK"){dialog, _ ->
//            Toast.makeText(this@game, "你選擇的是" + color_list[singleChoiceIndex], Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        popDialog.create()
        popDialog.show()

    }

    private var singleChoiceIndex = 0 // 初始選取的顏色
    private fun change_color() {
        AlertDialog.Builder(this@game)
                .setSingleChoiceItems(color_list.toTypedArray(), singleChoiceIndex
                ) { _, which -> singleChoiceIndex = which }
                .setPositiveButton(R.string.confirm) { dialog, _ ->
                    if(singleChoiceIndex == 0) { // black
                        layout_paint_board.pencolorchange("#000000")
                        color_button.setBackgroundResource(R.drawable.black_circle_button)

                    }
                    else if(singleChoiceIndex == 1){ // red
                        layout_paint_board.pencolorchange("#FF0000")
                        color_button.setBackgroundResource(R.drawable.red_circle_button)
                    }
                    else if(singleChoiceIndex == 2){ // blue
                        layout_paint_board.pencolorchange("#0000FF")
                        color_button.setBackgroundResource(R.drawable.blue_circle_button)
                    }
                    else if(singleChoiceIndex == 3){ // green
                        layout_paint_board.pencolorchange("#00FF00")
                        color_button.setBackgroundResource(R.drawable.green_circle_button)
                    }
                    else if(singleChoiceIndex == 4){ // yellow
                        layout_paint_board.pencolorchange("#FFFF00")
                        color_button.setBackgroundResource(R.drawable.yellow_circle_button)
                    }
                    Toast.makeText(this@game, "你選擇的是" + color_list[singleChoiceIndex], Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .show()

    }

}
