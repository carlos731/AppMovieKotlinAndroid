package com.mobiledevchtsca.movieapp.domain.repository.movie

import com.mobiledevchtsca.movieapp.data.model.GenresResponse
import com.mobiledevchtsca.movieapp.data.model.MovieResponse

interface MovieRepository {

    suspend fun getGenres(apiKey: String, language: String?): GenresResponse

    suspend fun getMoviesByGenre(
        apiKey: String,
        language: String?,
        genreId: Int?
    ): List<MovieResponse>

}