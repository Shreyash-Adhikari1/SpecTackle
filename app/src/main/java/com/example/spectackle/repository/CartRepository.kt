package com.example.spectackle.repository

import com.example.spectackle.model.CartModel
import com.example.spectackle.model.ProductModel

interface CartRepository {

    fun addToCart(cartModel : CartModel, callback : (Boolean, String) -> Unit)

    fun updateCart(cartProductId : String, data : MutableMap<String, Any>,
                      callback : (Boolean, String)-> Unit)

    fun deleteFromCart(cartProductId : String, callback : (Boolean, String) -> Unit)

    fun getProductById(cartProductId : String, callback : (CartModel?, Boolean, String) -> Unit)

    fun getAllProduct(callback : (List<CartModel>?, Boolean, String) -> Unit)

    fun clearCart(callback: (Boolean, String) -> Unit)
}