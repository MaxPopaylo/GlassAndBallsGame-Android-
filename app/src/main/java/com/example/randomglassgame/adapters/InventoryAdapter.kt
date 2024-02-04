package com.example.randomglassgame.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.randomglassgame.R
import com.example.randomglassgame.databinding.ItemGlassForInventoryBinding
import com.example.randomglassgame.entity.Glass
import com.example.randomglassgame.entity.Settings
import com.example.randomglassgame.entity.Skin

class InventoryAdapter(
    private var settings: Settings,
    private val recyclerView: RecyclerView,
    private var array: List<Skin>
): RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>(){

    class InventoryViewHolder (var binding: ItemGlassForInventoryBinding) : RecyclerView.ViewHolder ( binding.root )

    private lateinit var selectedSkin: Skin

    override fun getItemCount(): Int = array.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGlassForInventoryBinding.inflate(inflater, parent, false)

        return InventoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val skin = array[position]

        with(holder.binding) {
            tvSkinName.text = skin.name
            ivSkin.setImageResource(skin.img)
            if (settings.skin == skin) {
               ivSkin.setBackgroundResource(R.drawable.background_for_items_seletcted)
                selectedSkin = skin
            } else {
                ivSkin.setBackgroundResource(R.drawable.background_for_items_unseletcted)
            }

            llInventoryItem.setOnClickListener {
                settings.skin = skin

                (recyclerView.findViewHolderForAdapterPosition(array.indexOf(selectedSkin)) as InventoryViewHolder)
                    .binding.ivSkin.setBackgroundResource(R.drawable.background_for_items_unseletcted)

                (recyclerView.findViewHolderForAdapterPosition(array.indexOf(skin)) as InventoryViewHolder)
                    .binding.ivSkin.setBackgroundResource(R.drawable.background_for_items_seletcted)

                selectedSkin = skin
            }
        }

    }



}