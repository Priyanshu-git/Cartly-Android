package com.nexxlabs.cartly.data.models

import com.google.gson.annotations.SerializedName

data class ShoppingList(
    @SerializedName("id")
    val id: String = "",

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("created_by")
    val createdBy: String,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("members")
    val members: List<User> = emptyList(),
)
