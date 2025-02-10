package com.example.spectackle.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spectackle.R
import com.example.spectackle.model.ProductModel

class HomeProductsAdapter(
    private var productList: List<ProductModel>  // This is the list of products
) : RecyclerView.Adapter<HomeProductsAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productDescription: TextView = itemView.findViewById(R.id.productDescription)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productName.text = product.productName  // Use productName from ProductModel
        holder.productDescription.text = product.productDesc  // Use productDesc from ProductModel
        holder.productPrice.text = "Price: $${product.price}"
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<ProductModel>) {
        productList = newList
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }
}
