package  com.example.spectackle.adapter

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable

class SearchAdapter(
    context: Context,
    private val resource: Int,
    private val items: MutableList<String>
) : ArrayAdapter<String>(context, resource, items), Filterable {

    private val originalItems = ArrayList(items)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                if (constraint.isNullOrEmpty()) {
                    results.values = originalItems
                    results.count = originalItems.size
                } else {
                    val filteredList = originalItems.filter {
                        it.contains(constraint.toString(), ignoreCase = true)
                    }
                    results.values = ArrayList(filteredList) // Ensure it's mutable
                    results.count = filteredList.size
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.values is List<*>) {
                    items.clear()
                    items.addAll(results.values as List<String>)
                    notifyDataSetChanged()
                }
            }
        }
    }
}
