package com.example.spectackle.repository

import com.example.spectackle.model.WishlistModel

interface WishlistRepository {

    fun addToWishlist(wishlistModel: WishlistModel, callback: (Boolean, String) -> Unit)

    fun removeFromWishlist(wishlistId: String, callback: (Boolean, String) -> Unit)

    fun updateWishlistItem(wishlistId: String, quantity: Int, callback: (Boolean, String) -> Unit)

    fun getWishlistItems(userId: String, callback: (List<WishlistModel>?, Boolean, String) -> Unit)
}
