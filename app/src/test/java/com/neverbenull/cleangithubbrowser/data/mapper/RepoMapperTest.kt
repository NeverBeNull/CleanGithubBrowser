package com.neverbenull.cleangithubbrowser.data.mapper

import com.neverbenull.cleangithubbrowser.base.di.NetworkModuleFactory
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RepoMapperTest {

    @Test
    fun `repository json to domain model`() = runBlocking {
        val response = NetworkModuleFactory.githubService.searchRepositories(
            query = "CleanGithubBrowser"
        )

        response.items?.get(0).let { json ->
            val repositoryModel = RepoMapper.toDomainModel(json!!)
            println(repositoryModel)
        }
    }

}