package com.mobiledevchtsca.movieapp.domain.usecase.auth

import com.mobiledevchtsca.movieapp.domain.repository.auth.FirebaseAuthentication

class LoginUseCase constructor(
    private val firebaseAuthentication: FirebaseAuthentication
) {

    suspend operator fun invoke(email: String, password: String) {
        firebaseAuthentication.login(email, password)
    }

}