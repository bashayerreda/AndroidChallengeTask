package com.example.taskinstabug.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.instabugtask.pojo.WordsModel
import com.example.instabugtask.utils.parseHTML
import com.example.instabugtask.utils.splitBySpace
import com.example.taskinstabug.services.LoadingData
import com.example.taskinstabug.services.remote.WordRemoteDataSource
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class RemoteDataSourceTest {
    @Test
    fun testRemoteRepo(){
        val remoteDataSource = WordRemoteDataSource.getInstance()
        remoteDataSource?.getWords(object : LoadingData {
            override fun onLoaded(words: MutableList<WordsModel>?) {
                Assert.assertTrue(words!!.isNotEmpty())
            }

            override fun onNotAvailable() {

            }

            override fun onError() {

            }


        })
    }



}