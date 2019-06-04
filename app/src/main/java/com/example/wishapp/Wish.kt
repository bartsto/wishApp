package com.example.wishapp

class Wish {
    var name: String = ""
    var description: String = ""
    var category: Category = Category("", "")
    var imagePath: String = ""

    constructor(_name: String, _description: String, _category: Category, _imagePath: String) {
        name = _name
        description = _description
        category = _category
        imagePath = _imagePath
    }
}