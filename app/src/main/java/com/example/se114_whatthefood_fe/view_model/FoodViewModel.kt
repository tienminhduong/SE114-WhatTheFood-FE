package com.example.se114_whatthefood_fe.view_model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se114_whatthefood_fe.data.remote.FoodItemResponse
import com.example.se114_whatthefood_fe.model.FoodModel
import kotlinx.coroutines.launch

class FoodViewModel(private val foodModel: FoodModel) : ViewModel(){
    val tabGanBanList = mutableStateOf<List<FoodItemResponse>>(emptyList())

    init{
        viewModelScope.launch {
            loadTabGanBanList()
        }
    }

    suspend fun loadTabGanBanList(
        pageNumber: Int = 0,
        pageSize: Int = 30,
        categoryId: Int = -1,
        nameContains: String = "",
        restaurantId: Int = -1,
        isAvailableOnly: Boolean = true,
        priceLowerThan: Int = Int.MAX_VALUE,
        priceHigherThan: Int = 0,
        sortBy: String = ""
    ) {
        tabGanBanList.value = getFoodItem(
            pageNumber, pageSize, categoryId, nameContains, restaurantId, isAvailableOnly, priceLowerThan, priceHigherThan, sortBy
        )
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
        sortBy: String = ""): List<FoodItemResponse>
    {
        val res = foodModel.getFoodItem(pageNumber,
            pageSize,
            categoryId,
            nameContains,
            restaurantId,
            isAvailableOnly,
            priceLowerThan,
            priceHigherThan,
            sortBy
        ).body()
        return res ?: emptyList()
    }
}