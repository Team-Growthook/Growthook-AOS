package com.growthook.aos.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.databinding.ItemHomeCaveBinding
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.util.extension.ItemDiffCallback

class CaveAdapter : ListAdapter<Cave, CaveAdapter.CaveViewHolder>(diffCallback) {

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaveViewHolder {
        val binding: ItemHomeCaveBinding =
            ItemHomeCaveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CaveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CaveViewHolder, position: Int) {
        return holder.onBind(currentList[position])
    }

    class CaveViewHolder(private val binding: ItemHomeCaveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Cave) {
            binding.tvHomeCaveTitle.text = item.name
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: Cave)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    companion object {
        private val diffCallback =
            ItemDiffCallback<Cave>(
                onContentsTheSame = { old, new -> old == new },
                onItemsTheSame = { old, new -> old == new },
            )
    }
}
