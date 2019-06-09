package com.example.wishapp.model

/**
 * Wish class with name, description.
 * @param takrka
 */

class Wish (
    var wishId: Int,
    var name: String,
    var description: String,
//    var category: Category = Category("", "")
    var imagePath: String?,
    var urlImage: String?)