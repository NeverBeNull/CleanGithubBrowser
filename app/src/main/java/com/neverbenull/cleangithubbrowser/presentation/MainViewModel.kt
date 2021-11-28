package com.neverbenull.cleangithubbrowser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.neverbenull.cleangithubbrowser.domain.model.RepoModel
import com.neverbenull.cleangithubbrowser.domain.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    @Inject lateinit var githubRepository: GithubRepository

    fun searchRepository() : Flow<PagingData<RepoModel>> {
        return githubRepository.searchRepositories("android/architectures")
            .cachedIn(viewModelScope)
    }
}