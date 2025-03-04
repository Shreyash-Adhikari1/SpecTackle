package com.example.spectackle.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.spectackle.adapter.WishlistAdapter
import com.example.spectackle.databinding.FragmentWishlistBinding
import com.example.spectackle.model.ProductModel
import com.example.spectackle.model.WishlistModel
import com.example.spectackle.repository.ProductRepositoryImpl
import com.example.spectackle.repository.WishlistRepository
import com.example.spectackle.repository.WishlistRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.NumberFormat
import java.util.Locale

class WishlistFragment : Fragment() {
    private lateinit var binding: FragmentWishlistBinding
    private lateinit var wishlistAdapter: WishlistAdapter
    private lateinit var wishlistRepository: WishlistRepository
    private lateinit var productRepository: ProductRepositoryImpl

    private var wishlistList = ArrayList<WishlistModel>()
    private var productMap = HashMap<String, ProductModel>() // Maps productId to product details

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wishlistRepository = WishlistRepositoryImpl()
        productRepository = ProductRepositoryImpl()

        setupRecyclerView()
        fetchCartItems()
    }

    private fun setupRecyclerView() {
        wishlistAdapter = WishlistAdapter(requireContext(), wishlistList, productMap,
            onRemoveClick = { wishlistId -> removeFromWishlist(wishlistId) },
        )

        val layoutManager = GridLayoutManager(requireContext(), 1)
        layoutManager.isSmoothScrollbarEnabled = true

        binding.wishlistRecycler.apply {
            this.layoutManager = layoutManager
            this.adapter = wishlistAdapter
            this.setHasFixedSize(true)
            this.itemAnimator = null
        }
    }



    private fun fetchCartItems() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val wishlistRef = FirebaseDatabase.getInstance().getReference("Wishlist").child(userId)

        wishlistRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                wishlistList.clear()
                for (itemSnapshot in snapshot.children) {
                    val cartItem = itemSnapshot.getValue(WishlistModel::class.java)
                    cartItem?.let { wishlistList.add(it) }
                }
                wishlistAdapter.notifyDataSetChanged()
                fetchProductDetails()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to load wishlist items", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchProductDetails() {
        val productIds = wishlistList.map { it.productId }.toSet()

        productIds.forEach { productId ->
            productRepository.getProductById(productId) { product, success, _ ->
                if (success && product != null) {
                    productMap[productId] = product
                    wishlistAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun removeFromWishlist(wishlistId: String) {
        wishlistRepository.removeFromWishlist(wishlistId) { success, _ ->
            if (success) {
                wishlistList.removeAll { it.wishlistId == wishlistId }
                wishlistAdapter.notifyDataSetChanged()

            }
        }
    }
}