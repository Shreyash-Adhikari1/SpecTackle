package com.example.spectackle.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.spectackle.adapter.CartAdapter
import com.example.spectackle.databinding.FragmentCartBinding
import com.example.spectackle.model.CartModel
import com.example.spectackle.model.ProductModel
import com.example.spectackle.repository.CartRepository
import com.example.spectackle.repository.CartRepositoryImpl
import com.example.spectackle.repository.ProductRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import java.text.NumberFormat
import java.util.Locale

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartRepository: CartRepository
    private lateinit var productRepository: ProductRepositoryImpl

    private var cartList = ArrayList<CartModel>()
    private var productMap = HashMap<String, ProductModel>() // Maps productId to product details

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartRepository = CartRepositoryImpl()
        productRepository = ProductRepositoryImpl()

        setupRecyclerView()
        fetchCartItems()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(requireContext(), cartList, productMap,
            onRemoveClick = { cartId -> removeFromCart(cartId) },
            onQuantityChange = { cartId, newQuantity -> updateQuantity(cartId, newQuantity) }
        )

        val layoutManager = GridLayoutManager(requireContext(), 1)
        layoutManager.isSmoothScrollbarEnabled = true

        binding.cartRecycler.apply {
            this.layoutManager = layoutManager
            this.adapter = cartAdapter
            this.setHasFixedSize(true)
            this.itemAnimator = null
        }
    }

    private fun fetchCartItems() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val cartRef = FirebaseDatabase.getInstance().getReference("Cart").child(userId)

        cartRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartList.clear()
                for (itemSnapshot in snapshot.children) {
                    val cartItem = itemSnapshot.getValue(CartModel::class.java)
                    cartItem?.let { cartList.add(it) }
                }
                cartAdapter.notifyDataSetChanged()
                fetchProductDetails()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to load cart items", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchProductDetails() {
        val productIds = cartList.map { it.productId }.toSet()

        productIds.forEach { productId ->
            productRepository.getProductById(productId) { product, success, _ ->
                if (success && product != null) {
                    productMap[productId] = product
                    cartAdapter.notifyDataSetChanged()
                    updateTotalPrice()
                }
            }
        }
    }

    private fun removeFromCart(cartId: String) {
        cartRepository.removeFromCart(cartId) { success, _ ->
            if (success) {
                cartList.removeAll { it.cartId == cartId }
                cartAdapter.notifyDataSetChanged()
                updateTotalPrice()
            }
        }
    }

    private fun updateQuantity(cartId: String, newQuantity: Int) {
        cartRepository.updateCartItem(cartId, newQuantity) { success, _ ->
            if (success) {
                val cartItem = cartList.find { it.cartId == cartId }
                cartItem?.quantity = newQuantity
                cartAdapter.notifyDataSetChanged()
                updateTotalPrice()
            }
        }
    }

    private fun updateTotalPrice() {
        val totalPrice = cartList.sumOf { it.price * it.quantity }
        val formattedPrice = NumberFormat.getCurrencyInstance(Locale.US).format(totalPrice)
        binding.cartTotal.text = formattedPrice
    }
}
