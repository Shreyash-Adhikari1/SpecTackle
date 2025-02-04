package com.example.spectackle.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    val context: Context,
    val cartImageList: ArrayList<Int>,
    val cartNameList: ArrayList<String>,
    val cartPriceList: ArrayList<String>,
): RecyclerView.Adapter <CartAdapter.CartViewHolder>() {
    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}