package com.example.spectackle.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spectackle.R

class SearchAdapter(private var items: List<SearchItem>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item.name

        // Using Glide to load image into the ImageView
        Glide.with(holder.itemView.context)
            .load(item.imageResId)
            .into(holder.itemImage)
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<SearchItem>) {
        items = newList
        notifyDataSetChanged()
    }
}
