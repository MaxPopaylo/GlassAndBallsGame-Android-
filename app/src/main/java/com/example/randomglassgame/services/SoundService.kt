package com.example.randomglassgame.services

import android.content.Context
import android.media.MediaPlayer
import com.example.randomglassgame.R

class SoundService {
    companion object {

        fun getMoveSound(context: Context) {
            MediaPlayer.create(context, R.raw.move).start()
        }

        fun getAlertSound(context: Context) {
            MediaPlayer.create(context, R.raw.allert).start()
        }

        fun getSuccessSound(context: Context) {
            MediaPlayer.create(context, R.raw.success).start()
        }

        fun getTapSound(context: Context) {
            MediaPlayer.create(context, R.raw.tap).start()
        }

        fun getShakeSound(context: Context) {
            MediaPlayer.create(context, R.raw.shake_in_glass).start()
        }

        fun getChooseSound(context: Context) {
            MediaPlayer.create(context, R.raw.choose).start()
        }

        fun getStartSound(context: Context) {
            MediaPlayer.create(context, R.raw.start).start()
        }

    }
}