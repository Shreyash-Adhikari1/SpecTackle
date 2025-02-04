package com.example.spectackle.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spectackle.R
import com.example.spectackle.adapter.CartAdapter
import com.example.spectackle.adapter.HomeProductsAdapter
import com.example.spectackle.databinding.FragmentCartBinding
import com.example.spectackle.ui.activity.home.HomeActivity


class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater,container,false)// maathi parameter ma jj xa tehi pass garney
        return binding.root
    }

    //code chahi yes bhitra garney
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        // Initialize your lists
//        imageList.add(R.drawable.specx_gold)
//        imageList.add(R.drawable.specx_cateye_green)
//        imageList.add(R.drawable.specx_black)
//
//        nameList.add("SpecX Gold Shades")
//        nameList.add("SpecX Cateye Green")
//        nameList.add("Black & Gold")
//
//        priceList.add("Rs 1,500")
//        priceList.add("Rs 1,335")
//        priceList.add("Rs 1,900")
//
//
//        adapter = HomeProductsAdapter(
//            requireContext(),
//            imageList,
//            nameList,
//            priceList
//        )
//
//        binding.recyclerView.adapter = adapter
//        binding.recyclerView.layoutManager=
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)


    }

}