package com.neverbenull.cleangithubbrowser.base.di

import com.neverbenull.cleangithubbrowser.data.remote.api.GithubService

object NetworkModuleFactory {

    val githubService : GithubService
        get() = provideGithubService()

    private fun provideOkHttpClient() = NetworkModule.provideOkHttpClient()

    private fun provideRetrofit() = NetworkModule.provideRetrofit(provideOkHttpClient())

    private fun provideGithubService() = RepositoryModule.provideGithubService(provideRetrofit())

}