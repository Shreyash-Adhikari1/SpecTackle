package com.example.spectackle.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spectackle.adapter.CartAdapter
import com.example.spectackle.databinding.FragmentCartBinding
import com.example.spectackle.manager.CartManager
import java.text.NumberFormat
import java.util.Locale

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        updateCartTotal()
    }

    override fun onResume() {
        super.onResume()
        // Refresh cart data when fragment becomes visible
        adapter.updateCartItems(CartManager.getCartItems().toMutableList())
        updateCartTotal()
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(
            requireContext(),
            CartManager.getCartItems().toMutableList()
        ) {
            // This lambda is called when cart is updated
            updateCartTotal()
        }

        binding.cartRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CartFragment.adapter
        }
    }

    private fun updateCartTotal() {
        val total = CartManager.getCartTotal()
        // Format the total price
        val formatter = NumberFormat.getNumberInstance(Locale.getDefault())
        formatter.maximumFractionDigits = 2

        binding.cartRsTxt.text = "Rs. ${formatter.format(total)}"
    }
}