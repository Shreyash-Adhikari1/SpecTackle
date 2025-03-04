package com.example.spectackle.model

import android.os.Parcel
import android.os.Parcelable

class
ProductModel(
    var productId: String = "",
    var productName: String = "",
    var productDesc: String = "",
    var productCategory: String="",
    var productPrice: Int=0,
    var productImage: String="",
    var imageUrl: String = "",

    ):Parcelable  {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readInt()?:0,
        parcel.readString()?:"",
        parcel.readString()?:""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productId)
        parcel.writeString(productName)
        parcel.writeString(productDesc)
        parcel.writeString(productCategory)
        parcel.writeInt(productPrice)
        parcel.writeString(productImage)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductModel> {
        override fun createFromParcel(parcel: Parcel): ProductModel {
            return ProductModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductModel?> {
            return arrayOfNulls(size)
        }
    }
}