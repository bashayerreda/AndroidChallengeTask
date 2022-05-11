package com.example.taskinstabug.services.remote

import com.example.taskinstabug.services.WordsService

interface RemoteWordsDataSource {

    fun getWords(callback: WordsService.LoadingData, keyword: String, sortBy: String)


}