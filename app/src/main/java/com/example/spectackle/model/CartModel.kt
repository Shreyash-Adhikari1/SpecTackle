package com.example.spectackle.model

import android.os.Parcel
import android.os.Parcelable

class CartModel(
    var cartProductId: String = "",
    var userId: String = "",
    var productId: String = "",
    var cartProductName: String = "",
    var cartProductDesc: String = "",
    var cartProductPrice: Int = 0,
    var cartImageUrl: String = "",
    var quantity: Int = 1
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cartProductId)
        parcel.writeString(userId)
        parcel.writeString(productId)
        parcel.writeString(cartProductName)
        parcel.writeString(cartProductDesc)
        parcel.writeInt(cartProductPrice)
        parcel.writeString(cartImageUrl)
        parcel.writeInt(quantity)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<CartModel> {
        override fun createFromParcel(parcel: Parcel): CartModel {
            return CartModel(parcel)
        }

        override fun newArray(size: Int): Array<CartModel?> {
            return arrayOfNulls(size)
        }
    }
}