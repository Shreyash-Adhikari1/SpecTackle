package com.example.spectackle.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns
import android.util.Log
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.spectackle.model.ProductModel
import com.google.firebase.database.*
import java.io.InputStream
import java.util.concurrent.Executors

class ProductRepositoryImpl : ProductRepository {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val ref: DatabaseReference = database.reference.child("products")

    override fun addProduct(
        productModel: ProductModel,
        callback: (Boolean, String) -> Unit
    ) {
        val id = ref.push().key ?: return callback(false, "Failed to generate product ID")
        productModel.productId = id

        // Ensure correct database structure
        ref.child(productModel.productCategory).child(id).setValue(productModel)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FirebaseSuccess", "Product added: ${productModel.productName}")
                    callback(true, "Product Added successfully")
                } else {
                    Log.e("FirebaseError", "Error adding product: ${task.exception?.message}")
                    callback(false, task.exception?.message ?: "Unknown error")
                }
            }
    }

    override fun updateProduct(
        productId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(productId).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Product Updated successfully")
            } else {
                callback(false, it.exception?.message ?: "Unknown error")
            }
        }
    }

    override fun deleteProduct(productId: String, callback: (Boolean, String) -> Unit) {
        ref.child(productId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Product Deleted successfully")
            } else {
                callback(false, it.exception?.message ?: "Unknown error")
            }
        }
    }

    override fun getProductById(
        productId: String,
        callback: (ProductModel?, Boolean, String) -> Unit
    ) {
        ref.child(productId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val model = snapshot.getValue(ProductModel::class.java)
                    callback(model, true, "Product fetched successfully")
                } else {
                    callback(null, false, "Product not found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

    override fun getAllProduct(callback: (List<ProductModel>?, Boolean, String) -> Unit) {
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = mutableListOf<ProductModel>()
                if (snapshot.exists()) {
                    for (eachProduct in snapshot.children) {
                        val data = eachProduct.getValue(ProductModel::class.java)
                        if (data != null) {
                            products.add(data)
                        }
                    }
                    callback(products, true, "Products fetched successfully")
                } else {
                    callback(emptyList(), false, "No products found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

    override fun getProductByCategory(
        category: String,
        callback: (List<ProductModel>?, Boolean, String) -> Unit
    ) {
        ref.child(category)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val products = mutableListOf<ProductModel>()
                    if (snapshot.exists()) {
                        for (eachProduct in snapshot.children) {
                            val data = eachProduct.getValue(ProductModel::class.java)
                            if (data != null) {
                                products.add(data)
                            }
                        }
                        callback(products, true, "Products fetched successfully")
                    } else {
                        callback(emptyList(), false, "No products found")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null, false, error.message)
                }
            })
    }

    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dccum3yur",
            "api_key" to "293776692987476",
            "api_secret" to "vlAa3_qbiszuCDGwTomGaXb9GHI"
        )
    )

    override fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
                var fileName = getFileNameFromUri(context, imageUri)

                fileName = fileName?.substringBeforeLast(".") ?: "uploaded_image"

                // Ensure unique filename to prevent overwriting
                val uniqueFileName = "${System.currentTimeMillis()}_$fileName"

                val response = cloudinary.uploader().upload(
                    inputStream, ObjectUtils.asMap(
                        "folder", "spectackle_products",
                        "public_id", "products/$uniqueFileName",
                        "resource_type", "image"
                    )
                )

                var imageUrl = response["secure_url"] as String?

                Handler(Looper.getMainLooper()).post {
                    callback(imageUrl)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    callback(null)
                }
            }
        }
    }

    override fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var fileName: String? = null
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }
}
