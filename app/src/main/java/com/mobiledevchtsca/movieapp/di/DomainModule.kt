package com.mobiledevchtsca.movieapp.di

import com.mobiledevchtsca.movieapp.data.repository.auth.FirebaseAuthenticationImpl
import com.mobiledevchtsca.movieapp.domain.repository.auth.FirebaseAuthentication
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindsFirebaseAuthenticationImpl(
        bindsFirebaseAuthenticationImpl: FirebaseAuthenticationImpl
    ): FirebaseAuthentication

}