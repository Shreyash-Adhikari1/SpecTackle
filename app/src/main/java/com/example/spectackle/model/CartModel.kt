package com.example.spectackle.model

import android.media.Image
import android.os.Parcel
import android.os.Parcelable

class CartModel(
    var cartId: String = "",
    var userId: String = "",
    var productId: String = "",
    var productName: String="",
    var productImage: String="",
    var quantity: Int = 1,
    var price: Int = 0,
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readInt()?:0,
        parcel.readInt()?:0,

        ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cartId)
        parcel.writeString(userId)
        parcel.writeString(productId)
        parcel.writeString(productName)
        parcel.writeString(productImage)
        parcel.writeInt(quantity)
        parcel.writeInt(price)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartModel> {
        override fun createFromParcel(parcel: Parcel): CartModel {
            return CartModel(parcel)
        }

        override fun newArray(size: Int): Array<CartModel?> {
            return arrayOfNulls(size)
        }
    }

}