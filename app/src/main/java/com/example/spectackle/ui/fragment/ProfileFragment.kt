package com.example.spectackle.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.spectackle.databinding.FragmentProfileBinding
import com.example.spectackle.repository.UserRepositoryImpl
import com.example.spectackle.ui.activity.loginui.LoginActivity
import com.example.spectackle.utils.FirebaseHelper
import com.example.spectackle.utils.Resource
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val userRepository = UserRepositoryImpl(FirebaseAuth.getInstance())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch user profile data and update UI
        loadUserProfile()

        // 🔥 Logout button click listener
        binding.textView22.setOnClickListener {
            FirebaseHelper.logout() // Call the logout function

            // Navigate back to LoginActivity
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadUserProfile() {
        userRepository.getUserProfile { result ->
            when (result) {
                is Resource.Success -> {
                    val user = result.data
                    binding.profileUserName.text = "UID: ${user?.uid ?: "N/A"}"
                    binding.profileUserEmail.text = "Email: ${user?.email ?: "N/A"}"
                }
                is Resource.Error -> {
                    binding.profileUserName.text = "Error"
                    binding.profileUserEmail.text = result.message
                }
                else -> {
                    binding.profileUserName.text = "Loading..."
                    binding.profileUserEmail.text = "Fetching profile..."
                }
            }
        }
    }
}
