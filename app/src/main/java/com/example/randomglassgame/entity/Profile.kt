package com.example.randomglassgame.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Profile(
    var maxScore: Int,
    var balance: Int,
    var inventory: MutableList<Skin> = mutableListOf()
) : Parcelable {

    init {
        inventory.add(Skin.DEFAULT)
        inventory.add(Skin.LIST[1])
    }

    fun buySkin(newSkin: Skin) {
        if (inventory.indexOf(newSkin) == -1) {
            val skin = Skin.LIST[Skin.LIST.indexOf(newSkin)]
            skin.isUnlock = true

            inventory.add(skin)
        }
    }

    companion object {
        @JvmStatic val EXTRA_PROFILE = "EXTRA_PROFILE"
        @JvmStatic val DEFAULT_STATE = Profile(300, 150)
    }
}




