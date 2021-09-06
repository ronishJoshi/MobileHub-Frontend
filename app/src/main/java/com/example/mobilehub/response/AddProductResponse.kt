package com.example.mobilehub.response

import com.example.mobilehub.entity.Product

data class AddProductResponse (
    val success : Boolean? = null,
    val data : Product? = null
        )