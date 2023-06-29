package com.adhibuchori.finalsubmissionbfaa.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.adhibuchori.finalsubmissionbfaa.data.GitHubUserRepository
import com.adhibuchori.finalsubmissionbfaa.data.room.GitHubUserRoomDatabase
import com.adhibuchori.finalsubmissionbfaa.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context, dataStore: DataStore<Preferences>? = null): GitHubUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = GitHubUserRoomDatabase.getInstance(context)
        val dao = database.gitHubUserDao()
        return GitHubUserRepository.getInstance(apiService, dao, dataStore)
    }
}