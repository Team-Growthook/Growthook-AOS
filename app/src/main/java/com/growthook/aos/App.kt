package com.growthook.aos

import android.app.Application
import com.growthook.aos.BuildConfig.NATIVE_APP_KEY
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "$NATIVE_APP_KEY")
        Timber.plant(Timber.DebugTree())
        Timber.d("키 해시: ${Utility.getKeyHash(this)}")
    }
}
