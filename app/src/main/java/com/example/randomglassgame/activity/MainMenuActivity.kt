package com.example.randomglassgame.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.example.randomglassgame.R
import com.example.randomglassgame.contracts.HasAudio
import com.example.randomglassgame.contracts.HasBalanceInfo
import com.example.randomglassgame.contracts.ResultListener
import com.example.randomglassgame.contracts.Router
import com.example.randomglassgame.databinding.ActivityMainMenuBinding
import com.example.randomglassgame.entity.Profile
import com.example.randomglassgame.entity.Settings
import com.example.randomglassgame.fragments.GameFragment
import com.example.randomglassgame.fragments.HomeFragment
import com.example.randomglassgame.fragments.InventoryFragment
import com.example.randomglassgame.fragments.ShopFragment
import com.example.randomglassgame.fragments.StartFragment
import com.example.randomglassgame.services.AudioService
import com.example.randomglassgame.services.Sounds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainMenuActivity : AppCompatActivity(), Router, HasBalanceInfo, HasAudio {

    private lateinit var binding: ActivityMainMenuBinding

    private lateinit var settings: Settings
    private lateinit var profile: Profile

    private lateinit var audioService: AudioService

    private val currentFragment: Fragment
        get() = supportFragmentManager.findFragmentById(R.id.fragmentContainer)!!

    private val fragmentListener = object  : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateMenu()
        }
    }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater). also { setContentView(it.root) }
        audioService = AudioService(applicationContext)

        settings = savedInstanceState?.getParcelable(Settings.EXTRA_SETTINGS, Settings::class.java)
            ?: Settings.DEFAULT_STATE

        profile = savedInstanceState?.getParcelable(Profile.EXTRA_PROFILE, Profile::class.java)
            ?: Profile.DEFAULT_STATE

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, StartFragment.newInstance(profile, settings))
                .commit()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)

        with(binding) {
            menu.setOnItemSelectedListener {
                when(it) {
                    R.id.home -> showHomeScreen(profile, settings)
                    R.id.shop -> showShopScreen(profile)
                    R.id.inventory -> showInventoryScreen(profile, settings)
                }
            }

            tvBalance.text = profile.balance.toString()
            ivBackToStart.setOnClickListener {
                backToStartScreen()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
        audioService.release()
    }

    override fun showHomeScreen(profile: Profile, settings: Settings) {
        launchFragment(HomeFragment.newInstance(profile, settings))
    }

    override fun showInventoryScreen(profile: Profile, settings: Settings) {
        launchFragment(InventoryFragment.newInstance(profile, settings))
    }

    override fun showShopScreen(profile: Profile) {
        launchFragment(ShopFragment.newInstance(profile))
    }

    override fun showGameScreen(settings: Settings) {
        launchFragment(GameFragment.newInstance(settings))
    }

    override fun backToStartScreen() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        playSound(Sounds.TAP_SOUND)
    }

    override fun goBack() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun <T : Parcelable> publishResult(result: T) {
        supportFragmentManager.setFragmentResult(result.javaClass.name, bundleOf(KEY_RESULT to result))
    }

    @SuppressLint("NewApi")
    override fun <T : Parcelable> listenResult( clazz: Class<T>, owner: LifecycleOwner, listener: ResultListener<T>) {
        supportFragmentManager.setFragmentResultListener(clazz.name, owner) { _, bundle ->
            listener.invoke(bundle.getParcelable(KEY_RESULT, clazz)!!)
        }
    }

    fun updateMenu() {

        val currentItem = when(currentFragment) {
            is HomeFragment -> R.id.home
            is InventoryFragment -> R.id.inventory
            is ShopFragment -> R.id.shop
            else -> {null}
        }

        if (currentItem == null) {
            binding.menu.isVisible = false
            binding.topMenu.isVisible = false
        } else {
            binding.topMenu.isVisible = true
            binding.menu.isVisible = true
            binding.menu.setItemSelected(currentItem)
        }

    }

    override fun updateBalance() {
        binding.tvBalance.text = profile.balance.toString()
    }

    override fun muteOrUnMuteSounds(isNotMute: Boolean) {
        audioService.muteOrUnMuteSounds(isNotMute)
    }

    override fun muteOrUnMuteMusic(isNotMute: Boolean) {
        audioService.muteOrUnMuteMusic(isNotMute)
    }

    override fun playSound(sounds: Sounds) {
        audioService.playSound(sounds)
    }

    override fun playMusic() {
        audioService.playMusic()
    }

    private var itFirstUsage = false
    private fun launchFragment(fragment: Fragment) {
        CoroutineScope(Dispatchers.Main).launch {

            supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, fragment)
                .commit()

            if (itFirstUsage) {
                playSound(Sounds.TAP_SOUND)
            }
            itFirstUsage = true
        }
    }


    companion object {
        @JvmStatic private val KEY_RESULT = "RESULT"
    }

}