package com.example.randomglassgame.activity

import androidx.appcompat.app.AppCompatActivity

open class BasicActivity : AppCompatActivity() {

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}