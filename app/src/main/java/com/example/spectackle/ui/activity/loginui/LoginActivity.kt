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

        binding.signUpText.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.button.setOnClickListener {
            val email = binding.emailField.text.toString()
            val password = binding.passwordField.text.toString()

            if (email.isEmpty()) {
                binding.emailField.error = "Email Can't be Empty"
            } else if (password.isEmpty()) {
                binding.passwordField.error = "Password Can't be Empty"
            } else {
                userViewModel.login(email, password)
            }
        }

        userViewModel.loginStatus.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, resource.message ?: "Login failed", Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
