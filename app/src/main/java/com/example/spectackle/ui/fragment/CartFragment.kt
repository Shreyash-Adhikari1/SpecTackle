package com.example.spectackle.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.spectackle.adapter.CartAdapter
import com.example.spectackle.databinding.FragmentCartBinding
import com.example.spectackle.model.CartModel

class CartFragment : Fragment(), CartAdapter.OnQuantityChangeListener {

    private lateinit var binding: FragmentCartBinding
    private lateinit var adapter: CartAdapter
    private val cartItems = mutableListOf<CartModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sample data
        cartItems.addAll(listOf(
            CartModel("1", "user1", "prod1", "SpecX Gold Shades", "Gold frame shades", 1500, "https://example.com/specx_gold.jpg", 1),
            CartModel("2", "user1", "prod2", "SpecX Cate Green", "Cate design in green", 1335, "https://example.com/specx_cateye_green.jpg", 1),
            CartModel("3", "user1", "prod3", "Black & Gold", "Black and gold finish", 1900, "https://example.com/specx_black.jpg", 1)
        ))

        setupRecyclerView()
        updateTotal()
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(requireContext(), cartItems, this)
        binding.cartRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecycler.adapter = adapter
    }

    override fun onQuantityChanged() {
        updateTotal()
    }

    @SuppressLint("SetTextI18n")
    private fun updateTotal() {
        val total = cartItems.sumOf { it.cartProductPrice * it.quantity }
        binding.cartRsTxt.text = "Rs. $total"
    }
}