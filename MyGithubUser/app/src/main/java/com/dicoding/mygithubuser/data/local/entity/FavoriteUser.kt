package com.dicoding.mygithubuser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteUser")
data class FavoriteUser(
    @PrimaryKey(autoGenerate = false)
    val username: String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
)
