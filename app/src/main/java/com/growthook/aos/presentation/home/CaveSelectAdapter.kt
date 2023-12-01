package com.growthook.aos.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.databinding.ItemHomeSelectCaveBinding
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.util.extension.ItemDiffCallback

class CaveSelectAdapter() :
    ListAdapter<Cave, CaveSelectAdapter.CaveSelectViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaveSelectViewHolder {
        val binding: ItemHomeSelectCaveBinding =
            ItemHomeSelectCaveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CaveSelectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CaveSelectViewHolder, position: Int) {
        return holder.onBind(currentList[position])
    }

    class CaveSelectViewHolder(private val binding: ItemHomeSelectCaveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Cave) {
            binding.tvSelectCaveTitle.text = item.name
        }
    }

    companion object {
        private val diffCallback =
            ItemDiffCallback<Cave>(
                onContentsTheSame = { old, new -> old == new },
                onItemsTheSame = { old, new -> old == new },
            )
    }
}
