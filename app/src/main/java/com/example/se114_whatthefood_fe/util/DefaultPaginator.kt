package com.example.se114_whatthefood_fe.util

import retrofit2.Response
import kotlin.jvm.Throws


class DefaultPaginator<Key, Item>(
    private val initializeKey: Key,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Key) -> Response<List<Item>>,
    private val getNextKey: suspend (List<Item>) -> Key,
    private val onError: suspend (Throwable)-> Unit,
    private val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
): Paginator<Key, Item> {

    private var currentKey = initializeKey
    private var isMakingRequest = false

    override suspend fun loadNextItems(){
        // neu dang request thi return
        if(isMakingRequest)
            return
        // neu khong dang request thi set isMakingRequest = true
        isMakingRequest = true
        onLoadUpdated(true)
        try {
            val result = onRequest(currentKey)

            if (result.isSuccessful) {
                val items = result.body() ?: emptyList()
                currentKey = getNextKey(items)
                onSuccess(items, currentKey)
            } else {
                onError(Exception("Request failed with code: ${result.code()}"))
            }
        } catch (e: Throwable) {
            onError(e) // ✅ Bắt lỗi thật sự
        } finally {
            isMakingRequest = false
            onLoadUpdated(false)
        }
    }
    override fun reset(){
        currentKey = initializeKey
    }
}