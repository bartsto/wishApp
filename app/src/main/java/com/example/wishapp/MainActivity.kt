package com.example.wishapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_MESSAGE = "com.example.wishapp.MESSAGE"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewWishList()
    }

    override fun onResume() {
        super.onResume()
        viewWishList()
    }

    fun newWish(view: View) {
        val newWishActivity: Intent = Intent(applicationContext, NewWishActivity::class.java)
        startActivity(newWishActivity)
    }

    fun viewWishList() {
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val wishList: List<Wish> = databaseHandler.getWishList()
        val wishArrayId = Array<String>(wishList.size){"0"}
        val wishArrayName = Array<String>(wishList.size) { "null" }
        var index = 0
        for (w in wishList) {
            wishArrayId[index] = w.wishId.toString()
            wishArrayName[index] = w.name
            index++
        }
        val myListAdapter = MyListAdapter(this, wishArrayId, wishArrayName, wishList)
        listView.adapter = myListAdapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedWish: Int = wishList.get(position).wishId
            val detailIntent = Intent(this, WishDetailActivity::class.java).apply {
                putExtra("wishId", selectedWish)
            }
            startActivity(detailIntent)
        }
    }
}