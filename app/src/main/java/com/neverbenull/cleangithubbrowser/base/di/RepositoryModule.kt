package com.neverbenull.cleangithubbrowser.base.di

import com.neverbenull.cleangithubbrowser.data.remote.GithubRepositoryImpl
import com.neverbenull.cleangithubbrowser.data.remote.api.GithubService
import com.neverbenull.cleangithubbrowser.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideGithubService(
        retrofit: Retrofit
    ) : GithubService {
        return retrofit.create(GithubService::class.java)
    }

    @Singleton
    @Provides
    fun provideGithubRepository(
        githubService: GithubService
    ) : GithubRepository {
        return GithubRepositoryImpl(githubService)
    }

}