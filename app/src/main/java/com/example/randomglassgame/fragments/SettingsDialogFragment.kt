package com.example.randomglassgame.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.randomglassgame.R
import com.example.randomglassgame.contracts.router
import com.example.randomglassgame.databinding.FragmentSettingsDialogBinding
import com.example.randomglassgame.entity.Difficulty
import com.example.randomglassgame.entity.Language
import com.example.randomglassgame.entity.Settings
import com.example.randomglassgame.services.SoundService

class SettingsDialogFragment : DialogFragment() {

    private var _binding: FragmentSettingsDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var settings: Settings

    private lateinit var difficultyAdapter: ArrayAdapter<Difficulty>
    private lateinit var languageAdapter: ArrayAdapter<Language>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settings = getSettings()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentSettingsDialogBinding.inflate(LayoutInflater.from(requireContext()))

        setupDifficultyAdapter()
        setupLanguageAdapter()

        with(binding) {
            swSounds.isChecked = settings.isSoundsOn
            swMusic.isChecked = settings.isMusicOn

            swSounds.setOnCheckedChangeListener { _, isChecked ->
                settings.isSoundsOn = isChecked
                muteOrUnMuteSounds(!isChecked)
            }
            swMusic.setOnCheckedChangeListener { _, isChecked ->
                settings.isMusicOn = isChecked
                muteOrUnMuteMusic(!isChecked)
            }

            spDifficulty.setSelection(settings.difficulty.ordinal)
            spLanguage.setSelection(settings.language.ordinal)

            btnClose.setOnClickListener { onCloseClickListener() }
            btnConfirm.setOnClickListener { onConfirmClickListener() }
        }

        val dialog = Dialog(requireContext())
            .apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(false)
                setContentView(binding.root)
            }

        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            setGravity(Gravity.CENTER);
        }

        return dialog
    }

    private fun onCloseClickListener() {
        dialog?.dismiss()
    }

    private fun onConfirmClickListener() {
        router().publishResult(settings)
        dialog?.dismiss()
    }

    fun muteOrUnMuteSounds(isNotMute: Boolean) {
       (context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager)
           .apply {
               setStreamMute(AudioManager.STREAM_SYSTEM, isNotMute)
               setStreamMute(AudioManager.STREAM_NOTIFICATION, isNotMute)
               setStreamMute(AudioManager.STREAM_ALARM, isNotMute)
               setStreamMute(AudioManager.STREAM_RING, isNotMute)
               setStreamMute(AudioManager.STREAM_MUSIC, isNotMute)
           }
    }

    fun muteOrUnMuteMusic(isNotMute: Boolean) {
        (context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager)
            .apply {
                setStreamMute(AudioManager.STREAM_MUSIC, isNotMute)
            }
    }

    private fun setupDifficultyAdapter() {
        val data = Difficulty.entries.toTypedArray()

        difficultyAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            R.id.tvSpinnerItem,
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

    private fun setupLanguageAdapter() {
        val data = Language.entries.toTypedArray()

        languageAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            R.id.tvSpinnerItem,
            data
        )

        binding.spLanguage.adapter = languageAdapter
        binding.spLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                settings.language = data[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getSettings() : Settings = requireArguments().getParcelable(ARG_SETTINGS, Settings::class.java)!!

    companion object {

        @JvmStatic
        private val ARG_SETTINGS = "ARG_SETTINGS"

        @JvmStatic
        fun newInstance(settings: Settings): SettingsDialogFragment {
            val args = Bundle().apply {
                putParcelable(ARG_SETTINGS, settings)
            }
            val fragment = SettingsDialogFragment()
            fragment.arguments = args
            return fragment
        }

    }
}