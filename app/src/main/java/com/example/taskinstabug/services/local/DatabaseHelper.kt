package com.example.taskinstabug.services.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object{
        const val DATABASE_NAME = "CachedData.db"
        const val DATABASE_VERSION = 2
        const val TABLE_WORD = "word_table"
        const val COL_ID = "id"
        const val COL_NAME ="name"
        const val COL_QUANTITY ="quantity"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_WORD (" +
                    "$COL_ID INTEGER PRIMARY KEY," +
                    "$COL_NAME TEXT," +
                    "$COL_QUANTITY TEXT)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_WORD"
    }




}