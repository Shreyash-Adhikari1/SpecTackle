package com.example.spectackle.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WishlistAdapter(
    val context: Context,
    val wishlistImageList: ArrayList<Int>,
    val wishlistNameList: ArrayList<String>,
    val wishlistPriceList: ArrayList<String>,
): RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {
    class WishlistViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}