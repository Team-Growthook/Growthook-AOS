package com.growthook.aos.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.R
import com.growthook.aos.databinding.ItemHomeCaveBinding
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.util.extension.ItemDiffCallback
import com.growthook.aos.util.extension.setOnSingleClickListener

class CaveAdapter(private val selectedItem: (Cave) -> Unit) :
    ListAdapter<Cave, CaveAdapter.CaveViewHolder>(diffCallback) {

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].caveImageIndex) {
            0 -> sunrise
            1 -> sunset
            2 -> pink
            3 -> night
            else -> sunrise
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaveViewHolder {
        val binding: ItemHomeCaveBinding =
            ItemHomeCaveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CaveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CaveViewHolder, position: Int) {
        return holder.onBind(currentList[position])
    }

    inner class CaveViewHolder(private val binding: ItemHomeCaveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Cave) {
            binding.tvHomeCaveTitle.text = item.name

            binding.root.setOnSingleClickListener {
                selectedItem(item)
            }

            when (itemViewType) {
                sunrise -> binding.ivHomeCave.setImageResource(R.drawable.ic_home_cave_sunrise)
                sunset -> binding.ivHomeCave.setImageResource(R.drawable.ic_home_cave_sunset)
                pink -> binding.ivHomeCave.setImageResource(R.drawable.ic_home_cave_pink)
                night -> binding.ivHomeCave.setImageResource(R.drawable.ic_home_cave_night)
            }
        }
    }

    companion object {

        private const val sunrise = 0
        private const val sunset = 1
        private const val pink = 2
        private const val night = 3

        private val diffCallback =
            ItemDiffCallback<Cave>(
                onContentsTheSame = { old, new -> old == new },
                onItemsTheSame = { old, new -> old == new },
            )
    }
}
