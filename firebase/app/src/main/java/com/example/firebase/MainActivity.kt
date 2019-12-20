package com.example.firebase

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {
    private  var imageView :ImageView?=null
    private  var downloadView :ImageView?=null
    private val PICK_IMAGE_REQUEST = 71
    private var storageReference : StorageReference?=null
    var filepath : Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView= findViewById(R.id.imgView)
        downloadView = findViewById(R.id.download)

    }

    fun click(v: View)
    {
        when(v.id)
        {
            R.id.btnChoose ->chooseImage()
            R.id.btnUpload ->{

                uploadImage()
            }

            R.id.btndownload -> { Log.e("debug","uploadbutton")
                downloadImage()}
        }
    }

    fun chooseImage()
    {
        var intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data!= null && data.data != null)
        {
            filepath = data.data
            try{
                var bitmap:Bitmap
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath)
                imageView!!.setImageBitmap(bitmap)
            }
            catch ( e : IOException)
            {
                e.printStackTrace();
            }
        }
    }

    fun uploadImage(){
        val storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

        val filePath = filepath
        if (filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            val ref =
                storageReference!!.child("images/" + UUID.randomUUID().toString())
            ref.putFile(filePath)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(this@MainActivity, "Uploaded", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(this@MainActivity, "Failed " + e.message, Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress =
                        100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                            .totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        }
    }

    fun downloadImage(){
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReferenceFromUrl("gs:///databaseimage-e5b6b.appspot.com/images").child("449a2685-0563-4117-8bd7-cc9ff5c2b793")
        try {
            val localFile: File = File.createTempFile("images", "jpg")
            storageRef.getFile(localFile)
                .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot?> {
                    val bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath())
                    downloadView!!.setImageBitmap(bitmap)
                }).addOnFailureListener(OnFailureListener {
                    Log.e("debug","download fail")
                })
        } catch (e: IOException) {
            e.printStackTrace()
        }


    }

}
