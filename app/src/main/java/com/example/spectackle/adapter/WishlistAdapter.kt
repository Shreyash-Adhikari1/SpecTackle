package com.example.spectackle.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spectackle.R
import com.example.spectackle.model.ProductModel
import com.example.spectackle.model.WishlistModel

class WishlistAdapter(
    private val context: Context,
    private val wishlistItems: ArrayList<WishlistModel>,
    private val product: Map<String, ProductModel>,
    private val onRemoveClick: (String) -> Unit // Remove product from wishlist callback
) : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    class WishlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.wishlistProductImage)
        val productName: TextView = itemView.findViewById(R.id.wishlistProductName)
        val btnRemove: ImageView = itemView.findViewById(R.id.removeFromWishlist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sample_wishlist, parent, false)
        return WishlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val wishlistItem = wishlistItems[position]
        val product = product[wishlistItem.productId]

        if (product != null) {
            // Set product name
            holder.productName.text = product.productName

            // Load product image using Glide
            Glide.with(context)
                .load(product.productImage) // Load the image from the URL
                .placeholder(R.drawable.placeholder) // Show a placeholder while loading
                .error(R.drawable.error) // Show an error image if loading fails
                .into(holder.productImage) // Set the image into the ImageView
        } else {
            holder.productName.text = "Unknown Product"
            holder.productImage.setImageResource(R.drawable.placeholder)
        }

        // Set click listeners
        holder.btnRemove.setOnClickListener {
            wishlistItem.wishlistId?.let { onRemoveClick(it) }
        }
    }

    override fun getItemCount(): Int = wishlistItems.size
}
