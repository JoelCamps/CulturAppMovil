package com.example.culturapp.api.users

import Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersService {
    @GET("api/Users/{email}/{password}")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Users
}