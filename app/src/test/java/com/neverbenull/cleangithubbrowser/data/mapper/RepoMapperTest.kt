package com.neverbenull.cleangithubbrowser.data.mapper

import com.neverbenull.cleangithubbrowser.base.di.NetworkModuleFactory
import com.neverbenull.cleangithubbrowser.data.remote.api.adapter.ApiResponse
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RepoMapperTest {

    @Test
    fun `repository json to domain model`() = runBlocking {
        val response = NetworkModuleFactory.githubService.searchRepositories(
            query = "CleanGithubBrowser"
        )

        when(response) {
            is ApiResponse.ApiSuccessResponse -> {
                response.body.items[0].let { json ->
                    val repositoryModel = RepoMapper.toDomainModel(json)
                    println(repositoryModel)
                }
            }
            is ApiResponse.ApiErrorResponse -> {
                println(response.errorMessage)
            }
            is ApiResponse.ApiEmptyResponse -> {
                println("empty response")
            }
        }
    }

}