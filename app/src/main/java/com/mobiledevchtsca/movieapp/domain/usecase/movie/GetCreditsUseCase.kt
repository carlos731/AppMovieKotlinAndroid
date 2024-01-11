package com.mobiledevchtsca.movieapp.domain.usecase.movie

import com.mobiledevchtsca.movieapp.data.mapper.toDomain
import com.mobiledevchtsca.movieapp.domain.model.Credit
import com.mobiledevchtsca.movieapp.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class GetCreditsUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) {

    suspend operator fun invoke(apiKey: String, language: String?, movieId: Int?): Credit {
        return repository.getCredits(
            apiKey = apiKey,
            language = language,
            movieId = movieId
        ).toDomain()
    }

}