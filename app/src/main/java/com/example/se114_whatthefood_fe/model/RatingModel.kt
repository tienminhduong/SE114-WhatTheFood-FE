package com.example.se114_whatthefood_fe.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.data.remote.AverageRating
import com.example.se114_whatthefood_fe.data.remote.RatingFood
import kotlinx.coroutines.flow.first


class RatingModel(
    private val api: ApiService,
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    suspend fun getRatings(foodItemId: Int): List<RatingFood> {
        val token = getToken()
        val result = api.getRatingFood(token = "Bearer $token", id = foodItemId)
        return try {
            if (result.isSuccessful) {
                result.body() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()

        }
    }

    suspend fun getRatingsForSeller(restaurantId: String): List<RatingFood> {
        return try {
            val token = getToken() ?: return emptyList()
            val result = api.getRatingBelongToRestaurant(
                token = "Bearer $token",
                restaurantId = restaurantId
            )

            if (result.isSuccessful) {
                result.body() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }


    suspend fun getAverageRatingFoodItem(foodItemId: Int): AverageRating? {
        val token = getToken()
        val result = api.getSummaryRatingFoodItem(token = "Bearer $token", id = foodItemId)
        return try {
            if (result.isSuccessful)
                result.body()
            else
                null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[TOKEN_KEY]
    }
}