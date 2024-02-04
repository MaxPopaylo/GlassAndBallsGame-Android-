package com.example.randomglassgame.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.randomglassgame.contracts.PlayActivityContract
import com.example.randomglassgame.contracts.SettingsActivityContract
import com.example.randomglassgame.databinding.ActivityMainMenuBinding
import com.example.randomglassgame.entity.Difficulty
import com.example.randomglassgame.entity.Profile
import com.example.randomglassgame.entity.Settings
import com.example.randomglassgame.entity.Skin

class MainMenuActivity : BasicActivity() {

    private lateinit var binding: ActivityMainMenuBinding

    private lateinit var profile: Profile
    private lateinit var settings: Settings

    private val changeSettingsLauncher = registerForActivityResult(SettingsActivityContract()) { result ->
        if (result != null) {
            settings =  result
            changeData()
        }
    }

    private val createGame = registerForActivityResult(PlayActivityContract()) { result ->
        if (result != null) {
            profile.currentScore = result.score
            profile.maxScore =
                if(result.score > profile.maxScore) result.score else profile.maxScore
            changeData()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater). also { setContentView(it.root) }

        profile = savedInstanceState?.getParcelable(Profile.EXTRA_PROFILE, Profile::class.java)
            ?: Profile(Build.MODEL.toString(), 0, 0)

        settings = savedInstanceState?.getParcelable(Settings.EXTRA_SETTINGS, Settings::class.java)
            ?: Settings(Difficulty.NORMAL, false, Skin().list[0])

        with(binding) {
            btnExit.setOnClickListener { exitOnClickListener() }
            btnProfile.setOnClickListener  { profileOnClickListener() }
            btnSettings.setOnClickListener { settingsOnClickListener() }
            btnPlay.setOnClickListener { playOnClickListener() }
        }
        changeData()
    }

    private fun changeData() {
        binding.tvScoreVal.text = profile.currentScore.toString()
        binding.tvDifficultyVal.text = settings.difficulty.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(Profile.EXTRA_PROFILE, profile)
        outState.putParcelable(Settings.EXTRA_SETTINGS, settings)
    }

    private fun settingsOnClickListener() {
        changeSettingsLauncher.launch(settings)
    }

    private fun playOnClickListener() {
        createGame.launch(settings)
    }

    private fun exitOnClickListener() {
        finish()
    }

    private fun profileOnClickListener() {
        Intent(applicationContext, ProfileActivity::class.java).apply {
            putExtra(Profile.EXTRA_PROFILE, profile)
            startActivity(this)
        }
    }


}