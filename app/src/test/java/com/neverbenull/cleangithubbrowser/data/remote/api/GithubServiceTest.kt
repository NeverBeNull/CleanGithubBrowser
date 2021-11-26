package com.neverbenull.cleangithubbrowser.data.remote.api

import com.neverbenull.cleangithubbrowser.base.di.NetworkModuleFactory
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GithubServiceTest {

    @Test
    fun `search repositories`() = runBlocking {
        val response = NetworkModuleFactory.githubService.searchRepositories(
            query = "CleanGithubBrowser"
        )

        println(response)
    }

    @Test
    fun `search repositories no result`() = runBlocking {
        val response = NetworkModuleFactory.githubService.searchRepositories(
            query = "CleanGithubBrowserNoResult"
        )

        println(response)
    }

}