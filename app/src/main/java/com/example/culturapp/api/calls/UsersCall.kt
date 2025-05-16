package com.example.culturapp.api.calls

import Users
import com.example.culturapp.api.RetrofitClient

class UsersCall {
    suspend fun getUserLogin(email: String, password: String): Users {
        return RetrofitClient.instance.getUserLogin(email, password)
    }

    suspend fun postUser(user: Users): Users {
        return RetrofitClient.instance.postUser(user)
    }
}