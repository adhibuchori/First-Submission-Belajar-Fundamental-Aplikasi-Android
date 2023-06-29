package com.adhibuchori.initialsubmissionbfaa.network

import com.adhibuchori.initialsubmissionbfaa.response.DetailGitHubUserResponse
import com.adhibuchori.initialsubmissionbfaa.response.GitHubUserResponse
import com.adhibuchori.initialsubmissionbfaa.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getGitHubUser(
        @Query("q") login: String
    ): Call<GitHubUserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailGitHubUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username"
        ) username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}