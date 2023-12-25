package com.growthook.aos.presentation.insight.noactionplan

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.R
import com.growthook.aos.databinding.ItemInsightCaveBinding
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.util.extension.ItemDiffCallback

class InsightCaveAdapter(private val selectedItem: (String) -> Unit) :
    ListAdapter<Cave, InsightCaveAdapter.InsightCaveViewHolder>(
        ItemDiffCallback<Cave>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.id == new.id },
        ),
    ) {
    private var selectedItemPosition = RecyclerView.NO_POSITION

    inner class InsightCaveViewHolder(
        private val binding: ItemInsightCaveBinding,
        private val selectedItem: (String) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var isItemSelected = false
        fun onBind(data: Cave) {
            with(binding) {
                tvItemCaveTitle.text = data.name
                clItemCaveMain.setOnClickListener {
                    isItemSelected = !isItemSelected
                    selectedItemPosition = bindingAdapterPosition
                    val selectedCaveItem = getItem(selectedItemPosition)
                    Log.d("insightCaveAdaper", "selectedCaveItem:: ${selectedCaveItem.name}")
                    if (isItemSelected) {
                        clItemCaveMain.setBackgroundResource(R.color.Gray500)
                        ivItemCaveSelected.visibility = View.VISIBLE
                        selectedItem(selectedCaveItem.name)
                    } else {
                        clItemCaveMain.setBackgroundResource(R.color.Gray400)
                        ivItemCaveSelected.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): InsightCaveViewHolder {
        val binding =
            ItemInsightCaveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InsightCaveViewHolder(binding, selectedItem)
    }

    override fun onBindViewHolder(holder: InsightCaveViewHolder, position: Int) {
        holder.onBind(
            getItem(position) as Cave,
        )
    }
}
