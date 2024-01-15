package com.growthook.aos.presentation.actionlist.completed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.R
import com.growthook.aos.databinding.ItemCompletedActionplanBinding
import com.growthook.aos.domain.entity.ActionlistDetail
import com.growthook.aos.util.extension.ItemDiffCallback

class CompletedActionlistAdapter(
    private val clickSeedDetail: (Int) -> Unit,
    private val clickReviewDetail: (Int) -> Unit,

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
        private val clickReviewDetail: (Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ActionlistDetail) {
            with(binding) {
                tvCompletedActionplanTitle.text = data.content
                tvCompletedActionplanBtnLeft.setOnClickListener {
                    selectedItem(data.actionplanId)
                }
                tvCompletedActionplanBtnRight.setOnClickListener {
                    clickReviewDetail(data.actionplanId)
                }
                if (data.isScraped) {
                    ivCompletedActionplan.setImageResource(R.drawable.ic_scrap_selected)
                } else {
                    ivCompletedActionplan.setImageResource(R.drawable.ic_scrap_unselected)
                }
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
