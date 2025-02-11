package com.example.spectackle.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spectackle.R
import com.example.spectackle.model.CartModel


class CartAdapter(
    private val context: Context,
    private val cartList: MutableList<CartModel>,
    private val onQuantityChangeListener: OnQuantityChangeListener
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    interface OnQuantityChangeListener {
        fun onQuantityChanged()
    }

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.cartImage)
        val productName: TextView = itemView.findViewById(R.id.cartProductPrice)
        val productPrice: TextView = itemView.findViewById(R.id.displayPrice)
        val quantityText: TextView = itemView.findViewById(R.id.cartQuantitytext)
        val btnIncrease: ImageButton = itemView.findViewById(R.id.imageButton4)
        val btnDecrease: ImageButton = itemView.findViewById(R.id.imageButton2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sample_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int = cartList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartList[position]

        Glide.with(context).load(item.cartImageUrl).into(holder.productImage)
        holder.productName.text = item.cartProductName
        holder.productPrice.text = "Rs ${item.cartProductPrice}"
        holder.quantityText.text = item.quantity.toString()

        holder.btnIncrease.setOnClickListener {
            item.quantity++
            holder.quantityText.text = item.quantity.toString()
            onQuantityChangeListener.onQuantityChanged()
        }

        holder.btnDecrease.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                holder.quantityText.text = item.quantity.toString()
                onQuantityChangeListener.onQuantityChanged()
            }
        }
    }
}