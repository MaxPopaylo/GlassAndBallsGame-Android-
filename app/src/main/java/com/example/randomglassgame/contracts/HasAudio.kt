package com.example.randomglassgame.contracts

import androidx.fragment.app.Fragment
import com.example.randomglassgame.services.Sounds

fun Fragment.audioManager():  HasAudio {
    return requireActivity() as  HasAudio
}

interface HasAudio {
    fun muteOrUnMuteSounds(isNotMute: Boolean)
    fun muteOrUnMuteMusic(isNotMute: Boolean)
    fun playSound(sounds: Sounds)
    fun playMusic()
}