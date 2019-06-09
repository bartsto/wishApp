package com.example.wishapp.model

/**
 * Wish class with name, description.
 * @param wishId id wish in database
 * @param name name of wish
 * @param description description of wish
 * @param imagePath local path to picture of wish in storage
 * @param urlImage url address of image
 *
 */

class Wish (
    var wishId: Int,
    var name: String,
    var description: String,
//    var category: Category = Category("", "")
    var imagePath: String?,
    var urlImage: String?)