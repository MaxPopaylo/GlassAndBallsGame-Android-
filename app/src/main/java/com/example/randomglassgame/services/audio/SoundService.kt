package com.example.randomglassgame.services.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.example.randomglassgame.R

enum class Sounds {
    MOVE_SOUND,
    ALERT_SOUND,
    SUCCESS_SOUND,
    TAP_SOUND,
    SHAKE_SOUND,
    CHOOSE_SOUND
}

class SoundService(
    private val context: Context
) {

    private val soundMap: MutableMap<Sounds, Int> = mutableMapOf()
    private var isMuted = false

    private var soundPool: SoundPool = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()
        .let {
            SoundPool.Builder()
                .setAudioAttributes(it)
                .setMaxStreams(Sounds.entries.size)
                .build()
        }

    init {
        loadSounds()
    }

    private fun loadSounds() {
        loadSound(Sounds.MOVE_SOUND, R.raw.move)
        loadSound(Sounds.ALERT_SOUND, R.raw.allert)
        loadSound(Sounds.SUCCESS_SOUND, R.raw.success)
        loadSound(Sounds.TAP_SOUND, R.raw.tap)
        loadSound(Sounds.SHAKE_SOUND, R.raw.shake_in_glass)
        loadSound(Sounds.CHOOSE_SOUND, R.raw.choose)
    }

    private fun loadSound(soundId: Sounds, soundResources: Int) {
        soundMap[soundId] = soundPool.load(context, soundResources, 0)
    }

    fun play(soundId: Sounds) {
        if (!isMuted) {
            soundPool.play(soundMap[soundId]!!, 1f, 1f, 1, 0, 1f)
        }
    }

    fun mute() {
        isMuted = true
    }

    fun unMute() {
        isMuted = false
    }

    fun release() {
        soundPool.release()
    }

}