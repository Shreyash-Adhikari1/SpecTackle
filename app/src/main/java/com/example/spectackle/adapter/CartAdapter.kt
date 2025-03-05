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
import com.example.spectackle.model.CartModel
import com.example.spectackle.model.ProductModel
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(
    private val context: Context,
    private val cartItems: ArrayList<CartModel>,
    private val product: Map<String, ProductModel>,
    private val onRemoveClick: (String) -> Unit,
    private val onQuantityChange: (String, Long) -> Unit // Changed to Long
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.cartProductImage)
        val productName: TextView = itemView.findViewById(R.id.cartProductName)
        val productPrice: TextView = itemView.findViewById(R.id.cartProductPrice)
        val productQuantity: TextView = itemView.findViewById(R.id.cartProductQuantity)
        val btnRemove: ImageView = itemView.findViewById(R.id.removeFromCart)
        val btnDecrease: ImageView = itemView.findViewById(R.id.btnDecreaseQuantity)
        val btnIncrease: ImageView = itemView.findViewById(R.id.btnIncreaseQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sample_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        val product = product[cartItem.productId]
        if (product != null) {
            // Set product name
            holder.productName.text = product.productName

            // Format price with currency symbol
            val formatter = NumberFormat.getCurrencyInstance(Locale.US)
            val totalPrice = cartItem.productPrice * cartItem.quantity
            holder.productPrice.text = formatter.format(totalPrice)

            // Load product image using Glide
            Glide.with(context)
                .load(product.productImage) // Load the image from the URL
                .placeholder(R.drawable.placeholder) // Show a placeholder while loading
                .error(R.drawable.error) // Show an error image if loading fails
                .into(holder.productImage) // Set the image into the ImageView

        } else {
            holder.productName.text = "Unknown Product"
            holder.productImage.setImageResource(R.drawable.placeholder)
            holder.productPrice.text = "Price Unavailable"
        }

        // Set quantity
        holder.productQuantity.text = cartItem.quantity.toString()

        // Set click listeners
        holder.btnRemove.setOnClickListener {
            cartItem.cartId?.let { onRemoveClick(it) }
        }

        holder.btnDecrease.setOnClickListener {
            val newQuantity = cartItem.quantity - 1
            if (newQuantity >= 1) {
                // Update local data
                cartItem.quantity = newQuantity
                holder.productQuantity.text = newQuantity.toString() // Update UI immediately
                notifyItemChanged(position) // Notify adapter of the change

                // Update Firebase
                onQuantityChange(cartItem.cartId, newQuantity)
            } else {
                // Auto-remove if quantity reaches 0
                onRemoveClick(cartItem.cartId)
            }
        }

        holder.btnIncrease.setOnClickListener {
            val newQuantity = cartItem.quantity + 1

            // Update local data
            cartItem.quantity = newQuantity
            holder.productQuantity.text = newQuantity.toString() // Update UI immediately
            notifyItemChanged(position) // Notify adapter of the change

            // Update Firebase
            onQuantityChange(cartItem.cartId, newQuantity)
        }
    }

    override fun getItemCount(): Int = cartItems.size
}