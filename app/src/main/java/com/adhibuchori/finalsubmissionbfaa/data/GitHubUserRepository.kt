package com.adhibuchori.finalsubmissionbfaa.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.adhibuchori.finalsubmissionbfaa.data.remote.retrofit.ApiService
import com.adhibuchori.finalsubmissionbfaa.data.remote.response.GitHubUserDetail
import com.adhibuchori.finalsubmissionbfaa.data.remote.response.ItemGitHubUser
import com.adhibuchori.finalsubmissionbfaa.data.room.GitHubUserDao
import kotlinx.coroutines.flow.map

class GitHubUserRepository private constructor(
    private val apiService: ApiService,
    private val gitHubUserDao: GitHubUserDao,
    private val dataStore: DataStore<Preferences>? = null
) {

    private val themeKey = booleanPreferencesKey("theme_setting")

    fun searchGitHubUser(query: String): LiveData<Result<List<ItemGitHubUser>>> = liveData {
        emit(Result.Loading)
        try {
            Log.i("TEST", "REPO searchGitHubUser: $query")
            val response = apiService.searchGitHubUser(query)
            val listGitHubUser = response.items
            emit(Result.Success(listGitHubUser))
        } catch (e: Exception) {
            Log.d("GitHubUserRepository", "searchGitHubUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getGitHubUserDetail(login: String): LiveData<Result<GitHubUserDetail>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getGitHubUserDetail(login)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("GitHubUserRepository", "getGitHubUserDetail: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getGitHubUserFollower(login: String): LiveData<Result<List<ItemGitHubUser>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getGitHubUserFollower(login)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("GitHubUserRepository", "getGitHubUserFollower: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getGitHubUserFollowing(login: String): LiveData<Result<List<ItemGitHubUser>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getGitHubUserFollowing(login)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("GitHubUserRepository", "getGitHubUserFollowing: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFavouriteGitHubUser(): LiveData<List<ItemGitHubUser>> {
        return gitHubUserDao.getFavouriteUGitHubser()
    }

    suspend fun addFavouriteGitHubUser(user: ItemGitHubUser): Long {
        return gitHubUserDao.addFavouriteGitHubUser(user)
    }

    suspend fun deleteFavouriteGitHubUser(login: String): Int {
        return gitHubUserDao.deleteFavouriteGitHubUser(login)
    }

    fun getFavouriteGitHubUserByLogin(login: String): LiveData<ItemGitHubUser> {
        return gitHubUserDao.getFavouriteGitHubUserByLogin(login)
    }

    fun getThemeSetting(): LiveData<Boolean>? {
        return dataStore?.data?.map { preferences ->
            preferences[themeKey] ?: false
        }?.asLiveData()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore?.edit { preferences ->
            preferences[themeKey] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var instance: GitHubUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            gitHubUserDao: GitHubUserDao,
            dataStore: DataStore<Preferences>? = null
        ): GitHubUserRepository =
            instance ?: synchronized(this) {
                instance ?: GitHubUserRepository(apiService, gitHubUserDao, dataStore)
            }.also { instance = it }
    }
}