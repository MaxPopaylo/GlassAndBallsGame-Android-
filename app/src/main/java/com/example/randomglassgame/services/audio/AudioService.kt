package com.example.randomglassgame.services.audio

import android.content.Context
import android.media.AudioManager


class AudioService (
    private val context: Context
) {
    private val  musicService = MusicService(context)
    private val  soundService = SoundService(context)

    fun muteOrUnMuteSounds(isNotMute: Boolean) {
        if (isNotMute) {
            soundService.unMute()
        } else {
            soundService.mute()
        }

        val adjust = if (isNotMute) AudioManager.ADJUST_UNMUTE else AudioManager.ADJUST_MUTE
        (context.getSystemService(Context.AUDIO_SERVICE) as AudioManager)
            .apply {
                adjustStreamVolume(AudioManager.STREAM_SYSTEM, adjust, 0)
                adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, adjust, 0)
                adjustStreamVolume(AudioManager.STREAM_ALARM, adjust, 0)
                adjustStreamVolume(AudioManager.STREAM_RING, adjust, 0)
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