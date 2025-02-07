package com.example.spectackle.repository

import com.example.spectackle.model.CartModel
import com.example.spectackle.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartRepositoryImpl : CartRepository {
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val ref : DatabaseReference = database.reference.child("cart")

    override fun addToCart(cartModel: CartModel, callback: (Boolean, String) -> Unit) {
        var id = ref.push().key.toString()
        cartModel.cartProductId = id

        ref.child(id).setValue(cartModel).addOnCompleteListener{
            if(it.isSuccessful){
                callback(true,"Product Added to Cart Successfully")
            } else{
                callback(false,"${it.exception?.message.toString()}")
            }
        }
    }

    override fun updateCart(
        cartProductId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(cartProductId).updateChildren(data).addOnCompleteListener{
            if(it.isSuccessful){
                callback(true,"Product Added to Cart Successfully")
            } else{
                callback(false,"${it.exception?.message}")
            }
        }
    }

    override fun deleteFromCart(cartProductId: String, callback: (Boolean, String) -> Unit) {
        ref.child(cartProductId).removeValue().addOnCompleteListener{
            if(it.isSuccessful){
                callback(true,"Product Deleted from Cart Successfully")
            } else{
                callback(false,"${it.exception?.message}")
            }
        }
    }

    override fun getProductById(
        cartProductId: String,
        callback: (CartModel?, Boolean, String) -> Unit
    ) {
        ref.child(cartProductId).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var model = snapshot.getValue(CartModel::class.java)
                    callback(model,true,"Product Fetched Successfully")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.toString())
            }
        })
    }

    override fun getAllProduct(callback: (List<CartModel>?, Boolean, String) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var cartProducts = mutableListOf<CartModel>()
                if(snapshot.exists()){
                    for(eachProduct in snapshot.children){
                        var data = eachProduct.getValue(CartModel::class.java)
                        if(data != null){
                            cartProducts.add(data)
                        }
                    }
                    callback(cartProducts,true,"Product Fetched to CartSuccessfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null,false,error.toString())
            }
        })
    }

    override fun clearCart(callback: (Boolean, String) -> Unit) {
        TODO("Not yet implemented")
    }


}