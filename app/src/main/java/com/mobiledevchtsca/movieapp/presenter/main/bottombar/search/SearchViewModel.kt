package com.mobiledevchtsca.movieapp.presenter.main.bottombar.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mobiledevchtsca.movieapp.BuildConfig
import com.mobiledevchtsca.movieapp.domain.usecase.movie.SearchMoviesUseCase
import com.mobiledevchtsca.movieapp.util.Constants
import com.mobiledevchtsca.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMoviesUseCase
): ViewModel() {

    fun searchMovies(query: String?) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val movies = searchMovieUseCase.invoke(
                apiKey = BuildConfig.API_KEY,
                language = Constants.Movie.LANGUAGE,
                query = query
            )

            emit(StateView.Success(movies))

        } catch (e: HttpException) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }

}