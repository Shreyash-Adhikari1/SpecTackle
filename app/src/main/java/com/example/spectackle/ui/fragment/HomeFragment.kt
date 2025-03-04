package com.example.spectackle.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spectackle.R
import com.example.spectackle.adapter.HomeProductsAdapter
import com.example.spectackle.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var recyclerView: RecyclerView

    private var imageList = ArrayList<Int>()
    private var nameList = ArrayList<String>()
    private var priceList = ArrayList<String>()
    private lateinit var adapter: HomeProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState) // Call super method

        // Initialize your lists

        imageList.add(R.drawable.milkyclassic)
        imageList.add(R.drawable.blackcateye)
        imageList.add(R.drawable.specx_cateye_green)



        nameList.add("Milky Classic")
        nameList.add("Black Cateye")
        nameList.add("SpecX Cateye Green")




        priceList.add("Rs 1,500")
        priceList.add("Rs 1,000")
        priceList.add("Rs 1,900")



        adapter = HomeProductsAdapter(
            requireContext(),
            imageList,
            nameList,
            priceList
        )

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),0)

        //Open Sunglass Fragment From Home
        binding.homeSunglass.setOnClickListener {
            replaceFragment(SunglassesFragment())
        }
        binding.homeSunglassText.setOnClickListener {
            replaceFragment(SunglassesFragment())
        }

        //Open Eyewear Fragment From Home
        binding.homeEyewear.setOnClickListener {
            replaceFragment(SunglassesFragment())
        }
        binding.homeEyewearText.setOnClickListener {
            replaceFragment(EyewearFragment())
        }

        //Open Lens Fragment From Home
        binding.homeLens.setOnClickListener {
            replaceFragment(SunglassesFragment())
        }
        binding.homeLensText.setOnClickListener {
            replaceFragment(LensFragment())
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.commit {
            replace(R.id.mainFrame,fragment)
            addToBackStack(null)
        }
    }
}