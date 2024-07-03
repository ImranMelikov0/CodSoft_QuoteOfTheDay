package com.imranmelikov.codsoft_quoteoftheday.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.imranmelikov.codsoft_quoteoftheday.R
import com.imranmelikov.codsoft_quoteoftheday.adapter.QuoteAdapter
import com.imranmelikov.codsoft_quoteoftheday.databinding.FragmentFavBinding
import com.imranmelikov.codsoft_quoteoftheday.mvvm.QuoteViewModel

class FavFragment : Fragment() {
    private lateinit var binding:FragmentFavBinding
    private lateinit var quoteAdapter: QuoteAdapter
    private lateinit var viewModel: QuoteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentFavBinding.inflate(inflater,container,false)
        viewModel=ViewModelProvider(requireActivity())[QuoteViewModel::class.java]
        quoteAdapter= QuoteAdapter(viewModel)
        viewModel.getQuoteFromRoom()
        initializeRv()
        observeQuotes()
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    private fun observeQuotes(){
        viewModel.quoteLocalLiveData.observe(viewLifecycleOwner){
            quoteAdapter.quoteList=it
        }
    }

    private fun initializeRv(){
        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerView.adapter=quoteAdapter
    }
}