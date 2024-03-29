package com.example.randomglassgame.entity

import android.os.Parcelable
import com.example.randomglassgame.services.Language
import kotlinx.parcelize.Parcelize

@Parcelize
class Settings(
    var isSoundsOn: Boolean,
    var isMusicOn: Boolean,
    var difficulty: Difficulty,
    var language: Language,
    var skin: Skin
): Parcelable {

    companion object {
        @JvmStatic val EXTRA_SETTINGS = "EXTRA_SETTINGS"
        @JvmStatic val DEFAULT_STATE = Settings(isSoundsOn = true, isMusicOn = false, Difficulty.NORMAL, Language.ENGLISH, Skin.DEFAULT)
    }
}
