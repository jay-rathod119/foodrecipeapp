package com.solutelabs.foodrecipeapp.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.solutelabs.foodrecipeapp.R

abstract class BaseFragment : Fragment() {

    private lateinit var rootView: View


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view
    }

    fun showSnackBar(message: String) {
        val snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }
}
