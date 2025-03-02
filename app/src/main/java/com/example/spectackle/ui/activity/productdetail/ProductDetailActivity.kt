package com.example.spectackle.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.spectackle.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Receive Data from Intent
        val productName = intent.getStringExtra("productName")
        val productPrice = intent.getStringExtra("productPrice")
        val productImage = intent.getIntExtra("productImage", 0) // Default to 0 if missing

        // Set Data to UI
        binding.productName.text = productName
        binding.productPrice.text = "Rs $productPrice"
        binding.productImage.setImageResource(productImage) // Set Image

        // Add to Cart Button Click
        binding.addToCartButton.setOnClickListener {
            // Handle adding to cart
        }

        // Wishlist Button Click
        binding.wishlistButton.setOnClickListener {
            // Handle adding to wishlist
        }
    }
}
