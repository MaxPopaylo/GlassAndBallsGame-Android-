package com.example.randomglassgame.contracts

import androidx.fragment.app.Fragment
import com.example.randomglassgame.services.Sounds

fun Fragment.musicManager(): HasMusic {
    return requireActivity() as HasMusic
}

interface HasMusic {
    fun playMusic()
    fun muteMusic()
    fun unMuteMusic()
}