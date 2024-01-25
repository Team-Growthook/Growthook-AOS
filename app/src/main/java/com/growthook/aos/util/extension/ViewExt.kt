package com.growthook.aos.util.extension

import android.content.Context
import android.view.View
import com.growthook.aos.util.OnSingleClickListener

fun getWidthProportionalToDevice(context: Context, rate: Float): Int {
    val display = context.applicationContext.resources.displayMetrics
    val deviceWidth = display.widthPixels
    return (deviceWidth * rate).toInt()
}

fun View.setOnSingleClickListener(onSingleClick: (View) -> Unit) {
    val oneClick = OnSingleClickListener {
        onSingleClick(it)
    }
    setOnClickListener(oneClick)
}