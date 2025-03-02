package com.example.spectackle.manager

import com.example.spectackle.model.Product

object CartManager {
    private val cartItems = mutableListOf<Product>()

    fun addToCart(product: Product) {
        // Check if product already exists in cart
        val existingProduct = cartItems.find { it.name == product.name }
        if (existingProduct != null) {
            // If product exists, increment quantity
            existingProduct.quantity++
        } else {
            // If not, add new product
            cartItems.add(product)
        }
    }

    fun removeFromCart(product: Product) {
        cartItems.remove(product)
    }

    fun getCartItems(): List<Product> {
        return cartItems.toList()
    }

    fun getCartTotal(): Double {
        return cartItems.sumOf {
            // Remove "Rs " and convert to double, then multiply by quantity
            val priceValue = it.price.replace("Rs ", "").replace(",", "").toDouble()
            priceValue * it.quantity
        }
    }

    fun clearCart() {
        cartItems.clear()
    }
}