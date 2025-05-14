package com.example.culturapp.clases

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Type_event(
    @SerializedName("Id") val id: Int,
    @SerializedName("Name") val name: String ) : Serializable