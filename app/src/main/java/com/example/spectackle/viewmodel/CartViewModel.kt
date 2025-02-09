package com.example.spectackle.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spectackle.model.CartModel
import com.example.spectackle.repository.CartRepositoryImpl

class CartViewModel(private  val repo : CartRepositoryImpl) :ViewModel(){

    fun addToCart(
        cartModel: CartModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.addToCart(cartModel,callback)
    }

    fun updateCart(
        cartProductId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        repo.updateCart(cartProductId, data, callback)
    }

    fun deleteFromCart(
        cartProductId: String,
        callback: (Boolean, String) -> Unit
    ) {
        repo.deleteFromCart(cartProductId, callback)
    }

    var _cartProducts = MutableLiveData<CartModel?>()
    var cartProducts = MutableLiveData<CartModel?>()
        get() = _cartProducts

    var _allProducts = MutableLiveData<List<CartModel>?>()
    var allProducts = MutableLiveData<List<CartModel>?>()
        get() = _allProducts


    fun getProductById(cartProductId: String){
        repo.getProductById(cartProductId){
                cartProducts,success,message->
            if(success){
                _cartProducts.value = cartProducts
            }
        }
    }

    var _loading = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
        get() = _loading

    fun getAllProduct() {
        _loading.value = true
        repo.getAllProduct{
                cartProducts,success,message->
            if(success){
                _allProducts.value = cartProducts
                _loading.value = false
            }
        }
    }

    fun clearCart(){
        repo.clearCart { success, message ->
            if (success) {
                _allProducts.value = emptyList() // Clear the LiveData
            }
        }
    }
    }
