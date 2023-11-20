package com.dicoding.mygithubuser.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.dicoding.mygithubuser.data.local.entity.FavoriteUser
import com.dicoding.mygithubuser.data.local.room.FavoriteUserDao
import com.dicoding.mygithubuser.data.remote.response.DetailResponse
import com.dicoding.mygithubuser.data.remote.response.ItemsItem
import com.dicoding.mygithubuser.data.remote.retrofit.ApiService
import com.dicoding.mygithubuser.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class UserRepository(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDao
) {
    fun getListUsers(query: String) : Flow<List<ItemsItem>> = flow{
        wrapEspressoIdlingResource {
            try {
                val listUser = apiService.getListUsers(query).items
                emit(listUser)
            } catch (e: HttpException) {
                Log.e(TAG, "getListUsers: ${e.message.toString()}")
            }
        }
    }

    fun getDetailUser(username: String) : Flow<DetailResponse> = flow {
        wrapEspressoIdlingResource {
            try {
                val detailUser = apiService.getDetailUser(username)
                emit(detailUser)
            } catch (e: HttpException) {
                Log.e(TAG, "getDetailUser: ${e.message.toString()}")
            }
        }
    }

    suspend fun addFavorite(favoriteUser: FavoriteUser) {
        favoriteUserDao.addFavorite(favoriteUser)
    }

    fun getAllFavorite() : Flow<List<FavoriteUser>> = flow {
        try {
            val listUser = favoriteUserDao.getAllFavoriteUsers()
            emit(listUser)
        } catch (e: Exception) {
            Log.d(TAG, "getAllFavorite: ${e.message.toString()}")
        }
    }

    fun isUserFavorite(username: String) : LiveData<Boolean> {
        return favoriteUserDao.isUserFavorite(username)
    }

    suspend fun deleteFavorite(username: String) {
        favoriteUserDao.deleteFavorite(username)
    }

    companion object {
        private const val TAG = "USERREPOSITORY"
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteUserDao: FavoriteUserDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, favoriteUserDao)
            }.also { instance = it }
    }
}