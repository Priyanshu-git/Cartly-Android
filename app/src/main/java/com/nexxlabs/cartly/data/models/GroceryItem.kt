package com.nexxlabs.cartly.data.models

import com.google.gson.annotations.SerializedName

data class GroceryItem(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String,

    @SerializedName("quantity")
    val quantity: Int,

    @SerializedName("unit")
    val unit: String,

    @SerializedName("status")
    val status: String = "pending", // "pending" | "purchased"

    @SerializedName("date_added")
    val dateAdded: String? = null,

    @SerializedName("date_ordered")
    val dateOrdered: String? = null,

    @SerializedName("added_by_userId")
    val addedByUserId: String
)
