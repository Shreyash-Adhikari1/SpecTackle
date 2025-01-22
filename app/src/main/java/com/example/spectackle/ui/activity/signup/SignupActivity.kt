package com.example.spectackle.ui.activity.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.spectackle.R
import com.example.spectackle.databinding.ActivityLoginBinding
import com.example.spectackle.databinding.ActivitySignupBinding
import com.example.spectackle.ui.activity.loginui.LoginActivity

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signIn.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.signUpBtn.setOnClickListener {
            val username: String = binding.regEmail.text.toString()
            val password: String = binding.regPassword.text.toString()
            val number: String = binding.regNumber.text.toString()
            if (username.isEmpty()) {
                binding.regEmail.error = "Email Can't be Empty"
            } else if (password.isEmpty()) {
                binding.regPassword.error = "Password Can't be Empty"
            } else if (number.isEmpty()) {
                binding.regNumber.error = "Number Can't be Empty "

            }
//            else {
//                if (binding.rememberMe.isChecked){
//                    val editor= sharedPreferences.edit()
//
//                    editor.putString("username",username)
//                    editor.putString("password",password)
//                    editor.apply()
//
//                }



            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
}