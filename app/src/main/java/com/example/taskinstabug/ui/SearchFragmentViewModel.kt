package com.example.taskinstabug.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskinstabug.services.remote.WordRemoteDataSource
import com.example.instabugtask.pojo.WordsModel
import com.example.taskinstabug.services.LoadingData


class SearchFragmentViewModel : ViewModel() {
    val wordRemoteDataSource: WordRemoteDataSource = WordRemoteDataSource.getInstance()!!
    val wordsLiveData = MutableLiveData<MutableList<WordsModel>?>()
    val errorMessages = MutableLiveData<String?>()
    var loadingLiveData = MutableLiveData<Boolean>()
    val onlineLiveData = MutableLiveData(true)
    private val wordCallback = WordCallback()
    var sortBy: String = "DESC"

    fun loadData() {
        setIsLoading(true)
        wordRemoteDataSource.getWords(wordCallback)

    }

    inner class WordCallback : LoadingData {
        override fun onLoaded(words: MutableList<WordsModel>?) {
            setIsLoading(false)
            wordsLiveData.postValue(words as MutableList<WordsModel>?)
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