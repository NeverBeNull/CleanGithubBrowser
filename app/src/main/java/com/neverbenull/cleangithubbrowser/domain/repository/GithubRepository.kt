package com.neverbenull.cleangithubbrowser.domain.repository

import androidx.paging.PagingData
import com.neverbenull.cleangithubbrowser.domain.model.RepoModel
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    fun searchRepositories(
        query: String,
        sort: String = "best match",
        pageSize: Int = 1
    ) : Flow<PagingData<RepoModel>>

}