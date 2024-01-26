package com.mobiledevchtsca.movieapp.data.repository.movie

import com.mobiledevchtsca.movieapp.data.api.ServiceApi
import com.mobiledevchtsca.movieapp.data.model.CreditResponse
import com.mobiledevchtsca.movieapp.data.model.GenresResponse
import com.mobiledevchtsca.movieapp.data.model.MovieResponse
import com.mobiledevchtsca.movieapp.data.model.MovieReviewResponse
import com.mobiledevchtsca.movieapp.domain.repository.movie.MovieDetailsRepository
import com.mobiledevchtsca.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
): MovieDetailsRepository {
    override suspend fun getMovieDetails(
        apiKey: String?,
        language: String?,
        movieId: Int?
    ): MovieResponse {
        return serviceApi.getMovieDetails(
            apiKey = apiKey,
            language = language,
            movieId = movieId
        )
    }

    override suspend fun getCredits(
        apiKey: String?,
        language: String?,
        movieId: Int?
    ): CreditResponse {
        return serviceApi.getCredits(
            movieId = movieId,
            language = language,
            apiKey = apiKey
        )
    }

    override suspend fun getSimilar(
        apiKey: String?,
        language: String?,
        movieId: Int?
    ): List<MovieResponse> {
        return serviceApi.getSimilar(
            movieId = movieId,
            apiKey = apiKey,
            language = language,
        ).results ?: emptyList()
    }

    override suspend fun getMovieReviews(
        apiKey: String?,
        language: String?,
        movieId: Int?
    ): List<MovieReviewResponse> {
        return serviceApi.getMovieReviews(
            movieId = movieId,
            apiKey = apiKey,
            language = language,
        ).results ?: emptyList()
    }


}