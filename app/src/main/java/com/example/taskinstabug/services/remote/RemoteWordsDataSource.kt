package com.example.taskinstabug.services.remote

import com.example.taskinstabug.services.LoadingData

import com.example.taskinstabug.ui.SearchFragmentViewModel

interface RemoteWordsDataSource {

    fun getWords(callback: LoadingData)


}