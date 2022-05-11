package com.example.taskinstabug.services.local

import com.example.instabugtask.pojo.WordsModel
import com.example.taskinstabug.services.LoadingData


interface LocalDataSource {
    fun saveWords(words: List<WordsModel>?)
    fun getWords(callback: LoadingData)
}