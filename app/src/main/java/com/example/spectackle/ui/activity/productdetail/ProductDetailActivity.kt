package com.example.spectackle.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.spectackle.R
import com.example.spectackle.databinding.ActivityProductDetailBinding
import com.example.spectackle.manager.CartManager
import com.example.spectackle.manager.WishlistManager
import com.example.spectackle.model.Product

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var currentProduct: Product
    private var isInWishlist = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Receive Data from Intent
        val productName = intent.getStringExtra("productName") ?: ""
        val productPrice = intent.getStringExtra("productPrice") ?: ""
        val productImage = intent.getIntExtra("productImage", 0) // Default to 0 if missing

        // Create product object
        currentProduct = Product(productName, productPrice, productImage)

        // Check if product is already in wishlist
        isInWishlist = WishlistManager.isInWishlist(productName)
        updateWishlistIcon()

        // Set Data to UI
        binding.productName.text = productName
        binding.productPrice.text = productPrice
        binding.productImage.setImageResource(productImage) // Set Image

        // Add to Cart Button Click
        binding.addToCartButton.setOnClickListener {
            CartManager.addToCart(currentProduct)
            Toast.makeText(this, "$productName added to cart", Toast.LENGTH_SHORT).show()
        }

        // Wishlist Button Click
        binding.wishlistButton.setOnClickListener {
            if (isInWishlist) {
                // Remove from wishlist
                WishlistManager.removeFromWishlist(currentProduct)
                isInWishlist = false
                Toast.makeText(this, "$productName removed from wishlist", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Add to wishlist
                WishlistManager.addToWishlist(currentProduct)
                isInWishlist = true
                Toast.makeText(this, "$productName added to wishlist", Toast.LENGTH_SHORT).show()
            }
            updateWishlistIcon()
        }
    }

    private fun updateWishlistIcon() {
        // Update the wishlist icon based on whether the product is in the wishlist
        val wishlistIcon = if (isInWishlist) {
            R.drawable.wish_filled
        } else {
            // You need to create or use an empty wishlist icon
            // For now, using wish_filled as placeholder
            R.drawable.wish_filled // Ideally replace with wish_empty or similar
        }
        binding.wishlistButton.setImageResource(wishlistIcon)
    }
}