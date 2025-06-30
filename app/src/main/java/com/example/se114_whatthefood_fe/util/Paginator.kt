package com.example.se114_whatthefood_fe.util

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}