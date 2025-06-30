package com.example.se114_whatthefood_fe.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList

class CustomPaginateList<T> {
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var endReached by mutableStateOf(false)
    var page by mutableStateOf(0)
    val items: SnapshotStateList<T> = mutableStateListOf()
}