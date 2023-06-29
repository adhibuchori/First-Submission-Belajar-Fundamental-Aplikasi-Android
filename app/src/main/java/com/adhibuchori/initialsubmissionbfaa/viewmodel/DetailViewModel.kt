package com.adhibuchori.initialsubmissionbfaa.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adhibuchori.initialsubmissionbfaa.network.ApiConfig
import com.adhibuchori.initialsubmissionbfaa.response.DetailGitHubUserResponse
import com.adhibuchori.initialsubmissionbfaa.response.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _detailGitHubUserData = MutableLiveData<DetailGitHubUserResponse>()
    val detailGitHubUserData: LiveData<DetailGitHubUserResponse> = _detailGitHubUserData

    private val _gitHubUserFollowers = MutableLiveData<List<ItemsItem>>()
    val gitHubUserFollower: LiveData<List<ItemsItem>> = _gitHubUserFollowers

    private val _gitHubUserFollowing = MutableLiveData<List<ItemsItem>>()
    val gitHubUserFollowing: LiveData<List<ItemsItem>> = _gitHubUserFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingFollow = MutableLiveData<Boolean>()
    val isLoadingFollow: LiveData<Boolean> = _isLoadingFollow

    fun getGitHubUser(login: String) {
        getDetailGitHubUser(login)
        getGitHubUserFollowers(login)
        getGitHubUserFollowing(login)
    }

    private fun getDetailGitHubUser(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(login)
        client.enqueue(object : Callback<DetailGitHubUserResponse> {
            override fun onResponse(
                call: Call<DetailGitHubUserResponse>,
                response: Response<DetailGitHubUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _detailGitHubUserData.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailGitHubUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getGitHubUserFollowers(login: String) {
        _isLoadingFollow.value = true
        val client = ApiConfig.getApiService().getFollowers(login)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadingFollow.value = false
                if (response.isSuccessful && response.body() != null) {
                    _gitHubUserFollowers.value = response.body()
                } else {
                    Log.e(TAG, "onResponse: $response")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollow.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getGitHubUserFollowing(login: String) {
        _isLoadingFollow.value = true
        val client = ApiConfig.getApiService().getFollowing(login)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadingFollow.value = false
                if (response.isSuccessful && response.body() != null) {
                    _gitHubUserFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onResponse: $response")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollow.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}