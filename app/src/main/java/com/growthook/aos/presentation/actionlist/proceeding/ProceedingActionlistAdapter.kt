package com.growthook.aos.presentation.actionlist.proceeding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.databinding.ItemProceedingActionplanBinding
import com.growthook.aos.domain.entity.Actionplan
import com.growthook.aos.util.extension.ItemDiffCallback

class ProceedingActionlistAdapter(
    private val selectedItem: (Int) -> Unit,
) :
    ListAdapter<Actionplan, ProceedingActionlistAdapter.ProceedingActionlistViewHolder>(
        ItemDiffCallback<Actionplan>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.actionplanId == new.actionplanId },
        ),
    ) {
    class ProceedingActionlistViewHolder(
        private val binding: ItemProceedingActionplanBinding,
        private val selectedItem: (Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Actionplan) {
            binding.tvProceedingActionplanTitle.text = data.content

            binding.root.setOnClickListener {
                selectedItem(data.seedId)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProceedingActionlistViewHolder {
        val binding =
            ItemProceedingActionplanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return ProceedingActionlistViewHolder(binding, selectedItem)
    }

    override fun onBindViewHolder(holder: ProceedingActionlistViewHolder, position: Int) {
        holder.onBind(
            getItem(position) as Actionplan,
        )
    }
}
