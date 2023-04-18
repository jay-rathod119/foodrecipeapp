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
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : BaseFragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        val bundle = arguments
        if (bundle != null) {
            val imageUri = bundle.getString(ARG_FEATURED_IMAGE)
            val titleText = bundle.getString(ARG_TITLE)
            val ingredients = bundle.getStringArrayList(ARG_INGREDIENTS)

            binding.textviewDetailRecipeTitle.text = titleText

            ingredients?.let {
                for (ingredient in it) {
                    binding.textviewDetailRecipeIngredients.append("\n -> $ingredient\n")
                }
            }

            Glide.with(this)
                .load(imageUri)
                .into(binding.imageviewDetailRecipeImage)

        }

        return view
    }



    companion object {
        private const val ARG_FEATURED_IMAGE = "featured_image"
        private const val ARG_TITLE = "title"
        private const val ARG_INGREDIENTS = "ingredients"

        fun newInstance(featuredImage: String, title: String, ingredients: ArrayList<String>): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString(ARG_FEATURED_IMAGE, featuredImage)
            args.putString(ARG_TITLE, title)
            args.putStringArrayList(ARG_INGREDIENTS, ingredients)
            fragment.arguments = args
            return fragment
        }

    }

}