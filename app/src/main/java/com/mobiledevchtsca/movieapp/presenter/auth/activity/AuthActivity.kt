package com.mobiledevchtsca.movieapp.presenter.auth.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mobiledevchtsca.movieapp.R
import com.mobiledevchtsca.movieapp.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStatusBarTranslucent()

        initNavigation()
    }

    private fun initNavigation() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id != R.id.onboardingFragment) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    private fun setStatusBarTranslucent() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        // Altera a cor da NavigationBar
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //    window.navigationBarColor = getColor(R.color.black)
        //}

    }

    // Mudar a cor da StatusBar
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun setStatusBarColor(color: Int) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

}