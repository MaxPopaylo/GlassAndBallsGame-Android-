package com.example.randomglassgame.contracts

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.randomglassgame.entity.Profile
import com.example.randomglassgame.entity.Settings

typealias ResultListener<T> = (T) -> Unit

fun Fragment.router(): Router {
    return requireActivity() as Router
}

interface Router {

    fun showHomeScreen(profile: Profile, settings: Settings)
    fun showInventoryScreen(profile: Profile, settings: Settings)
    fun showShopScreen(profile: Profile)
    fun showGameScreen(settings: Settings)
    fun backToStartScreen()
    fun recreateActivity()
    fun goBack()
    fun <T : Parcelable> publishResult(result: T)
    fun <T : Parcelable> listenResult(clazz: Class<T>, owner: LifecycleOwner, listener: ResultListener<T>)

}