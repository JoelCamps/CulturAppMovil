package com.example.culturapp.api.calls

import Users
import com.example.culturapp.api.RetrofitClient
import retrofit2.Response

class UsersCall {
    suspend fun getUserLogin(email: String, password: String): Users {
        return RetrofitClient.instance.getUserLogin(email, password)
    }

    suspend fun postUser(user: Users): Users {
        return RetrofitClient.instance.postUser(user)
    }

    suspend fun putUser(id: Int, user: Users): Response<Unit> {
        return RetrofitClient.instance.putUser(id, user)
    }

    suspend fun putUsersPassword(email: String, password: String): Response<Unit> {
        return RetrofitClient.instance.putUsersPassword(email, password)
    }
}