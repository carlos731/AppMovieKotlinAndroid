package com.mobiledevchtsca.movieapp.data.mapper

import com.mobiledevchtsca.movieapp.data.model.CountryResponse
import com.mobiledevchtsca.movieapp.data.model.GenreResponse
import com.mobiledevchtsca.movieapp.data.model.MovieResponse
import com.mobiledevchtsca.movieapp.domain.model.Country
import com.mobiledevchtsca.movieapp.domain.model.Genre
import com.mobiledevchtsca.movieapp.domain.model.Movie
import com.mobiledevchtsca.movieapp.presenter.model.GenrePresentation

fun GenreResponse.toDomain(): Genre {
    return Genre(
        id = id,
        name = name
    )
}

fun MovieResponse.toDomain(): Movie {
    return Movie(
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
        productionCountries = productionCountries?.map { it.toDomain() }
    )
}

fun Genre.toPresentation(): GenrePresentation {
    return GenrePresentation(
        id = id,
        name = name,
        movies = emptyList()
    )
}

fun CountryResponse.toDomain() = Country(
    name = name
)