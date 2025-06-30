package com.example.se114_whatthefood_fe.util

import android.icu.text.Transliterator
import android.icu.text.Transliterator.Position
import android.location.Location
import android.util.Log
import retrofit2.Response
import kotlin.jvm.Throws


class DefaultPaginator<Key, Item>(
    private val initializeKey: Key,
    private var position: Location? = null,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Key, location: Location?) -> Response<List<Item>>,
    private val getNextKey: suspend (List<Item>) -> Key,
    private val onError: suspend (Throwable)-> Unit,
    private val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit,
    private val getPostion: (suspend () -> Location?)? = null
): Paginator<Key, Item> {

    private var currentKey = initializeKey
    private var isMakingRequest = false

    suspend fun getLocation(){
        if(getPostion == null)
            return
        position = getPostion() // Lấy vị trí nếu có hàm getPostion
    }
    override suspend fun loadNextItems(){
        // neu dang request thi return
        if(isMakingRequest)
            return
        // neu khong dang request thi set isMakingRequest = true
        isMakingRequest = true
        onLoadUpdated(true)
        try {
            if(getPostion != null) {
                getLocation() // Lấy vị trí nếu có hàm getPostion
            }
            val result = onRequest(currentKey, position)
            Log.d("DEBUG", "Requesting items with key: $currentKey, position: $position")
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