package com.example.wishapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "WishDB"
        private val TABLE_WISH = "WishTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_DESCRIPTION = "description"
        private val KEY_IMAGEPATH = "imagePath"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_WISH)
        val CREATE_WISH_TABLE = ("CREATE TABLE " + TABLE_WISH + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_DESCRIPTION + " TEXT,"
                + KEY_IMAGEPATH + " TEXT" + ")")
        db?.execSQL(CREATE_WISH_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_WISH)
        onCreate(db)
    }

    fun addWish(wish: Wish): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, wish.wishId)
        contentValues.put(KEY_NAME, wish.name)
        contentValues.put(KEY_DESCRIPTION, wish.description)
        contentValues.put(KEY_IMAGEPATH, wish.imagePath)
        val success = db.insert(TABLE_WISH, null, contentValues)
        db.close()
        return success
    }

    fun getWishList(): List<Wish> {
        val wishList: ArrayList<Wish> = ArrayList<Wish>()
        val selectQuery = "SELECT  * FROM $TABLE_WISH"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var wishId: Int
        var wishName: String
        var wishDescription: String
        var wishImage: String
        if (cursor.moveToFirst()) {
            do {
                wishId = cursor.getInt(cursor.getColumnIndex("id"))
                wishName = cursor.getString(cursor.getColumnIndex("name"))
                wishDescription = cursor.getString(cursor.getColumnIndex("description"))
                wishImage = cursor.getString(cursor.getColumnIndex("name"))
                val wish = Wish(wishId = wishId, name = wishName, description = wishDescription, imagePath = wishImage)
                wishList.add(wish)
            } while (cursor.moveToNext())
        }
        return wishList
    }
}