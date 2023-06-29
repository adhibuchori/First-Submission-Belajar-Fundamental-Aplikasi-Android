package com.adhibuchori.finalsubmissionbfaa.ui.viewmodel

import androidx.lifecycle.*
import com.adhibuchori.finalsubmissionbfaa.data.GitHubUserRepository
import com.adhibuchori.finalsubmissionbfaa.data.Result
import com.adhibuchori.finalsubmissionbfaa.data.remote.response.GitHubUserDetail
import com.adhibuchori.finalsubmissionbfaa.data.remote.response.ItemGitHubUser
import kotlinx.coroutines.launch

class DetailViewModel(private val gitHubUserRepository: GitHubUserRepository) : ViewModel() {

    private val _gitHubUserDetail = MutableLiveData<Result<GitHubUserDetail>>()
    val gitHubUserDetail : LiveData<Result<GitHubUserDetail>>
        get() = _gitHubUserDetail

    private val observerGitHubUserDetail = Observer<Result<GitHubUserDetail>> {
        _gitHubUserDetail.value = it
    }

    private val _listGitHubUserFollower = MutableLiveData<Result<List<ItemGitHubUser>>>()
    val listGitHubUserFollower : LiveData<Result<List<ItemGitHubUser>>>
        get() = _listGitHubUserFollower

    private val observerGitHubUserFollower = Observer<Result<List<ItemGitHubUser>>> {
        _listGitHubUserFollower.value = it
    }

    private val _listGitHubUserFollowing = MutableLiveData<Result<List<ItemGitHubUser>>>()
    val listGitHubUserFollowing : LiveData<Result<List<ItemGitHubUser>>>
        get() = _listGitHubUserFollowing

    private val observerGitHubUserFollowing = Observer<Result<List<ItemGitHubUser>>> {
        _listGitHubUserFollowing.value = it
    }

    fun getGitHubUser(login: String){
        getGitHubUserDetail(login)
        getGitHubUserFollower(login)
        getGitHubUserFollowing(login)
    }
    private fun getGitHubUserDetail(login: String){
        LOGIN = login
        gitHubUserRepository.getGitHubUserDetail(login).observeForever(observerGitHubUserDetail)
    }

    private fun getGitHubUserFollower(login: String) {
        LOGIN = login
        gitHubUserRepository.getGitHubUserFollower(login).observeForever(observerGitHubUserFollower)
    }

    private fun getGitHubUserFollowing(login: String) {
        LOGIN = login
        gitHubUserRepository.getGitHubUserFollowing(login).observeForever(observerGitHubUserFollowing)
    }

    fun addFavouriteUser(user: ItemGitHubUser){
        viewModelScope.launch {
            gitHubUserRepository.addFavouriteGitHubUser(user)
        }
    }

    fun deleteFavouriteUser(login: String){
        viewModelScope.launch {
            gitHubUserRepository.deleteFavouriteGitHubUser(login)
        }
    }

    fun getFavoriteUserByLogin(login: String) = gitHubUserRepository.getFavouriteGitHubUserByLogin(login)

    override fun onCleared() {
        super.onCleared()
        gitHubUserRepository.getGitHubUserDetail(LOGIN).removeObserver(observerGitHubUserDetail)
        gitHubUserRepository.getGitHubUserFollower(LOGIN).removeObserver(observerGitHubUserFollower)
        gitHubUserRepository.getGitHubUserFollowing(LOGIN).removeObserver(observerGitHubUserFollowing)
    }

    companion object{
        private var LOGIN = ""
    }
}