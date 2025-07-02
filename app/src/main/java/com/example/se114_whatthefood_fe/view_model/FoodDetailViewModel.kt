package com.example.se114_whatthefood_fe.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se114_whatthefood_fe.data.remote.FoodItemResponse
import com.example.se114_whatthefood_fe.data.remote.ShippingInfo
import com.example.se114_whatthefood_fe.model.FoodModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FoodDetailViewModel(private val foodModel: FoodModel): ViewModel() {
    private val _foodItem = MutableStateFlow<FoodItemResponse?>(null)
    val foodItem: StateFlow<FoodItemResponse?> = _foodItem

    fun LoadById(Id: Int){
        viewModelScope.launch {
            _foodItem.value = foodModel.getFoodById(id = Id).body()
        }
    }
}