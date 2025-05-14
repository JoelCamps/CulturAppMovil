package com.example.culturapp.clases

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Events(
    @SerializedName("Id") val id: Int,
    @SerializedName("Title") val title: String,
    @SerializedName("Start_datetime") val start_datetime: String,
    @SerializedName("End_datetime") val end_datetime: String,
    @SerializedName("Capacity") val capacity: Int,
    @SerializedName("Description") val description: String,
    @SerializedName("Price") val price: Int,
    @SerializedName("Active") val active: Boolean,
    @SerializedName("Room_id") val room_id: Int,
    @SerializedName("Type_id") val type_id: Int,
    @SerializedName("Type_event") val type_event: Type_event,
    @SerializedName("Rooms") val rooms: Rooms ) : Serializable