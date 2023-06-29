package com.adhibuchori.initialsubmissionbfaa.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adhibuchori.initialsubmissionbfaa.response.GitHubUserResponse
import com.adhibuchori.initialsubmissionbfaa.response.ItemsItem
import com.adhibuchori.initialsubmissionbfaa.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listGitHubUserData = MutableLiveData<List<ItemsItem>>()
    val listGitHubUserData: LiveData<List<ItemsItem>> = _listGitHubUserData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findGitHubUser(GITHUBUSER_ID)
    }

    fun findGitHubUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGitHubUser(query)
        client.enqueue(object : Callback<GitHubUserResponse> {
            override fun onResponse(
                call: Call<GitHubUserResponse>,
                response: Response<GitHubUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _listGitHubUserData.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GitHubUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
        private const val GITHUBUSER_ID = "adhib"
    }
}