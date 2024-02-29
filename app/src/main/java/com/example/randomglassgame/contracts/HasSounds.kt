package com.example.randomglassgame.contracts

import androidx.fragment.app.Fragment
import com.example.randomglassgame.services.Sounds

fun Fragment.soundManager(): HasSounds {
    return requireActivity() as HasSounds
}

interface HasSounds {
    fun playSound(sounds: Sounds)
}