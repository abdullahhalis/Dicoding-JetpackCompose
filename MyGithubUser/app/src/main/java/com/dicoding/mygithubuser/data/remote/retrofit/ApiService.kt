package com.dicoding.mygithubuser.data.remote.retrofit

import com.dicoding.mygithubuser.data.remote.response.DetailResponse
import com.dicoding.mygithubuser.data.remote.response.ListUsersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getListUsers(
        @Query("q") q: String
    ) : ListUsersResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ) : DetailResponse
}