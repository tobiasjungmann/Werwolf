package com.example.tobias.werwolf_v1.database.models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by Tobias on 19.01.2018.
 */
@Deprecated("Replaced by new Database")
class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    init {
        val db = this.writableDatabase
    }

    override fun onCreate(db: SQLiteDatabase) {
        val tabelle =
            "CREATE TABLE " + TABLE_NAME + "(" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 + " TEXT, " + COL_5 + " TEXT)"
        db.execSQL(tabelle)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addData(item: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, item)
        contentValues.put(COL_5, "nein")
        Log.d(ContentValues.TAG, "Eingefügt: " + item + "   in: " + TABLE_NAME)
        val result = db.insert(TABLE_NAME, null, contentValues)
        return result != -1L
    }

    fun addCharakter(name: String, charakter: String?) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, charakter)
        contentValues.put(COL_5, "nein")
        Log.d(ContentValues.TAG, "Eingefügt: " + name + "   in: " + TABLE_NAME)
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun addVerzaubert(item: String, charakter: String?) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, item)
        contentValues.put(COL_3, charakter)
        contentValues.put(COL_5, "ja")
        Log.d(ContentValues.TAG, "Eingefügt: " + item + "   in: " + TABLE_NAME)
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun addjungesExtra(item: String, verzaubert: String?) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, item)
        contentValues.put(COL_3, "werwolf")
        contentValues.put(COL_5, verzaubert)
        //contentValues.put(COL_4, "1");
        Log.d(ContentValues.TAG, "Eingefügt: " + item + "   in: " + TABLE_NAME)
        db.insert(TABLE_NAME, null, contentValues)
    }

    val data: Cursor
        get() {
            val db = this.writableDatabase
            val query = "SELECT * FROM " + TABLE_NAME
            return db.rawQuery(query, null)
        }

    fun getItemID(name: String): Cursor {
        val db = this.writableDatabase
        val query =
            "SELECT " + COL_1 + " FROM " + TABLE_NAME + " WHERE " + COL_2 + " = '" + name + "'"
        return db.rawQuery(query, null)
    }

    fun deleteName(id: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "ID = ?", arrayOf(id))
    }

    fun deleteOnlyName(name: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "NAME = ?", arrayOf(name))
    }

    fun clearDatabase() {
        val db = this.writableDatabase
        val clearDBQuery = "DELETE FROM " + TABLE_NAME
        db.execSQL(clearDBQuery)
    }

    companion object {
        const val DATABASE_NAME = "Werwolf.db"
        const val TABLE_NAME = "werwolf_tabelle"
        const val COL_1 = "ID"
        const val COL_2 = "NAME"
        const val COL_3 = "CHARAKTER"
        const val COL_4 = "LEBENDIG"
        const val COL_5 = "VERZAUBERT"
    }
}