package com.example.nisumtest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nisumtest.data.local.entity.LfEntity
import com.example.nisumtest.databinding.ItemRecentAbbreviationsLayoutBinding

class AdapterRecent(private val interaction: Interaction) :
    ListAdapter<LfEntity, AdapterRecent.RecentViewHolder>(
        AdapterAbbreviation.DIFF_CALLBACK
    ) {
    inner class RecentViewHolder(private val binding: ItemRecentAbbreviationsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LfEntity) {
            binding.content.setOnClickListener {
                interaction.onDetailAbbreviationInteraction(item.lfId)
            }
            binding.mModel = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentAbbreviationsLayoutBinding.inflate(inflater, parent, false)
        return RecentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    interface Interaction {
        fun onDetailAbbreviationInteraction(id: Long)
    }
}