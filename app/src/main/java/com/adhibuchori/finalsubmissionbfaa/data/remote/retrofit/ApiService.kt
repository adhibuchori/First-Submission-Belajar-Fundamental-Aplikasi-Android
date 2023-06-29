package com.adhibuchori.finalsubmissionbfaa.data.remote.retrofit

import com.adhibuchori.finalsubmissionbfaa.data.remote.response.GitHubUserDetail
import com.adhibuchori.finalsubmissionbfaa.data.remote.response.GitHubUserResponse
import com.adhibuchori.finalsubmissionbfaa.data.remote.response.ItemGitHubUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun searchGitHubUser(@Query("q") login: String): GitHubUserResponse

    @GET("users/{login}")
    suspend fun getGitHubUserDetail(@Path("login") login: String): GitHubUserDetail

    @GET("users/{login}/followers")
    suspend fun getGitHubUserFollower(@Path("login") login: String): List<ItemGitHubUser>

    @GET("users/{login}/following")
    suspend fun getGitHubUserFollowing(@Path("login") login: String): List<ItemGitHubUser>
}