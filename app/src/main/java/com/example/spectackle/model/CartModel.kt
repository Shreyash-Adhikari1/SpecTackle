package com.example.spectackle.model

import android.os.Parcel
import android.os.Parcelable

class CartModel (
    var cartProductId : String = "",
    var cartProductName : String = "",
    var cartProductDesc : String = "",
    var cartProductPrice : Int = 0,
    var cartImageUrl : String = "",
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readInt()?:0,
        parcel.readString()?:""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cartProductId)
        parcel.writeString(cartProductName)
        parcel.writeString(cartProductDesc)
        parcel.writeInt(cartProductPrice)
        parcel.writeString(cartImageUrl)
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