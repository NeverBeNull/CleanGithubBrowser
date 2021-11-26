package com.neverbenull.cleangithubbrowser.base.extension

import kotlinx.coroutines.runBlocking
import org.junit.Test

class StringExtTest {

    @Test
    fun `updatedAt string to date`() = runBlocking {
        val updatedAt = "2021-11-26T06:35:11Z"
        println(updatedAt.toDateWithTimeZone())
    }

}