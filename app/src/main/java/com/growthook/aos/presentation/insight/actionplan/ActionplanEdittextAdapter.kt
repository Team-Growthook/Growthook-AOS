package com.growthook.aos.presentation.insight.actionplan

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.databinding.ItemActionplanEdittextBinding
import timber.log.Timber

class ActionplanEdittextAdapter(
    private val list: MutableList<String>,
) :
    RecyclerView.Adapter<ActionplanEdittextAdapter.ActionplanEdittextViewHolder>() {

    inner class ActionplanEdittextViewHolder(
        private val binding: ItemActionplanEdittextBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        var editText = binding.tilActionInsight
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ActionplanEdittextViewHolder {
        val binding =
            ItemActionplanEdittextBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )

        return ActionplanEdittextViewHolder(binding)
    }

    override fun getItemCount(): Int {
        Log.d("adapter", "list.size:: ${list.size}")
        return if (list.isEmpty()) 1 else list.size
    }

    override fun onBindViewHolder(holder: ActionplanEdittextViewHolder, position: Int) {
        val currentItem = list[position]
        holder.editText.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Timber.tag("adapter").d("onTextChanged")
            }

            override fun afterTextChanged(s: Editable?) {
                Timber.tag("adapter").d("currentItem.length:: %s", currentItem.length)
            }
        })
    }

    fun addItem(item: String) {
        val insertPosition = list.size
        list.add(item)
        notifyItemInserted(insertPosition)
    }
}
