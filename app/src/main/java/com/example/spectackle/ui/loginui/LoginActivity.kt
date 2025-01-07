package com.example.spectackle.ui.loginui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.spectackle.R
import com.example.spectackle.databinding.ActivityLoginBinding
import com.example.spectackle.ui.dashboard.DashboardActivity
import com.example.spectackle.ui.signup.SignupActivity

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.signUpText.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity :: class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val username: String = binding.emailField.text.toString()
            val password: String = binding.passwordField.text.toString()
            if (username.isEmpty()) {
                binding.emailField.error = "Username Can't be Empty"
            } else if (password.isEmpty()) {
                binding.passwordField.error = "Password Can't be Empty"
            }else{
                Toast.makeText(this@LoginActivity,"Login successful",Toast.LENGTH_LONG).show()
                val intent= Intent(this@LoginActivity, DashboardActivity :: class.java)
                startActivity(intent)
            }




            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
}