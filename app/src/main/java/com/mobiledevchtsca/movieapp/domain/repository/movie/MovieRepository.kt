package com.mobiledevchtsca.movieapp.domain.repository.movie

import com.mobiledevchtsca.movieapp.data.model.GenreResponse
import com.mobiledevchtsca.movieapp.data.model.MovieResponse

interface MovieRepository {

    suspend fun getGenres(apiKey: String, language: String?): GenreResponse

    suspend fun getMoviesByGenre(
        apiKey: String,
        language: String?,
        genreId: Int?
    ): List<MovieResponse>

}