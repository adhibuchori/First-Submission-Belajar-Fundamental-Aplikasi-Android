package com.adhibuchori.finalsubmissionbfaa.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adhibuchori.finalsubmissionbfaa.data.remote.response.ItemGitHubUser

@Database(entities = [ItemGitHubUser::class], version = 1, exportSchema = false)
abstract class GitHubUserRoomDatabase : RoomDatabase() {
    abstract fun gitHubUserDao(): GitHubUserDao

    companion object {
        @Volatile
        private var instance: GitHubUserRoomDatabase? = null
        fun getInstance(context: Context): GitHubUserRoomDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    GitHubUserRoomDatabase::class.java, "GitHubUser.db"
                ).build()
            }
    }
}