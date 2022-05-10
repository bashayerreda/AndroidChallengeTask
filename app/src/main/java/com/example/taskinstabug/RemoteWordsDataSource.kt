package com.example.instabugtask

interface RemoteWordsDataSource {

    fun getWords(callback: WordsService.LoadingData, keyword: String, sortBy: String)


}