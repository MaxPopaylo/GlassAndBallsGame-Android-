package com.example.randomglassgame.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randomglassgame.R
import com.example.randomglassgame.adapters.GlassActionListener
import com.example.randomglassgame.adapters.GlassAdapter
import com.example.randomglassgame.contracts.router
import com.example.randomglassgame.databinding.FragmentGameBinding
import com.example.randomglassgame.entity.GameInfo
import com.example.randomglassgame.entity.Glass
import com.example.randomglassgame.entity.Settings
import com.example.randomglassgame.services.GameService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("NewApi")
class GameFragment: Fragment() {

    private lateinit var binding: FragmentGameBinding


    private lateinit var settings: Settings
    private lateinit var info: GameInfo

    private lateinit var adapter: GlassAdapter
    private lateinit var service: GameService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settings = getSettings()
        info = savedInstanceState?.getParcelable(GameInfo.EXTRA_GAME, GameInfo::class.java)
            ?: GameInfo(0, 0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGameBinding.inflate(inflater, container, false)

        adapter = GlassAdapter(object : GlassActionListener {
            override fun onGlassPick(glass: Glass) {
                pickGlass(glass)
            }
        }, binding.rvPlayBoard, settings)

        service = GameService(settings, adapter)
            .apply {
                configureGame()
                gameInit()
            }

        with(binding) {
            rvPlayBoard.adapter = adapter
            rvPlayBoard.layoutManager = GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
            rvPlayBoard.setHasFixedSize(true)

            btnBack.setOnClickListener {onBackClickListener()}
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            startGame()
        }
        return binding.root
    }

    private fun startGame() {
        with(binding) {
            keepEyeInfo.isVisible = true
        }

        CoroutineScope(Dispatchers.Main).launch {
            service.markCorrectGlass()
            service.shuffleItems()

            binding.keepEyeInfo.isVisible = false
            binding.pickGlassInfo.isVisible = true
            adapter.isClicked = true
        }
    }

    private fun pickGlass(glass: Glass) {
        CoroutineScope(Dispatchers.Main).launch {
            if (service.isCorrect(glass)) {
                updateInfo()
                binding.pickGlassInfo.isVisible = false

                service.changeGameSettings(info)
                service.gameInit()

                service.showWinToasts(requireContext(), layoutInflater)

                startGame()
            } else {
                service.markCorrectGlass()
                service.createLoseDialog(info, parentFragmentManager)
            }
        }
    }

    private fun updateInfo() {
        info = service.updateInfo(info)

        binding.tvScoreVal.text = info.score.toString()
        binding.tvCoinsVal.text = info.coins.toString()
    }

    private fun onBackClickListener() {
        router().publishResult(info)
        router().goBack()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(GameInfo.EXTRA_GAME, info)
    }

    private fun getSettings() : Settings = requireArguments().getParcelable(ARG_SETTINGS, Settings::class.java)!!

    companion object {

        @JvmStatic
        private val ARG_SETTINGS = "ARG_SETTINGS"

        @JvmStatic
        fun newInstance(settings: Settings): GameFragment {
            val args = Bundle().apply {
                putParcelable(ARG_SETTINGS, settings)
            }
            val fragment = GameFragment()
            fragment.arguments = args
            return fragment
        }

    }


}