package com.example.randomglassgame.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.randomglassgame.R
import com.example.randomglassgame.contracts.HasBalanceInfo
import com.example.randomglassgame.databinding.ItemGlassForInventoryBinding
import com.example.randomglassgame.databinding.ItemGlassForShopBinding
import com.example.randomglassgame.entity.Profile
import com.example.randomglassgame.entity.Settings
import com.example.randomglassgame.entity.Skin

class ShopAdapter(
    private var profile: Profile,
    private var array: List<Skin>,
    private var balanceInfo: HasBalanceInfo
): RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    class ShopViewHolder (var binding: ItemGlassForShopBinding) : RecyclerView.ViewHolder ( binding.root )

    override fun getItemCount(): Int = array.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val  binding = ItemGlassForShopBinding.inflate(inflater, parent, false)

        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val skin = array[position]

        with(holder.binding) {
            tvCost.text = skin.cost.toString()
            tvMinScore.text = skin.minScore.toString()

            ivSkin.setImageResource(skin.img)

            if (!skin.isUnlock) {
                markToLocked(this@with)
            } else {
                markToUnlocked(this@with)
            }

            llShopItem.setOnClickListener {
                if (!skin.isUnlock && profile.balance >= skin.cost && profile.maxScore >= skin.minScore) {
                    profile.balance -= skin.cost

                    profile.buySkin(skin)
                    markToUnlocked(this@with)

                    balanceInfo.updateBalance()
                }
            }
        }

    }

    private fun markToLocked(binding: ItemGlassForShopBinding) {
        with(binding) {
            ivSkin.setColorFilter(Color.BLACK)
            ivSkin.setBackgroundResource(R.drawable.background_for_items_locked)
            llCost.setBackgroundResource(R.drawable.background_for_items_locked)
            llMinScore.setBackgroundResource(R.drawable.background_for_items_locked)
        }
    }

    private fun markToUnlocked(binding: ItemGlassForShopBinding) {
        with(binding) {
            ivSkin.setBackgroundResource(R.drawable.background_for_items_unseletcted)
            llCost.setBackgroundResource(R.drawable.button_background)
            llMinScore.setBackgroundResource(R.drawable.button_background)

            llCostForBuy.isVisible = false
        }
    }

}