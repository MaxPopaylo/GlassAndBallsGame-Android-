package com.example.randomglassgame.services

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.SlideDistanceProvider

class AnimationService {

    companion object {

        private var DURATION = 600L

        fun getSlideAnimationForRoute(gravity: Int) : MaterialFadeThrough = MaterialFadeThrough().apply {
            secondaryAnimatorProvider = SlideDistanceProvider(gravity)
            duration = DURATION
        }

        fun getAnimation(context: Context, animation: Int): Animation = AnimationUtils.loadAnimation(context, animation)

    }
}