package com.growthook.aos.data.service

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class KakaoAuthService @Inject constructor(@ActivityContext private val context: Context) {
    fun startKakaoLogin(kakaoLoginCallBack: (OAuthToken?, Throwable?) -> Unit) {
        val kakaoLoginState =
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                KAKAO_APP_LOGIN
            } else {
                KAKAO_ACCOUNT_LOGIN
            }

        when (kakaoLoginState) {
            KAKAO_APP_LOGIN -> {
                UserApiClient.instance.loginWithKakaoTalk(
                    context,
                    callback = kakaoLoginCallBack,
                )
            }

            KAKAO_ACCOUNT_LOGIN -> {
                UserApiClient.instance.loginWithKakaoAccount(
                    context,
                    callback = kakaoLoginCallBack,
                )
            }
        }
    }

    fun kakaoLogout(kakaoLogoutCallBack: (Throwable?) -> Unit) {
        UserApiClient.instance.logout(kakaoLogoutCallBack)
    }

    fun kakaoDeleteAccount(kakaoLogoutCallBack: (Throwable?) -> Unit) {
        UserApiClient.instance.unlink(kakaoLogoutCallBack)
    }

    fun kakaoGetUserInfo(userCallback: (User?, Throwable?) -> Unit) {
        UserApiClient.instance.me(callback = userCallback)
    }

    companion object {
        const val KAKAO_APP_LOGIN = 0
        const val KAKAO_ACCOUNT_LOGIN = 1
    }
}
