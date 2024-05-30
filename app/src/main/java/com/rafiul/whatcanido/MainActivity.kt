package com.rafiul.whatcanido

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rafiul.whatcanido.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        //  navController = findNavController(binding.navHostFragment.id)

        setUpNavController()
        setUpAppBar()
        setUpNavigationDrawer()
        setupActionBarWithNavController(navController, binding.drawerLayout)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setUpNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setUpAppBar() {

        val topLevelDestinations = setOf(
            R.id.taskFragment,
            R.id.statisticsFragment
        )

        appBarConfiguration = AppBarConfiguration
            .Builder(topLevelDestinations)
            .setOpenableLayout(binding.drawerLayout)
            .build()
    }

    private fun setUpNavigationDrawer() {
        with(binding) {
            navViewDrawer.setupWithNavController(navController)
            drawerLayout.apply {
                setStatusBarBackground(R.color.black)
                setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
    }
}