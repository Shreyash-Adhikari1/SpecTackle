package  com.example.spectackle.adapter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.example.spectackle.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {

    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var searchAdapter: SearchAdapter
    private val searchResults = mutableListOf<String>()  // Store search results

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        autoCompleteTextView = view.findViewById(R.id.searchAutoComplete)

        searchAdapter = SearchAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, searchResults)
        autoCompleteTextView.setAdapter(searchAdapter)

        autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    fetchSearchResults(s.toString())
                }
            }
        })

        return view
    }

    private fun fetchSearchResults(query: String) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Products")  // Adjust as needed
        databaseRef.orderByChild("name").startAt(query).endAt(query + "\uf8ff")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    searchResults.clear()
                    for (child in snapshot.children) {
                        val name = child.child("name").getValue(String::class.java)
                        name?.let { searchResults.add(it) }
                    }
                    searchAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("SearchFragment", "Database Error: ${error.message}")
                }
            })
    }
}
