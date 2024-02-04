package com.example.randomglassgame.contracts

import androidx.fragment.app.Fragment

fun Fragment.balanceUpdater(): HasBalanceInfo {
    return requireActivity() as HasBalanceInfo
}

interface HasBalanceInfo {
    fun updateBalance()
}