package com.example.spectackle.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spectackle.R
import com.example.spectackle.model.ProductModel
import com.example.spectackle.ui.activity.admin.UpdateProductActivity

class ProductAdapter(
    private val context: Context,
    private var data: ArrayList<ProductModel>,
    private val onAddToCartClick: (ProductModel) -> Unit,
    private val onAddToWishlistClick: (ProductModel)->Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pName: TextView = itemView.findViewById(R.id.displayProductName)
        val pPrice: TextView = itemView.findViewById(R.id.displayProductPrice)
        val pDesc: TextView = itemView.findViewById(R.id.displayProductDesc)
        val pImage: ImageView = itemView.findViewById(R.id.displayProductImage)
        val edit: TextView = itemView.findViewById(R.id.lblEdit)
        val AddToCart: ImageView = itemView.findViewById(R.id.addToCart)
        val AddToWishlist: ImageView = itemView.findViewById(R.id.addToWishlist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.sample_products, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = data[position]

        holder.pName.text = product.productName
        holder.pPrice.text = product.productPrice.toString()
        holder.pDesc.text = product.productDesc

        // Load the image using Glide
        Glide.with(context)
            .load(product.productImage)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(holder.pImage)

        holder.edit.setOnClickListener {
            val intent = Intent(context, UpdateProductActivity::class.java)
            intent.putExtra("productId", product.productId)
            context.startActivity(intent)
        }

        holder.AddToCart.setOnClickListener {
            Log.d("CartDebug", "Add to Cart clicked for product: ${product.productName}")
            onAddToCartClick(product)
        }

        holder.AddToWishlist.setOnClickListener {
            Log.d("WishlistDebug","Add to Wishlist click for product: ${product.productName}")
            onAddToWishlistClick(product)
        }
    }

    fun updateData(products: List<ProductModel>) {
        data.clear()
        data.addAll(products)
        notifyDataSetChanged()
    }
}