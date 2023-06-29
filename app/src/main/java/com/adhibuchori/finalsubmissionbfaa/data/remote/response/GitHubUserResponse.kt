package com.adhibuchori.finalsubmissionbfaa.data.remote.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GitHubUserResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<ItemGitHubUser>
)

@Entity(tableName = "FavoriteUser")
@Parcelize
data class ItemGitHubUser(

    @field:ColumnInfo(name = "login")
    @field:PrimaryKey
    @field:SerializedName("login")
    val login: String,

    @field:ColumnInfo(name = "type")
    @field:SerializedName("type")
    val type: String,

    @field:ColumnInfo(name = "avatarUrl")
    @field:SerializedName("avatar_url")
    val avatarUrl: String

) : Parcelable

data class GitHubUserDetail(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("html_url")
    val htmlUrl: String,

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