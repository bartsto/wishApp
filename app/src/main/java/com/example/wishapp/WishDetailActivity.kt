package com.example.wishapp

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_wish_detail.*

class WishDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_detail)

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
        }

        button_find.setOnClickListener {

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

    fun dispatchSaveEditedWishIntent() {

    }

    fun dispatchDeleteWishIntent() {

    }

    fun searchImage(imgPath: String) {
        var base_url: String = "https://www.google.com/searchbyimage?site=search&sa=X&image_url="
        var google_shop: String = "https://www.google.com/search?q={}&source=lnms&tbm=shop&"
            .format("slowo + klucz")
        val uri = Uri.parse(base_url + imgPath)
        var intent: Intent = Intent(Intent.ACTION_VIEW, uri)
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
