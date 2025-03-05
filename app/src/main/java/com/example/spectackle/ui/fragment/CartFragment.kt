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
import com.example.spectackle.adapter.CartAdapter
import com.example.spectackle.databinding.FragmentCartBinding
import com.example.spectackle.model.CartModel
import com.example.spectackle.model.ProductModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter
    private val cartItems = ArrayList<CartModel>()
    private val productMap = HashMap<String, ProductModel>()
    private val database = FirebaseDatabase.getInstance().reference
    private var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
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
        binding.cartRecycler.layoutManager = LinearLayoutManager(requireContext())
        cartAdapter = CartAdapter(requireContext(), cartItems, productMap,
            onRemoveClick = { cartId -> removeCartItem(cartId) },
            onQuantityChange = { cartId, newQuantity -> updateCartQuantity(cartId, newQuantity) }
        )
        binding.cartRecycler.adapter = cartAdapter

        // Load cart items
        fetchCartItems()
    }

    private fun fetchCartItems() {
        val cartRef = database.child("Cart").child(userId!!)

        cartRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartItems.clear()
                val productIds = mutableSetOf<String>()

                for (itemSnapshot in snapshot.children) {
                    val cartItem = itemSnapshot.getValue(CartModel::class.java)
                    cartItem?.let {
                        it.cartId = itemSnapshot.key ?: ""
                        cartItems.add(it)
                        productMap[it.productId] = ProductModel(
                            productId = it.productId,
                            productName = it.productName,
                            productPrice = it.productPrice,
                            productCategory = it.productCategory,
                            imageUrl = it.productImage
                        )

                    }
                }

                // Fetch product details only if there are products in the cart
                if (productIds.isNotEmpty()) {
                    fetchProductDetails(productIds)
                } else {
                    updateUI()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CartFragment", "Database error: ${error.message}")
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
                Log.e("CartFragment", "Failed to fetch product details: ${error.message}")
            }
        })
    }

    private fun updateUI() {
        cartAdapter.notifyDataSetChanged()

        val totalPrice = cartItems.sumOf {
            val price = productMap[it.productId]?.productPrice?.toDouble() ?: 0.0
            val quantity = it.quantity.toDouble() // Ensure quantity is a valid numeric type
            price * quantity
        }

        binding.cartTotal.text = "Total: $%.2f".format(totalPrice)
    }


    private fun removeCartItem(cartId: String) {
        val cartItemRef = database.child("Cart").child(userId!!).child(cartId)

        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Remove Item")
            .setMessage("Are you sure you want to remove this item from the cart?")
            .setPositiveButton("Yes") { dialog, _ ->
                cartItemRef.removeValue()
                    .addOnSuccessListener {
                        cartItems.removeAll { it.cartId == cartId }
                        updateUI()
                        Toast.makeText(requireContext(), "Item removed from cart", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Log.e("CartFragment", "Failed to remove item: ${e.message}")
                        Toast.makeText(requireContext(), "Failed to remove item", Toast.LENGTH_SHORT).show()
                    }
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .create()

        alertDialog.show()
    }

    private fun updateCartQuantity(cartId: String, newQuantity: Long) {
        val quantityRef = database.child("Cart").child(userId!!).child(cartId).child("quantity")

        quantityRef.setValue(newQuantity)
            .addOnSuccessListener {
                Log.d("CartFragment", "Quantity updated successfully")
                updateUI()
            }
            .addOnFailureListener { e ->
                Log.e("CartFragment", "Failed to update quantity: ${e.message}")
            }
    }

    private fun clearCart() {
        val cartRef = database.child("Cart").child(userId!!)
        cartRef.removeValue()
            .addOnSuccessListener {
                cartItems.clear()
                updateUI()
                Toast.makeText(requireContext(), "Cart cleared", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e("CartFragment", "Failed to clear cart: ${e.message}")
            }
    }
}
