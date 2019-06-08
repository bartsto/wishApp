package com.example.wishapp

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_wish_detail.*
import java.io.ByteArrayOutputStream

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
