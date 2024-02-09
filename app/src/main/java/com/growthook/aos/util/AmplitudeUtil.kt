package com.growthook.aos.util

import android.content.Context
import com.amplitude.android.Amplitude
import com.amplitude.android.Configuration
import com.growthook.aos.BuildConfig.AMPLITUDE_APP_KEY
import timber.log.Timber

object AmplitudeUtil {

    fun trackEvent(eventName: String, context: Context, properties: Map<String, String>? = null) {
        val amplitude = Amplitude(Configuration(AMPLITUDE_APP_KEY, context))

        if (properties == null) {
            amplitude.track(eventName)
            Timber.d("amplitude")
        } else {
            amplitude.track(eventName, properties)
            Timber.d("amplitude properties")
        }
    }
}