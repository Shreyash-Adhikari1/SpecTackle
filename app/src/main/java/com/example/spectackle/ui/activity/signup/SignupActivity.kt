package com.example.spectackle.ui.activity.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.spectackle.R
import com.example.spectackle.databinding.ActivitySignupBinding
import com.example.spectackle.model.UserModel
import com.example.spectackle.repository.UserRepositoryImpl
import com.example.spectackle.ui.activity.loginui.LoginActivity
import com.example.spectackle.utils.LoadingUtils
import com.example.spectackle.viewmodel.UserViewModel


class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    lateinit var userViewModel: UserViewModel
    lateinit var loadingUtils: LoadingUtils



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //yeha initialize garney
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)


        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)


        binding.signUp.setOnClickListener {
            val email = binding.registerEmail.text.toString().trim()
            val password = binding.registerPassword.text.toString().trim()
            val firstName = binding.registerFname.text.toString().trim()
            val lastName = binding.registerLName.text.toString().trim()
            val address = binding.registerAddress.text.toString().trim()
            val phoneNumber = binding.registerContact.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() ||
                lastName.isEmpty() || address.isEmpty() || phoneNumber.isEmpty()
            ) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loadingUtils.show()

            userViewModel.signup(email, password) { success, message, userId ->
                if (success) {
                    val userModel =
                        UserModel(userId, firstName, lastName, address, phoneNumber, email)
                    userViewModel.addUserToDatabase(userId, userModel) { dbSuccess, dbMessage ->
                        loadingUtils.dismiss()
                        if (dbSuccess) {
                            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                            Toast.makeText(
                                this@SignupActivity,
                                "Signup Successful!",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@SignupActivity,
                                "Database Error: $dbMessage",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    loadingUtils.dismiss()
                    Toast.makeText(
                        this@SignupActivity,
                        "Signup Failed: $message",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}