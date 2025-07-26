package com.nexxlabs.cartly.data.api

import com.nexxlabs.cartly.data.api.model.ApiResponse
import retrofit2.Response

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<ApiResponse<T>>): NetworkResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null && body.success && body.data != null) {
                NetworkResult.Success(body.data)
            } else {
                NetworkResult.Failure(response.code(), body?.message ?: "Unknown error")
            }
        } else {
            NetworkResult.Failure(response.code(), response.message())
        }
    } catch (e: Exception) {
        NetworkResult.Exception(e)
    }
}
