package com.example.se114_whatthefood_fe.model

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.data.remote.FoodItemNearByResponse
import com.example.se114_whatthefood_fe.data.remote.ShippingInfo
import kotlinx.coroutines.flow.first
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response


class OrderModel(private val api: ApiService,
                 private val dataStore: DataStore<Preferences>) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[TOKEN_KEY]
    }

    // get ordersById
    suspend fun getOrdersById(orderId: Int): Response<ShippingInfo> {
        return try {
            val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
            val response = api.getOrderById("Bearer $token", orderId)
            if (response.isSuccessful) {
                response
            } else {
                Response.error(response.code(), response.errorBody() ?: "".toResponseBody(null))
            }
        } catch (e: Exception){
            Log.d("OrderModel", "getOrdersById: ${e.message}")
            Response.error(500, "".toResponseBody(null))
        }
    }
    // get all orders
    suspend fun getAllOrders(pageNumber: Int = 0, pageSize: Int = 10): Response<List<ShippingInfo>> {
        return try {
            val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
            val response = api.getAllOrder("Bearer $token", pageNumber, pageSize)
            if (response.isSuccessful) {
                response
            } else {
                Response.error(response.code(), response.errorBody() ?: "".toResponseBody(null))
            }
        } catch (e: Exception) {
            Log.d("OrderModel", "getAllOrders: ${e.message}")
                Response.error(500, "".toResponseBody(null))
        }
    }

    //getDeliveringOrder
    suspend fun getDeliveringOrders(pageNumber: Int = 0, pageSize: Int = 10): Response<List<ShippingInfo>> {
        return try {
            val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
            val response = api.getDeliveringOrder("Bearer $token", pageNumber, pageSize)
            if (response.isSuccessful) {
                response
            } else {
                Response.error(response.code(), response.errorBody() ?: "".toResponseBody(null))
            }
        } catch (e: Exception) {
            Log.d("OrderModel", "getAllOrders: ${e.message}")
            Response.error(500, "".toResponseBody(null))
        }
    }
    //getCompletedOrder
    suspend fun getCompletedOrders(pageNumber: Int = 0, pageSize: Int = 10): Response<List<ShippingInfo>> {
        return try {
            val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
            val response = api.getCompletedOrder("Bearer $token", pageNumber, pageSize)
            if (response.isSuccessful) {
                response
            } else {
                Response.error(response.code(), response.errorBody() ?: "".toResponseBody(null))
            }
        } catch (e: Exception) {
            Log.d("OrderModel", "getAllOrders: ${e.message}")
            Response.error(500, "".toResponseBody(null))
        }
    }
    suspend fun getPendingOrders(pageNumber: Int = 0, pageSize: Int = 10): Response<List<ShippingInfo>> {
        return try {
            val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
            val response = api.getPendingOrder("Bearer $token", pageNumber, pageSize)
            if (response.isSuccessful) {
                response
            } else {
                Response.error(response.code(), response.errorBody() ?: "".toResponseBody(null))
            }
        } catch (e: Exception) {
            Response.error(500, "".toResponseBody(null))
        }
    }
}