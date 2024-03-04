package com.example.randomglassgame.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.randomglassgame.contracts.musicManager
import com.example.randomglassgame.contracts.router
import com.example.randomglassgame.contracts.soundManager
import com.example.randomglassgame.databinding.FragmentStartBinding
import com.example.randomglassgame.entity.Profile
import com.example.randomglassgame.entity.Settings
import com.example.randomglassgame.services.Sounds

@SuppressLint("NewApi")
class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    private lateinit var settings: Settings
    private lateinit var profile: Profile


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settings = savedInstanceState?.getParcelable(Settings.EXTRA_SETTINGS, Settings::class.java)
            ?: getSettings()

        profile = savedInstanceState?.getParcelable(Profile.EXTRA_PROFILE, Profile::class.java)
            ?: getProfile()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentStartBinding.inflate(inflater, container, false)

        with(binding) {
            btnPlay.setOnClickListener { onPlayClickListener() }
            btnSettings.setOnClickListener { onSettingClickListener() }
        }

        muteOrUnMuteSounds(settings.isSoundsOn, requireContext())
        muteOrUnMuteMusic(settings.isMusicOn)

        return binding.root
    }

    private fun onPlayClickListener() {
        musicManager().playMusic()
        router().showHomeScreen(profile, settings)
    }

    private fun onSettingClickListener() {
        soundManager().playSound(Sounds.TAP_SOUND)
        val dialog = SettingsDialogFragment.newInstance(settings)
        dialog.show(parentFragmentManager, "dialog")
    }

    private fun muteOrUnMuteSounds(isNotMute: Boolean, context: Context) {
        if (isNotMute) {
            soundManager().unMuteSounds()
        } else {
            soundManager().muteSounds()
        }

        (context.getSystemService(Context.AUDIO_SERVICE) as AudioManager)
            .apply {
                setStreamMute(AudioManager.STREAM_SYSTEM, isNotMute)
                setStreamMute(AudioManager.STREAM_NOTIFICATION, isNotMute)
                setStreamMute(AudioManager.STREAM_ALARM, isNotMute)
                setStreamMute(AudioManager.STREAM_RING, isNotMute)
            }
    }

    private fun muteOrUnMuteMusic(isNotMute: Boolean) {
        if (isNotMute) {
            musicManager().unMuteMusic()
        } else {
            musicManager().muteMusic()
        }
    }


    private fun getProfile() : Profile = requireArguments().getParcelable(HomeFragment.ARG_PROFILE, Profile::class.java)!!
    private fun getSettings() : Settings = requireArguments().getParcelable(ARG_SETTINGS, Settings::class.java)!!


    companion object {

        @JvmStatic
        private val ARG_PROFILE = "ARG_PROFILE"

        @JvmStatic
        private val ARG_SETTINGS = "ARG_SETTINGS"

        @JvmStatic
        fun newInstance(profile: Profile, settings: Settings): StartFragment {
            val args = Bundle().apply {
                putParcelable(ARG_PROFILE, profile)
                putParcelable(ARG_SETTINGS, settings)
            }
            val fragment = StartFragment()
            fragment.arguments = args
            return fragment
        }

    }


}