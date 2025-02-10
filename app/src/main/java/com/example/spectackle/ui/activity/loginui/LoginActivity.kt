package com.example.spectackle.ui.activity.loginui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.spectackle.R
import com.example.spectackle.databinding.ActivityLoginBinding
import com.example.spectackle.repository.UserRepositoryImpl
import com.example.spectackle.ui.activity.home.HomeActivity
import com.example.spectackle.ui.activity.signup.SignupActivity
import com.example.spectackle.utils.FirebaseHelper
import com.example.spectackle.utils.Resource
import com.example.spectackle.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.Factory(UserRepositoryImpl(FirebaseHelper.firebaseAuth))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate to SignupActivity
        binding.signUpText.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

        // Handle login button click
        binding.button.setOnClickListener {
            val email: String = binding.emailField.text.toString()
            val password: String = binding.passwordField.text.toString()

            if (email.isEmpty()) {
                binding.emailField.error = "Email Can't be Empty"
            } else if (password.isEmpty()) {
                binding.passwordField.error = "Password Can't be Empty"
            } else {
                // Perform Firebase authentication
                userViewModel.login(email, password)
            }
        }

        // Observe login status
        userViewModel.loginStatus.observe(this) { resource ->
            when (resource) {
                is Resource.Success<*> -> {
                    binding.progressBar.visibility = View.GONE // Hide progress bar
                    Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish() // Close the LoginActivity to prevent going back
                }
                is Resource.Error<*> -> {
                    binding.progressBar.visibility = View.GONE // Hide progress bar
                    val errorMessage = resource.message ?: "An unknown error occurred"
                    Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading<*> -> {
                    binding.progressBar.visibility = View.VISIBLE // Show progress bar
                }
            }
        }

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}