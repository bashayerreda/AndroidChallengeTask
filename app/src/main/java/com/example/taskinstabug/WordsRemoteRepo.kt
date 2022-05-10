package com.example.instabugtask

import android.util.Log
import com.example.instabugtask.pojo.WordsModel
import com.example.instabugtask.utils.*
import com.example.instabugtask.WordsService.LoadingData
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor

class WordsRemoteRepo constructor(private val executor: Executor) :
    RemoteWordsDataSource {

    override fun getWords(callback: LoadingData, keyword: String, sortBy: String) {
        val runnable = Runnable {

            var result: String
            val url = URL(BASE_URL)

            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            try {
                urlConnection.doOutput = true
                urlConnection.setChunkedStreamingMode(0)

                val inputStream: InputStream = BufferedInputStream(urlConnection.inputStream)
                result = inputStream.readStream()

            }catch (e: Exception){
                result = ""
                callback.onError()
            } finally {
                urlConnection.disconnect()
            }

            val list: MutableList<WordsModel> = result.parseHTML().splitBySpace().fetchWords()
            list.sortedByDescending { it.quantity }


            if (list.isNotEmpty()) {
                callback.onLoaded(list)
            } else {
                callback.onNotAvailable()
            }
        }
        executor.execute(runnable)
    }

    companion object {
        private var instance: WordsRemoteRepo? = null
        fun getInstance(): WordsRemoteRepo? {
            if (instance == null) {
                instance = WordsRemoteRepo(ExecutorPool())
            }
            return instance
        }
    }
}