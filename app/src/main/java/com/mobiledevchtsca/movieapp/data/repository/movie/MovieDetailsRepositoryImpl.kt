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
        movieId: Int?
    ): MovieResponse {
        return serviceApi.getMovieDetails(
            movieId = movieId
        )
    }

    override suspend fun getCredits(
        movieId: Int?
    ): CreditResponse {
        return serviceApi.getCredits(
            movieId = movieId,
        )
    }

    override suspend fun getSimilar(
        movieId: Int?
    ): List<MovieResponse> {
        return serviceApi.getSimilar(
            movieId = movieId,
        ).results ?: emptyList()
    }

    override suspend fun getMovieReviews(
        movieId: Int?
    ): List<MovieReviewResponse> {
        return serviceApi.getMovieReviews(
            movieId = movieId,
        ).results ?: emptyList()
    }


}