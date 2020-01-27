package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import kotlinx.android.synthetic.main.update_layout.*
import java.io.IOException


class UpdateActivity : AppCompatActivity() {

    private val PICK_IMAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_layout)

        val tundaString = intent.getStringExtra("tundaData")
        val gson = Gson()
        val tunda = gson.fromJson<Tunda>(tundaString,Tunda::class.java)

        foodImage.setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()

            //startActivityForResult(Intent.createChooser(intent, "Sellect Picture"), PICK_IMAGE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data
                //foodImage.setImageURI(fileUri)

                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, fileUri)
                    foodImage.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                //You can get File object from intent
                //val file: File? = ImagePicker.getFile(data)

                //You can also get File Path from intent
                val filePath: String? = ImagePicker.getFilePath(data)
                Log.i("FilePath: ", filePath)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}