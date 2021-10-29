package com.example.nisumtest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nisumtest.data.local.entity.VarEntity
import com.example.nisumtest.databinding.ItemChipRelatedVarLayoutBinding
import com.example.nisumtest.ui.adapter.AdapterDetail.DetailViewHolder

class AdapterDetail(private val list: List<VarEntity>) : RecyclerView.Adapter<DetailViewHolder>() {

    inner class DetailViewHolder(val binding: ItemChipRelatedVarLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VarEntity) {
            binding.`var` = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChipRelatedVarLayoutBinding.inflate(inflater, parent, false)
        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}