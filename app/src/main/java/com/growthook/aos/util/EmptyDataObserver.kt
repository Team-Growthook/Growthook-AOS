package com.growthook.aos.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EmptyDataObserver(
    private val recyclerView: RecyclerView,
    private val view: View? = null,
    vararg emptyViews: View,
) :
    RecyclerView.AdapterDataObserver() {

    private val emptyViews = emptyViews

    private fun checkIfEmpty() {
        val itemCount = recyclerView.adapter?.itemCount
        if (itemCount != null) {
            if (itemCount == 0) {
                recyclerView.visibility = View.INVISIBLE
                view?.let { it.visibility = View.INVISIBLE }
                emptyViews.forEach { it.visibility = View.VISIBLE }
            } else {
                recyclerView.visibility = View.VISIBLE
                view?.let { it.visibility = View.VISIBLE }
                emptyViews.forEach { it.visibility = View.INVISIBLE }
            }
        }
    }

    override fun onChanged() {
        super.onChanged()
        checkIfEmpty()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        super.onItemRangeChanged(positionStart, itemCount)
        checkIfEmpty()
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        super.onItemRangeInserted(positionStart, itemCount)
        checkIfEmpty()
    }

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount)
        checkIfEmpty()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        super.onItemRangeRemoved(positionStart, itemCount)
        checkIfEmpty()
    }
}
