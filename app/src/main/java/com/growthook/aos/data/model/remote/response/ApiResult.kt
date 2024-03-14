package com.growthook.aos.data.model.remote.response

import com.growthook.aos.util.NETWORK_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.internal.http.HTTP_UNAUTHORIZED
import retrofit2.HttpException
import timber.log.Timber

sealed class ApiResult<out T> {
    data class Success<out T>(val value: T) : ApiResult<T>()
    data class Error<out T>(val code: Int, val message: String?) : ApiResult<T>()
    data class Exception(val e: Throwable) : ApiResult<Nothing>()
}

fun <T> safeFlow(apiFunc: suspend () -> T): Flow<ApiResult<T>> = flow {
    try {
        emit(ApiResult.Success(apiFunc.invoke()))
    } catch (e: HttpException) {
        if (e.code() == NETWORK_ERROR || e.code() == HTTP_UNAUTHORIZED) {
            Timber.e("auth ${e.message}")
        } else {
            emit(ApiResult.Error(code = e.code(), message = e.stackTraceToString()))
        }
    } catch (e: Exception) {
        emit(ApiResult.Exception(e))
    } catch (e: Throwable) {
        emit(ApiResult.Exception(e))
    }
}
