package com.mobiledevchtsca.movieapp.domain.usecase.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mobiledevchtsca.movieapp.data.mapper.toDomain
import com.mobiledevchtsca.movieapp.domain.model.Movie
import com.mobiledevchtsca.movieapp.domain.repository.movie.MovieRepository
import com.mobiledevchtsca.movieapp.util.Constants.Paging.DEFAULT_PAGE_INDEX
import com.mobiledevchtsca.movieapp.util.Constants.Paging.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    operator fun invoke(
        apiKey: String,
        language: String?,
        genreId: Int?
    ): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = DEFAULT_PAGE_INDEX
        ),
        pagingSourceFactory = {
            repository.getMoviesByGenre(
                apiKey = apiKey,
                language = language,
                genreId = genreId,
            )
        }
    ).flow.map { pagingData ->
        pagingData.map { movieResponse ->
            movieResponse.toDomain()
        }
    }

    /* Desnecessário após o uso do Paging3
    suspend operator fun invoke(apiKey: String, language: String?, genreId: Int?): List<Movie> {
        return repository.getMoviesByGenre(
            apiKey = apiKey,
            language = language,
            genreId = genreId,
        ).map { it.toDomain() }
    }
    */

}