package com.example.culturapp.api

import Users
import com.example.culturapp.clases.Bookings
import com.example.culturapp.clases.Events
import com.example.culturapp.clases.Rooms
import com.example.culturapp.clases.Type_event
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    // USERS
    @GET("api/Users/{email}/{password}")
    suspend fun getUserLogin(
        @Query("email") email: String,
        @Query("password") password: String
    ): Users

    @POST("api/Users")
    suspend fun postUser(
        @Body user: Users
                         ): Users

    // EVENTS
    @GET("api/Events")
    suspend fun getEvents(): List<Events>

    @POST("api/Events")
    suspend fun postEvent(
        @Body events: Events
        ): Events

    // BOOKINGS
    @GET("api/Bookings/Events/{id_event}")
    suspend fun getBookingEvent(
        @Query("id_event") id_event: Int,
        ): List<Bookings>

    @GET("api/Bookings/Users/{id_user}")
    suspend fun getBookingUser(
        @Query("id_user") id_user: Int,
        ): List<Bookings>

    @POST("api/Bookings")
    suspend fun postBooking(
        @Body booking: Bookings
        ): Bookings

    @PUT("api/Bookings")
    suspend fun putBooking(
        @Body booking: Bookings
        ): Response<Bookings?>

    // ROOMS
    @GET("api/Rooms")
    suspend fun getRooms(): List<Rooms>

    // TYPE_EVENT
    @GET("api/TypeEvent")
    suspend fun getTypeEvent(): List<Type_event>
}