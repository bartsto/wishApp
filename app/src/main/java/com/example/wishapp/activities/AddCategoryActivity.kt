package com.example.wishapp.activities

import android.os.Bundle
import android.view.View
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.example.wishapp.R

class AddCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_second)
    }

    fun addCategory(view: View) {
        val nameEditText = findViewById<EditText>(R.id.editText4)
        val name = nameEditText.text.toString()
        val descriptionEditText = findViewById<EditText>(R.id.editText5)
        val description = descriptionEditText.text.toString()
        // add category to db
    }
}