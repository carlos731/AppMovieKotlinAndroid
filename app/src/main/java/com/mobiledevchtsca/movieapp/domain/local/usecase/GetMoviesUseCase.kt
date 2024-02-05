package com.mobiledevchtsca.movieapp.domain.local.usecase

import com.mobiledevchtsca.movieapp.data.mapper.toDomain
import com.mobiledevchtsca.movieapp.domain.local.repository.MovieLocalRepository
import com.mobiledevchtsca.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieLocalRepository
){
    operator fun invoke(): Flow<List<Movie>> {
        return repository.getMovies().map { movieList ->
            movieList.map { it.toDomain() }
        }
    }

}