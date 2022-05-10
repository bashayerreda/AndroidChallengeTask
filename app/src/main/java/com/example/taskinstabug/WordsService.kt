package com.example.instabugtask

import com.example.instabugtask.pojo.WordsModel

interface WordsService {
    interface LoadingData {
        fun onLoaded(words: MutableList<WordsModel>?)
        fun onNotAvailable()
        fun onError()
    }


}
