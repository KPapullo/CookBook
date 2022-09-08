package ru.netology.cookbook.cookbook.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.netology.cookbook.R

class BottomFragment : Fragment(R.layout.bottom_layout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bNav = view.findViewById<BottomNavigationView>(R.id.bNav)

        val navController =
            ((childFragmentManager.findFragmentById(R.id.mainContainerView)) as NavHostFragment).navController
        NavigationUI.setupWithNavController(bNav, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.recipeCreateFragment2
                || destination.id == R.id.filterSetupFragment
                || destination.id == R.id.stageCreateFragment
                || destination.id == R.id.recipeViewFragment
            ) {
                bNav.visibility = View.GONE
            } else {
                bNav.visibility = View.VISIBLE
            }
        }
    }
}

