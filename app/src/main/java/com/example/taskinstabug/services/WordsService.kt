package com.example.taskinstabug.services

import com.example.instabugtask.pojo.WordsModel

interface LoadingData {
        fun onLoaded(words: MutableList<WordsModel>?)
        fun onNotAvailable()
        fun onError()
    }


