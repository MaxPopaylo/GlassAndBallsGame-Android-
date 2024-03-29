package com.example.randomglassgame.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.randomglassgame.R
import com.example.randomglassgame.contracts.HasAudio
import com.example.randomglassgame.databinding.ItemGlassForGameBinding
import com.example.randomglassgame.entity.Glass
import com.example.randomglassgame.entity.Settings
import com.example.randomglassgame.services.AnimationService
import com.example.randomglassgame.services.audio.Sounds
import kotlinx.coroutines.delay
import java.util.Collections

interface GlassActionListener{
    fun onGlassPick(glass: Glass)
}

class GlassAdapter(
    private val actionClickListener: GlassActionListener,
    private val recyclerView: RecyclerView,
    private val settings: Settings,
    private val audioManager: HasAudio
) : RecyclerView.Adapter<GlassAdapter.GlassViewHolder>(), View.OnClickListener {

    class GlassViewHolder (val binding: ItemGlassForGameBinding) : RecyclerView.ViewHolder( binding.root )

    var array: List<Glass> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var isClicked = false

    override fun getItemCount(): Int = array.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlassViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGlassForGameBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)

        return GlassViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GlassViewHolder, position: Int) {
        holder.itemView.tag = array[position]
        holder.binding.ivGlass.setImageResource(settings.skin.img)
    }

    override fun onClick(v: View) {
        if (isClicked) {
            actionClickListener.onGlassPick(v.tag as Glass)
        }
    }

    suspend fun markGlass(glass: Glass, context: Context) {
        val index = array.indexOf(glass)
        val holder = recyclerView.findViewHolderForAdapterPosition(index) as GlassViewHolder

        holder.binding.ivGlass.startAnimation(AnimationService.getAnimation(context, R.anim.shaking_anim))
        audioManager.playSound(Sounds.SHAKE_SOUND)
        delay(1200L)
    }

    fun swapItems(oldIndex: Int, newIndex: Int) {
        Collections.swap(array, oldIndex, newIndex)
        notifyItemMoved(oldIndex, newIndex)
        audioManager.playSound(Sounds.MOVE_SOUND)
        }

}