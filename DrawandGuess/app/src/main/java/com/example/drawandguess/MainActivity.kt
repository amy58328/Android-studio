package com.example.drawandguess


import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix

import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity

import java.io.FileNotFoundException
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class MainActivity: AppCompatActivity() {
    //宣告
    private var btn: Button? = null
    private var textViewMessage: TextView? = null
    private var editTextMessage: EditText? = null
    private var mImg: ImageView? = null
    private var mPhone: DisplayMetrics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        textViewMessage = findViewById<View>(R.id.textView) as TextView
        textViewMessage!!.text = "NICKNAME "
        editTextMessage = findViewById<View>(R.id.name) as EditText
        btn = findViewById<View>(R.id.enter_name) as Button
        btn!!.setOnClickListener { textViewMessage!!.text = "     " + editTextMessage!!.text.toString() }
        //讀取手機解析度
        mPhone = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(mPhone)

        mImg = findViewById<View>(R.id.img) as ImageView
        val mCamera = findViewById<View>(R.id.camera) as Button
        val mPhoto = findViewById<View>(R.id.photo) as Button

        mCamera.setOnClickListener {
            val value = ContentValues()
            value.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    value)
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri!!.path)
            startActivityForResult(intent, CAMERA)
        }

        mPhoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, PHOTO)
        }

    }



    // button click 之後的行為
    fun click(v:View){
        val intent = Intent()
        when(v.id)
        {
            R.id.button_signout-> intent.setClass(this@MainActivity,user1::class.java)
            R.id.draw_and_guess_button ->intent.setClass(this@MainActivity, game::class.java)
            R.id.chart_button -> intent.setClass(this@MainActivity, charts::class.java)
            R.id.photo -> intent.setClass(this@MainActivity, photo::class.java)
            R.id.draw_button ->intent.setClass(this@MainActivity, connect::class.java)
            R.id.guess_button -> intent.setClass(this@MainActivity,normal::class.java)
        }
        startActivity(intent)
    }

    //拍照完畢或選取圖片後呼叫此函式
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //藉由requestCode判斷是否為開啟相機或開啟相簿而呼叫的，且data不為null
        if ((requestCode == CAMERA || requestCode == PHOTO) && data != null) {
            //取得照片路徑uri
            val uri = data.data
            val cr = this.contentResolver

            try {
                //讀取照片，
                val mOptions = BitmapFactory.Options()
//Size=2為將原始圖片縮小1/2，Size=4為1/4，以此類推
                mOptions.inSampleSize = 8
                val bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri!!))
                //val bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri!!))

                //判斷照片為橫向或者為直向，並進入ScalePic判斷圖片是否要進行縮放
                if (bitmap.width > bitmap.height)
                    ScalePic(bitmap,
                            mPhone!!.heightPixels)
                else
                    ScalePic(bitmap, mPhone!!.widthPixels)
            } catch (e: FileNotFoundException) {
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun ScalePic(bitmap: Bitmap, phone: Int) {
        //縮放比例預設為1
        var mScale = 1f

        //如果圖片寬度大於手機寬度則進行縮放，否則直接將圖片放入ImageView內
        if (bitmap.width > phone) {
            //判斷縮放比例
            mScale = phone.toFloat() / bitmap.width.toFloat()

            val mMat = Matrix()
            mMat.setScale(mScale, mScale)

            val mScaleBitmap = Bitmap.createBitmap(bitmap,
                    0,
                    0,
                    bitmap.width,
                    bitmap.height,
                    mMat,
                    false)
            mImg!!.setImageBitmap(mScaleBitmap)
        } else
            mImg!!.setImageBitmap(bitmap)
    }

    companion object {
        private val CAMERA = 66
        private val PHOTO = 99
    }
}
