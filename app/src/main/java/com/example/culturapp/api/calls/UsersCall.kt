package com.example.culturapp.api.calls

import Users
import com.example.culturapp.api.RetrofitClient

class UsersCall {
    suspend fun login(email: String, password: String): Users {
        return RetrofitClient.instance.login(email, password)
    }
}