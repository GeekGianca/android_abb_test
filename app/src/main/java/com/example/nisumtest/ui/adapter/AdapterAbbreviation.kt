package com.example.nisumtest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nisumtest.data.local.entity.LfEntity
import com.example.nisumtest.databinding.ItemAvailableAbbreviationsLayoutBinding

class AdapterAbbreviation(private val interaction: Interaction) :
    ListAdapter<LfEntity, AdapterAbbreviation.AbbreviationViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LfEntity>() {
            override fun areItemsTheSame(oldItem: LfEntity, newItem: LfEntity): Boolean {
                return oldItem.lfId == newItem.lfId
            }

            override fun areContentsTheSame(oldItem: LfEntity, newItem: LfEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbbreviationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAvailableAbbreviationsLayoutBinding.inflate(inflater, parent, false)
        return AbbreviationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AbbreviationViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class AbbreviationViewHolder(private val binding: ItemAvailableAbbreviationsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LfEntity) {
            binding.mModel = item
            binding.contentAbbreviation.setOnClickListener {
                interaction.onItemSelected(item.abbreviationId)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(id: Long)
    }
}