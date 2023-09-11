package com.ttp.usermanagement.ui.main

import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.ttp.usermanagement.R
import com.ttp.usermanagement.base.view.BaseActivity
import com.ttp.usermanagement.common.custom.font.TextViewPoppinsMedium
import com.ttp.usermanagement.common.util.ScreenUtils.setMarginsStatusBar
import com.ttp.usermanagement.databinding.ActivityMainBinding
import com.ttp.usermanagement.network.Resource
import com.ttp.usermanagement.ui.main.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun setupData() {
        viewModel.getFullName()
        initView()
    }

    override fun setupObserver() {
        viewModel.nameUser.observe(this) {
            binding.navView.getHeaderView(0).findViewById<TextViewPoppinsMedium>(R.id.tv_full_name_nav).text = it
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initView() {
        setSupportActionBar(binding.appBarMain.toolbar)
        binding.appBarMain.llAppBar.setMarginsStatusBar(this)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_list, R.id.nav_register, R.id.nav_statistics, R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}