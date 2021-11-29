package com.neverbenull.cleangithubbrowser.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.neverbenull.cleangithubbrowser.data.mapper.RepoMapper
import com.neverbenull.cleangithubbrowser.data.remote.api.GithubService
import com.neverbenull.cleangithubbrowser.domain.model.RepoModel
import retrofit2.HttpException
import java.io.IOException

class GithubPagingSource(
    private val githubService: GithubService,
    private val query: String,
    private val sort: String
): PagingSource<Int, RepoModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoModel> {
        return try {
            val nextPage = params.key ?: 1

            val response = githubService.searchRepositories(
                query = query,
                sort = sort,
                page = nextPage
            )

            LoadResult.Page(
                data = response.items.map { RepoMapper.toDomainModel(it) },
                prevKey = if(nextPage == 1) null else nextPage - 1,
                nextKey = nextPage.plus(1)
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepoModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}