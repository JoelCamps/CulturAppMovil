package com.example.culturapp.api.calls

import com.example.culturapp.api.RetrofitClient
import com.example.culturapp.clases.Type_event

class TypeEventCall {
    suspend fun getTypeEvent(): List<Type_event> {
        return RetrofitClient.instance.getTypeEvent()
    }
}