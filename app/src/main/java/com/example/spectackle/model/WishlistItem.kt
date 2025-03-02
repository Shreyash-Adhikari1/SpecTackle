package com.example.spectackle.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WishlistItem(
    val productImage: Int,
    val productName: String,
    val productPrice: String,
    var isWishlisted: Boolean = false
) : Parcelable
