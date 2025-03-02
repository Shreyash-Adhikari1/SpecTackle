package com.example.spectackle.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spectackle.R
import com.example.spectackle.manager.CartManager
import com.example.spectackle.model.Product

class CartAdapter(
    private val context: Context,
    private var cartItems: MutableList<Product>,
    private val onCartUpdated: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.cartItemImage)
        val productName: TextView = itemView.findViewById(R.id.cartItemName)
        val productPrice: TextView = itemView.findViewById(R.id.cartItemPrice)
        val productQuantity: TextView = itemView.findViewById(R.id.itemQuantity)
        val increaseBtn: ImageButton = itemView.findViewById(R.id.increaseQuantityBtn)
        val decreaseBtn: ImageButton = itemView.findViewById(R.id.decreaseQuantityBtn)
        val deleteBtn: ImageButton = itemView.findViewById(R.id.deleteItemBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sample_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartItems[position]

        holder.productImage.setImageResource(product.imageResource)
        holder.productName.text = product.name
        holder.productPrice.text = product.price
        holder.productQuantity.text = product.quantity.toString()

        holder.increaseBtn.setOnClickListener {
            // Increase quantity
            product.quantity++
            holder.productQuantity.text = product.quantity.toString()
            onCartUpdated()
        }

        holder.decreaseBtn.setOnClickListener {
            // Decrease quantity, but don't go below 1
            if (product.quantity > 1) {
                product.quantity--
                holder.productQuantity.text = product.quantity.toString()
                onCartUpdated()
            }
        }

        holder.deleteBtn.setOnClickListener {
            // Remove item from cart
            CartManager.removeFromCart(product)
            // Refresh data
            updateCartItems(CartManager.getCartItems().toMutableList())
            onCartUpdated()
        }
    }

    fun updateCartItems(newCartItems: MutableList<Product>) {
        this.cartItems = newCartItems
        notifyDataSetChanged()
    }
}