package com.androidprojects.foodrecipeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidprojects.foodrecipeapp.databinding.FragmentDetailBinding
import com.androidprojects.foodrecipeapp.model.Recipe
import com.androidprojects.foodrecipeapp.utils.ImageLoaderUtil


class DetailFragment : BaseFragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.textviewDetailRecipeTitle.text = recipeBody!!.title

        recipeBody!!.ingredients.let {
            for (ingredient in it) {
                binding.textviewDetailRecipeIngredients.append("\n -> $ingredient\n")
            }
        }

        ImageLoaderUtil.load(requireContext(), recipeBody!!.featured_image, binding.imageviewDetailRecipeImage)

        return view
    }

    companion object {
        var recipeBody: Recipe? = null

        fun newInstance(resultX: Recipe): DetailFragment {
            val args = Bundle()
            val fragment = DetailFragment()
            recipeBody = resultX
            fragment.arguments = args
            return fragment
        }
    }
}