package com.example.mobilehubwear.api



import com.example.mobilehubwear.entity.User
import com.example.mobilehubwear.response.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    //register user
    @POST("auth/register")
    suspend fun registerUser(
            @Body user: User
    ):Response<LoginResponse>

    //Login user
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun checkUser(
            @Field ("username") username : String,
            @Field ("password") password : String
    ):Response<LoginResponse>


}