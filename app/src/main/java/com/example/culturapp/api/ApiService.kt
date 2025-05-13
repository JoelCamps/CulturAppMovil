package com.example.culturapp.api

import Users
import com.example.culturapp.clases.Events
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // USERS
    @GET("api/Users/{email}/{password}")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Users

    // EVENTS
    @GET("api/Events")
    suspend fun getEvents(): List<Events>

    // BOOKINGS

    // ROOMS

    // TYPE_EVENT
}