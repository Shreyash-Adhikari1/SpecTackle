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

class HomeItemsAdapter(
    val context: Context,
    private val items: List<WishlistItem>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<HomeItemsAdapter.HomeItemsViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: WishlistItem)
    }

    class HomeItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.imageView12)
        val productName: TextView = itemView.findViewById(R.id.textView18)
        val productPrice: TextView = itemView.findViewById(R.id.textView19)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sample_wishlist, parent, false)
        return HomeItemsViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HomeItemsViewHolder, position: Int) {
        val item = items[position]
        holder.productImage.setImageResource(item.productImage)
        holder.productName.text = item.productName
        holder.productPrice.text = item.productPrice

        // On item click, trigger the listener
        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }
    }
}
