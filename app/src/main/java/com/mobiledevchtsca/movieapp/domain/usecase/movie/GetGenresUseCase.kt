package com.mobiledevchtsca.movieapp.domain.usecase.movie

import com.mobiledevchtsca.movieapp.data.mapper.toDomain
import com.mobiledevchtsca.movieapp.domain.model.Genre
import com.mobiledevchtsca.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(apiKey: String, language: String?): List<Genre> {
        return repository.getGenres(
            apiKey = apiKey,
            language = language,
        ).genres?.map { it.toDomain() } ?: emptyList()
    }

}