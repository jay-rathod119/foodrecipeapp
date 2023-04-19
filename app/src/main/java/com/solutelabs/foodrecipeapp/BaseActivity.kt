package com.solutelabs.foodrecipeapp

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.solutelabs.foodrecipeapp.fragments.DetailFragment
import com.solutelabs.foodrecipeapp.fragments.HomeFragment

abstract class BaseActivity : AppCompatActivity()
{

    fun navigateToFragment(fragment: Fragment, tag: String, addToBackStack: Boolean) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment, tag)
            .apply {
                if (addToBackStack) {
                    addToBackStack(tag)
                }
            }
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            if (currentFragment is DetailFragment) {
                navigateToFragment(HomeFragment(), "HomeFragment", false)
            } else {
                super.onBackPressed()
            }
        }
    }

}