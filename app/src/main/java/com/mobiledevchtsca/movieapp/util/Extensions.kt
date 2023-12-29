package com.mobiledevchtsca.movieapp.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    val view = activity?.currentFocus
    if (view != null) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }
}

fun String.isEmailValid(): Boolean {
    val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    return emailPattern.matches(this)
}

fun String.isPasswordValid(): Boolean {
    val passwordPattern = Regex("^(?=.*[!@#$%^&*()_+\\-=\\\\[\\\\]{};':\\\"\\\\\\\\|,.<>\\\\/?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$")
    return passwordPattern.matches(this)
}