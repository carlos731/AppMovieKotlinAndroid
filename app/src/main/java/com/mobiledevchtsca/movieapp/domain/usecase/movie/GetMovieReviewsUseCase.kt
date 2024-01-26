package com.mobiledevchtsca.movieapp.domain.usecase.movie

import com.mobiledevchtsca.movieapp.data.mapper.toDomain
import com.mobiledevchtsca.movieapp.domain.model.MovieReview
import com.mobiledevchtsca.movieapp.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) {

    suspend operator fun invoke(apiKey: String, language: String?, movieId: Int?): List<MovieReview> {
        return repository.getMovieReviews(
            apiKey = apiKey,
            language = language,
            movieId = movieId
        ).map { it.toDomain() }
    }

}