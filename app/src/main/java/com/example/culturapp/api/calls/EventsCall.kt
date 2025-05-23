package com.example.culturapp.api.calls

import com.example.culturapp.api.RetrofitClient
import com.example.culturapp.clases.Events

class EventsCall {
    suspend fun getEvents(): List<Events> {
        return RetrofitClient.instance.getEvents()
    }

    suspend fun postEvent(event: Events): Events {
        return RetrofitClient.instance.postEvent(event)
    }
}