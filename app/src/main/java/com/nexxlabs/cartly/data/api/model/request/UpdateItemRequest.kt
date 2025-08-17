package com.nexxlabs.cartly.data.api.model.request

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UpdateItemRequest(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("quantity")
    val quantity: Int? = null,

    @SerializedName("unit")
    val unit: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("date_ordered")
    val dateOrdered: Date? = null
)
