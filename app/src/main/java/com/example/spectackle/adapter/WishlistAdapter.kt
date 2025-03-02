package com.example.spectackle.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spectackle.R
import com.example.spectackle.model.WishlistItem

class WishlistAdapter(
    private val context: Context,
    private val wishlistItems: MutableList<WishlistItem>,
    private val onWishlistToggle: (WishlistItem, Boolean) -> Unit
) : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    class WishlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.imageView12)
        val productName: TextView = itemView.findViewById(R.id.textView18)
        val productPrice: TextView = itemView.findViewById(R.id.textView19)
        val wishlistIcon: ImageView = itemView.findViewById(R.id.imageView13) // Wishlist Icon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sample_wishlist, parent, false)
        return WishlistViewHolder(view)
    }

    override fun getItemCount(): Int = wishlistItems.size

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val item = wishlistItems[position]

        holder.productImage.setImageResource(item.productImage)
        holder.productName.text = item.productName
        holder.productPrice.text = item.productPrice

        // Set initial wishlist icon
        holder.wishlistIcon.setImageResource(
            if (item.isWishlisted) R.drawable.wish_filled else R.drawable.wish_black
        )

        // Handle Wishlist Toggle
        holder.wishlistIcon.setOnClickListener {
            val newState = !item.isWishlisted
            item.isWishlisted = newState
            holder.wishlistIcon.setImageResource(if (newState) R.drawable.wish_filled else R.drawable.wish_black)
            onWishlistToggle(item, newState)
        }
    }

    // Function to add a new item to the wishlist
    fun addItem(item: WishlistItem) {
        wishlistItems.add(item)
        notifyItemInserted(wishlistItems.size - 1)
    }
}
