package com.example.mobilehub.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilehub.R
import com.example.mobilehub.entity.Product

class CheckoutAdapter(val lstProduct: ArrayList<Product>, val context: Context) :
    RecyclerView.Adapter<CheckoutAdapter.ProductViewHolder>() {
    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvProductname: TextView = view.findViewById(R.id.tvProductname)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val tvProducttype: TextView = view.findViewById(R.id.tvProducttype)
        val profilePic: ImageView = view.findViewById(R.id.profilePic)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.display_cart, parent, false)
        return ProductViewHolder(view)
    }
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val checkout = lstProduct[position]
        holder.tvProductname.text = checkout.productname
        holder.tvDescription.text = checkout.description
        holder.tvPrice.text = checkout.price.toString()
        holder.tvProducttype.text = checkout.producttype
        if (checkout.producttype == "Wine") {
            holder.profilePic.setImageResource(R.drawable.noimage)
        } else if (checkout.producttype == "Vodka") {
            holder.profilePic.setImageResource(R.drawable.noimage)
        } else
            holder.profilePic.setImageResource(R.drawable.noimage)
    }

    override fun getItemCount(): Int {
        return lstProduct.size
    }
    }
