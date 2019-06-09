package com.example.wishapp

import android.app.Activity
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyListAdapter(private val context: Activity, private val imagePath: Array<String>, private val name: Array<String>, private val wishList:List<Wish>)
    : ArrayAdapter<String>(context, R.layout.wish_list, name) {

        override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.wish_list, null, true)

        val icon = rowView.findViewById(R.id.wish_list_thumbnail) as ImageView
        val nameText = rowView.findViewById(R.id.textViewName) as TextView
        icon.setImageURI(Uri.parse(Uri.decode(imagePath[position])))
        nameText.text = "${name[position]}"

        return rowView
    }

    override fun getCount(): Int {
        return wishList.size
    }
}