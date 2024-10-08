package com.mobiledevchtsca.movieapp.di

import com.mobiledevchtsca.movieapp.data.local.repository.MovieLocalRepositoryImpl
import com.mobiledevchtsca.movieapp.data.repository.auth.FirebaseAuthenticationImpl
import com.mobiledevchtsca.movieapp.data.repository.movie.MovieDetailsRepositoryImpl
import com.mobiledevchtsca.movieapp.data.repository.movie.MovieRepositoryImpl
import com.mobiledevchtsca.movieapp.domain.local.repository.MovieLocalRepository
import com.mobiledevchtsca.movieapp.domain.repository.auth.FirebaseAuthentication
import com.mobiledevchtsca.movieapp.domain.repository.movie.MovieDetailsRepository
import com.mobiledevchtsca.movieapp.domain.repository.movie.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindsFirebaseAuthenticationImpl(
       firebaseAuthenticationImpl: FirebaseAuthenticationImpl
    ): FirebaseAuthentication

    @Binds
    abstract fun bindsMovieRepositoryImpl(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    abstract fun bindsMovieDetailsRepositoryImpl(
        movieRepositoryDetailsImpl: MovieDetailsRepositoryImpl
    ): MovieDetailsRepository

    @Binds
    abstract fun bindsMovieLocalRepositoryImpl(
        movieLocalRepositoryImpl: MovieLocalRepositoryImpl
    ): MovieLocalRepository

}