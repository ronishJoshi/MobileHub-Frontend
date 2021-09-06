package com.example.mobilehub.api

import com.example.mobilehub.entity.Product
import com.example.mobilehub.response.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ProductApi {
    @POST("product/")
    suspend fun addProduct(
        @Header("Authorization") token: String,
        @Body product: Product
    ): Response<AddProductResponse>


    @GET("product/")
    suspend fun getAllProducts(
        @Header("Authorization") token :String
    ):Response<ProductResponse>

    suspend fun getAllCheckout(
            @Header("Authorization") token :String
    ):Response<CheckoutResponse>


    @DELETE("product/{id}")
    suspend fun deleteProduct(
        @Header("Authorization") token: String,
        @Path("id") id: String

    ):Response<DeleteProductResponse>
    @DELETE("checkout/{id}")
    suspend fun deleteCheckout(
            @Header("Authorization") token: String,
            @Path("id") id: String

    ):Response<DeleteCheckoutResponse>

    @Multipart
    @PUT("product/{id}/photo")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Part file: MultipartBody.Part
    ): Response<ImageResponse>

    abstract fun deleteCheckout(token: String): Response<DeleteCheckoutResponse>
}