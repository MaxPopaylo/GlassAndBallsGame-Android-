package com.example.randomglassgame.services

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.example.randomglassgame.adapters.GlassAdapter
import com.example.randomglassgame.databinding.ToastLostMessageBinding
import com.example.randomglassgame.databinding.ToastWinMessageBinding
import com.example.randomglassgame.entity.Difficulty
import com.example.randomglassgame.entity.GameInfo
import com.example.randomglassgame.entity.Glass
import com.example.randomglassgame.entity.Settings
import kotlinx.coroutines.delay
import kotlin.properties.Delegates

class GameService(
    val settings: Settings,
    val adapter: GlassAdapter,
    val info: GameInfo
) {

    private lateinit var array: List<Glass>
    private lateinit var correctGlass: Glass
    private var glassCount by Delegates.notNull<Int>()
    private var shuffleDelay by Delegates.notNull<Long>()
    private var shuffleCount by Delegates.notNull<Int>()
    private var scoreMultipleRatio by Delegates.notNull<Int>()
    private var timerMaxDelay by Delegates.notNull<Int>()

    fun configureGame() {
        when(settings.difficulty) {
            Difficulty.EASY -> {
                glassCount = 3
                shuffleDelay = 2000L
                shuffleCount = 2
                scoreMultipleRatio = 10
                timerMaxDelay = 10
            }
            Difficulty.NORMAL -> {
                glassCount = 6
                shuffleDelay = 1500L
                shuffleCount = 4
                scoreMultipleRatio = 20
                timerMaxDelay = 8
            }
            Difficulty.HARD -> {
                glassCount = 9
                shuffleDelay = 1000L
                shuffleCount = 6
                scoreMultipleRatio = 30
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

    suspend fun markCorrectGlass() {
        var isGray = false
        var imgResource: Int
        for (i in (0..5)) {

            imgResource = if (isGray) {
                settings.skin.img
            } else {
                settings.skin.img_gray
            }

            adapter.markGlass(correctGlass, imgResource)
            isGray = !isGray

            if(i != 5) delay(300L)
        }
    }

    suspend fun shuffleItems() {
        var oldIndex = array.indexOf(correctGlass)

        for (i in 0..shuffleCount) {
            val newIndex = (array.indices).random()
            adapter.swapItems(oldIndex, newIndex)
            oldIndex = newIndex

            if (i != shuffleCount)  delay(shuffleDelay)
        }
    }

    fun isCorrect(glass: Glass): Boolean{
        return correctGlass.id == glass.id
    }

    suspend fun createToast(isWin: Boolean, layoutInflater: LayoutInflater, context: Context) {
        val v = if (isWin) ToastWinMessageBinding.inflate(layoutInflater).root
        else ToastLostMessageBinding.inflate(layoutInflater).root

        Toast(context).apply {
            duration = Toast.LENGTH_SHORT
            view = v
            show()
        }
        delay(2100L)
    }

    fun addScore(): Int {
        val score = info.score + (1 * scoreMultipleRatio)
        info.score = score

        return score
    }


    fun changeGameSettings() {
        if (info.score > 100) {
            glassCount += if(glassCount < 9) 1 else 0
            shuffleDelay -= 100L
            shuffleCount += 1
            timerMaxDelay -= 1
        }
    }


}