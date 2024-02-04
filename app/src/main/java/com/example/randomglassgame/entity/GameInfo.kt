package com.example.randomglassgame.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GameInfo(
    var score: Int
) : Parcelable {

    companion object {
        @JvmStatic val EXTRA_GAME = "EXTRA_GAME"
    }
}
