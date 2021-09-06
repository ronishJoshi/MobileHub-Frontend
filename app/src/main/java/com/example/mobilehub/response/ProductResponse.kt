package com.example.mobilehub.response

import com.example.mobilehub.entity.Product

data class ProductResponse(
    val success : Boolean? = null,
    val data : MutableList<Product>? =null
)
