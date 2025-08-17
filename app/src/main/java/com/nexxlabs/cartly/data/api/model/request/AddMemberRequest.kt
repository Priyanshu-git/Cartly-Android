package com.nexxlabs.cartly.data.api.model.request

import com.google.gson.annotations.SerializedName

data class AddMemberRequest(
    @SerializedName("user_id")
    val userId: String,

    @SerializedName("role")
    val role: String? = null // backend defaults to "member" if missing
)
