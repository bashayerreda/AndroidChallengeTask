package com.example.taskinstabug.services.local

import android.util.Log
import com.example.instabugtask.pojo.WordsModel
import com.example.instabugtask.utils.ExecutorPool
import com.example.taskinstabug.WordRepository
import com.example.taskinstabug.services.LoadingData
import com.example.taskinstabug.services.remote.RemoteWordsDataSource
import com.example.taskinstabug.services.remote.WordRemoteDataSource

class CachedData private constructor(
    wordRemote: WordRemoteDataSource?,
    wordLocal: WordLocalDataSource?
) : WordRepository {
    private val wordRemote: RemoteWordsDataSource?
    private val wordLocal: LocalDataSource?



    override fun getWords(callback: LoadingData) {
        if (callback == null) return
        wordLocal!!.getWords(object : LoadingData {
            override fun onLoaded(words: MutableList<WordsModel>?) {
                Log.v("get local", "From Local: $words")
                callback.onLoaded(words)
            }

            override fun onNotAvailable() {
                getDataFromRemoteDataSource(callback)
            }
            override fun onError() {

            }
        })
    }

    private fun getDataFromRemoteDataSource(callback: LoadingData) {
        wordRemote!!.getWords(object : LoadingData {
            override fun onLoaded(words: MutableList<WordsModel>?) {
                saveWords(words)
                Log.v("tsts", "From Remote Into Local: $words")
                callback.onLoaded(words?.sortedBy { it.quantity } as MutableList<WordsModel>?)
            }

            override fun onNotAvailable() {
                callback.onNotAvailable()
            }

            override fun onError() {

            }


        })



    }



    override fun saveWords(words: List<WordsModel>?) {
        wordLocal!!.saveWords(words)

    }


    init {
        this.wordRemote = wordRemote
        this.wordLocal = wordLocal
    }

}