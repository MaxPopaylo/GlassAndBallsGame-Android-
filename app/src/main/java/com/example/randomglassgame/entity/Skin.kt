package com.example.randomglassgame.entity

import android.os.Parcelable
import com.example.randomglassgame.R
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
        val DEFAULT = Skin("Default", R.mipmap.ic_default_glass, 0, 0, true)
        private val BEER1_GLASS = Skin("Beer1", R.mipmap.ic_beer1_glass, 320, 460, true)
        private val BEER2_GLASS = Skin("Beer1", R.mipmap.ic_beer2_glass, 200, 750, false)
        private val BEER_GUINNESS_GLASS = Skin("Guinness", R.mipmap.ic_beer_ginnes_glass, 445, 625, false)
        private val WINE1_GLASS = Skin("Wine1", R.mipmap.ic_wine1_glass, 575, 800, false)
        private val WINE2_GLASS = Skin("Wine2", R.mipmap.ic_wine2_glass, 575, 800, false)
        private val WINE3_GLASS = Skin("Wine3", R.mipmap.ic_wine3_glass, 575, 800, false)
        private val TEA_GLASS = Skin("Tea", R.mipmap.ic_tea_glass, 150, 200, false)
        private val JUICE_GLASS = Skin("Juice", R.mipmap.ic_juice_glass, 180, 250, false)
        private val COFFEE_GLASS = Skin("Coffee", R.mipmap.ic_coffee_glass, 200, 220, false)
        private val COCOS_GLASS = Skin("Cocos", R.mipmap.ic_cocos_glass, 170, 180, false)
        private val SODA1_GLASS = Skin("Soda1", R.mipmap.ic_soda_glass, 160, 210, false)
        private val SODA2_GLASS = Skin("Soda2", R.mipmap.ic_soda2_glass, 190, 240, false)
        private val SMILE_SODA_GLASS = Skin("SmileSoda", R.mipmap.ic_smile_soda_glass, 175, 230, false)
        private val MARTINI_GLASS = Skin("Martini", R.mipmap.ic_martini_glass, 195, 260, false)
        private val WHISKY_GLASS = Skin("Whisky", R.mipmap.ic_whisky_glass, 185, 270, false)

        @JvmStatic val LIST = listOf(DEFAULT, BEER1_GLASS, BEER2_GLASS, BEER_GUINNESS_GLASS, WINE1_GLASS, WINE2_GLASS, WINE3_GLASS, TEA_GLASS, JUICE_GLASS, COFFEE_GLASS, COCOS_GLASS, SODA1_GLASS, SODA2_GLASS, SMILE_SODA_GLASS, MARTINI_GLASS, WHISKY_GLASS)
    }

    override fun toString(): String {
        return name
    }

}