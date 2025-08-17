package com.nexxlabs.cartly.data.api.model.request

import com.google.gson.annotations.SerializedName

data class CreateGroupRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("created_by")
    val createdBy: String
)
