package com.mobiledevchtsca.movieapp.presenter.main.moviedetails.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mobiledevchtsca.movieapp.BuildConfig
import com.mobiledevchtsca.movieapp.domain.usecase.movie.GetMovieReviewsUseCase
import com.mobiledevchtsca.movieapp.util.Constants
import com.mobiledevchtsca.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor (
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase
): ViewModel() {
    fun getMovieReviews(movieId: Int?) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val movies = getMovieReviewsUseCase(
                apiKey = BuildConfig.API_KEY,
                language = Constants.Movie.LANGUAGE_ENGLISH,
                movieId = movieId
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