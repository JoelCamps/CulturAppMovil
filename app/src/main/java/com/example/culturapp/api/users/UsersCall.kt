package com.example.culturapp.api.users

import Users
import com.example.culturapp.api.RetrofitClient
import retrofit2.Response

class UsersCall {

       suspend fun login(email: String, password: String): Users {
        return RetrofitClient.instance.login(email, password)
    }
}