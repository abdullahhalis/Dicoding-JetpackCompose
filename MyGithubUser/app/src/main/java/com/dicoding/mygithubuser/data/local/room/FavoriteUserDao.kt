package com.dicoding.mygithubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.mygithubuser.data.local.entity.FavoriteUser

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser ORDER BY username ASC")
    suspend fun getAllFavoriteUsers(): List<FavoriteUser>

    @Query("SELECT EXISTS(SELECT * FROM FavoriteUser WHERE username = :username)")
    fun isUserFavorite(username: String): LiveData<Boolean>

    @Query("DELETE FROM FavoriteUser Where username = :username")
    suspend fun deleteFavorite(username: String)
}