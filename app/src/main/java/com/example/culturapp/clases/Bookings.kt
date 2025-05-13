package com.example.culturapp.clases

import java.io.Serializable

data class Bookings(
    var user_id: Int,
    var event_id: Int,
    var quantity: Int,
    val active: Boolean ) : Serializable