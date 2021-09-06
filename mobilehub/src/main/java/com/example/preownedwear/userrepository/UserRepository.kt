package com.example.mobilehubwear.userrepository

import com.example.mobilehubwear.api.MyApiRequest
import com.example.mobilehubwear.api.ServiceBuilder
import com.example.mobilehubwear.api.UserApi
import com.example.mobilehubwear.response.LoginResponse

class UserRepository :
        MyApiRequest() {
    val myApi = ServiceBuilder.buildService(UserApi::class.java)

    suspend fun checkUser(username: String, password: String): LoginResponse {
        return apiRequest {
            myApi.checkUser(username, password)
        }
    }
}