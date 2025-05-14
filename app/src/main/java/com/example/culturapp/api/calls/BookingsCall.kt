package com.example.culturapp.api.calls

import com.example.culturapp.api.RetrofitClient
import com.example.culturapp.clases.Bookings

class BookingsCall {
    suspend fun getBookingEvent(id_event: Int): List<Bookings> {
        return RetrofitClient.instance.getBookingEvent(id_event)
    }

    suspend fun getBookingUser(id_user: Int): List<Bookings> {
        return RetrofitClient.instance.getBookingUser(id_user)
    }

    suspend fun postBooking(booking: Bookings): Bookings {
        return RetrofitClient.instance.postBooking(booking)
    }
}