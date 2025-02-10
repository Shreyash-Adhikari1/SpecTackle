package com.example.spectackle.repository

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.example.spectackle.model.ProductModel

class ProductRepositoryImpl : ProductRepository {

    // Sample data to work with in the repository
    private val productList = mutableListOf(
        ProductModel("1", "Sunglasses A", "Stylish sunglasses", 10),
        ProductModel("2", "glasses B", "Comfortable eyewear", 20),
        ProductModel("3", "eye-wear C", "Premium sunglasses", 30)
    )

    override fun searchProducts(query: String): List<ProductModel> {
        return productList.filter {
            it.productName.contains(query, ignoreCase = true) || it.productDesc.contains(query, ignoreCase = true)
        }
    }

    override fun addProduct(productModel: ProductModel, callback: (Boolean, String) -> Unit) {
        productList.add(productModel)
        callback(true, "Product added successfully")
    }

    override fun updateProduct(productId: String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit) {
        val product = productList.find { it.productId == productId }
        if (product != null) {
            // Update product data
            data.forEach { (key, value) ->
                when (key) {
                    "productName" -> product.productName = value as String
                    "productDesc" -> product.productDesc = value as String
                    "price" -> product.price = value as Int
                }
            }
            callback(true, "Product updated successfully")
        } else {
            callback(false, "Product not found")
        }
    }

    override fun deleteProduct(productId: String, callback: (Boolean, String) -> Unit) {
        val product = productList.find { it.productId == productId }
        if (product != null) {
            productList.remove(product)
            callback(true, "Product deleted successfully")
        } else {
            callback(false, "Product not found")
        }
    }

    override fun getProductById(productId: String, callback: (ProductModel?, Boolean, String) -> Unit) {
        val product = productList.find { it.productId == productId }
        if (product != null) {
            callback(product, true, "Product found")
        } else {
            callback(null, false, "Product not found")
        }
    }

    override fun getAllProduct(callback: (List<ProductModel>?, Boolean, String) -> Unit) {
        if (productList.isNotEmpty()) {
            callback(productList, true, "Products found")
        } else {
            callback(null, false, "No products found")
        }
    }

    override fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        // Placeholder logic to simulate image upload
        callback("Image uploaded successfully")
    }

    override fun getFileNameFromUri(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst()) {
                return it.getString(columnIndex)
            }
        }
        return null
    }
}
