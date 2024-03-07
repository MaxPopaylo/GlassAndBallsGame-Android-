package com.example.randomglassgame.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.randomglassgame.R
import com.example.randomglassgame.contracts.HasAudio
import com.example.randomglassgame.contracts.HasBalanceInfo
import com.example.randomglassgame.databinding.ItemGlassForShopBinding
import com.example.randomglassgame.entity.Profile
import com.example.randomglassgame.entity.Skin
import com.example.randomglassgame.services.audio.Sounds

class ShopAdapter(
    private var profile: Profile,
    private var array: List<Skin>,
    private var balanceInfo: HasBalanceInfo,
    private var audioManager: HasAudio
): RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    class ShopViewHolder (var binding: ItemGlassForShopBinding) : RecyclerView.ViewHolder ( binding.root )


    override fun getItemCount(): Int = array.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val  binding = ItemGlassForShopBinding.inflate(inflater, parent, false)

        return ShopViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val skin = array[position]

        with(holder.binding) {
            tvCost.text = skin.cost.toString()
            tvMinScore.text = skin.minScore.toString()

            ivSkin.setImageResource(skin.img)

            if (!skin.isUnlock) {
                if (isCanBuy(skin)) {
                    markToLockedCanBuy(this@with)
                } else {
                    markToLocked(this@with, skin)
                }
            } else {
                markToUnlocked(this@with)
            }

            llShopItem.setOnClickListener {
                if (isCanBuy(skin)) {
                    audioManager.playSound(Sounds.SUCCESS_SOUND)
                    profile.balance -= skin.cost

                    profile.buySkin(skin)
                    markToUnlocked(this@with)

                    balanceInfo.updateBalance()
                    notifyDataSetChanged()
                }
            }
        }

    }

    private fun isCanBuy(skin: Skin): Boolean {
        return !skin.isUnlock && isCoinsEnough(skin) && isMinScoreEnough(skin)
    }

    private fun isCoinsEnough(skin: Skin): Boolean {
        return profile.balance >= skin.cost
    }

    private fun isMinScoreEnough(skin: Skin): Boolean {
        return profile.maxScore >= skin.minScore
    }

    private fun markToLockedCanBuy(binding: ItemGlassForShopBinding) {
        with(binding) {
            ivSkin.setColorFilter(Color.BLACK)
            ivSkin.setBackgroundResource(R.drawable.background_for_items_locked_canbuy)
            llCost.setBackgroundResource(R.drawable.background_for_items_locked_canbuy)
            llMinScore.setBackgroundResource(R.drawable.background_for_items_locked_canbuy)

            llCostForBuy.isVisible = true
        }
    }

    private fun markToLocked(binding: ItemGlassForShopBinding, skin: Skin) {
        with(binding) {
            ivSkin.setColorFilter(Color.BLACK)
            ivSkin.setBackgroundResource(R.drawable.background_for_items_locked)

            if (isCoinsEnough(skin)) {
                llCost.setBackgroundResource(R.drawable.background_for_items_locked_canbuy)
            } else {
                llCost.setBackgroundResource(R.drawable.background_for_items_locked)
            }

            if (isMinScoreEnough(skin)) {
                llMinScore.setBackgroundResource(R.drawable.background_for_items_locked_canbuy)
            } else {
                llMinScore.setBackgroundResource(R.drawable.background_for_items_locked)
            }

            llCostForBuy.isVisible = true
        }
    }

    private fun markToUnlocked(binding: ItemGlassForShopBinding) {
        with(binding) {
            ivSkin.setColorFilter(Color.TRANSPARENT)
            ivSkin.setBackgroundResource(R.drawable.background_for_items_unseletcted)
            llCost.setBackgroundResource(R.drawable.button_background)
            llMinScore.setBackgroundResource(R.drawable.button_background)

            llCostForBuy.isVisible = false
        }
    }

}