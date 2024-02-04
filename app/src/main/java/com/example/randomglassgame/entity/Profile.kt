package com.example.randomglassgame.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Profile(
    var name: String?,
    var maxScore: Int,
    var currentScore: Int
) : Parcelable {

    companion object {
        @JvmStatic val EXTRA_PROFILE = "EXTRA_PROFILE"
    }
}




