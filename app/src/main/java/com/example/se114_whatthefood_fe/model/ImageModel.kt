package com.example.se114_whatthefood_fe.model

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.data.remote.FoodItemResponse
import com.example.se114_whatthefood_fe.data.remote.UserInfo
import com.example.se114_whatthefood_fe.util.FileUtils
import kotlinx.coroutines.flow.first

class ImageModel(
    private val api: ApiService,
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    suspend fun PushImageAndGetUrl(context: Context, uri: Uri): UserInfo? {
        try {
            val preferences = dataStore.data.first()
            val token = preferences[TOKEN_KEY]
            val multipart = FileUtils.createMultipartFromUri(context, uri, "image")
            val response = api.uploadProfileImage(token = "Bearer ${token}", image = multipart!!)
            return if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.i("Loi", "PushImageAndGetUrl: ${e.message}")
            return null
        }
    }

    suspend fun PushImageProductAndGetUrl(context: Context, uri: Uri, id: Int): FoodItemResponse? {
        try {
            val preferences = dataStore.data.first()
            val token = preferences[TOKEN_KEY]
            val multipart = FileUtils.createMultipartFromUri(context, uri, "image")
            val response = api.uploadFooditem(token = "Bearer ${token}", image = multipart!!, id)
            return if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.i("Loi", "PushImageAndGetUrl: ${e.message}")
            return null
        }
    }
}