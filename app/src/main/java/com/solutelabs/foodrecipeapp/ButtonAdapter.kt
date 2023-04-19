package com.solutelabs.foodrecipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class ButtonAdapter(
    private val keywords: List<String>,
    private val onKeywordClick: (String) -> Unit
) : RecyclerView.Adapter<ButtonAdapter.KeywordViewHolder>() {

    private var selectedButton: Button? = null

    inner class KeywordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val button: Button = itemView.findViewById(R.id.button_keyword)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_button, parent, false)
        return KeywordViewHolder(view)
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
