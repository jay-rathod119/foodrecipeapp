package com.androidprojects.foodrecipeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidprojects.foodrecipeapp.R
import com.androidprojects.foodrecipeapp.databinding.ItemLayoutBinding
import com.androidprojects.foodrecipeapp.model.Recipe
import com.androidprojects.foodrecipeapp.utils.ImageLoaderUtil


class RecipeAdapter(private val context: Context, var recipes: List<Recipe>, private val listener: RecipeItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var recyclerView: RecyclerView? = null
    private var isLoading = false

    private var checkedStateMap = mutableMapOf<Int, Boolean>()

    companion object {
        private const val VIEW_TYPE_SHIMMER = 0
        private const val VIEW_TYPE_RECIPE = 1

    }

    init {
        // Initialize checkedStateMap in the constructor
        for (recipe in recipes) {
            checkedStateMap[recipe.pk] = recipe.isSaved
        }
    }

    inner class RecipeViewHolder(binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        var recipeImage = binding.imageviewRecipeImage
        var recipeTitle = binding.textviewRecipeTitle
        var recipeRating = binding.textviewRecipeRating
        var recipeCheckbox = binding.checkboxRecipeSave

    }

    inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // onCreateViewHolder in RecipeAdapter.kt
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType.equals(VIEW_TYPE_SHIMMER)) {
            val view = LayoutInflater.from(context).inflate(R.layout.item_layout_shimmer, parent, false)
            ShimmerViewHolder(view)
        } else {
            val view = ItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
            recyclerView = parent as RecyclerView
            RecipeViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return if (isLoading || recipes.isEmpty()) 6 else recipes.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading || recipes.isEmpty()) VIEW_TYPE_SHIMMER else VIEW_TYPE_RECIPE
    }

    fun getCheckedRecipes(): MutableMap<Int, Boolean> {
        return checkedStateMap
    }

    fun addRecipes(newRecipes: List<Recipe>) {
        val oldSize = recipes.size
        recipes += newRecipes
        notifyItemRangeInserted(oldSize, newRecipes.size)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecipeViewHolder) {
            if (isLoading || recipes.isEmpty()) return
            val recipe = recipes[position]

            holder.recipeTitle.text = recipe.title
            holder.recipeRating.text = recipe.rating.toString()

            ImageLoaderUtil.load(context, recipe.featured_image, holder.recipeImage)

            holder.itemView.setOnClickListener {
                listener.onRecipeItemClicked(recipe)
            }

            holder.recipeCheckbox.setOnClickListener {
                checkedStateMap[recipe.pk] = holder.recipeCheckbox.isChecked
            }

            holder.recipeCheckbox.isChecked = checkedStateMap[recipe.pk] ?: false

        }
    }

    interface RecipeItemClickListener {
        fun onRecipeItemClicked(recipe: Recipe)
    }

}