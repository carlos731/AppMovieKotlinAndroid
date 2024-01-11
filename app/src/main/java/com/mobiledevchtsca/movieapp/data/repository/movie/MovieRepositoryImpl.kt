package com.mobiledevchtsca.movieapp.data.repository.movie

import com.mobiledevchtsca.movieapp.data.api.ServiceApi
import com.mobiledevchtsca.movieapp.data.model.GenresResponse
import com.mobiledevchtsca.movieapp.data.model.MovieResponse
import com.mobiledevchtsca.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
): MovieRepository {
    override suspend fun getGenres(apiKey: String?, language: String?): GenresResponse {
        return serviceApi.getGenres(
            apiKey = apiKey,
            language = language
        )
    }

    override suspend fun getMoviesByGenre(
        apiKey: String?,
        language: String?,
        genreId: Int?
    ): List<MovieResponse> {
        return serviceApi.getMovieByGenre(
            apiKey = apiKey,
            genreId = genreId,
            language = language
        ).results ?: emptyList()
    }

    override suspend fun searchMovies(
        apiKey: String?,
        language: String?,
        query: String?
    ): List<MovieResponse> {
        return serviceApi.searchMovies(
            apiKey = apiKey,
            language = language,
            query = query
        ).results ?: emptyList()
    }

}