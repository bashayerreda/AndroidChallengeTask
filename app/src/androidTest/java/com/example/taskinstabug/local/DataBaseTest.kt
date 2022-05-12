package com.example.taskinstabug.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.instabugtask.pojo.WordsModel
import com.example.taskinstabug.services.LoadingData
import com.example.taskinstabug.services.local.DataBaseQuery
import com.example.taskinstabug.services.remote.WordRemoteDataSource
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class DataBaseTest {
    var dbQuery: DataBaseQuery = DataBaseQuery(InstrumentationRegistry.getInstrumentation().targetContext)

    @Before
    fun setUp() {
        dbQuery.open()

    }

    @After
    fun finish() {
        dbQuery.close()
    }

    @Test
    fun testPreConditions() {
        assertNotNull(dbQuery.open())
    }



    @Test
    fun testDeleteAllData() {
        dbQuery.deleteAllWords()
        val wordList: List<WordsModel> = dbQuery.getWords()
        assertThat(wordList.size, `is`(0))
    }



    @Test
    fun testAssertData(){
        dbQuery.deleteAllWords()
        dbQuery.saveWord(WordsModel(name = "bashayer", quantity = 1))
        dbQuery.saveWord(WordsModel(name = "abdelrahman", quantity = 2))
        dbQuery.saveWord(WordsModel(name = "reda", quantity = 10))
        val list: List<WordsModel> = dbQuery.getWords()
        assertTrue(list.size == 3)
        assertTrue("bashayer" == list[0].name)
        assertTrue(list[2].quantity == 10)

    }

    @Test
    @Throws(Exception::class)
    fun testShouldAddOneItem() {
        dbQuery.deleteAllWords()
        dbQuery.saveWord(WordsModel(name = "bashayer", quantity = 10))
        val word: List<WordsModel> = dbQuery.getWords()
        assertThat(word.size, `is`(1))
        assertTrue(word[0].name == "bashayer")
        assertTrue(word[0].quantity == 10)
    }

    }

