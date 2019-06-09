package com.example.wishapp.model

class Category{
    /**
     * Category model of wish:
     * @param name - name of category wish
     * @param description - description of wish
     */
    var name: String = ""
    var description: String = ""

    constructor(_name: String, _description: String) {
        name = _name;
        description = _description;
    }
}