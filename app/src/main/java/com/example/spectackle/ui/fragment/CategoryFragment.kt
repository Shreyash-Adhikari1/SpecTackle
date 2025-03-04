package com.example.spectackle.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.spectackle.R
import com.example.spectackle.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sunglassesCard.setOnClickListener {
            replaceFragment(SunglassesFragment())
        }

        binding.eyewearCard.setOnClickListener {
            replaceFragment(EyewearFragment())
        }

        binding.lensCard.setOnClickListener {
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
