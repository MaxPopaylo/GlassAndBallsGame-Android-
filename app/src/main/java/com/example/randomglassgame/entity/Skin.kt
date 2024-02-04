package com.example.randomglassgame.entity

import android.os.Parcelable
import com.example.randomglassgame.R
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class Skin(
    var name: String,
    var img: Int,
    var cost: Int,
    var minScore: Int,
    var isUnlock: Boolean
) : Parcelable {

    companion object {
        val DEFAULT = Skin("Default", R.drawable.ic_beer_glass, 0, 0, true)
        private val EMPTY_GLASS = Skin("Empty", R.drawable.ic_empty_glass, 20, 60, true)
        private val CLEAR_GLASS = Skin("Clear", R.drawable.ic_clear_glass, 0, 150, false)
        private val WINE_GLASS = Skin("Wine", R.drawable.ic_wine_glass, 75, 200, false)
        private val BEAR_GLASS = Skin("Beer", R.drawable.ic_beer_glass, 45, 125, false)

        @JvmStatic val LIST = listOf(DEFAULT, EMPTY_GLASS, WINE_GLASS, CLEAR_GLASS, BEAR_GLASS)
    }

    override fun toString(): String {
        return name
    }

}