package com.example.spectackle.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spectackle.R
import com.example.spectackle.manager.CartManager
import com.example.spectackle.manager.WishlistManager
import com.example.spectackle.model.Product
import com.example.spectackle.ui.activity.ProductDetailActivity

class WishlistAdapter(
    private val context: Context,
    private var wishlistItems: MutableList<Product>
) : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    class WishlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.wishlistItemImage)
        val productName: TextView = itemView.findViewById(R.id.wishlistItemName)
        val productPrice: TextView = itemView.findViewById(R.id.wishlistItemPrice)
        val addToCartBtn: ImageButton = itemView.findViewById(R.id.addToCartFromWishlist)
        val removeFromWishlist: ImageButton = itemView.findViewById(R.id.removeFromWishlist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sample_wishlist, parent, false)
        return WishlistViewHolder(view)
    }

    override fun getItemCount(): Int = wishlistItems.size

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val product = wishlistItems[position]

        holder.productImage.setImageResource(product.imageResource)
        holder.productName.text = product.name
        holder.productPrice.text = product.price

        // Item click to view details
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("productName", product.name)
            intent.putExtra("productPrice", product.price)
            intent.putExtra("productImage", product.imageResource)
            context.startActivity(intent)
        }

        // Add to cart button
        holder.addToCartBtn.setOnClickListener {
            CartManager.addToCart(product)
            // Optional: show toast or other feedback
        }

        // Remove from wishlist button
        holder.removeFromWishlist.setOnClickListener {
            WishlistManager.removeFromWishlist(product)
            // Update the list
            updateWishlistItems(WishlistManager.getWishlistItems().toMutableList())
        }
    }

    fun updateWishlistItems(newWishlistItems: MutableList<Product>) {
        this.wishlistItems = newWishlistItems
        notifyDataSetChanged()
    }
}