package com.mobiledevchtsca.movieapp.data.api

import com.mobiledevchtsca.movieapp.data.model.BasePaginationRemote
import com.mobiledevchtsca.movieapp.data.model.CreditResponse
import com.mobiledevchtsca.movieapp.data.model.GenreResponse
import com.mobiledevchtsca.movieapp.data.model.GenresResponse
import com.mobiledevchtsca.movieapp.data.model.MovieResponse
import com.mobiledevchtsca.movieapp.data.model.MovieReviewResponse
import com.mobiledevchtsca.movieapp.data.model.PersonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {
    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
    ): GenresResponse

    @GET("discover/movie")
    suspend fun getMovieByGenre(
        @Query("api_key") apiKey: String?,
        @Query("with_genres") genreId: Int?,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): BasePaginationRemote<List<MovieResponse>>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("query") query: String?,
        @Query("page") page: Int?,
    ): BasePaginationRemote<List<MovieResponse>>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?
    ): MovieResponse

    // https://api.themoviedb.org/3/movie/{movie_id}/credits: Busca o elenco do filme
    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?
    ): CreditResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilar(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?
    ): BasePaginationRemote<List<MovieResponse>>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?
    ): BasePaginationRemote<List<MovieReviewResponse>>

}