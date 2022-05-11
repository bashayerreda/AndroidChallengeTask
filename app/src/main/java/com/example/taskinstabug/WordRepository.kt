package com.example.taskinstabug

import com.example.instabugtask.pojo.WordsModel
import com.example.taskinstabug.services.LoadingData

interface WordRepository {
    fun getWords(callback: LoadingData)
    fun saveWords(words: List<WordsModel>?)
}