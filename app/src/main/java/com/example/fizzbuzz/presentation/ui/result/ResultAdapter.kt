package com.example.fizzbuzz.presentation.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fizzbuzz.databinding.ResultItemBinding

class ResultAdapter(
    private val list: MutableList<String> = mutableListOf()
) : RecyclerView.Adapter<ResultViewHolder>() {

    fun updateList(items: List<String>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder =
        ResultViewHolder(
            ResultItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int): Unit =
        holder.bind(list[position])
}

class ResultViewHolder(
    private val binding: ResultItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(result: String) {
        binding.root.text = result
    }
}
