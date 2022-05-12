package com.example.taskinstabug.services.local

import android.content.Context
import com.example.instabugtask.pojo.WordsModel
import com.example.instabugtask.utils.ExecutorPool
import com.example.taskinstabug.services.LoadingData
import java.util.concurrent.Executor

class WordLocalDataSource constructor(
private val executor: Executor,
private val query: DataBaseQuery
) : LocalDataSource {
    override fun getWords(callback: LoadingData) {
        val runnable = Runnable {
            val words = query.getWords()
            if (words.isNotEmpty()) {
                callback.onLoaded(words)
            } else {
                callback.onNotAvailable()
            }
        }
        executor.execute(runnable)
    }

    override fun saveWords(words: List<WordsModel>?) {
        val runnable = Runnable {
            query?.saveWords(words!!) }
        executor.execute(runnable)
    }

    companion object {
        private var instance: WordLocalDataSource? = null
        fun getInstance(context: Context): WordLocalDataSource? {
            if (instance == null) {
                instance = WordLocalDataSource(ExecutorPool(), DataBaseQuery(context))
            }
            return instance
        }
    }
}