package com.example.culturapp.clases

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Rooms(
    @SerializedName("Id") val id: Int,
    @SerializedName("Name") val name: String,
    val description: String,
    val size: Float ) : Serializable