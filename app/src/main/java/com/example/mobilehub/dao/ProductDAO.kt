package com.example.mobilehub.dao

import androidx.room.*
import com.example.mobilehub.entity.Product

@Dao
interface ProductDAO {
    @Insert
    suspend fun insertProduct(product : Product)

    @Query("SELECT * FROM Product")
    suspend fun getAllProducts() : List<Product>

    @Update
    suspend fun updatePoduct(product : Product)

    @Delete
    suspend fun DeleteProduct(product : Product)
}