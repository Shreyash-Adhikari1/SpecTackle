package com.example.spectackle.manager

import com.example.spectackle.model.Product

object WishlistManager {
    private val wishlistItems = mutableListOf<Product>()

    fun addToWishlist(product: Product): Boolean {
        // Check if product already exists in wishlist
        val existingProduct = wishlistItems.find { it.name == product.name }
        if (existingProduct != null) {
            // If product exists, return false (already in wishlist)
            return false
        } else {
            // If not, add new product and return true
            wishlistItems.add(product)
            return true
        }
    }

    fun removeFromWishlist(product: Product) {
        wishlistItems.removeIf { it.name == product.name }
    }

    fun isInWishlist(productName: String): Boolean {
        return wishlistItems.any { it.name == productName }
    }

    fun getWishlistItems(): List<Product> {
        return wishlistItems.toList()
    }
}