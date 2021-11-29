package com.neverbenull.cleangithubbrowser.data.remote.api

import com.neverbenull.cleangithubbrowser.data.remote.api.adapter.ApiResponse
import com.neverbenull.cleangithubbrowser.data.remote.response.search.repositories.SearchRepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    /**
     * https://docs.github.com/en/rest/reference/search#search-repositories
     *
     * @param query The query contains one or more search keywords and qualifiers.
     *              Qualifiers allow you to limit your search to specific areas of GitHub.
     *              The REST API supports the same qualifiers as GitHub.com.
     * @param sort Sorts the results of your query by number of stars, forks,
     *             or help-wanted-issues or how recently the items were updated.
     * @param order Determines whether the first search result returned is the highest number of
     *              matches (desc) or lowest number of matches (asc).
     *              This parameter is ignored unless you provide sort.
     * @param itemsPerPage Results per page (max 100)
     * @param page Page number of the results to fetch.
     * @return
     */
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "best match",
        @Query("order") order: String = "desc",
        @Query("per_page") itemsPerPage: Int = 30,
        @Query("page") page: Int = 1
    ): ApiResponse<SearchRepositoriesResponse>

}