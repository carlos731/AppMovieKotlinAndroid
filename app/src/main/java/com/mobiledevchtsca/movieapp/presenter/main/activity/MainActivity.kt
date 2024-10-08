package com.mobiledevchtsca.movieapp.presenter.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.mobiledevchtsca.movieapp.BuildConfig
import com.mobiledevchtsca.movieapp.R
import com.mobiledevchtsca.movieapp.databinding.ActivityMainBinding
import com.mobiledevchtsca.movieapp.presenter.auth.forgot.ForgotFragment
import com.mobiledevchtsca.movieapp.presenter.auth.login.LoginFragment
import com.mobiledevchtsca.movieapp.presenter.auth.register.RegisterFragment
import com.mobiledevchtsca.movieapp.presenter.onboarding.OnboardingFragment
import com.mobiledevchtsca.movieapp.util.FirebaseHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()

        /*
        val registerFragment = RegisterFragment()
        val loginFragment = LoginFragment()
        val forgotFragment = ForgotFragment()
        val onboardingFragment = OnboardingFragment()

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.container, loginFragment)
        transaction.commit()
        */
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.btnv, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id != R.id.onboardingFragment) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }

        navController.addOnDestinationChangedListener { controller, destination, arg ->
            binding.btnv.isVisible =
                destination.id == R.id.menu_home ||
                destination.id == R.id.menu_search ||
                destination.id == R.id.menu_favorite ||
                destination.id == R.id.menu_download ||
                destination.id == R.id.menu_profile
        }
    }

}