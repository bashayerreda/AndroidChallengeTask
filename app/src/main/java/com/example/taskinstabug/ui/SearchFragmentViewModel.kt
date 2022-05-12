package com.example.taskinstabug.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskinstabug.services.remote.WordRemoteDataSource
import com.example.instabugtask.pojo.WordsModel
import com.example.taskinstabug.services.LoadingData
import com.example.taskinstabug.services.local.CachedData
import com.example.taskinstabug.services.local.WordLocalDataSource


class SearchFragmentViewModel : ViewModel() {
    val wordRemoteDataSource: WordRemoteDataSource = WordRemoteDataSource.getInstance()!!
    val wordsLiveData = MutableLiveData<MutableList<WordsModel>?>()
    val errorMessages = MutableLiveData<String?>()
    var loadingLiveData = MutableLiveData<Boolean>()
    private val wordCallback = WordCallback()
    var context: Context? = null
    var cachedData: CachedData? = null

    fun loadData(context: Context) {
        this.context = context
        this.cachedData = CachedData(wordRemoteDataSource, WordLocalDataSource.getInstance(context))
        setIsLoading(true)
        cachedData?.getWords(wordCallback)

    }

    inner class WordCallback : LoadingData {
        override fun onLoaded(words: MutableList<WordsModel>?) {
            setIsLoading(false)
            wordsLiveData.postValue(words)
            cachedData?.saveWords(words)
        }

        override fun onNotAvailable() {

        }


        override fun onError() {
            setIsLoading(false)
            errorMessages.postValue("something went Wrong")
        }
    }

    fun setIsLoading(loading: Boolean) {
        loadingLiveData.postValue(loading)
    }

}