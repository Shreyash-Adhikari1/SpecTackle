package com.example.spectackle.utils

import com.google.firebase.auth.FirebaseAuth

object FirebaseHelper {
    val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun logout(){
        firebaseAuth.signOut()
    }
}
