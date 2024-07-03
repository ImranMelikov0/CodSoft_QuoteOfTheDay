package com.imranmelikov.codsoft_quoteoftheday.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.imranmelikov.codsoft_quoteoftheday.R
import com.imranmelikov.codsoft_quoteoftheday.api.Quote
import com.imranmelikov.codsoft_quoteoftheday.api.Slip
import com.imranmelikov.codsoft_quoteoftheday.databinding.FragmentQuoteBinding
import com.imranmelikov.codsoft_quoteoftheday.mvvm.QuoteViewModel

class QuoteFragment : Fragment() {
    private lateinit var binding:FragmentQuoteBinding
    private lateinit var viewModel:QuoteViewModel
    private var fav=false
    private lateinit var quote:Quote
    private var quoteRoom=com.imranmelikov.codsoft_quoteoftheday.db.Quote("")
    private var mutableList= mutableListOf<com.imranmelikov.codsoft_quoteoftheday.db.Quote>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentQuoteBinding.inflate(inflater,container,false)
        viewModel=ViewModelProvider(requireActivity())[QuoteViewModel::class.java]

        val slip=Slip("",0)
        quote=Quote(slip)

        viewModel.getQuoteFromRoom()
            viewModel.getQuote()
        observeQuote()
        saveQuote()
        share()
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_quoteFragment_to_favFragment)
            fav=false
        }
        return binding.root
    }

    private fun observeQuote(){
        viewModel.quoteLiveData.observe(viewLifecycleOwner){
            binding.text.text=it.slip.advice
            quote=it
            quoteRoom=com.imranmelikov.codsoft_quoteoftheday.db.Quote(it.slip.advice)
        }
    }

    private fun share(){
        binding.share.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, quote.slip.advice)
            }
            startActivity(Intent.createChooser(shareIntent, "Share"))
        }
    }


    private fun saveQuote(){
        if(fav){
            binding.fav.setImageResource(R.drawable.heart)
        }else{
            binding.fav.setImageResource(R.drawable.heart_outline)
        }
        binding.fav.setOnClickListener {
            if (!fav){
                binding.fav.setImageResource(R.drawable.heart)
                viewModel.insertQuote(quoteRoom)
                fav=true
            }
        }
    }
}