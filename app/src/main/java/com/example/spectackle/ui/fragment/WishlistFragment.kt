//package com.example.spectackle.ui.fragment
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.spectackle.R
//import com.example.spectackle.adapter.WishlistAdapter
//import com.example.spectackle.model.WishlistItem
//
//class WishlistFragment : Fragment() {
//
//    private lateinit var wishlistRecyclerView: RecyclerView
//    private lateinit var wishlistAdapter: WishlistAdapter
//    private val wishlistItems = mutableListOf<WishlistItem>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)
//
//        wishlistRecyclerView = view.findViewById(R.id.wishlistRecycler)
//        wishlistRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        wishlistAdapter = WishlistAdapter(requireContext(), wishlistItems) { item, isWishlisted ->
//            if (!isWishlisted) {
//                wishlistItems.remove(item)
//                wishlistAdapter.notifyDataSetChanged()
//            }
//        }
//
//        wishlistRecyclerView.adapter = wishlistAdapter
//
//        val addButton: Button = view.findViewById(R.id.button2)
//        addButton.setOnClickListener {
//            val newItem = WishlistItem(
//                productImage = R.drawable.sunglasses,
//                productName = "New Product",
//                productPrice = "$99.99",
//                isWishlisted = true
//            )
//            wishlistAdapter.addItem(newItem)
//        }
//
//        return view
//    }
//}
