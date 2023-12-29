package com.mobiledevchtsca.movieapp.presenter.auth.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mobiledevchtsca.movieapp.domain.usecase.auth.ForgotUseCase
import com.mobiledevchtsca.movieapp.domain.usecase.auth.LoginUseCase
import com.mobiledevchtsca.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
    private val forgotUseCase: ForgotUseCase
) : ViewModel() {

    fun forgot(email: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            forgotUseCase.invoke(email)

            emit(StateView.Success(Unit))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(StateView.Error(message = exception.message))
        }
    }

}