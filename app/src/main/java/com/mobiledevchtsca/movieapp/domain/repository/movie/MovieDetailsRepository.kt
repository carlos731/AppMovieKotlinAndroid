package com.mobiledevchtsca.movieapp.domain.repository.movie

import com.mobiledevchtsca.movieapp.data.model.CreditResponse
import com.mobiledevchtsca.movieapp.data.model.MovieResponse

interface MovieDetailsRepository {

    suspend fun getMovieDetails(
        apiKey: String?,
        language: String?,
        movieId: Int?
    ): MovieResponse

    suspend fun getCredits(
        apiKey: String?,
        language: String?,
        movieId: Int?
    ): CreditResponse

}