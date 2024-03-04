package com.example.randomglassgame.services

import android.content.Context
import android.media.AudioManager


class AudioService (
    val context: Context
) {
    private val  musicService = MusicService(context)
    private val  soundService = SoundService(context)

    fun muteOrUnMuteSounds(isNotMute: Boolean) {
        if (isNotMute) {
            soundService.unMute()
        } else {
            soundService.mute()
        }

        (context.getSystemService(Context.AUDIO_SERVICE) as AudioManager)
            .apply {
                setStreamMute(AudioManager.STREAM_SYSTEM, !isNotMute)
                setStreamMute(AudioManager.STREAM_NOTIFICATION, !isNotMute)
                setStreamMute(AudioManager.STREAM_ALARM, !isNotMute)
                setStreamMute(AudioManager.STREAM_RING, !isNotMute)
            }
    }

    fun muteOrUnMuteMusic(isNotMute: Boolean) {
        if (isNotMute) {
            musicService.unMute()
        } else {
            musicService.mute()
        }
    }

    fun playSound(sounds: Sounds) {
        soundService.play(sounds)
    }


    fun playMusic() {
        musicService.play()
    }

    fun release() {
        soundService.release()
        musicService.stopPlayer()
    }


}