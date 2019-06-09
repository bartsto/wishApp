package com.example.wishapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_wish_detail.*

class WishDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_detail)


        button_find.setOnClickListener{

        }

        button_save_edited.setOnClickListener{
            saveWish()
        }

        button_delete_wish.setOnClickListener{
            deleteWish()
        }

        val wishId = intent.getIntExtra("wishId", -1)
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val wish = databaseHandler.getWishList().get(wishId)

        val wish_name_display = findViewById<EditText>(R.id.wish_name_display).apply {
            setText(wish.name)
        }

        val wish_desc_display = findViewById<EditText>(R.id.wish_desc_display).apply {
            setText(wish.description)
        }


    }

    fun dispatchSaveEditedWishIntent(){

    }

    fun dispatchDeleteWishIntent(){

    }

    fun searchImage(imgPath : String) {
        var base_url : String  = "https://www.google.com/searchbyimage?site=search&sa=X&image_url="
        var google_shop : String = "https://www.google.com/search?q={}&source=lnms&tbm=shop&"
            .format("slowo + klucz")
        val uri = Uri.parse(base_url + imgPath)
        var intent : Intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    fun saveWish(){
        val message = Toast.makeText(applicationContext, "Changes saved", Toast.LENGTH_LONG)
        message.show()

        // zapisuje edytowane zmainy, zostaje w tym samym widoku
    }

    fun deleteWish(){
        val message = Toast.makeText(applicationContext, "Deleted wish", Toast.LENGTH_LONG)
        message.show()

        // usuwanie, po usunieciu przechodzi do poprzedniego widoku
    }

}