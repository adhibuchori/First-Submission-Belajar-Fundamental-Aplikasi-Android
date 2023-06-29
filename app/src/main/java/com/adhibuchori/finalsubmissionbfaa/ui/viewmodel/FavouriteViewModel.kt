package com.adhibuchori.finalsubmissionbfaa.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.adhibuchori.finalsubmissionbfaa.data.GitHubUserRepository

// For Testing
class FavouriteViewModel(private val gitHubUserRepository: GitHubUserRepository) : ViewModel() {
    fun getFavouriteUser() = gitHubUserRepository.getFavouriteGitHubUser()
}