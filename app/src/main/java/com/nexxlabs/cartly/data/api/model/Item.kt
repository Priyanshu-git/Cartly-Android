package com.nexxlabs.cartly.data.api.model

data class Item(
    val id: String,
    val label: String,
    val addedBy: String,
    val dateAdded: String,
    val dateOrdered: String?,
    val quantity: Int,
    val unit: String
)
