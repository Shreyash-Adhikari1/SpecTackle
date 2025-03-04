package com.example.spectackle.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spectackle.model.ProductModel
import com.example.spectackle.repository.ProductRepository


class ProductViewModel(val repo: ProductRepository) : ViewModel() {

    val products = MutableLiveData<ProductModel?>()  // Allow nullable value to avoid crash
    private val _allProducts = MutableLiveData<List<ProductModel>>().apply { value = emptyList() }
    val allProducts: MutableLiveData<List<ProductModel>> get() = _allProducts

    private val _loading = MutableLiveData<Boolean>()
    val loading: MutableLiveData<Boolean> get() = _loading

    fun addProduct(
        productModel: ProductModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.addProduct(productModel, callback)
    }

    fun updateProduct(
        productId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        repo.updateProduct(productId, data, callback)
    }

    fun deleteProduct(
        productId: String,
        callback: (Boolean, String) -> Unit
    ) {
        repo.deleteProduct(productId, callback)
    }

    fun getProductById(productId: String) {
        _loading.value = true
        repo.getProductById(productId) { product, success, message ->
            if (success) {
                products.value = product
            } else {
                products.value = null // Ensure null safety
            }
            _loading.value = false
        }
    }

    fun getAllProduct() {
        _loading.value = true
        repo.getAllProduct { productsList, success, message ->
            _allProducts.value = if (success && productsList != null) {
                productsList
            } else {
                emptyList() // Ensure non-nullable value
            }
            _loading.value = false
        }
    }

    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        repo.uploadImage(context, imageUri, callback)
    }
}
