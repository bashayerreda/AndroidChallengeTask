package com.example.taskinstabug.services.local

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.instabugtask.pojo.WordsModel

class DataBaseQuery(context: Context) {

    private val dbHelper = DatabaseHelper(context)
    @Throws(SQLException::class)
    fun open() : SQLiteDatabase {
        return dbHelper.writableDatabase
    }




    fun getWords(): ArrayList<WordsModel>{
       var keyword: String = ""
        val mDatabase = dbHelper.readableDatabase
        val contentList: ArrayList<WordsModel> = ArrayList()
        val selectionQuery = if(keyword.isEmpty()){
            " "
        }else{
            " WHERE ${DatabaseHelper.COL_NAME} LIKE '%$keyword%' "
        }
        val selectQuery = "SELECT * FROM ${DatabaseHelper.TABLE_WORD}$selectionQuery order by CAST(${DatabaseHelper.COL_QUANTITY} AS INTEGER )"



        val cursor = mDatabase.rawQuery(selectQuery, null)
        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(DatabaseHelper.COL_ID))
                val itemName = getString(getColumnIndexOrThrow(DatabaseHelper.COL_NAME))
                val itemQuantity = getInt(getColumnIndexOrThrow(DatabaseHelper.COL_QUANTITY))
                contentList.add(WordsModel(itemId, itemName, itemQuantity))
            }
        }
        cursor.close()
        close()
        return contentList
    }

    fun deleteAllWords(){
        open().delete(DatabaseHelper.TABLE_WORD, null, null)
    }

    fun saveWords(list: List<WordsModel>){
        val mDatabase = dbHelper.writableDatabase
        mDatabase.beginTransaction()
        try {
            deleteAllWords()
            val values = ContentValues()
            for (word in list) {
                values.put(DatabaseHelper.COL_NAME, word.name )
                values.put(DatabaseHelper.COL_QUANTITY, word.quantity)
                mDatabase.insert(DatabaseHelper.TABLE_WORD, null, values)
            }
            mDatabase.setTransactionSuccessful()
        } finally {
            mDatabase.endTransaction()
            close()
        }
    }

    fun saveWord(model: WordsModel): Long{
        val db = open()

        val contentValues = ContentValues()

        contentValues.put(DatabaseHelper.COL_NAME, model.name)
        contentValues.put(DatabaseHelper.COL_QUANTITY, model.quantity)

        val success = db.insert(DatabaseHelper.TABLE_WORD, null, contentValues)
        close()

        return success
    }

    fun close(){
        dbHelper.close()
    }
}