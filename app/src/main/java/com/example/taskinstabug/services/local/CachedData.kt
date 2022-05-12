package com.example.taskinstabug.services.local

import com.example.instabugtask.pojo.WordsModel
import com.example.taskinstabug.WordRepository
import com.example.taskinstabug.services.LoadingData
import com.example.taskinstabug.services.remote.RemoteWordsDataSource
import com.example.taskinstabug.services.remote.WordRemoteDataSource

class CachedData(
    wordRemote: WordRemoteDataSource?,
    wordLocal: WordLocalDataSource?
) : WordRepository {
    private val remoteWordsDataSource: RemoteWordsDataSource?
    private val localDataSource: LocalDataSource?

    override fun getWords(callback: LoadingData) {

        localDataSource!!.getWords(object : LoadingData {
            override fun onLoaded(words: MutableList<WordsModel>?) {
                callback.onLoaded(words)
            }

            override fun onNotAvailable() {
                getDataFromRemoteDataSource(callback)
            }
            override fun onError() {

            }
        })
    }

    fun getDataFromRemoteDataSource(callback: LoadingData) {
        remoteWordsDataSource!!.getWords(object : LoadingData {
            override fun onLoaded(words: MutableList<WordsModel>?) {
                saveWords(words)
                callback.onLoaded(words?.sortedBy { it.quantity } as MutableList<WordsModel>?)
            }

            override fun onNotAvailable() {
                callback.onNotAvailable()
            }

            override fun onError() {

            }


        })



    }

    companion object {
        private var instance: CachedData? = null
        fun getInstance(
            wordRemote: WordRemoteDataSource?,
            wordLocal: WordLocalDataSource?
        ): CachedData? {
            if (instance == null) {
                instance = CachedData(wordRemote, wordLocal)
            }
            return instance
        }
    }

    override fun saveWords(words: List<WordsModel>?) {
        localDataSource?.saveWords(words)


    }


    init {
        this.remoteWordsDataSource = wordRemote
        this.localDataSource = wordLocal
    }

}