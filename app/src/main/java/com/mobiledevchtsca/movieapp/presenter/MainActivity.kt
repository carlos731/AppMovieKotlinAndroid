package com.mobiledevchtsca.movieapp.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mobiledevchtsca.movieapp.R
import com.mobiledevchtsca.movieapp.databinding.ActivityMainBinding
import com.mobiledevchtsca.movieapp.presenter.auth.forgot.ForgotFragment
import com.mobiledevchtsca.movieapp.presenter.auth.login.LoginFragment
import com.mobiledevchtsca.movieapp.presenter.auth.register.RegisterFragment
import com.mobiledevchtsca.movieapp.presenter.onboarding.OnboardingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

}