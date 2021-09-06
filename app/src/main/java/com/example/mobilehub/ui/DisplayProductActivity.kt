package com.example.mobilehub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilehub.R
import com.example.mobilehub.adapters.ProductAdapter
import com.example.mobilehub.entity.Product
import com.example.mobilehub.userRepository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DisplayProductActivity : AppCompatActivity() {
    lateinit var btncheckout: Button
    lateinit var rvDisplayProducts : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_product)
        rvDisplayProducts = findViewById(R.id.rvDisplayProducts)

        //textview
        btncheckout = findViewById<Button>(R.id.btnCheckout)
        CoroutineScope(Dispatchers.IO).launch {
            val repository = ProductRepository()
            val response = repository.getProducts()
            val lst = response.data
            withContext(Main){
                if (response.success == true) {
                    val adapter = ProductAdapter(lst as ArrayList<Product>,this@DisplayProductActivity)
                    rvDisplayProducts.adapter=adapter
                    rvDisplayProducts.layoutManager = LinearLayoutManager(this@DisplayProductActivity)

                }

            }
        }

        btncheckout.setOnClickListener {

            startActivity(Intent(this, CheckoutActivity::class.java))
        }
    }


}