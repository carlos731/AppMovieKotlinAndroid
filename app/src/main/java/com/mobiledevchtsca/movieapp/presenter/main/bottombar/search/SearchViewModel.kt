package com.mobiledevchtsca.movieapp.presenter.main.bottombar.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiledevchtsca.movieapp.BuildConfig
import com.mobiledevchtsca.movieapp.domain.model.Movie
import com.mobiledevchtsca.movieapp.domain.usecase.movie.SearchMoviesUseCase
import com.mobiledevchtsca.movieapp.util.Constants
import com.mobiledevchtsca.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMoviesUseCase
): ViewModel() {

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> get() = _movieList

    private val _searchState = MutableLiveData<StateView<Unit>>()
    val searchState: LiveData<StateView<Unit>> get() = _searchState

    fun searchMovies(query: String?) {
        viewModelScope.launch {
            try {
                _searchState.postValue(StateView.Loading())

                val movies = searchMovieUseCase.invoke(
                    apiKey = BuildConfig.API_KEY,
                    language = Constants.Movie.LANGUAGE,
                    query = query
                )

                _movieList.postValue(movies)
                _searchState.postValue(StateView.Success(Unit))

            } catch (e: HttpException) {
                e.printStackTrace()
                _searchState.postValue(StateView.Error(message = e.message))
            } catch (e: Exception) {
                e.printStackTrace()
                _searchState.postValue(StateView.Error(message = e.message))
            }
        }
    }

}