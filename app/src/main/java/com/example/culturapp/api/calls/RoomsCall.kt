package com.example.culturapp.api.calls

import com.example.culturapp.api.RetrofitClient
import com.example.culturapp.clases.Rooms

class RoomsCall {
    suspend fun getRooms(): List<Rooms> {
        return RetrofitClient.instance.getRooms()
    }
}