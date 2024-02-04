package com.example.randomglassgame.adapters

import android.annotation.SuppressLint
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.randomglassgame.R
import com.example.randomglassgame.databinding.ItemGlassBinding
import com.example.randomglassgame.entity.Glass
import com.example.randomglassgame.entity.Settings
import java.util.Collections

interface GlassActionListener{
    fun onGlassPick(glass: Glass)
}

class GlassAdapter(
    private val actionClickListener: GlassActionListener,
    private val recyclerView: RecyclerView,
    private val settings: Settings
) : RecyclerView.Adapter<GlassAdapter.GlassViewHolder>(), View.OnClickListener {

    var array: List<Glass> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var isClicked = false

    class GlassViewHolder (val binding: ItemGlassBinding) : RecyclerView.ViewHolder( binding.root )

    override fun getItemCount(): Int = array.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlassViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGlassBinding.inflate(inflater, parent, false)

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

    fun swapItems(oldIndex: Int, newIndex: Int) {
        Collections.swap(array, oldIndex, newIndex)
        notifyItemMoved(oldIndex, newIndex)
    }

    fun markGlass(glass: Glass, imgResource: Int) {
        val index = array.indexOf(glass)
        if(index != -1) {
            val holder = recyclerView.findViewHolderForAdapterPosition(index) as GlassViewHolder
            holder.binding.ivGlass.setImageResource(imgResource)
        }
    }

}