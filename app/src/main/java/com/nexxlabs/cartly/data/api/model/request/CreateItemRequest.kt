package com.nexxlabs.cartly.data.api.model.request

import com.google.gson.annotations.SerializedName

data class CreateItemRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("quantity")
    val quantity: Int,

    @SerializedName("unit")
    val unit: String? = null,

    @SerializedName("added_by_userId")
    val addedByUserId: String
)
