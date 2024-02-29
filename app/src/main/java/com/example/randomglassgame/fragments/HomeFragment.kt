package com.example.randomglassgame.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.randomglassgame.contracts.balanceUpdater
import com.example.randomglassgame.contracts.router
import com.example.randomglassgame.databinding.FragmentHomeBinding
import com.example.randomglassgame.entity.GameInfo
import com.example.randomglassgame.entity.Profile
import com.example.randomglassgame.entity.Settings

@SuppressLint("NewApi")
class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var settings: Settings
    private lateinit var profile: Profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settings = savedInstanceState?.getParcelable(Settings.EXTRA_SETTINGS, Settings::class.java)
            ?: getSettings()

        profile = savedInstanceState?.getParcelable(Profile.EXTRA_PROFILE, Profile::class.java)
            ?: getProfile()

    }

    override fun onCreateView( inflater: LayoutInflater,  container: ViewGroup?,  savedInstanceState: Bundle? ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        router().listenResult(GameInfo::class.java, viewLifecycleOwner) {result ->
            profile.maxScore =
                if (result.score > profile.maxScore) result.score
                else profile.maxScore
            updateScoreUi()

            profile.balance = profile.balance + result.coins
            balanceUpdater().updateBalance()
        }

        with(binding) {
            ivSkins.setImageResource(settings.skin.img)
            ivSkins.setOnClickListener {
                router().showInventoryScreen(profile, settings)
            }

            fragmentHome.setOnClickListener {
                router().showGameScreen(settings)
            }
        }
        updateScoreUi()
        return binding.root
    }

    private fun updateScoreUi() {
        binding.tvMaxScoreVal.text = profile.maxScore.toString()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(Settings.EXTRA_SETTINGS, settings)
        outState.putParcelable(Profile.EXTRA_PROFILE, profile)
    }

    private fun getProfile() : Profile = requireArguments().getParcelable(ARG_PROFILE, Profile::class.java)!!
    private fun getSettings() : Settings = requireArguments().getParcelable(ARG_SETTINGS, Settings::class.java)!!

    companion object {

        @JvmStatic
        val ARG_PROFILE = "ARG_PROFILE"

        @JvmStatic
        private val ARG_SETTINGS = "ARG_SETTINGS"

        @JvmStatic
        fun newInstance(profile: Profile, settings: Settings): HomeFragment {
            val args = Bundle().apply {
                putParcelable(ARG_PROFILE, profile)
                putParcelable(ARG_SETTINGS, settings)
            }
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }

    }

}