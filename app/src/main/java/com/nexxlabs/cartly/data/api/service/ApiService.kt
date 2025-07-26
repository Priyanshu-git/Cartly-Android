package com.nexxlabs.cartly.data.api.service

import com.nexxlabs.cartly.data.api.model.ApiResponse
import com.nexxlabs.cartly.data.api.model.Group
import com.nexxlabs.cartly.data.api.model.LoginResponse
import com.nexxlabs.cartly.data.api.model.RequestOtpBody
import com.nexxlabs.cartly.data.api.model.VerifyOtpBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/request-otp")
    suspend fun requestOtp(@Body body: RequestOtpBody): Response<ApiResponse<Unit>>

    @POST("auth/verify-otp")
    suspend fun verifyOtp(@Body body: VerifyOtpBody): Response<ApiResponse<LoginResponse>>

    @GET("groups")
    suspend fun getGroups(): Response<ApiResponse<List<Group>>>

    // Define other endpoints similarly
}
