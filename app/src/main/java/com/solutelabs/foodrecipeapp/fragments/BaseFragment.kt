package com.solutelabs.foodrecipeapp.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.solutelabs.foodrecipeapp.R
import com.solutelabs.foodrecipeapp.model.Recipe

abstract class BaseFragment : Fragment() {

    private lateinit var rootView: View


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view
    }

    fun replaceFragmentWithDetailFragment(fragmentManager: FragmentManager) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, DetailFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun Fragment.showSnackBar(message: String) {
        val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

}
