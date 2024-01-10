package com.mobiledevchtsca.movieapp.domain.model

import android.os.Parcelable
import com.mobiledevchtsca.movieapp.data.model.PersonResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Credit(
    val cast: List<Person>?
): Parcelable
