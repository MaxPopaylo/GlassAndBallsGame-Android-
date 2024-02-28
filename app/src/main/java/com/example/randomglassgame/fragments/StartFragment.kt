package com.example.randomglassgame.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.randomglassgame.R
import com.example.randomglassgame.adapters.GlassAdapter
import com.example.randomglassgame.contracts.router
import com.example.randomglassgame.databinding.FragmentStartBinding
import com.example.randomglassgame.entity.Glass
import com.example.randomglassgame.entity.Profile
import com.example.randomglassgame.entity.Settings
import com.example.randomglassgame.services.SoundService

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentStartBinding.inflate(inflater, container, false)

        with(binding) {
            btnPlay.setOnClickListener { onPlayClickListener() }
            btnSettings.setOnClickListener { onSettingClickListener() }
        }

        return binding.root
    }

    private fun onPlayClickListener() {
        router().showHomeScreen(profile, settings)
        SoundService.getStartSound(requireContext())
    }

    private fun onSettingClickListener() {
        SoundService.getTapSound(requireContext())
        val dialog = SettingsDialogFragment.newInstance(settings)
        dialog.show(parentFragmentManager, "dialog")
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