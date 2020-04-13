package com.example.polyglobtrotter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.word_list_item.view.*

class WordRecyclerViewAdapter(private val dataset: Array<String>) : RecyclerView.Adapter<WordRecyclerViewAdapter.WordViewHolder>() {

    class WordViewHolder(wordListItem: View) : RecyclerView.ViewHolder(wordListItem) {
         val wordTextView: TextView = wordListItem.wordItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {

        val linearLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_list_item, parent, false)

        return WordViewHolder(linearLayout)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.wordTextView.text = dataset[position]
    }
}