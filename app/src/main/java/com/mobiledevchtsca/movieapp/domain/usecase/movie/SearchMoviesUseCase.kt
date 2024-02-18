package com.mobiledevchtsca.movieapp.domain.usecase.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mobiledevchtsca.movieapp.data.mapper.toDomain
import com.mobiledevchtsca.movieapp.domain.model.Movie
import com.mobiledevchtsca.movieapp.domain.repository.movie.MovieRepository
import com.mobiledevchtsca.movieapp.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    /* Desnecessário após o uso do Paging3
    suspend operator fun invoke(apiKey: String, language: String?, query: String?): List<Movie> {
        return repository.searchMovies(
            apiKey = apiKey,
            language = language,
            query = query
        ).filter { it.backdropPath != null } .map { it.toDomain() }
    }
    */

    operator fun invoke(
        query: String?
    ): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = Constants.Paging.NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = Constants.Paging.DEFAULT_PAGE_INDEX
        ),
        pagingSourceFactory = {
            repository.searchMovies(
                query = query
            )
        }
    ).flow.map { pagingData ->
        pagingData.map { movieResponse ->
            movieResponse.toDomain()
        }
    }

}