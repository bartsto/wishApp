package com.example.wishapp

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.TextView

const val EXTRA_MESSAGE = "com.example.wishapp.MESSAGE"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv = findViewById<TextView>(R.id.textView3)

        val db: SQLiteDatabase = openOrCreateDatabase("WishDB", Context.MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS Wish (Name VARCHAR, Description VARCHAR);")
        db.execSQL("INSERT INTO Wish VALUES ('Konsola', 'Konsola PS4 i dwa pady');")
        db.execSQL("INSERT INTO Wish VALUES ('PC Master race', 'Komputer i wiecej rdzeniow');")
        val cursor: Cursor = db.rawQuery("SELECT * FROM Wish", null)
        cursor.moveToFirst()
        tv.text = cursor.getString(cursor.getColumnIndex("Name"))
        db.close()

    }

    fun sendMessage(view: View) {
        val editText = findViewById<EditText>(R.id.editText)
        val message = editText.text.toString()
        val intent = Intent(this, SecondActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

}
