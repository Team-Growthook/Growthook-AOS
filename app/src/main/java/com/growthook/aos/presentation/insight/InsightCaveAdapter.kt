package com.growthook.aos.presentation.insight

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.databinding.ItemInsightCaveBinding
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.util.extension.ItemDiffCallback

class InsightCaveAdapter : ListAdapter<Cave, InsightCaveAdapter.InsightCaveViewHolder>(
    ItemDiffCallback<Cave>(
        onContentsTheSame = { old, new -> old == new },
        onItemsTheSame = { old, new -> old.id == new.id },
    ),
) {
    class InsightCaveViewHolder(private val binding: ItemInsightCaveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Cave) {
            binding.tvItemCave.text = data.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): InsightCaveViewHolder {
        val binding =
            ItemInsightCaveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InsightCaveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InsightCaveViewHolder, position: Int) {
        holder.onBind(
            getItem(position) as Cave,
        )
    }
}
