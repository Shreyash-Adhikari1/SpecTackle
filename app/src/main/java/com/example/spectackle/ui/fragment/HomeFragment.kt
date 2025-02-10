package com.example.spectackle.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spectackle.R
import com.example.spectackle.adapter.HomeProductsAdapter
import com.example.spectackle.model.ProductModel
import com.example.spectackle.repository.ProductRepository
import com.example.spectackle.repository.ProductRepositoryImpl

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeProductsAdapter
    private lateinit var searchView: SearchView
    private val productRepository: ProductRepository = ProductRepositoryImpl()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize SearchView
        searchView = view.findViewById(R.id.searchView)
        setupSearchView()

        // Fetch and display products
        val productList = fetchProducts() // Replace with your data source
        adapter = HomeProductsAdapter(productList)
        recyclerView.adapter = adapter

        return view
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    performSearch(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    filterResults(it)
                }
                return true
            }
        })
    }

    private fun performSearch(query: String) {
        // Call the repository to search for products
        val searchResults = productRepository.searchProducts(query)
        adapter.updateList(searchResults)
        Toast.makeText(requireContext(), "Searching for: $query", Toast.LENGTH_SHORT).show()
    }

    private fun filterResults(query: String) {
        // Call the repository to filter products in real-time
        val filteredProducts = productRepository.searchProducts(query)
        adapter.updateList(filteredProducts)
        Toast.makeText(requireContext(), "Filtering: $query", Toast.LENGTH_SHORT).show()
    }

    private fun fetchProducts(): List<ProductModel> {
        // Replace this with your actual data fetching logic
        return listOf(
            ProductModel("1", "Product 1", "Description 1", 10.0),
            ProductModel("2", "Product 2", "Description 2", 20.0),
            ProductModel("3", "Product 3", "Description 3", 30.0)
        )
    }
}