package com.example.spectackle.ui.activity.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.spectackle.R
import com.example.spectackle.adapter.HomeProductsAdapter
import com.example.spectackle.databinding.ActivityHomeBinding
import com.example.spectackle.ui.fragment.CartFragment
import com.example.spectackle.ui.fragment.CategoryFragment
import com.example.spectackle.ui.fragment.HomeFragment
import com.example.spectackle.ui.fragment.WishlistFragment

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityHomeBinding.inflate(layoutInflater);
        setContentView(binding.root)
        //default Fragment set to home fragment
        replaceFragment(HomeFragment())



        binding.bottomNavigationView.setOnItemSelectedListener { menu ->
            when(menu.itemId){
                R.id.navHome -> replaceFragment(HomeFragment())
                R.id.navCategory -> replaceFragment(CategoryFragment())
                R.id.navCart -> replaceFragment(CartFragment())
                R.id.navWishlist -> replaceFragment(WishlistFragment())
                else -> {}
            }
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun replaceFragment(fragment:Fragment) {
        val fragmentManager: FragmentManager =supportFragmentManager
        val fragmentTransaction: FragmentTransaction =fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFrame,fragment)
        fragmentTransaction.commit()

    }
}