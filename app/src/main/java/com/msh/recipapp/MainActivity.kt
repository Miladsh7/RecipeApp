package com.msh.recipapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.msh.recipapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navHost: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHost = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        binding.mainBottomNav.background = null
        binding.mainBottomNav.setupWithNavController(navHost.navController)

        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.onBoardingFragment -> visibilityBottomMenu(false)
                R.id.popularDetailFragment2 -> visibilityBottomMenu(false)
                R.id.recentDetailFragment -> visibilityBottomMenu(false)
                R.id.videoDetailFragment -> visibilityBottomMenu(false)
                R.id.homeFragment -> visibilityBottomMenu(true)
                R.id.bookMarkFragment -> visibilityBottomMenu(true)
                R.id.notificationFragment -> visibilityBottomMenu(true)
                R.id.profileFragment -> visibilityBottomMenu(true)
            }
        }
    }

    private fun visibilityBottomMenu(isVisibility: Boolean) {
        binding.apply {
            if (isVisibility) {
                lifecycleScope.launch {
                    delay(500)
                    mainBottomAppbar.isVisible = true
                    mainFabMenu.isVisible = true
                }
            } else {
                mainBottomAppbar.isVisible = false
                mainFabMenu.isVisible = false
            }
        }
    }


    override fun onNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}