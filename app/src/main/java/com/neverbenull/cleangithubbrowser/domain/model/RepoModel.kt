package com.neverbenull.cleangithubbrowser.domain.model

import java.util.*

data class RepoModel(
    val id: Int,
    val name: String,
    val fullName: String,
    val language: String,
    val description: String,
    val updatedAt: Date?,
    val stars: Int,
    val forks: Int,
    val license: String?
)