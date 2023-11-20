package com.dicoding.mygithubuser.utils

import android.content.Context
import com.dicoding.mygithubuser.data.UserRepository
import com.dicoding.mygithubuser.data.local.room.FavoriteUserDatabase
import com.dicoding.mygithubuser.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context) : UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserDatabase.getInstance(context)
        val dao = database.favoriteDao()
        return UserRepository.getInstance(apiService, dao)
    }
}