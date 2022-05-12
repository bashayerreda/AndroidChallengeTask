package com.example.taskinstabug

import androidx.test.filters.SmallTest
import com.example.instabugtask.utils.parseHTML
import com.example.instabugtask.utils.splitBySpace
import org.junit.Assert
import org.junit.Test


@SmallTest
class SplitWordsTest {
    @Test
    fun testSpliteWord(){
        val html = "<body><p>bashayer reda !</p></body>"
        val wordList = html.parseHTML().splitBySpace()
        Assert.assertEquals("bashayer", wordList[0])
        Assert.assertEquals("reda", wordList[1])

        Assert.assertTrue(!wordList.contains("!"))

    }
}