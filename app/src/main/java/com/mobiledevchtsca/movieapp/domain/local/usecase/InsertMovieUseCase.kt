package com.mobiledevchtsca.movieapp.domain.local.usecase

import com.mobiledevchtsca.movieapp.data.mapper.toEntity
import com.mobiledevchtsca.movieapp.domain.local.repository.MovieLocalRepository
import com.mobiledevchtsca.movieapp.domain.model.Movie
import javax.inject.Inject

class InsertMovieUseCase @Inject constructor(
    private val repository: MovieLocalRepository
){
    suspend operator fun invoke(movie: Movie) {
        return repository.insertMovie(movie.toEntity())
    }

}