package com.example.wishapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyListAdapter(private val context: Activity, private val name: Array<String>)
    : ArrayAdapter<String>(context, R.layout.wish_list, name) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.wish_list, null, true)

        val nameText = rowView.findViewById(R.id.textViewName) as TextView
        nameText.text = "${name[position]}"

        return rowView
    }
}