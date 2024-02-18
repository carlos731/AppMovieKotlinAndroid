package com.mobiledevchtsca.movieapp.domain.repository.movie

import androidx.paging.PagingSource
import com.mobiledevchtsca.movieapp.data.model.GenresResponse
import com.mobiledevchtsca.movieapp.data.model.MovieResponse

interface MovieRepository {

    suspend fun getGenres(): GenresResponse

    fun getMoviesByGenre(genreId: Int?): PagingSource<Int, MovieResponse>

    fun searchMovies(query: String?): PagingSource<Int, MovieResponse>

}