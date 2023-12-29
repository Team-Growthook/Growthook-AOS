package com.growthook.aos.presentation.insight.write


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.R
import com.growthook.aos.databinding.ItemInsightCaveBinding
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.util.extension.ItemDiffCallback

class InsightWriteCaveSelectAdapter(private val selectedCave: (String) -> Unit) :
    ListAdapter<Cave, InsightWriteCaveSelectAdapter.InsightWriteCaveSelectViewHolder>(
        ItemDiffCallback<Cave>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.id == new.id },
        )
    ) {

    private var selectedCavePosition = RecyclerView.NO_POSITION

    inner class InsightWriteCaveSelectViewHolder(
        private val binding: ItemInsightCaveBinding,
        private val selectedCave: (String) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        private var isCaveItemSelected = false

        fun onBind(cave: Cave) {
            with (binding) {
                tvItemCaveTitle.text = cave.name
                clItemCaveMain.setOnClickListener {
                    isCaveItemSelected = !isCaveItemSelected
                    selectedCavePosition = bindingAdapterPosition

                    val selectedCaveItem = getItem(selectedCavePosition)
                    if (isCaveItemSelected) {
                        clItemCaveMain.setBackgroundResource(R.color.Gray500)
                        ivItemCaveSelected.visibility = View.VISIBLE
                        selectedCave(selectedCaveItem.name)
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
        viewType: Int
    ): InsightWriteCaveSelectViewHolder {
        val binding = ItemInsightCaveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  InsightWriteCaveSelectViewHolder(binding, selectedCave)
    }

    override fun onBindViewHolder(holder: InsightWriteCaveSelectViewHolder, position: Int) {
        holder.onBind(
            getItem(position) as Cave
        )
    }
}