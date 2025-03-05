package com.example.spectackle.ui.fragment


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spectackle.adapter.WishlistAdapter
import com.example.spectackle.databinding.FragmentWishlistBinding
import com.example.spectackle.model.ProductModel
import com.example.spectackle.model.WishlistModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class WishlistFragment : Fragment() {

    private lateinit var binding: FragmentWishlistBinding
    private lateinit var wishlistAdapter: WishlistAdapter
    private val wishlistItems = ArrayList<WishlistModel>()
    private val productMap = HashMap<String, ProductModel>()
    private val database = FirebaseDatabase.getInstance().reference
    private var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        // Set up RecyclerView
        binding.wishlistRecycler.layoutManager = LinearLayoutManager(requireContext())
        wishlistAdapter = WishlistAdapter(requireContext(), wishlistItems, productMap,
            onRemoveClick = { wishlistId -> removeFromWishlist(wishlistId) }
        )
        binding.wishlistRecycler.adapter = wishlistAdapter

        // Load wishlist items
        fetchWishlistItems()
    }

    private fun fetchWishlistItems() {
        val wishlistRef = database.child("Wishlist").child(userId!!)

        wishlistRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                wishlistItems.clear()
                val productIds = mutableSetOf<String>()

                for (itemSnapshot in snapshot.children) {
                    val wishlistItem = itemSnapshot.getValue(WishlistModel::class.java)
                    wishlistItem?.let {
                        it.wishlistId = itemSnapshot.key ?: ""
                        wishlistItems.add(it)
                        productMap[it.productId] = ProductModel(
                            productId = it.productId,
                            productName = it.productName,
                            imageUrl = it.productImage
                        )
                    }
                }

                // Fetch product details only if there are products in the wishlist
                if (productIds.isNotEmpty()) {
                    fetchProductDetails(productIds)
                } else {
                    updateUI()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("WishlistFragment", "Database error: ${error.message}")
            }
        })
    }

    private fun fetchProductDetails(productIds: Set<String>) {
        val productRef = database.child("products")

        productRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productMap.clear()

                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(ProductModel::class.java)
                    product?.let {
                        if (productIds.contains(it.productId)) {
                            productMap[it.productId] = it
                        }
                    }
                }

                updateUI()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("WishlistFragment", "Failed to fetch product details: ${error.message}")
            }
        })
    }

    private fun updateUI() {
        wishlistAdapter.notifyDataSetChanged()
    }

    private fun removeFromWishlist(wishlistId: String) {
        val wishlistRef = database.child("Wishlist").child(userId!!).child(wishlistId)

        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Remove Item")
            .setMessage("Are you sure you want to remove this item from the wishlist?")
            .setPositiveButton("Yes") { dialog, _ ->
                wishlistRef.removeValue()
                    .addOnSuccessListener {
                        wishlistItems.removeAll { it.wishlistId == wishlistId }
                        updateUI()
                        Toast.makeText(requireContext(), "Item removed from wishlist", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Log.e("WishlistFragment", "Failed to remove item: ${e.message}")
                        Toast.makeText(requireContext(), "Failed to remove item", Toast.LENGTH_SHORT).show()
                    }
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .create()

        alertDialog.show()
    }
}
