package com.example.spectackle.repository

import com.example.spectackle.model.UserModel
import com.example.spectackle.utils.Resource

interface UserRepository {
    fun register(email: String, password: String, callback: (Resource<UserModel>) -> Unit)
    fun login(email: String, password: String, callback: (Resource<Unit>) -> Unit)
    fun getUserProfile(callback: (Resource<UserModel>) -> Unit)
}