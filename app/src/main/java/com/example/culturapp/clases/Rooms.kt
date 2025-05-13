package com.example.culturapp.clases

import java.io.Serializable

data class Rooms(
    val id: Int,
    val name: String,
    val description: String,
    val size: Float ) : Serializable