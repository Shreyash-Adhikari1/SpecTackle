package com.example.spectackle.ui.activity.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.spectackle.R
import com.example.spectackle.adapter.SearchFragment
import com.example.spectackle.databinding.ActivityHomeBinding
import com.example.spectackle.ui.activity.admin.AddProductActivity
import com.example.spectackle.ui.fragment.CartFragment
import com.example.spectackle.ui.fragment.CategoryFragment
import com.example.spectackle.ui.fragment.HomeFragment
import com.example.spectackle.ui.fragment.ProfileFragment
import com.example.spectackle.ui.fragment.WishlistFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = FirebaseAuth.getInstance().currentUser
        val email = currentUser?.email ?: ""

        // Initialize drawer
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        // Toggle for Hamburger Menu
        binding.homeHamburger.setOnClickListener {
            drawerLayout.openDrawer(navigationView)
        }

        // Handle Navigation Menu Clicks
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_addProduct->{
                    if (email=="specsadmin@gmail.com"){
                        val intent=Intent(this@HomeActivity, AddProductActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@HomeActivity,"Only Admin Can Access",Toast.LENGTH_LONG).show()
                    }
                }
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_category -> replaceFragment(CategoryFragment())
                R.id.nav_cart -> replaceFragment(CartFragment())
                R.id.nav_wishlist-> replaceFragment(WishlistFragment())
            }
            drawerLayout.closeDrawers()
            true
        }

        binding.bottomNavigationView.setOnItemSelectedListener { menu ->
            when(menu.itemId){
                R.id.navHome -> replaceFragment(HomeFragment())
                R.id.navCategory -> replaceFragment(CategoryFragment())
                R.id.navCart->replaceFragment(CartFragment())
                R.id.navWishlist -> replaceFragment(WishlistFragment())

                else -> {}
            }
            true
        }

        binding.homeProfile.setOnClickListener {
            replaceFragment(ProfileFragment())
        }

        binding.searchIcon.setOnClickListener {
            replaceFragment(SearchFragment())
        }

        // Default Fragment
        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFrame, fragment)
        fragmentTransaction.commit()
    }
}
