package com.example.wishapp.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Toast
import com.example.wishapp.R
import com.example.wishapp.db.DatabaseHandler
import com.example.wishapp.model.Wish
import kotlinx.android.synthetic.main.activity_new_wish.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class NewWishActivity : AppCompatActivity() {
    /**
     * Activity for create new wish
     * Possibility to add image from gallery or take picture by camera
     * Created wish with provided data(name and description)
     * Saved all date in local database
     */

    var currentPath: String? = ""
    var currentUrlImage: String? = ""
    val TAKE_PICTURE = 1
    val SELECT_PICTURE = 2

    companion object {
        private var WISH_INDEX = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_wish)

        button_gallery.setOnClickListener {
            dispatchGalleryIntent()
        }

        button_camera.setOnClickListener {
            dispatchCameraIntent()
        }

        button_save.setOnClickListener {
            saveWish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val image = findViewById<ImageView>(R.id.wish_photo)
        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            try {
                val file = File(currentPath)
                val uri = Uri.fromFile(file)
                currentPath = uri.toString()
                image.setImageURI(uri)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK) {
            try {
                val uri = data!!.data
                currentPath = uri.toString()
                image.setImageURI(uri)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun dispatchGalleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Wybierz zdjęcie"), SELECT_PICTURE)
    }

    fun dispatchCameraIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImage()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (photoFile != null) {
                var photoUri = FileProvider.getUriForFile(
                    this, "com.example.wishapp.fileprovider",
                    photoFile
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(intent, TAKE_PICTURE)
            }
        }
    }

    fun createImage(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageName = "JPEG_" + timeStamp + "_"
        var storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var image = File.createTempFile(imageName, ".jpg", storageDir)
        return image
    }


    fun saveWish() {
        val wish_id = WISH_INDEX++
        val wish_name = input_name.text.toString()
        val wish_description = input_description.text.toString()
        val image_path = currentPath
        val wish_urlImage = currentUrlImage
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        if (wish_name.trim() != "" && wish_description.trim() != "") {
            val status = databaseHandler.addWish(
                Wish(
                    wish_id,
                    wish_name,
                    wish_description,
                    image_path,
                    wish_urlImage
                )
            )
            if (status > -1) {
                Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
                input_name.text.clear()
                input_description.text.clear()
            }
            finish()
        } else {
            Toast.makeText(applicationContext, "Name or description cannot be blank", Toast.LENGTH_LONG).show()
        }

    }
}
