package com.mobiledevchtsca.movieapp.data.api

import com.mobiledevchtsca.movieapp.data.model.BasePaginationRemote
import com.mobiledevchtsca.movieapp.data.model.GenreResponse
import com.mobiledevchtsca.movieapp.data.model.GenresResponse
import com.mobiledevchtsca.movieapp.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {
    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
    ): GenresResponse

    @GET("discover/movie")
    suspend fun getMovieByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int?,
        @Query("language") language: String?,
    ): BasePaginationRemote<List<MovieResponse>>

}