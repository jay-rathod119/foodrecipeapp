package com.solutelabs.foodrecipeapp

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solutelabs.foodrecipeapp.fragments.DetailFragment
import com.solutelabs.foodrecipeapp.model.Recipe



class RecipeAdapter(val context: Context, var recipes: List<Recipe>) :
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

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeImage = itemView.findViewById<ImageView>(R.id.imageview_recipe_image)
        val recipeTitle = itemView.findViewById<TextView>(R.id.textview_recipe_title)
        val recipeRating = itemView.findViewById<TextView>(R.id.textview_recipe_rating)
        val recipeCheckBox = itemView.findViewById<CheckBox>(R.id.checkbox_recipe_save)

        fun bind(recipe: Recipe) {
            recipeTitle.text = recipe.title
            recipeRating.text = recipe.rating.toString()

            Glide.with(context)
                .load(recipe.featured_image)
                .placeholder(R.drawable.imge)
                .into(recipeImage)


            itemView.setOnClickListener {
                val fragment = DetailFragment.newInstance(
                    recipe.featured_image,
                    recipe.title,
                    ArrayList(recipe.ingredients)
                )
                val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }

    }

    inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType.equals(VIEW_TYPE_SHIMMER)) {
            val view = LayoutInflater.from(context).inflate(R.layout.item_layout_shimmer, parent, false)
            ShimmerViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
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


    fun addRecipes(newRecipes: List<Recipe>) {
        val oldSize = recipes.size
        recipes += newRecipes
        notifyItemRangeInserted(oldSize, newRecipes.size)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecipeViewHolder) {
            if (isLoading || recipes.isEmpty()) return
            val recipe = recipes[position]
            holder.bind(recipe)

            holder.recipeCheckBox.setOnClickListener {
                // Update checkedStateMap instead of checkedList
                checkedStateMap[recipe.pk] = holder.recipeCheckBox.isChecked
            }

            holder.recipeCheckBox.isChecked = checkedStateMap[recipe.pk] ?: false


        }
    }

//    private fun saveRecipes() {
//        val editor = sharedPreferences.edit()
//        val idsToSave = stateMap.filter { it.value }.keys.map { it.toString() }.toSet()
//        editor.putStringSet(KEY_SAVED_RECIPE_IDS, idsToSave)
//        editor.apply()
//    }


}