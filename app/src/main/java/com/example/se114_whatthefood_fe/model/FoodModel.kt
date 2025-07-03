package com.example.se114_whatthefood_fe.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.data.remote.FoodItemNearByResponse
import com.example.se114_whatthefood_fe.data.remote.FoodItemResponse
import com.example.se114_whatthefood_fe.data.remote.NewFoodItemRequest
import com.example.se114_whatthefood_fe.data.remote.UpdateFoodItemRequest
import kotlinx.coroutines.flow.first
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FoodModel(
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

    suspend fun getFoodById(id: Int): Response<FoodItemResponse> {
        return try {
            val response = api.getFoodItemById(id)
            response
        } catch (e: Exception) {
            Response.error(500, "".toResponseBody(null))
        }
    }

    suspend fun getFoodItemNearBy(
        longtitude: Float,
        latitude: Float,
        pageNumber: Int = 0,
        pageSize: Int = 10
    ): Response<List<FoodItemNearByResponse>> {
        return try {
            val response = api.getFoodItemsNearBy(longtitude, latitude, pageNumber, pageSize)
            response
        } catch (e: Exception) {
            Response.error(500, "".toResponseBody(null))
        }
    }

    suspend fun getFoodItemGoodRate(
        pageNumber: Int = 0,
        pageSize: Int = 10
    ): Response<List<FoodItemNearByResponse>> {
        return try {
            val response = api.getFoodItemsByRating(pageNumber, pageSize)
            response
        } catch (e: Exception) {
            Response.error(500, "".toResponseBody(null))
        }
    }

    suspend fun getFoodItemBestSeller(
        pageNumber: Int = 0,
        pageSize: Int = 10
    ): Response<List<FoodItemNearByResponse>> {
        return try {
            val response = api.getFoodItemsBySoldAmount(pageNumber, pageSize)
            response
        } catch (e: Exception) {
            Response.error(500, "".toResponseBody(null))
        }
    }

    suspend fun getFoodItem(
        pageNumber: Int = 0,
        pageSize: Int = 30,
        categoryId: Int = -1,
        nameContains: String = "",
        restaurantId: Int = -1,
        isAvailableOnly: Boolean = true,
        priceLowerThan: Int = Int.MAX_VALUE,
        priceHigherThan: Int = 0,
        sortBy: String = "",
        token: String? = null
    ): Response<List<FoodItemResponse>> {
        return try {
            val response = api.getFoodItems(
                pageNumber = pageNumber,
                pageSize = pageSize,
                categoryId = categoryId,
                nameContains = nameContains,
                restaurantId = restaurantId,
                isAvailableOnly = isAvailableOnly,
                priceLowerThan = priceLowerThan,
                priceHigherThan = priceHigherThan,
                sortBy = sortBy,
                token = token ?: "Bearer ${getToken() ?: ""}"
            )
            response
        } catch (e: Exception) {
            Response.error(500, "".toResponseBody(null))
        }
    }

    suspend fun getFoodItemsBySeller(
        sellerId: Int,
        pageNumber: Int = 0,
        pageSize: Int = 30
    ): Response<List<FoodItemResponse>> {
        return try {
            //val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
            val token =
                "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjAxMjM0NTY3ODkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiQWRtaW5BY2MiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJPd25lciIsImV4cCI6MTc1MTc4MjcxOSwiaXNzIjoiVGhlRm9vZCIsImF1ZCI6IkZvb2RBdWRpZW5jZSJ9.UgkK4txrLDDOoCEQonKOR27OFXSzZmE8zpSyZLuATnmjm4kMjJMnA4OBnDAShryGlyQvKextfgiKje7nBnmnkQ"
            val response = api.getFoodItems(
                token = "Bearer $token",
                pageNumber = pageNumber,
                pageSize = pageSize,
                restaurantId = sellerId
            )

            if (response.isSuccessful) {
                response
            } else {
                Response.error(response.code(), response.errorBody() ?: "".toResponseBody(null))
            }
        } catch (e: Exception) {
            Response.error(500, "".toResponseBody(null))
        }
    }


    suspend fun updateFoodItem(id: String, updated: UpdateFoodItemRequest): Response<Unit> {
        return try {
            //val token = getToken() ?: return Response.error(401, "".toResponseBody(null))
            api.updateFoodItem(
                token = "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjAxMjM0NTY3ODkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiQWRtaW5BY2MiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJPd25lciIsImV4cCI6MTc1MTc4MjcxOSwiaXNzIjoiVGhlRm9vZCIsImF1ZCI6IkZvb2RBdWRpZW5jZSJ9.UgkK4txrLDDOoCEQonKOR27OFXSzZmE8zpSyZLuATnmjm4kMjJMnA4OBnDAShryGlyQvKextfgiKje7nBnmnkQ",
                id = id,
                updatedFood = updated
            )
        } catch (e: Exception) {
            Response.error(500, "".toResponseBody(null))
        }
    }

    suspend fun createFoodItem(request: NewFoodItemRequest): Response<Any>? {
        //val token = getToken()
        val token =
            "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjAxMjM0NTY3ODkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiQWRtaW5BY2MiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJPd25lciIsImV4cCI6MTc1MTc4MjcxOSwiaXNzIjoiVGhlRm9vZCIsImF1ZCI6IkZvb2RBdWRpZW5jZSJ9.UgkK4txrLDDOoCEQonKOR27OFXSzZmE8zpSyZLuATnmjm4kMjJMnA4OBnDAShryGlyQvKextfgiKje7nBnmnkQ"
        return if (token != null) {
            api.newFoodItem("Bearer $token", request)
        } else {
            Response.error(401, "".toResponseBody(null))
        }
    }


}