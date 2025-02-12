package com.example.spectackle.ui.activity.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.spectackle.R
import com.example.spectackle.databinding.ActivityHomeBinding
import com.example.spectackle.ui.fragment.CartFragment
import com.example.spectackle.ui.fragment.CategoryFragment
import com.example.spectackle.ui.fragment.HomeFragment
import com.example.spectackle.ui.fragment.WishlistFragment
import com.example.spectackle.ui.activity.profilehome.ProfileHome

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.navHome -> replaceFragment(HomeFragment())
                R.id.navCategory -> replaceFragment(CategoryFragment())
                R.id.navCart -> replaceFragment(CartFragment())
                R.id.navWishlist -> replaceFragment(WishlistFragment())
                R.id.navProfile -> startActivity(Intent(this, ProfileHome::class.java))
                else -> {}
            }
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.mainFrame, fragment).commit()
    }
}
