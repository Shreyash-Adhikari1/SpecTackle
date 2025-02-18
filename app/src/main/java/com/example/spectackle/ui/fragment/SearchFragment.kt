package com.example.spectackle.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spectackle.R
import com.example.spectackle.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter

    // Example data to search from (with drawable image references)
    private val allItems = mutableListOf(
        SearchItem("Sunglasses", R.drawable.sunglasses),
        SearchItem("Eyewear", R.drawable.eyewear),
        SearchItem("lens", R.drawable.lens),
        SearchItem("Milky classic", R.drawable.specxgold),
        SearchItem("Black Cateye", R.drawable.specx_black),
        SearchItem("specx cateye green", R.drawable.specx_cateye_green),

    )

    // This will hold the filtered list that updates as the user types
    private var filteredList: MutableList<SearchItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView and Adapter
        setupRecyclerView()

        // Setup AutoCompleteTextView for search suggestions and real-time filtering
        setupSearch()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchAdapter(filteredList)
        binding.recyclerView.adapter = adapter
    }

    private fun setupSearch() {
        val searchAutoComplete: AutoCompleteTextView = binding.searchAutoComplete

        // Set up ArrayAdapter for AutoComplete suggestions (only showing the name here)
        val searchAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, allItems.map { it.name })
        searchAutoComplete.setAdapter(searchAdapter)

        // Filter the list when the user selects a suggestion
        searchAutoComplete.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = searchAdapter.getItem(position)
            filterList(selectedItem ?: "")
        }

        // Filter the list dynamically when the user types
        searchAutoComplete.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                editable?.let {
                    filterList(it.toString())
                }
            }
        })
    }

    private fun filterList(query: String) {
        // Filter the list based on the search query
        filteredList = allItems.filter { it.name.contains(query, ignoreCase = true) }.toMutableList()
        adapter.updateList(filteredList)
    }
}
