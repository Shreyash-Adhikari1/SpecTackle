package com.example.spectackle.ui.activity.signup

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
import com.example.spectackle.databinding.ActivitySignupBinding
import com.example.spectackle.model.UserModel
import com.example.spectackle.repository.UserRepositoryImpl
import com.example.spectackle.ui.activity.dashboard.DashboardActivity
import com.example.spectackle.utils.FirebaseHelper
import com.example.spectackle.utils.Resource
import com.example.spectackle.viewmodel.UserViewModel

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.Factory(UserRepositoryImpl(FirebaseHelper.firebaseAuth))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle sign-up button click
        binding.signUpBtn.setOnClickListener {
            val email: String = binding.regEmail.text.toString()
            val password: String = binding.regPassword.text.toString()

            if (email.isEmpty()) {
                binding.regEmail.error = "Email Can't be Empty"
            } else if (password.isEmpty()) {
                binding.regPassword.error = "Password Can't be Empty"
            } else {
                // Perform Firebase registration
                userViewModel.register(email, password)
            }
        }

        // Observe registration status
        userViewModel.registerStatus.observe(this) { resource ->
            when (resource) {
                is Resource.Success<UserModel> -> {
                    binding.progressBar.visibility = View.GONE // Hide progress bar
                    Toast.makeText(this@SignupActivity, "Registration successful", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@SignupActivity, DashboardActivity::class.java)
                    startActivity(intent)
                    finish() // Close the SignupActivity to prevent going back
                }
                is Resource.Error<UserModel> -> {
                    binding.progressBar.visibility = View.GONE // Hide progress bar
                    val errorMessage = resource.message ?: "An unknown error occurred"
                    Toast.makeText(this@SignupActivity, errorMessage, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading<UserModel> -> {
                    binding.progressBar.visibility = View.VISIBLE // Show progress bar
                }
            }
        }

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homeLogo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}