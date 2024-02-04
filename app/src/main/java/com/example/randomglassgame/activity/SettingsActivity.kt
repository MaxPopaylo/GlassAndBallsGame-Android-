package com.example.randomglassgame.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.example.randomglassgame.databinding.ActivitySettingsBinding
import com.example.randomglassgame.entity.Difficulty
import com.example.randomglassgame.entity.Settings
import com.example.randomglassgame.entity.Skin

class SettingsActivity : BasicActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var difficultyAdapter: ArrayAdapter<Difficulty>
    private lateinit var skinAdapter: ArrayAdapter<Skin>

    private lateinit var settings: Settings

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater). also { setContentView(it.root) }

        settings = intent.getParcelableExtra(Settings.EXTRA_SETTINGS, Settings::class.java)
            ?: Settings(Difficulty.NORMAL, false, Skin().list[0])

        setupDifficultyAdapter()
        setupSkinsAdapter()

        with(binding) {
            btnConfirm.setOnClickListener { saveOnClickListener() }
            btnBack.setOnClickListener { backOnClickListener() }

            spDifficulty.setSelection(settings.difficulty.ordinal)

            cbTimer.setOnCheckedChangeListener { _, isChecked ->
                settings.isTimerOn = isChecked
            }
            cbTimer.isChecked = settings.isTimerOn

            spSkin.setSelection(Skin().list.indexOf(settings.skin))
        }

    }


    private fun setupDifficultyAdapter() {
        val data = Difficulty.entries.toTypedArray()

        difficultyAdapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_dropdown_item_1line,
            android.R.id.text1,
            data
        )

        binding.spDifficulty.adapter = difficultyAdapter
        binding.spDifficulty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                settings.difficulty = data[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun setupSkinsAdapter() {
        val data = Skin().list

        skinAdapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_dropdown_item_1line,
            data
        )

        binding.spSkin.adapter = skinAdapter
        binding.spSkin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                settings.skin = data[position]
                binding.ivSkinImage.setImageResource(settings.skin.img)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        backOnClickListener()
        return true
    }

    private fun saveOnClickListener() {
        val resultIntent=  Intent().apply {
            putExtra(Settings.EXTRA_SETTINGS, settings)
        }

        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun backOnClickListener() {
        setResult(RESULT_CANCELED)
        finish()
    }

}