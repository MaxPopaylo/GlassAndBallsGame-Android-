package com.example.randomglassgame.services

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import com.example.randomglassgame.R
import kotlinx.coroutines.delay

class MusicService (
    private val context: Context
) {

    private lateinit var mediaPlayer: MediaPlayer
    private var isPrepared = false

    private val volume = 0.2f

    init {
        loadPlayer(R.raw.music)
    }

    private fun loadPlayer(musicResource: Int) {
        if (!isPrepared) {
            mediaPlayer = MediaPlayer.create(context, musicResource)
            mediaPlayer.setVolume(volume, volume)
            isPrepared = true
        } else {
            Log.d("MusicService", "MediaPlayer didn't loaded")
        }
    }

    fun play() {
        if (!mediaPlayer.isPlaying && isPrepared) {
            mediaPlayer.start()
            mediaPlayer.isLooping = true
        }
    }

    fun mute() {
        mediaPlayer.setVolume(0f, 0f)
    }

    fun unMute() {
        mediaPlayer.setVolume(volume, volume)
    }

    fun stopPlayer() {
        mediaPlayer.release()
        mediaPlayer = MediaPlayer()
        isPrepared = false
    }

}