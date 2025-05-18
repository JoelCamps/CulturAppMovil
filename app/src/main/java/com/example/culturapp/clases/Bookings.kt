package com.example.culturapp.clases

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Bookings(
    @SerializedName("User_id") var user_id: Int,
    @SerializedName("Event_id") var event_id: Int,
    @SerializedName("Quantity") var quantity: Int,
    @SerializedName("Active") var active: Boolean,
    @SerializedName("Events") val events: Events?
) : Serializable