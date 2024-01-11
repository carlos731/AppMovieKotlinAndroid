package com.mobiledevchtsca.movieapp.presenter.main.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mobiledevchtsca.movieapp.BuildConfig
import com.mobiledevchtsca.movieapp.domain.usecase.movie.GetCreditsUseCase
import com.mobiledevchtsca.movieapp.domain.usecase.movie.GetMovieDetailsUseCase
import com.mobiledevchtsca.movieapp.util.Constants
import com.mobiledevchtsca.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getCreditsUseCase: GetCreditsUseCase
): ViewModel() {

    fun getMovieDetails(movieId: Int?) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val result = getMovieDetailsUseCase.invoke(
                apiKey = BuildConfig.API_KEY,
                language = Constants.Movie.LANGUAGE,
                movieId = movieId
            )

            emit(StateView.Success(result))

        } catch (e: HttpException) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }

    fun getCredits(movieId: Int?) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val result = getCreditsUseCase(
                apiKey = BuildConfig.API_KEY,
                language = Constants.Movie.LANGUAGE,
                movieId = movieId
            )

            emit(StateView.Success(result))

        } catch (e: HttpException) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }

}