package com.growthook.aos.util.callback

import com.kakao.sdk.common.model.ApiErrorCause
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.model.User
import timber.log.Timber

class KakaoUserCallback(private val onSuccess: (userName: String) -> Unit) {
    fun handleResult(user: User?, error: Throwable?) {
        if (error != null) {
            when {
                error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                    Timber.e(error, "접근이 거부 됨(동의 취소)")
                }

                error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                    Timber.e(error, "유효하지 않은 앱")
                }

                error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                    Timber.e(error, "인증 수단이 유효하지 않아 인증할 수 없는 상태")
                }

                error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                    Timber.e(error, "요청 파라미터 오류")
                }

                error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                    Timber.e(error, "유효하지 않은 scope ID")
                }

                error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                    Timber.e(error, "설정이 올바르지 않음(android key hash)")
                }

                error.toString() == AuthErrorCause.ServerError.toString() -> {
                    Timber.e(error, "서버 내부 에러")
                }

                error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                    Timber.e(error, "앱이 요청 권한이 없음")
                }

                error.toString() == ApiErrorCause.InsufficientScope.toString() -> {
                    Timber.e(error, "해당 API에서 접근하는 리소스에 대해 사용자의 동의를 받지 않음")
                }

                error is ClientError && error.reason == ClientErrorCause.Cancelled -> {
                    Timber.e(error, "의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리")
                }

                else -> { //
                    Timber.e(error, "기타 에러")
                }
            }
        } else if (user != null) {
            Timber.d("유저 조회 성공")
            user.kakaoAccount?.profile?.nickname?.let { onSuccess(it) }
        }
    }
}
