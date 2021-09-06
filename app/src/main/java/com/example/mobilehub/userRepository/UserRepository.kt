package com.example.mobilehub.userRepository

import com.example.mobilehub.api.MyApiRequest
import com.example.mobilehub.api.ServiceBuilder
import com.example.mobilehub.api.UserApi
import com.example.mobilehub.entity.User
import com.example.mobilehub.response.LoginResponse

class UserRepository :
    MyApiRequest() {
    val myApi = ServiceBuilder.buildService(UserApi::class.java)
    suspend fun registerUser(user: User): LoginResponse {
        return apiRequest {
            myApi.registerUser(user)
        }
    }
    suspend fun checkUser(username: String, password: String): LoginResponse {
        return apiRequest {
            myApi.checkUser(username, password)
        }
    }
}