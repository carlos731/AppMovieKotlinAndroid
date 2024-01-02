package com.mobiledevchtsca.movieapp.presenter.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mobiledevchtsca.movieapp.BuildConfig
import com.mobiledevchtsca.movieapp.data.mapper.toPresentation
import com.mobiledevchtsca.movieapp.domain.usecase.movie.GetGenresUseCase
import com.mobiledevchtsca.movieapp.domain.usecase.movie.GetMoviesByGenreUseCase
import com.mobiledevchtsca.movieapp.util.Constants.*
import com.mobiledevchtsca.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase
): ViewModel() {

    fun getGenres() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val genres = getGenresUseCase.invoke(
                apiKey = BuildConfig.API_KEY,
                language = Movie.LANGUAGE
            ).map { it.toPresentation() }

            emit(StateView.Success(genres))

        } catch (e: HttpException) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }

}