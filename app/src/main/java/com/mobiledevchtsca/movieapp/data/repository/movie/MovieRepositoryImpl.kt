package com.mobiledevchtsca.movieapp.data.repository.movie

import com.mobiledevchtsca.movieapp.data.api.ServiceApi
import com.mobiledevchtsca.movieapp.data.model.GenreResponse
import com.mobiledevchtsca.movieapp.data.model.MovieResponse
import com.mobiledevchtsca.movieapp.domain.repository.movie.MovieRepository
import com.mobiledevchtsca.movieapp.util.Constants.*
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
): MovieRepository {
    override suspend fun getGenres(apiKey: String, language: String?): GenreResponse {
        return serviceApi.getGenres(
            apiKey = apiKey,
            language = Movie.LANGUAGE
        )
    }

    override suspend fun getMoviesByGenre(
        apiKey: String,
        language: String?,
        genreId: Int?
    ): List<MovieResponse> {
        return serviceApi.getMovieByGenre(
            apiKey = apiKey,
            genreId = genreId,
            language = Movie.LANGUAGE
        ).results ?: emptyList()
    }
}