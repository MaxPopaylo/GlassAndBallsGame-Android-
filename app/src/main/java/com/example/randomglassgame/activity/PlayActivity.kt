package com.example.randomglassgame.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randomglassgame.R
import com.example.randomglassgame.adapters.GlassActionListener
import com.example.randomglassgame.adapters.GlassAdapter
import com.example.randomglassgame.databinding.ActivityPlayBinding
import com.example.randomglassgame.databinding.ToastLostMessageBinding
import com.example.randomglassgame.databinding.ToastWinMessageBinding
import com.example.randomglassgame.entity.Difficulty
import com.example.randomglassgame.entity.GameInfo
import com.example.randomglassgame.entity.Glass
import com.example.randomglassgame.entity.Settings
import com.example.randomglassgame.entity.Skin
import com.example.randomglassgame.services.GameService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.parcelize.Parcelize
import java.time.Duration
import java.util.Collections
import java.util.Random
import kotlin.properties.Delegates

class PlayActivity : BasicActivity() {

    private lateinit var binding: ActivityPlayBinding

    private lateinit var settings: Settings
    private lateinit var info: GameInfo

    private lateinit var adapter: GlassAdapter
    private lateinit var service: GameService


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate( layoutInflater ). also { setContentView(it.root) }

        settings = intent.getParcelableExtra(Settings.EXTRA_SETTINGS, Settings::class.java)
            ?: Settings(Difficulty.NORMAL, false, Skin().list[0])

        info = savedInstanceState?.getParcelable(GameInfo.EXTRA_GAME, GameInfo::class.java)
            ?: GameInfo(0)

        adapter = GlassAdapter(object : GlassActionListener{
            override fun onGlassPick(glass: Glass) {
                pickGlass(glass)
            }
        }, binding.rvPlayBoard, settings)

        service = GameService(settings, adapter, info)

        service.configureGame()
        service.gameInit()


        with(binding) {
            rvPlayBoard.adapter = adapter
            rvPlayBoard.layoutManager = GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
            rvPlayBoard.setHasFixedSize(true)

            btnStart.setOnClickListener {startGame()}
            btnBack.setOnClickListener {onBackClickListener()}
        }

    }

    private fun onBackClickListener() {
        val intent = Intent(). apply {
            putExtra(GameInfo.EXTRA_GAME, info)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun startGame() {
        with(binding) {
            tvGameInfo.isVisible = true
            tvGameInfo.text = resources.getString(R.string.keep_an_eye_on_the_glasses)
            visibilityGroup.isVisible = false
        }

        CoroutineScope(Dispatchers.Main).launch {

            service.markCorrectGlass()
            service.shuffleItems()

            binding.tvGameInfo.text = resources.getString(R.string.pick_a_correct_glass)
            adapter.isClicked = true
        }
    }

    private fun pickGlass(glass: Glass) {
        CoroutineScope(Dispatchers.Main).launch {
            if (service.isCorrect(glass)) {
                service.createToast(true, layoutInflater, applicationContext)

                val score = service.addScore()
                binding.tvScoreVal.text = score.toString()

                service.changeGameSettings()
                service.gameInit()

            } else {
                service.markCorrectGlass()

                service.createToast(false, layoutInflater, applicationContext)
                onBackClickListener()
            }
            binding.visibilityGroup.isVisible = true
            binding.tvGameInfo.isVisible = false
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(GameInfo.EXTRA_GAME, info)
    }

}