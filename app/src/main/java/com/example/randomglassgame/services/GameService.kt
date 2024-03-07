package com.example.randomglassgame.services

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import com.example.randomglassgame.R
import com.example.randomglassgame.adapters.GlassAdapter
import com.example.randomglassgame.databinding.FragmentWinToastBinding
import com.example.randomglassgame.entity.Difficulty
import com.example.randomglassgame.entity.GameInfo
import com.example.randomglassgame.entity.Glass
import com.example.randomglassgame.entity.Settings
import com.example.randomglassgame.fragments.LoseDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates
import kotlin.random.Random

class GameService(
    val settings: Settings,
    private val adapter: GlassAdapter
) {

    private lateinit var array: List<Glass>
    private lateinit var correctGlass: Glass
    private var glassCount by Delegates.notNull<Int>()
    private var shuffleDelay by Delegates.notNull<Long>()
    private var shuffleCount by Delegates.notNull<Int>()
    private var scoreMultipleRatio by Delegates.notNull<Int>()
    private var timerMaxDelay by Delegates.notNull<Int>()

    private var roundsCount = 0

    var isGameWasStop = false

    fun configureGame() {
        when(settings.difficulty) {
            Difficulty.EASY -> {
                glassCount = 3
                shuffleDelay = 1500L
                shuffleCount = 2
                scoreMultipleRatio = 1
                timerMaxDelay = 10
            }
            Difficulty.NORMAL -> {
                glassCount = 6
                shuffleDelay = 1000L
                shuffleCount = 4
                scoreMultipleRatio = 2
                timerMaxDelay = 8
            }
            Difficulty.HARD -> {
                glassCount = 9
                shuffleDelay = 500L
                shuffleCount = 6
                scoreMultipleRatio = 3
                timerMaxDelay = 6
            }
        }
    }

    private fun setCorrectGlass() {
        correctGlass = array[(array.indices).random()]
    }

    fun gameInit() {
        adapter.isClicked = false

        array = (0..< glassCount).map { Glass(
            id = it
        ) }
        adapter.array = array
        setCorrectGlass()
    }


    suspend fun markCorrectGlass(context: Context) {
        adapter.markGlass(correctGlass, context)
    }

    suspend fun shuffleItems() {
        var firstIndex: Int
        var secondIndex: Int

        for (i in 0..shuffleCount) {
            if (isGameWasStop) break

            firstIndex = findRandomIndex(null)
            secondIndex = findRandomIndex(firstIndex)
            adapter.swapItems(firstIndex, secondIndex)

            if (i != shuffleCount)  delay(shuffleDelay)
        }
    }

    private fun findRandomIndex(firstIndex: Int?): Int {
        val correctNumber= array.indexOf(correctGlass)
        val maxNumber = array.size - 1
        val range = 3

        var randomNumber: Int
        do {
            randomNumber = Random.nextInt(correctNumber - range, correctNumber + range + 1).coerceIn(0, maxNumber)
        } while (firstIndex != null && firstIndex == randomNumber)

        return randomNumber
    }

    fun isCorrect(glass: Glass): Boolean{
        return correctGlass.id == glass.id
    }


    fun updateInfo(info: GameInfo): GameInfo {

        val score = info.score + (10 * scoreMultipleRatio)
        val coins = info.coins + (1 * scoreMultipleRatio)

        info.score = score
        info.coins = coins

        return info
    }

    suspend fun showWinToasts(context: Context, layoutInflater: LayoutInflater) {
        val binding = FragmentWinToastBinding.inflate(layoutInflater)
        roundsCount += 1

         Toast(context)
            .apply {
                setGravity(Gravity.CENTER_VERTICAL, -100, -500)
                duration = Toast.LENGTH_SHORT
                view = binding.root
            }.show()

        CoroutineScope(Dispatchers.Main).launch {

            with(binding) {
                tvScoreValue.text = "${10 * scoreMultipleRatio}"
                tvCoinsValue.text = "${1 * scoreMultipleRatio}"

                llScoreWinToast.startAnimation(AnimationService.getAnimation(context, R.anim.upping_anim))
                delay(200L)
                llCoinsWinToast.isVisible = true
                llCoinsWinToast.startAnimation(AnimationService.getAnimation(context, R.anim.upping_anim))

                delay(700L)

                llScoreWinToast.startAnimation(AnimationService.getAnimation(context, R.anim.falling_anim))
                delay(200L)
                llScoreWinToast.startAnimation(AnimationService.getAnimation(context, R.anim.falling_anim))

            }

        }
        delay(1000L)
    }

    fun createLoseDialog(info: GameInfo, manager: FragmentManager) {
        val dialog = LoseDialogFragment.newInstance(info)
        dialog.show(manager, "dialog")
    }


    fun changeGameSettings() {
        if (roundsCount % 3 == 0 && roundsCount > 0) {
            Log.d("", roundsCount.toString())

            glassCount += if(glassCount < 9) 1 else 0
            shuffleDelay -= if (shuffleDelay > 100) 100L else 0
            shuffleCount += if (shuffleCount < 15) 1 else 0
            timerMaxDelay -= 1
        }
    }


}