package com.solutelabs.foodrecipeapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.solutelabs.foodrecipeapp.R
import com.solutelabs.foodrecipeapp.RecipeAdapter
import com.solutelabs.foodrecipeapp.RecipeService
import com.solutelabs.foodrecipeapp.databinding.FragmentHomeBinding
import com.solutelabs.foodrecipeapp.model.RecipeSearchResponse
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RecipeAdapter

    var pageNum = 1
    var totalResults = -1
    var defaultsearchQuery = "beef carrot potato onion"
    var searchQuery = " "
    private var selectedButton: Button? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val rootView = binding.root

        if (savedInstanceState != null) {
            searchQuery = savedInstanceState.getString(KEY_SEARCH_QUERY, defaultsearchQuery)
            pageNum = savedInstanceState.getInt(KEY_PAGE_NUM, 1)
            if (searchQuery.isNotEmpty()) {
                binding.searchView.setQuery(searchQuery, false)
                getRecipes(pageNum, searchQuery)
            } else {
                getRecipes(pageNum, defaultsearchQuery)
            }
        } else {
            getRecipes(pageNum, defaultsearchQuery)
        }

        adapter = RecipeAdapter(requireContext(), emptyList())
        binding.recycleViewRecipeList.adapter = adapter
        binding.recycleViewRecipeList.layoutManager = LinearLayoutManager(requireContext())

        getRecipes(pageNum, defaultsearchQuery)

        setButtonListener(binding.chipBeef, KEYWORD_BEEF)
        setButtonListener(binding.chipCarrot, KEYWORD_CARROT)
        setButtonListener(binding.chipPotato, KEYWORD_POTATO)
        setButtonListener(binding.chipOnion, KEYWORD_ONION)
        setButtonListener(binding.chipSoups, KEYWORD_SOUP)
        setButtonListener(binding.chipChicken, KEYWORD_CHICKEN)
        setButtonListener(binding.chipCake, KEYWORD_CAKE)

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    performSearch(query)
                } else if (searchQuery.isNotEmpty()) {
                    restorePreviousSearchResults()
                } else {
                    showSnackBar(requireContext().getString(R.string.please_enter_a_search_query))
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    clearSearchQuery()
                    reloadAllRecipes()
                }
                return true
            }
        })

        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_SEARCH_QUERY, searchQuery)
        outState.putInt(KEY_PAGE_NUM, pageNum)
    }

    private fun getRecipes(pageNum: Int, searchQuery: String) {

        val recipes = RecipeService.RecipeInstance.getAllRecipes(pageNum, searchQuery)
        recipes.enqueue(object : Callback<RecipeSearchResponse> {
            override fun onResponse(
                call: Call<RecipeSearchResponse>,
                response: Response<RecipeSearchResponse>
            ) {
                val recipes = response.body()
                if (recipes != null) {
                    totalResults = recipes.count
                    if (pageNum == 1) {
                        adapter.recipes = recipes.results

                        if (recipes.results.isEmpty()) {
                            recycleViewRecipeList.visibility = View.GONE
                            no_data_layout.visibility = View.VISIBLE
                        } else {
                            recycleViewRecipeList.visibility = View.VISIBLE
                            no_data_layout.visibility = View.GONE
                            adapter.notifyDataSetChanged()
                        }
                    } else {
                        adapter.addRecipes(recipes.results)
                    }

                    if (recipes.results.isEmpty()) {
                        showSnackBar(requireContext().getString(R.string.no_results_found)+ " $searchQuery")
                    }
                } else {
                    showSnackBar(requireContext().getString(R.string.no_results_found)+ " $searchQuery")
                }
            }

            override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
                showSnackBar(requireContext().getString(R.string.error_while_fetching_data))
            }
        })
    }


    fun setButtonListener(button: Button, keyword: String) {
        button.setOnClickListener {
            if (!button.isSelected) {
                // Button is not selected, so select it
                getRecipes(pageNum, keyword)
                button.isSelected = true
            } else {
                // Button is already selected, so deselect it
                getRecipes(pageNum, defaultsearchQuery)
                button.isSelected = false
            }
            // Deselect the previously selected button
            selectedButton?.isSelected = false

            // Update the reference to the selected button
            selectedButton = if (button.isSelected) button else null
        }
    }

    fun loadMore() {
        if (adapter.recipes.size < totalResults) {
            pageNum++
            getRecipes(pageNum, searchQuery)
        }
    }


    override fun onResume() {
        super.onResume()

        binding.recycleViewRecipeList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
//                29 = 30 - 1
                if(lastVisiblePosition == adapter.recipes.size - 1) {
                    loadMore()
                }
            }
        })
    }

    companion object{
        const val KEY_SEARCH_QUERY = "searchQuery"
        const val KEY_PAGE_NUM = "pageNum"
        const val KEYWORD_BEEF = "beef"
        const val KEYWORD_CARROT = "carrot"
        const val KEYWORD_POTATO = "potato"
        const val KEYWORD_ONION = "onion"
        const val KEYWORD_SOUP = "soup"
        const val KEYWORD_CHICKEN = "chicken"
        const val KEYWORD_CAKE = "cake"


    }

    private fun performSearch(query: String) {
        pageNum = 1
        searchQuery = query
        getRecipes(pageNum, searchQuery)
    }

    private fun restorePreviousSearchResults() {
        getRecipes(pageNum, searchQuery)
    }

    private fun clearSearchQuery() {
        searchQuery = defaultsearchQuery
        pageNum = 1
    }

    private fun reloadAllRecipes() {
        getRecipes(pageNum, searchQuery)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
