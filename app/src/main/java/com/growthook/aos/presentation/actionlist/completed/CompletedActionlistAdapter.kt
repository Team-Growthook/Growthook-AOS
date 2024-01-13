package com.growthook.aos.presentation.actionlist.completed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.databinding.ItemCompletedActionplanBinding
import com.growthook.aos.domain.entity.ActionlistDetail
import com.growthook.aos.util.extension.ItemDiffCallback

class CompletedActionlistAdapter(
    private val clickSeedDetail: (Int) -> Unit,
    private val clickReviewDetail: () -> Unit,

) :
    ListAdapter<ActionlistDetail, CompletedActionlistAdapter.CompletedActionlistViewHolder>(
        ItemDiffCallback<ActionlistDetail>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.actionplanId == new.actionplanId },
        ),
    ) {
    class CompletedActionlistViewHolder(
        private val binding: ItemCompletedActionplanBinding,
        private val selectedItem: (Int) -> Unit,
        private val clickReviewDetail: () -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ActionlistDetail) {
            binding.tvCompletedActionplanTitle.text = data.content
            binding.tvCompletedActionplanBtnLeft.setOnClickListener {
                selectedItem(data.actionplanId)
            }
            binding.tvCompletedActionplanBtnRight.setOnClickListener {
                clickReviewDetail()
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
        return CompletedActionlistViewHolder(binding, clickSeedDetail, clickReviewDetail)
    }

    override fun onBindViewHolder(holder: CompletedActionlistViewHolder, position: Int) {
        holder.onBind(
            getItem(position) as ActionlistDetail,
        )
    }
}
