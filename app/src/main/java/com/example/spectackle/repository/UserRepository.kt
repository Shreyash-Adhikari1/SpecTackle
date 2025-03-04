package com.example.spectackle.repository

import com.example.spectackle.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface UserRepository {
    fun login(email:String, password:String,
              callback: (Boolean,String)->Unit)


    fun signup(email:String, password:String,
               callback: (Boolean,String,String)->Unit)

    fun addUserToDatabase(userId: String, userModel: UserModel,
                          callback: (Boolean, String) -> Unit)

    fun forgetPassword(email: String,
                       callback: (Boolean, String) -> Unit)

    fun getCurrentUser(): FirebaseUser? // "?" denotes nullable meaning data may or may not exist
}