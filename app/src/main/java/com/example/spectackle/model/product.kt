package com.example.spectackle.model

data class Product(
    val name: String,
    val price: String,
    val imageResource: Int,
    var quantity: Int = 1 // Default quantity for cart items
)