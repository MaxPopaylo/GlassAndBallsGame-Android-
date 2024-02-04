package com.example.randomglassgame.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Settings(
    var difficulty: Difficulty,
    var isTimerOn: Boolean,
    var skin: Skin
): Parcelable {

    companion object {
        @JvmStatic val EXTRA_SETTINGS = "EXTRA_SETTINGS"
    }
}
