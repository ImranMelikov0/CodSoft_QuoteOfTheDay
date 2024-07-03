package com.imranmelikov.codsoft_quoteoftheday.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.imranmelikov.codsoft_quoteoftheday.databinding.QuoteRvBinding
import com.imranmelikov.codsoft_quoteoftheday.db.Quote
import com.imranmelikov.codsoft_quoteoftheday.mvvm.QuoteViewModel

class QuoteAdapter(private val viewModel: QuoteViewModel):RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {
    class QuoteViewHolder(val binding:QuoteRvBinding):RecyclerView.ViewHolder(binding.root)

    // DiffUtil for efficient RecyclerView updates
    private val diffUtil=object : DiffUtil.ItemCallback<Quote>(){
        override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem==newItem
        }
    }
    private val recyclerDiffer= AsyncListDiffer(this,diffUtil)

    // Getter and setter list quotes
    var quoteList:List<Quote>
        get() = recyclerDiffer.currentList
        set(value) = recyclerDiffer.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding=QuoteRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return QuoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return quoteList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote=quoteList[position]
        holder.binding.text.text=quote.text

        holder.binding.delete.setOnClickListener {
            viewModel.deleteQuote(quote)
            notifyDataSetChanged()
            viewModel.getQuoteFromRoom()
        }

        holder.binding.share.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, quote.text)
            }
            holder.itemView.context.startActivity(Intent.createChooser(shareIntent, "Share"))
        }
    }
}