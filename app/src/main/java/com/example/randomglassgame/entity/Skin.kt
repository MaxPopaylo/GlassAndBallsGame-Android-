package com.example.randomglassgame.entity

import android.os.Parcelable
import com.example.randomglassgame.R
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class Skin(
    var name: String,
    var img: Int,
    var img_gray: Int
) : Parcelable {

    constructor() : this("", -1, -1)

    @IgnoredOnParcel
    val list = listOf(DEFAULT, EMPTY_GLASS, WINE_GLASS, CLEAR_GLASS, BEAR_GLASS)

    companion object {
        private val DEFAULT = Skin("Default", R.drawable.ic_glass, R.drawable.ic_glass_gray)
        private val EMPTY_GLASS = Skin("Empty Glass", R.drawable.ic_empty_glass, R.drawable.ic_empty_glass_gray)
        private val WINE_GLASS = Skin("Wine Glass", R.drawable.ic_wine_glass, R.drawable.ic_wine_glass_gray)
        private val CLEAR_GLASS = Skin("Clear Glass", R.drawable.ic_clear_glass, R.drawable.ic_clear_glass_gray)
        private val BEAR_GLASS = Skin("Beer Glass", R.drawable.ic_beer_glass, R.drawable.ic_beer_glass_gray)
    }

    override fun toString(): String {
        return name
    }

}