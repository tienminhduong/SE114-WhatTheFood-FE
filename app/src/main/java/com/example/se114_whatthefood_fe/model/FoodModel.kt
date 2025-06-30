package com.example.se114_whatthefood_fe.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.data.remote.FoodItemNearByResponse
import com.example.se114_whatthefood_fe.data.remote.FoodItemResponse
import kotlinx.coroutines.flow.first
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FoodModel(
    private val api: ApiService,
    private val dataStore: DataStore<Preferences>
) {
    companion object{
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[TOKEN_KEY]
    }

    suspend fun getFoodItemNearBy(longtitude: Float,
                                  latitude: Float,
                                  pageNumber: Int = 0,
                                  pageSize: Int = 10): Response<List<FoodItemNearByResponse>> {
        return try{
            val response = api.getFoodItemsNearBy(longtitude, latitude, pageNumber, pageSize)
            response
        }
        catch (e: Exception){
            Response.error(500, "".toResponseBody(null))
        }
    }

    suspend fun getFoodItemBestSeller(pageNumber: Int = 0,
                                  pageSize: Int = 10): Response<List<FoodItemNearByResponse>> {
        return try{
            val response = api.getFoodItemsBySoldAmount(pageNumber, pageSize)
            response
        }
        catch (e: Exception){
            Response.error(500, "".toResponseBody(null))
        }
    }

    suspend fun getFoodItem(pageNumber: Int = 0,
                            pageSize: Int = 30,
                            categoryId: Int = -1,
                            nameContains: String = "",
                            restaurantId: Int = -1,
                            isAvailableOnly: Boolean = true,
                            priceLowerThan: Int = Int.MAX_VALUE,
                            priceHigherThan: Int = 0,
                            sortBy: String = "",
                            token: String? = null): Response<List<FoodItemResponse>> {
        return try{
            val response = api.getFoodItems(pageNumber = pageNumber,
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
        }
        catch (e: Exception){
            Response.error(500, "".toResponseBody(null))
        }
    }
}