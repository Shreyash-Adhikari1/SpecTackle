package com.example.spectackle.model

import android.os.Parcel
import android.os.Parcelable

class WishlistModel(
    var wishlistId: String = "",
    var userId: String = "",
    var productId: String = "",
    var productName: String = "",
    var productImage: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(wishlistId)
        parcel.writeString(userId)
        parcel.writeString(productId)
        parcel.writeString(productName)
        parcel.writeString(productImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WishlistModel> {
        override fun createFromParcel(parcel: Parcel): WishlistModel {
            return WishlistModel(parcel)
        }

        override fun newArray(size: Int): Array<WishlistModel?> {
            return arrayOfNulls(size)
        }
    }
}
