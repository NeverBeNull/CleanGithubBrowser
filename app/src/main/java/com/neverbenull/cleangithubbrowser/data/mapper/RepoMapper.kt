package com.neverbenull.cleangithubbrowser.data.mapper

import com.neverbenull.cleangithubbrowser.base.extension.toDateWithTimeZone
import com.neverbenull.cleangithubbrowser.data.remote.response.search.repositories.RepoJson
import com.neverbenull.cleangithubbrowser.domain.model.RepoModel

object RepoMapper : BaseMapper<RepoModel, RepoJson>() {

    override fun toDomainModel(dataModel: RepoJson): RepoModel {
        return with(dataModel) {
            RepoModel(
                id = id,
                name = name,
                fullName = fullName,
                language = language,
                description = description ?: "",
                updatedAt = updatedAt.toDateWithTimeZone(),
                stars = stargazersCount ?: 0,
                forks = forks ?: 0,
                license = license?.name
            )
        }
    }

    override fun toDataModel(domainModel: RepoModel): RepoJson {
        TODO("Not yet implemented")
    }

}