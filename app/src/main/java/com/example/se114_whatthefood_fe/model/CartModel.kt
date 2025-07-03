package com.example.se114_whatthefood_fe.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.data.remote.CartItem
import kotlinx.coroutines.flow.first
import okhttp3.ResponseBody.Companion.toResponseBody

import retrofit2.Response

class CartModel(val api: ApiService,
    val dataStore: DataStore<Preferences>) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[TOKEN_KEY]
    }

    suspend fun getAllItemsInCart(): Response<List<CartItem>>{
        return try {
            val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
            val response = api.getAllItemsInCart(token = "Bearer $token")
            if (response.isSuccessful)
                response
            else
                Response.error(response.code(), response.errorBody() ?: "".toResponseBody(null))
        }
        catch (e: Exception){
            Response.error(500, "".toResponseBody(null))
        }
    }

    suspend fun getCartItemById(id: Int): Response<CartItem>{
        return try {
            val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
            val response = api.getCartById(token = "Bearer $token", restaurantId = id)
            if (response.isSuccessful)
                response
            else
                Response.error(response.code(), response.errorBody() ?: "".toResponseBody(null))
        }
        catch (e: Exception){
            Response.error(500, "".toResponseBody(null))
        }
    }
}