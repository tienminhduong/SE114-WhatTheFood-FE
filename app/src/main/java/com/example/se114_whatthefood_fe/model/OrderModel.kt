package com.example.se114_whatthefood_fe.model

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.data.remote.OwnedRestaurantInfo
import com.example.se114_whatthefood_fe.data.remote.ShippingInfo
import com.example.se114_whatthefood_fe.data.remote.commentRequest
import kotlinx.coroutines.flow.first
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response


class OrderModel(
    private val api: ApiService,
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[TOKEN_KEY]
    }

    suspend fun pushComment(orderId: Int, star: Int, comment: String): Boolean {
        val token = getToken() ?: return false
        val response = api.pushComment(token = "Bearer $token",
                                        shippingInfoId = orderId,
                                        request = commentRequest(star, comment))
        return response.isSuccessful

    }

    suspend fun setCompleted(orderId: Int): Boolean{
        val token = getToken() ?: return false
        val response = api.setCompleted("Bearer $token", orderId)
        return response.isSuccessful
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
        } catch (e: Exception) {
            Log.d("OrderModel", "getOrdersById: ${e.message}")
            Response.error(500, "".toResponseBody(null))
        }
    }

    // get all orders
    suspend fun getAllOrders(
        pageNumber: Int = 0,
        pageSize: Int = 10
    ): Response<List<ShippingInfo>> {
        return try {
            //val token =

            val response = api.getAllOrder(
                "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjEyMzQiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiVXNlcjMiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJVc2VyIiwiZXhwIjoxNzUxNzkzOTY3LCJpc3MiOiJUaGVGb29kIiwiYXVkIjoiRm9vZEF1ZGllbmNlIn0.VFQ6-Bx4gD42WNJCt7xyMD8uiydsVIeCh4efuD-0AN-Tut0MWXqvDaiMREsM4jFxSoIbD5xjx6qqgNRZfgS8SQ",
                pageNumber,
                pageSize
            )
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
    suspend fun getDeliveringOrders(
        pageNumber: Int = 0,
        pageSize: Int = 10
    ): Response<List<ShippingInfo>> {
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
    // getDeleveredOrder
    suspend fun getDeliveredOrders(
        pageNumber: Int = 0,
        pageSize: Int = 10
    ): Response<List<ShippingInfo>> {
        return try {
            val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
            val response = api.getDeliveredOrder("Bearer $token", pageNumber, pageSize)
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
    suspend fun getCompletedOrders(
        pageNumber: Int = 0,
        pageSize: Int = 10
    ): Response<List<ShippingInfo>> {
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

    suspend fun getPendingOrders(
        pageNumber: Int = 0,
        pageSize: Int = 10
    ): Response<List<ShippingInfo>> {
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

    suspend fun getApprovedOrders(
        pageNumber: Int = 0,
        pageSize: Int = 10
    ): Response<List<ShippingInfo>> {
        return try {
            val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
            val response = api.getApprovedOrder("Bearer $token", pageNumber, pageSize)
            if (response.isSuccessful) {
                response
            } else {
                Response.error(response.code(), response.errorBody() ?: "".toResponseBody(null))
            }
        } catch (e: Exception) {
            Log.d("OrderModel", "getApprovedOrders: ${e.message}")
            Response.error(500, "".toResponseBody(null))
        }
    }

    suspend fun approveOrder(id: Int): Response<Unit> {
        val token =
            "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjAxMjM0NTY3ODkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiQWRtaW5BY2MiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJPd25lciIsImV4cCI6MTc1MTc4MjcxOSwiaXNzIjoiVGhlRm9vZCIsImF1ZCI6IkZvb2RBdWRpZW5jZSJ9.UgkK4txrLDDOoCEQonKOR27OFXSzZmE8zpSyZLuATnmjm4kMjJMnA4OBnDAShryGlyQvKextfgiKje7nBnmnkQ"
        //val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
        return api.setOrderApproved("Bearer $token", id)
    }

    suspend fun startDelivery(id: Int): Response<Unit> {
        val token =
            "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjAxMjM0NTY3ODkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiQWRtaW5BY2MiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJPd25lciIsImV4cCI6MTc1MTc4MjcxOSwiaXNzIjoiVGhlRm9vZCIsImF1ZCI6IkZvb2RBdWRpZW5jZSJ9.UgkK4txrLDDOoCEQonKOR27OFXSzZmE8zpSyZLuATnmjm4kMjJMnA4OBnDAShryGlyQvKextfgiKje7nBnmnkQ"
        //val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
        return api.setOrderDelivering("Bearer $token", id)
    }

    suspend fun markDelivered(id: Int): Response<Unit> {
        val token =
            "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjAxMjM0NTY3ODkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiQWRtaW5BY2MiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJPd25lciIsImV4cCI6MTc1MTc4MjcxOSwiaXNzIjoiVGhlRm9vZCIsImF1ZCI6IkZvb2RBdWRpZW5jZSJ9.UgkK4txrLDDOoCEQonKOR27OFXSzZmE8zpSyZLuATnmjm4kMjJMnA4OBnDAShryGlyQvKextfgiKje7nBnmnkQ"
        //val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
        return api.setOrderDelivered("Bearer $token", id)
    }

//    suspend fun completeOrder(id: Int): Response<Unit> {
//        val token =
//            "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjAxMjM0NTY3ODkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiQWRtaW5BY2MiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJPd25lciIsImV4cCI6MTc1MTc4MjcxOSwiaXNzIjoiVGhlRm9vZCIsImF1ZCI6IkZvb2RBdWRpZW5jZSJ9.UgkK4txrLDDOoCEQonKOR27OFXSzZmE8zpSyZLuATnmjm4kMjJMnA4OBnDAShryGlyQvKextfgiKje7nBnmnkQ"
//        //val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
//        return api.setOrderCompleted("Bearer $token", id)
//    }

    suspend fun getOwnedRestaurant(): Response<OwnedRestaurantInfo> {
        val token =
            "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjAxMjM0NTY3ODkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiQWRtaW5BY2MiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJPd25lciIsImV4cCI6MTc1MTc4MjcxOSwiaXNzIjoiVGhlRm9vZCIsImF1ZCI6IkZvb2RBdWRpZW5jZSJ9.UgkK4txrLDDOoCEQonKOR27OFXSzZmE8zpSyZLuATnmjm4kMjJMnA4OBnDAShryGlyQvKextfgiKje7nBnmnkQ"
        //val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
        return api.getOwnedRestaurant("Bearer $token")
    }

    suspend fun getOwnerPendingOrders(
        pageNumber: Int = 0,
        pageSize: Int = 10
    ): Response<List<ShippingInfo>> {
        return try {
            val token =
                "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjAxMjM0NTY3ODkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiQWRtaW5BY2MiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJPd25lciIsImV4cCI6MTc1MTc4MjcxOSwiaXNzIjoiVGhlRm9vZCIsImF1ZCI6IkZvb2RBdWRpZW5jZSJ9.UgkK4txrLDDOoCEQonKOR27OFXSzZmE8zpSyZLuATnmjm4kMjJMnA4OBnDAShryGlyQvKextfgiKje7nBnmnkQ"
            //val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
            val response = api.getOwnerPendingOrders("Bearer $token", pageNumber, pageSize)
            if (response.isSuccessful) {
                response
            } else {
                Response.error(response.code(), response.errorBody() ?: "".toResponseBody(null))
            }
        } catch (e: Exception) {
            Log.d("OrderModel", "getOwnerPendingOrders: ${e.message}")
            Response.error(500, "".toResponseBody(null))
        }
    }

    suspend fun getOwnerApprovedOrders(
        pageNumber: Int = 0,
        pageSize: Int = 10
    ): Response<List<ShippingInfo>> {
        return try {
            //val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
            val token =
                "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjAxMjM0NTY3ODkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiQWRtaW5BY2MiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJPd25lciIsImV4cCI6MTc1MTc4MjcxOSwiaXNzIjoiVGhlRm9vZCIsImF1ZCI6IkZvb2RBdWRpZW5jZSJ9.UgkK4txrLDDOoCEQonKOR27OFXSzZmE8zpSyZLuATnmjm4kMjJMnA4OBnDAShryGlyQvKextfgiKje7nBnmnkQ"
            val response = api.getOwnerApprovedOrders("Bearer $token", pageNumber, pageSize)
            if (response.isSuccessful) {
                response
            } else {
                Response.error(response.code(), response.errorBody() ?: "".toResponseBody(null))
            }
        } catch (e: Exception) {
            Log.d("OrderModel", "getOwnerApprovedOrders: ${e.message}")
            Response.error(500, "".toResponseBody(null))
        }
    }

    suspend fun getOwnerDeliveringOrders(
        pageNumber: Int = 0,
        pageSize: Int = 10
    ): Response<List<ShippingInfo>> {
        return try {
            val token =
                "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjAxMjM0NTY3ODkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiQWRtaW5BY2MiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJPd25lciIsImV4cCI6MTc1MTc4MjcxOSwiaXNzIjoiVGhlRm9vZCIsImF1ZCI6IkZvb2RBdWRpZW5jZSJ9.UgkK4txrLDDOoCEQonKOR27OFXSzZmE8zpSyZLuATnmjm4kMjJMnA4OBnDAShryGlyQvKextfgiKje7nBnmnkQ"
            //val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
            val response = api.getOwnerDeliveringOrders("Bearer $token", pageNumber, pageSize)
            if (response.isSuccessful) {
                response
            } else {
                Response.error(response.code(), response.errorBody() ?: "".toResponseBody(null))
            }
        } catch (e: Exception) {
            Log.d("OrderModel", "getOwnerDeliveringOrders: ${e.message}")
            Response.error(500, "".toResponseBody(null))
        }
    }

    suspend fun getOwnerDeliveredOrders(
        pageNumber: Int = 0,
        pageSize: Int = 10
    ): Response<List<ShippingInfo>> {
        return try {
            val token =
                "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjAxMjM0NTY3ODkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiQWRtaW5BY2MiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJPd25lciIsImV4cCI6MTc1MTc4MjcxOSwiaXNzIjoiVGhlRm9vZCIsImF1ZCI6IkZvb2RBdWRpZW5jZSJ9.UgkK4txrLDDOoCEQonKOR27OFXSzZmE8zpSyZLuATnmjm4kMjJMnA4OBnDAShryGlyQvKextfgiKje7nBnmnkQ"
            //val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
            val response = api.getOwnerDeliveredOrders("Bearer $token", pageNumber, pageSize)
            if (response.isSuccessful) {
                response
            } else {
                Response.error(response.code(), response.errorBody() ?: "".toResponseBody(null))
            }
        } catch (e: Exception) {
            Log.d("OrderModel", "getOwnerDeliveredOrders: ${e.message}")
            Response.error(500, "".toResponseBody(null))
        }
    }

    suspend fun getOwnerCompletedOrders(
        pageNumber: Int = 0,
        pageSize: Int = 10
    ): Response<List<ShippingInfo>> {
        return try {
            val token =
                "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjAxMjM0NTY3ODkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiQWRtaW5BY2MiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJPd25lciIsImV4cCI6MTc1MTc4MjcxOSwiaXNzIjoiVGhlRm9vZCIsImF1ZCI6IkZvb2RBdWRpZW5jZSJ9.UgkK4txrLDDOoCEQonKOR27OFXSzZmE8zpSyZLuATnmjm4kMjJMnA4OBnDAShryGlyQvKextfgiKje7nBnmnkQ"
            //val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
            val response = api.getOwnerCompletedOrders("Bearer $token", pageNumber, pageSize)
            if (response.isSuccessful) {
                response
            } else {
                Response.error(response.code(), response.errorBody() ?: "".toResponseBody(null))
            }
        } catch (e: Exception) {
            Log.d("OrderModel", "getOwnerCompletedOrders: ${e.message}")
            Response.error(500, "".toResponseBody(null))
        }
    }


}