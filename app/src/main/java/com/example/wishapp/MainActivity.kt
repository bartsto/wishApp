package com.example.wishapp

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

const val EXTRA_MESSAGE = "com.example.wishapp.MESSAGE"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db: SQLiteDatabase = openOrCreateDatabase("WishDB", Context.MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS Wish (Name VARCHAR, Description VARCHAR);")
        db.execSQL("INSERT INTO Wish VALUES ('Konsola', 'Konsola PS4 i dwa pady');")
        db.execSQL("INSERT INTO Wish VALUES ('PC Master race', 'Komputer i wiecej rdzeniow');")
        val cursor: Cursor = db.rawQuery("SELECT * FROM Wish", null)
        cursor.moveToFirst()
        db.close()

    }

    fun newWish(view: View) {
        val newWishActivity: Intent = Intent(applicationContext, NewWishActivity::class.java)
        startActivity(newWishActivity)
    }

}
