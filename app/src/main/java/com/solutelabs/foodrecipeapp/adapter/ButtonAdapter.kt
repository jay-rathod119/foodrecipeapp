package com.solutelabs.foodrecipeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.solutelabs.foodrecipeapp.R
import com.solutelabs.foodrecipeapp.databinding.ItemButtonBinding
import com.solutelabs.foodrecipeapp.databinding.ItemLayoutBinding

class ButtonAdapter(
    private val keywords: List<String>,
    private val onKeywordClick: (String) -> Unit
) : RecyclerView.Adapter<ButtonAdapter.KeywordViewHolder>() {

    private var selectedButton: Button? = null

    inner class KeywordViewHolder(private val binding: ItemButtonBinding) : RecyclerView.ViewHolder(binding.root) {
        val button: Button = binding.buttonKeyword
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        val binding = ItemButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KeywordViewHolder(binding)
    }


    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        val keyword = keywords[position]
        holder.button.apply {
            text = keyword
            isSelected = selectedButton == this
            setOnClickListener {
                if (selectedButton == this) {
                    // Deselect the button if it was already selected
                    selectedButton = null
                    isSelected = false
                    onKeywordClick("")
                } else {
                    // Deselect the previously selected button
                    selectedButton?.isSelected = false

                    // Update the reference to the selected button
                    selectedButton = this
                    isSelected = true

                    // Call the click listener with the keyword
                    onKeywordClick(keyword)
                }
            }
        }
    }

    override fun getItemCount(): Int = keywords.size
}
