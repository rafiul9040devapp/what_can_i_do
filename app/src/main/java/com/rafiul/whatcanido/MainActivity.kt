package com.rafiul.whatcanido

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var navigationView: NavigationView

    companion object{
        val topLevelDestinations = setOf(
            R.id.taskFragment,
            R.id.statisticsFragment,
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavigationDrawer()
        setSupportActionBar(findViewById(R.id.toolbar))
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfigurationSetUp()
        setupActionBarWithNavController(navController, drawerLayout)
        navigationView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun appBarConfigurationSetUp() {
        appBarConfiguration = AppBarConfiguration
            .Builder(topLevelDestinations)
            .setOpenableLayout(drawerLayout)
            .build()
    }

    private fun setUpNavigationDrawer() {
        navigationView = findViewById(R.id.nav_view)
        drawerLayout = findViewById<DrawerLayout?>(R.id.drawer_layout)
            .apply {
                setStatusBarBackground(R.color.black)
            }
        }
    }
