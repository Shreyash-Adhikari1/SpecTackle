package com.example.spectackle.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spectackle.R
import com.example.spectackle.ui.activity.ProductDetailActivity

class HomeProductsAdapter(
    val context: Context,
    val imageList: ArrayList<Int>,
    val nameList: ArrayList<String>,
    val priceList: ArrayList<String>
) : RecyclerView.Adapter<HomeProductsAdapter.HomeViewHolder>() {

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.homeProductImage)
        var name: TextView = itemView.findViewById(R.id.homeProductName)
        var price: TextView = itemView.findViewById(R.id.homeProductPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.sample_home_products, parent, false)
        return HomeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.image.setImageResource(imageList[position])
        holder.name.text = nameList[position]
        holder.price.text = priceList[position]

        // **Set Click Listener to Open Product Detail Activity**
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("productName", nameList[position])
            intent.putExtra("productPrice", priceList[position])
            intent.putExtra("productImage", imageList[position]) // Sending image resource ID
            context.startActivity(intent)
        }
    }
}
