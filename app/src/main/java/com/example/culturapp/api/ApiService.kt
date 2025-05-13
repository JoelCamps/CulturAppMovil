package com.example.culturapp.api

import Users
import com.example.culturapp.clases.Bookings
import com.example.culturapp.clases.Events
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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
    @GET("api/Bookings/Events/{id_event}")
    suspend fun getBookingEvent(
        @Query("id_event") id_event: Int,
        ): List<Bookings>

    @POST("api/Bookings")
    suspend fun postBooking(
        @Body booking: Bookings
        ): Bookings

    // ROOMS

    // TYPE_EVENT
}