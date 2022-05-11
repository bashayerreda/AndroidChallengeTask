package com.example.taskinstabug.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskinstabug.services.remote.WordsRemoteRepo
import com.example.taskinstabug.services.WordsService
import com.example.instabugtask.pojo.WordsModel

class SearchFragmentViewModel  : ViewModel() {
    val wordsRemoteRepo: WordsRemoteRepo? = WordsRemoteRepo.getInstance()
    val wordsLiveData = MutableLiveData<MutableList<WordsModel>?>()
    val errorMessages = MutableLiveData<String?>()
    var loadingLiveData = MutableLiveData<Boolean>()
    val onlineLiveData = MutableLiveData(true)
    private val wordCallback = WordCallback()
    var sortBy: String = "DESC"



    fun loadData(keyword: String = "") {
        setIsLoading(true)
        wordsRemoteRepo?.getWords(wordCallback, keyword, sortBy)

    }

   inner class WordCallback : WordsService.LoadingData {
        override fun onLoaded(words: MutableList<WordsModel>?) {
            setIsLoading(false)
            wordsLiveData.postValue(words)
        }

       override fun onError() {
            setIsLoading(false)
            errorMessages.postValue("something went Wrong")
        }
       override fun onNotAvailable() {

       }
    }

    fun setIsLoading(loading: Boolean) {
        loadingLiveData.postValue(loading)
    }


}