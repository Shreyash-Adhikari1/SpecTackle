package com.example.spectackle.repository

import com.google.firebase.auth.FirebaseAuth

class AuthRepoImpl(var auth: FirebaseAuth) : AuthRepo{

    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    callback(true,"Login Successful")
                }else{
                    callback(false,it.exception?.message.toString())
                }

            }

    }

    override fun signup(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            callback(true, "Registration Successful", userId)
                        } else {
                            callback(false, "Error: User ID is null", "")
                        }
                    } else {
                        callback(false, task.exception?.localizedMessage ?: "Unknown error", "")
                    }
                }
        }


    }

