package com.solutelabs.foodrecipeapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.solutelabs.foodrecipeapp.R
import com.solutelabs.foodrecipeapp.databinding.FragmentDetailBinding
import com.solutelabs.foodrecipeapp.databinding.FragmentHomeBinding
import com.solutelabs.foodrecipeapp.model.Recipe
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : BaseFragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.textviewDetailRecipeTitle.text = recipeBody!!.title

        recipeBody!!.ingredients?.let {
            for (ingredient in it) {
                binding.textviewDetailRecipeIngredients.append("\n -> $ingredient\n")
            }
        }

        Glide.with(this)
            .load(recipeBody!!.featured_image)
            .into(binding.imageviewDetailRecipeImage)

        return view
    }



    companion object {
        private const val ARG_RECIPE = "recipe"
        var recipeBody: Recipe? = null

        fun newInstance(resultX: Recipe): DetailFragment {
            val args = Bundle()
            //args.putParcelable(ARG_RECIPE, result)
            val fragment = DetailFragment()
            recipeBody = resultX
            fragment.arguments = args
            return fragment
        }

    }

}