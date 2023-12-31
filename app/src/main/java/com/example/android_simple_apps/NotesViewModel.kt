package com.example.android_simple_apps

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.lifecycle.ViewModel
import java.security.AccessController.getContext

class NotesDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE Notes(id INTEGER PRIMARY KEY, title TEXT, body TEXT)")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Notes")
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object { const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "SimpleApps.db"
    }
}

class NotesViewModel : ViewModel() {
    private lateinit var db : SQLiteDatabase
    fun startup (context: Context) {
        db = NotesDbHelper(context).writableDatabase
    }
    fun create(title : String, body : String)
    {
        val values = ContentValues().apply {
            put("title", title)
            put("body", body)
        }
        db?.insert("Notes", null, values)
    }
    fun selectAll() : Array<Array<String>>
    {
        var outputArray = arrayOf<Array<String>>()
        val cursor = db?.rawQuery("SELECT * FROM notes", null)
        if (cursor != null) {
            while(cursor.moveToNext())
                outputArray += arrayOf( cursor?.getString(0).toString(),
                                        cursor?.getString(1).toString(),
                                        cursor?.getString(2).toString()  )
        }
        return outputArray
    }
    fun selectOne(id: String) : Array<String>
    {
        var outputArray = arrayOf<String>()
        var cursor = db?.rawQuery("SELECT * FROM notes WHERE id=${id}", null)
        if (cursor != null)
        {
            if (cursor.moveToNext())
                outputArray = arrayOf( cursor?.getString(0).toString(),
                    cursor?.getString(1).toString(),
                    cursor?.getString(2).toString()  )
        }
        return outputArray
    }
    fun update(id: String, title: String, body: String){
        val values = ContentValues().apply {
            put("title", title)
            put("body", body)
        }
        db?.update("notes", values, "id=?", arrayOf(id))
    }
    fun delete(id : String) {
        db?.delete("notes", "id=${id}", null)
    }
}