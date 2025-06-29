package com.example.se114_whatthefood_fe.util

import com.example.se114_whatthefood_fe.data.remote.FoodItemResponse

data class PaginateList(
    val isLoading: Boolean = false,
    val items: List<FoodItemResponse> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0,
)