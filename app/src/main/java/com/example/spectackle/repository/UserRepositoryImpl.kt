package com.example.spectackle.repository

import com.example.spectackle.model.UserModel
import com.example.spectackle.utils.Resource
import com.google.firebase.auth.FirebaseAuth

class UserRepositoryImpl(private val firebaseAuth: FirebaseAuth) : UserRepository {

    override fun register(email: String, password: String, callback: (Resource<UserModel>) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        callback(Resource.Success(UserModel(user.uid, user.email ?: "")))
                    } else {
                        callback(Resource.Error("User not found"))
                    }
                } else {
                    callback(Resource.Error(task.exception?.message ?: "Registration failed"))
                }
            }
    }

    override fun login(email: String, password: String, callback: (Resource<Unit>) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(Resource.Success(Unit))
                } else {
                    callback(Resource.Error(task.exception?.message ?: "Login failed"))
                }
            }
    }

    override fun getUserProfile(callback: (Resource<UserModel>) -> Unit) {
        val user = firebaseAuth.currentUser
        if (user != null) {
            callback(Resource.Success(UserModel(user.uid, user.email ?: "No Email Found")))
        } else {
            callback(Resource.Error("User not logged in"))
        }
    }

    // 🔥 Implement Delete User Functionality
    override fun deleteUser(callback: (Resource<Unit>) -> Unit) {
        val user = firebaseAuth.currentUser

        if (user != null) {
            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback(Resource.Success(Unit)) // Deletion successful
                    } else {
                        callback(Resource.Error(task.exception?.message ?: "Failed to delete user"))
                    }
                }
        } else {
            callback(Resource.Error("User not logged in"))
        }
    }
}
