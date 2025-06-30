package com.example.se114_whatthefood_fe.util

data class CustomPaginateList<T>(
    var isLoading: Boolean = false,
    var items: List<T> = emptyList(),
    var error: String? = null,
    var endReached: Boolean = false,
    var page: Int = 0,
)