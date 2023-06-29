package com.adhibuchori.finalsubmissionbfaa.ui.viewmodel

import androidx.lifecycle.*
import com.adhibuchori.finalsubmissionbfaa.data.GitHubUserRepository
import com.adhibuchori.finalsubmissionbfaa.data.remote.response.ItemGitHubUser
import com.adhibuchori.finalsubmissionbfaa.data.Result
import kotlinx.coroutines.launch


class HomeViewModel(private val gitHubUserRepository: GitHubUserRepository) : ViewModel() {

    private val _gitHubUserResult = MutableLiveData<Result<List<ItemGitHubUser>>>()
    val gitHubUserResult : LiveData<Result<List<ItemGitHubUser>>>
        get() = _gitHubUserResult

    private val observer = Observer<Result<List<ItemGitHubUser>>> {
        _gitHubUserResult.value = it
    }

    init{
        searchGitHubUser()
    }

    fun searchGitHubUser(query: String = "adhib"){
        QUERY = query
        gitHubUserRepository.searchGitHubUser(query).observeForever(observer)
    }

    fun getFavouriteUser() = gitHubUserRepository.getFavouriteGitHubUser()

    fun getThemeSetting() = gitHubUserRepository.getThemeSetting()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            gitHubUserRepository.saveThemeSetting(isDarkModeActive)
        }
    }

    override fun onCleared() {
        super.onCleared()
        gitHubUserRepository.searchGitHubUser(QUERY).removeObserver(observer)
    }

    companion object {
        private var QUERY = ""
    }
}