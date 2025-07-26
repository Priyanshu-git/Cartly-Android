package com.nexxlabs.cartly.data.api.model

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?
)
