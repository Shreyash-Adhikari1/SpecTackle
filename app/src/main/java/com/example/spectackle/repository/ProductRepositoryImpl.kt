package com.example.spectackle.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.spectackle.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.InputStream
import java.util.concurrent.Executors

class ProductRepositoryImpl : ProductRepository {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val ref: DatabaseReference = database.reference.child("products")

    override fun addProduct(
        productModel: ProductModel,
        callback: (Boolean, String) -> Unit
    ) {
        var id = ref.push().key.toString()
        productModel.productId = id

        ref.child(id).setValue(productModel).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Product Added successfully")
            } else {
                callback(false, "${it.exception?.message}")

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
                callback(false, "${it.exception?.message}")

            }
        }
    }

    override fun deleteProduct(productId: String, callback: (Boolean, String) -> Unit) {
        ref.child(productId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Product Deleted successfully")
            } else {
                callback(false, "${it.exception?.message}")

            }
        }
    }

    override fun getProductById(
        productId: String,
        callback: (ProductModel?, Boolean, String) -> Unit
    ) {
        ref.child(productId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var model = snapshot.getValue(ProductModel::class.java)
                    callback(model, true, "Product fetched successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

    override fun getAllProduct(callback: (List<ProductModel>?, Boolean, String) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var products = mutableListOf<ProductModel>()
                if (snapshot.exists()) {
                    for (eachProduct in snapshot.children) {
                        var data = eachProduct.getValue(ProductModel::class.java)
                        if (data != null) {
                            products.add(data)
                        }
                    }
                    callback(products, true, "product added successfully")
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
        ref.orderByChild("productCategory").equalTo(category)
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
                        callback(null, false, "No products found")
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

                val response = cloudinary.uploader().upload(
                    inputStream, ObjectUtils.asMap(
                        "folder", "spectackle_products",
                        "public_id", "products/$fileName",
                        "resource_type", "image"
                    )
                )



                var imageUrl = response["url"] as String?

                imageUrl = imageUrl?.replace("http://", "https://")

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
