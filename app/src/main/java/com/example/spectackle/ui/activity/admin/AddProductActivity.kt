package com.example.spectackle.ui.activity.admin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.spectackle.R
import com.example.spectackle.databinding.ActivityAddProductBinding
import com.example.spectackle.model.ProductModel
import com.example.spectackle.repository.ProductRepositoryImpl
import com.example.spectackle.ui.activity.home.HomeActivity
import com.example.spectackle.utils.ImageUtils
import com.example.spectackle.utils.LoadingUtils
import com.example.spectackle.viewmodel.ProductViewModel

class AddProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var loadingUtils: LoadingUtils
    private lateinit var imageUtils: ImageUtils

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)
        imageUtils = ImageUtils(this)

        val repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)

        imageUtils.registerActivity { url ->
            url?.let {
                imageUri = it
                Glide.with(this)
                    .load(it)
                    .placeholder(R.drawable.placeholder)
                    .into(binding.imageBrowse)
            }
        }

        binding.imageBrowse.setOnClickListener {
            imageUtils.launchGallery(this)
        }

        binding.btnAddProduct.setOnClickListener {
            Log.d("Image Upload", "Uploading image: $imageUri")
            uploadImage()
        }

        binding.backHome.setOnClickListener {
            val intent= Intent(this@AddProductActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun uploadImage() {
        loadingUtils.show()
        imageUri?.let { uri ->
            productViewModel.uploadImage(this, uri) { imageUrl ->
                Log.d("Upload Status", imageUrl.toString())
                if (imageUrl != null) {
                    addProduct(imageUrl)
                } else {
                    Log.e("Upload Error", "Failed to upload image to Cloudinary")
                    loadingUtils.dismiss()
                }
            }
        } ?: run {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_LONG).show()
            loadingUtils.dismiss()
        }
    }

    private fun addProduct(url: String) {
        val productName = binding.editProductName.text.toString().trim()
        val productDesc = binding.editProductDesc.text.toString().trim()
        val productCategory = binding.editProductCategory.text.toString().trim()
        val productPrice = binding.editProductPrice.text.toString().trim().toInt() ?: 0

        if (productName.isEmpty() || productDesc.isEmpty() || productCategory.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_LONG).show()
            return
        }

        val model = ProductModel(
            "",
            productName,
            productDesc,
            productCategory,
            productPrice,
            url
        )

        productViewModel.addProduct(model) { success, message ->
            Toast.makeText(this@AddProductActivity, message, Toast.LENGTH_LONG).show()
            if (success){
                val intent = Intent(this@AddProductActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            loadingUtils.dismiss()
        }
    }
}
