package com.example.spectackle.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.spectackle.R
import com.example.spectackle.databinding.FragmentCartBinding
import com.example.spectackle.ui.activity.home.HomeActivity


class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding

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

        Toast.makeText(requireContext (CartFragment),"Bruhh",Toast.LENGTH_LONG).show() //this@ garera context haalnu mildaina so requireContext garnu parxa

        val intent = Intent(requireActivity())//just in-case requireContext ma error aayo bhaney requireActivity garney

    }

}