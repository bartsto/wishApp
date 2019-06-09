package com.example.wishapp

import android.content.DialogInterface
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_wish_detail.*
import java.util.*


class WishDetailActivity : AppCompatActivity() {

    private var filePath: Uri? = null
    private var urlPath: String? = null

    internal var storage:FirebaseStorage? = null
    internal var storageReferences: StorageReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_detail)

        storage = FirebaseStorage.getInstance()
        storageReferences = storage!!.reference

        val wishId = intent.getIntExtra("wishId", -1)
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val wish = databaseHandler.getWishList().get(wishId)

        val wish_name_display = findViewById<TextView>(R.id.wish_name_display).apply {
            text = wish.name
        }

        val wish_desc_display = findViewById<TextView>(R.id.wish_desc_display).apply {
            text = wish.description
        }

        val wishDetailImage = findViewById<ImageView>(R.id.wish_detail_image).apply {
            setImageURI(Uri.parse(Uri.decode(wish.imagePath)))
            filePath = Uri.parse(Uri.decode(wish.imagePath))

        }

        button_find.setOnClickListener {
            uploadFile()
        }

        button_save_edited.setOnClickListener {
            editWish(wish)
        }

        button_delete_wish.setOnClickListener {
            deleteWish(wish)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
    }

    private fun uploadFile() {
        println(filePath)
        if(filePath != null){
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val imageRef = storageReferences!!.child("images/" + UUID.randomUUID().toString())
            imageRef.putFile(filePath!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "File uploaded", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener{
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener {taskSnapShot ->
                    val progress = 100.0 * taskSnapShot.bytesTransferred/taskSnapShot.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
                }
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        urlPath = task.result.toString()
                        println("************************************************** GET URL ADDRESSES")
                        println(urlPath)
                    }

                }

//            searchImage(urlPath)
        }
    }

    fun searchImage(imgPath: String) {
        var base_url: String = "https://www.google.com/searchbyimage?site=search&sa=X&image_url="
//        var google_shop: String = "https://www.google.com/search?q={}&source=lnms&tbm=shop&"
//            .format("slowo + klucz")
        val uri = Uri.parse(base_url + imgPath)
        var intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    fun deleteWish(wish: Wish) {
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        val status = databaseHandler.deleteWish(wish)
        if (status > -1) {
            Toast.makeText(applicationContext, "Wish deleted.", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(applicationContext, "Something went wrong.", Toast.LENGTH_LONG).show()
        }
    }

    fun editWish(wish: Wish) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(dialogView)

        val wishName = dialogView.findViewById(R.id.updateName) as EditText
        wishName.setText(wish.name)
        val wishDescription = dialogView.findViewById(R.id.updateDescription) as EditText
        wishDescription.setText(wish.description)

        dialogBuilder.setTitle("Wish update")
        dialogBuilder.setMessage("Enter data below")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->

            val updateName = wishName.text.toString()
            val updateDescription = wishDescription.text.toString()
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            if (updateName.trim() != "" && updateDescription.trim() != "") {
                val updatedWish = wish
                updatedWish.name = updateName
                updatedWish.description = updateDescription
                val status = databaseHandler.updateWish(updatedWish)
                if (status > -1) {
                    Toast.makeText(applicationContext, "Wish updated", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(applicationContext, "Name or description cannot be blank", Toast.LENGTH_LONG).show()
            }
            finish()
            startActivity(getIntent())
        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
        })
        val b = dialogBuilder.create()
        b.show()
    }
}
