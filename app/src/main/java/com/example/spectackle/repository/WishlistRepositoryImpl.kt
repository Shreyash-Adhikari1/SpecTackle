package com.example.spectackle.repository

import android.util.Log
import com.example.spectackle.model.WishlistModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class WishlistRepositoryImpl : WishlistRepository {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val ref: DatabaseReference = database.reference.child("Wishlist")

    override fun addToWishlist(wishlistModel: WishlistModel, callback: (Boolean, String) -> Unit) {
        val wishlistId = ref.push().key
        if (wishlistId == null) {
            callback(false, "Failed to generate wishlist ID")
            return
        }

        wishlistModel.wishlistId = wishlistId

        // Log Firebase path and data
        Log.d("WishlistDebug", "Saving to Firebase path: wishlist/$wishlistId")
        Log.d("WishlistDebug", "Wishlist Data: $wishlistModel")

        ref.child(wishlistId).setValue(wishlistModel)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, "Added to wishlist")
                } else {
                    callback(false, task.exception?.message ?: "Unknown error")
                }
            }
    }



    override fun removeFromWishlist(wishlistId: String, callback: (Boolean, String) -> Unit) {
        ref.child(wishlistId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Removed from wishlist")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun updateWishlistItem(
        wishlistId: String,
        quantity: Int,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(wishlistId).child("quantity").setValue(quantity).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Wishlist updated")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun getWishlistItems(
        userId: String,
        callback: (List<WishlistModel>?, Boolean, String) -> Unit
    ) {
        ref.orderByChild("userId").equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val wishlistItems = mutableListOf<WishlistModel>()
                    for (item in snapshot.children) {
                        val wishlistItem = item.getValue(WishlistModel::class.java)
                        if (wishlistItem != null) {
                            wishlistItems.add(wishlistItem)
                        }
                    }
                    callback(wishlistItems, true, "Wishlist items fetched")
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null, false, error.message)
                }
            })
    }
}