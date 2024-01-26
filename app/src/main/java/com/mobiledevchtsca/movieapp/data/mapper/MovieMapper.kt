package com.mobiledevchtsca.movieapp.data.mapper

import com.mobiledevchtsca.movieapp.data.model.AuthorDetailsResponse
import com.mobiledevchtsca.movieapp.data.model.CountryResponse
import com.mobiledevchtsca.movieapp.data.model.CreditResponse
import com.mobiledevchtsca.movieapp.data.model.GenreResponse
import com.mobiledevchtsca.movieapp.data.model.MovieResponse
import com.mobiledevchtsca.movieapp.data.model.MovieReviewResponse
import com.mobiledevchtsca.movieapp.data.model.PersonResponse
import com.mobiledevchtsca.movieapp.domain.model.AuthorDetails
import com.mobiledevchtsca.movieapp.domain.model.Country
import com.mobiledevchtsca.movieapp.domain.model.Credit
import com.mobiledevchtsca.movieapp.domain.model.Genre
import com.mobiledevchtsca.movieapp.domain.model.Movie
import com.mobiledevchtsca.movieapp.domain.model.MovieReview
import com.mobiledevchtsca.movieapp.domain.model.Person
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
        genres = genres?.map { it.toDomain() },
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

fun CountryResponse.toDomain(): Country {
    return Country(
        name = name
    )
}

fun PersonResponse.toDomain(): Person {
    return Person (
        adult = adult,
        gender = gender,
        id = id,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        profilePath = profilePath,
        castId = castId,
        character = character,
        creditId = creditId,
        order = order,
    )
}

fun CreditResponse.toDomain(): Credit {
    return Credit(
        cast = cast?.map { it.toDomain()}
    )
}

fun AuthorDetailsResponse.toDomain(): AuthorDetails {
    return AuthorDetails(
        name = name,
        username = username,
        avatarPath = "$avatarPath",
        rating = rating
    )
}

fun MovieReviewResponse.toDomain(): MovieReview {
    return MovieReview(
        author = author,
        authorDetails = authorDetailsResponse?.toDomain(),
        content = content,
        createdAt = createdAt,
        id = id,
        updatedAt = updatedAt,
        url = url
    )
}