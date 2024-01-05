package com.growthook.aos.presentation.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.R
import com.growthook.aos.databinding.ItemDescExplainBinding
import com.growthook.aos.domain.entity.Explain

class DescAdapter : RecyclerView.Adapter<DescAdapter.DescViewHolder>() {

    val itemList: List<Explain> = listOf(
        Explain("인사이트 기록", "내가 얻은 인사이트를 한 줄로 표현해요.", R.drawable.ic_onboarding_desc1),
        Explain(
            "보관함 아카이빙",
            "인사이트를 보관할 나만의 동굴을 만들고,\n" +
                "씨앗을 심어요.",
            R.drawable.ic_onboarding_desc2,
        ),
        Explain("액션 플랜 설정", "인사이트와 관련된 도전을 계획하고 달성해요.", R.drawable.ic_onboarding_desc3),
        Explain(
            "인사이트 잠금 해제",
            "기간이 지나 잠긴 인사이트는\n" +
                "액션 플랜을 달성해 얻은 ‘쑥으로 잠금을 해제해요.",
            R.drawable.ic_onboarding_desc4,
        ),
    )

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescViewHolder {
        val binding: ItemDescExplainBinding =
            ItemDescExplainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DescViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DescViewHolder, position: Int) {
        return holder.onBind(itemList[position])
    }

    class DescViewHolder(private val binding: ItemDescExplainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Explain) {
            binding.tvExplainTitle.text = item.title
            binding.tvExplainDesc.text = item.desc
            binding.ivExplain.setImageResource(item.image)
        }
    }
}
