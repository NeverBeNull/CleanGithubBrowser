package com.neverbenull.cleangithubbrowser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.neverbenull.cleangithubbrowser.domain.model.RepoModel
import com.neverbenull.cleangithubbrowser.domain.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    @Inject lateinit var githubRepository: GithubRepository

    private val _search = MutableStateFlow("android/architecture")
    val search: StateFlow<String> = _search

    val repos: Flow<PagingData<RepoModel>> = search
        .filter { it.isNotEmpty() }
        .flatMapLatest { query -> searchRepository(query) }
        .cachedIn(viewModelScope)

    private fun searchRepository(query: String) : Flow<PagingData<RepoModel>> {
        return githubRepository.searchRepositories(query)
    }

    fun onInputSearchTextChanged(inputSearchText: String) {
        _search.value = inputSearchText
    }

}