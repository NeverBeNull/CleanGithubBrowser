package com.neverbenull.cleangithubbrowser.data.remote.response.search.repositories


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchRepositoriesResponse(
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean?,
    @Json(name = "items")
    val items: List<Repository>?,
    @Json(name = "total_count")
    val totalCount: Int?
)