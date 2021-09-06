package com.example.mobilehub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilehub.R
import com.example.mobilehub.adapters.CheckoutAdapter
import com.example.mobilehub.entity.Product
import com.example.mobilehub.userRepository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckoutActivity : AppCompatActivity() {
    lateinit var btnConfirm: ImageView
    lateinit var rvDisplayProducts : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        rvDisplayProducts = findViewById(R.id.rvDisplayProducts)
        //textview
        btnConfirm = findViewById<ImageView>(R.id.btnConfirm)
        CoroutineScope(Dispatchers.IO).launch {
            val repository = ProductRepository()
            val response = repository.getProducts()
            val lst = response.data
            withContext(Main){
                val adapter = CheckoutAdapter(lst as ArrayList<Product>,this@CheckoutActivity)
                rvDisplayProducts.adapter=adapter
                rvDisplayProducts.layoutManager = LinearLayoutManager(this@CheckoutActivity)

            }
        }

        btnConfirm.setOnClickListener {
            Toast.makeText(this, "OK!!!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, DisplayProductActivity::class.java))
        }
    }


}