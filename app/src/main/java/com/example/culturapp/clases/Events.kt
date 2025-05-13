package com.example.culturapp.clases

import java.io.Serializable
import java.sql.Timestamp

data class Events(
    val id: Int,
    val title: String,
    val start_datetime: String,
    val end_datetime: String,
    val capacity: Int,
    val description: String,
    val price: Int,
    val active: Boolean,
    val room_id: Int,
    val type_id: Int,
    val type_event: Type_event,
    val rooms: Rooms ) : Serializable