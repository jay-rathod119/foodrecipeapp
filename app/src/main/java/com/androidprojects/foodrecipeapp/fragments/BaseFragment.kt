package com.androidprojects.foodrecipeapp.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.androidprojects.foodrecipeapp.R

abstract class BaseFragment : Fragment() {

    private lateinit var rootView: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view
    }

    fun replaceFragmentWithDetailFragment(fragmentManager: FragmentManager,fragment: Fragment,tag:String) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment,fragment, tag)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun Fragment.showSnackBar(message: String) {
        val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

}
