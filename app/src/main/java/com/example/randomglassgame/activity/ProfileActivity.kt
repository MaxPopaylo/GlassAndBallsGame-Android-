package com.example.randomglassgame.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.randomglassgame.databinding.ActivityProfileBinding
import com.example.randomglassgame.entity.Profile

class ProfileActivity : BasicActivity() {

    private lateinit var binding: ActivityProfileBinding

    private lateinit var name: String
    private var maxScore: Int = 0
    private var currentScore: Int = 0

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater). also { setContentView(it.root) }

        val profile = intent.getParcelableExtra(Profile.EXTRA_PROFILE, Profile::class.java)

        name = profile?.name.toString()
        maxScore = profile?.maxScore ?: 0
        currentScore = profile?.currentScore ?: 0

        with(binding) {
            tvNameVal.text = name
            tvMaxScoreVal.text = maxScore.toString()
            tvCurrentScoreVal.text = currentScore.toString()
        }

        binding.btnBack.setOnClickListener() {
            finish()
        }

    }
}