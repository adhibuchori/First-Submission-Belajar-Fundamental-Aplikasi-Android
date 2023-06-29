package com.adhibuchori.initialsubmissionbfaa.response

import com.google.gson.annotations.SerializedName

data class DetailGitHubUserResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("public_repos")
	val publicRepos: Int
)
