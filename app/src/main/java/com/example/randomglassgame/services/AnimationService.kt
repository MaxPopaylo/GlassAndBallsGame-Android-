package com.example.randomglassgame.services

import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.SlideDistanceProvider

class AnimationService {

    companion object {

        private var DURATION = 600L

        fun getSlideAnimationForRoute(gravity: Int) = MaterialFadeThrough().apply {
            secondaryAnimatorProvider = SlideDistanceProvider(gravity)
            duration = DURATION
        }

    }
}