package com.example.spectackle.ui.activity.profilehome

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.spectackle.databinding.ProfileHomeBinding
import com.example.spectackle.ui.activity.loginui.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileHome : AppCompatActivity() {

    private lateinit var binding: ProfileHomeBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        loadUserData()

        binding.logoutButton.setOnClickListener {
            logoutUser()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadUserData() {
        val user: FirebaseUser? = auth.currentUser
        user?.let {
            binding.userNameText.text = "Name: ${it.displayName ?: "N/A"}"
            binding.userEmailText.text = "Email: ${it.email ?: "N/A"}"
            binding.userIdText.text = "User ID: ${it.uid}"
        }
    }

    private fun logoutUser() {
        auth.signOut()
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
