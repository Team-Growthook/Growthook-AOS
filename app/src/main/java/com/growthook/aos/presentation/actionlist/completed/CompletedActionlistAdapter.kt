package com.growthook.aos.presentation.actionlist.completed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.databinding.ItemCompletedActionplanBinding
import com.growthook.aos.domain.entity.Actionplan
import com.growthook.aos.util.extension.ItemDiffCallback

class CompletedActionlistAdapter(
    private val clickSeedDetail: (Int) -> Unit,

) :
    ListAdapter<Actionplan, CompletedActionlistAdapter.CompletedActionlistViewHolder>(
        ItemDiffCallback<Actionplan>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.actionplanId == new.actionplanId },
        ),
    ) {
    class CompletedActionlistViewHolder(
        private val binding: ItemCompletedActionplanBinding,
        private val selectedItem: (Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Actionplan) {
            binding.tvCompletedActionplanTitle.text = data.content
            binding.tvCompletedActionplanBtnLeft.setOnClickListener {
                selectedItem(data.actionplanId)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CompletedActionlistViewHolder {
        val binding =
            ItemCompletedActionplanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return CompletedActionlistViewHolder(binding, clickSeedDetail)
    }

    override fun onBindViewHolder(holder: CompletedActionlistViewHolder, position: Int) {
        holder.onBind(
            getItem(position) as Actionplan,
        )
    }
}
