package com.example.se114_whatthefood_fe.util

import com.example.se114_whatthefood_fe.data.remote.FoodCategory
import com.example.se114_whatthefood_fe.data.remote.FoodItemResponse
import com.example.se114_whatthefood_fe.data.remote.Restaurant
import kotlinx.coroutines.delay
import retrofit2.Response

class TestRepo {
    private val remoteRepo = (1..100).map{
        FoodItemResponse(
            id = it,
            foodName =  "Food Item $it",
            description = "Description for Food Item $it",
            soldAmount = it * 10,
            available = it % 2 == 0, // Even items are available
            price =  10 * it,
            foodCategory = FoodCategory(
                id = it % 5, // Categories from 0 to 4
                name = "Category ${it % 5}"
            ),
            restaurant = Restaurant(
                id = it % 10, // Restaurants from 0 to 9
                name = "Restaurant ${it % 10}",
                address = "Address for Restaurant ${it % 10}"
            )
        )
    }

    suspend fun getItems(page: Int, pageSize: Int): Response<List<FoodItemResponse>> {
        delay(2000)
        val startIndex = page * pageSize
        return if(startIndex < remoteRepo.size) {
            val endIndex = minOf(startIndex + pageSize, remoteRepo.size)
            Response.success(remoteRepo.subList(startIndex, endIndex))
        } else {
            Response.success(emptyList())
        }
    }
}
