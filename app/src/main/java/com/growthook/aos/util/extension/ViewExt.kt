package com.growthook.aos.util.extension

import android.content.Context

fun getWidthProportionalToDevice(context: Context, rate: Float): Int {
    val display = context.applicationContext.resources.displayMetrics
    val deviceWidth = display.widthPixels
    return (deviceWidth * rate).toInt()
}