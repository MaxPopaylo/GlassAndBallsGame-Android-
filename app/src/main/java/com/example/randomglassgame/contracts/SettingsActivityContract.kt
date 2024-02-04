package com.example.randomglassgame.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresApi
import com.example.randomglassgame.activity.SettingsActivity
import com.example.randomglassgame.entity.Settings

class SettingsActivityContract : ActivityResultContract<Settings, Settings?>() {
    override fun createIntent(context: Context, input: Settings) = Intent(context, SettingsActivity::class.java). apply {
        putExtra(Settings.EXTRA_SETTINGS, input)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun parseResult(resultCode: Int, intent: Intent?): Settings? {
        if (intent == null) return null
        if (resultCode != Activity.RESULT_OK) return null

        return intent.getParcelableExtra(Settings.EXTRA_SETTINGS, Settings::class.java)
    }

}