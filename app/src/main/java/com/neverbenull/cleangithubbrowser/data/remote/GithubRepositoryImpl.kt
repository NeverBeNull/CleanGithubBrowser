package com.neverbenull.cleangithubbrowser.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.neverbenull.cleangithubbrowser.data.remote.api.GithubService
import com.neverbenull.cleangithubbrowser.data.remote.paging.GithubPagingSource
import com.neverbenull.cleangithubbrowser.domain.model.RepoModel
import com.neverbenull.cleangithubbrowser.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow

class GithubRepositoryImpl(
    private val githubService: GithubService
) : GithubRepository {

    override fun searchRepositories(
        query: String,
        sort: String,
        pageSize: Int
    ): Flow<PagingData<RepoModel>> {
        return Pager(
            config = PagingConfig(pageSize)
        ) {
            GithubPagingSource(
                githubService = githubService,
                query = query,
                sort = sort
            )
        }.flow
    }

}