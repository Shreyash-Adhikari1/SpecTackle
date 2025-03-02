package com.example.spectackle.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spectackle.adapter.WishlistAdapter
import com.example.spectackle.databinding.FragmentWishlistBinding
import com.example.spectackle.manager.WishlistManager

class WishlistFragment : Fragment() {

    private lateinit var binding: FragmentWishlistBinding
    private lateinit var adapter: WishlistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        // Refresh wishlist data when fragment becomes visible
        adapter.updateWishlistItems(WishlistManager.getWishlistItems().toMutableList())
    }

    private fun setupRecyclerView() {
        adapter = WishlistAdapter(
            requireContext(),
            WishlistManager.getWishlistItems().toMutableList()
        )

        binding.wishlistRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@WishlistFragment.adapter
        }
    }
}