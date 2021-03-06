package com.example.wishapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.wishapp.adapter.MyListAdapter
import com.example.wishapp.R
import com.example.wishapp.db.DatabaseHandler
import com.example.wishapp.model.Wish
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    /**
     * The main activity of project
     * This activity has displayed list of wishes end button to create a new one
     * @param savedInstanceState
     */

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
        val wishArrayIcon = Array<String>(wishList.size){"null"}
        val wishArrayName = Array<String>(wishList.size) { "null" }
        var index = 0
        for (w in wishList) {
            wishArrayIcon[index] = w.imagePath.toString()
            wishArrayName[index] = w.name
            index++
        }
        val myListAdapter = MyListAdapter(this, wishArrayIcon, wishArrayName, wishList)
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