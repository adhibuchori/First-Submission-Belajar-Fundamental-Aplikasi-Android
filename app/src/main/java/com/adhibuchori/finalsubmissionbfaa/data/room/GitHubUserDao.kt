package com.adhibuchori.finalsubmissionbfaa.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.adhibuchori.finalsubmissionbfaa.data.remote.response.ItemGitHubUser

@Dao
interface GitHubUserDao {
    @Query("SELECT * FROM FavoriteUser")
    fun getFavouriteUGitHubser(): LiveData<List<ItemGitHubUser>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavouriteGitHubUser(gitHubUser: ItemGitHubUser): Long

    @Query("DELETE FROM FavoriteUser WHERE login = :login")
    suspend fun deleteFavouriteGitHubUser(login: String): Int

    @Query("SELECT * FROM FavoriteUser WHERE login = :login")
    fun getFavouriteGitHubUserByLogin(login: String): LiveData<ItemGitHubUser>
}