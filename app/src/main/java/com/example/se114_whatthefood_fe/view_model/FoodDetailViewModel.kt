package com.example.se114_whatthefood_fe.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se114_whatthefood_fe.data.remote.AverageRating
import com.example.se114_whatthefood_fe.data.remote.FoodItemResponse
import com.example.se114_whatthefood_fe.data.remote.RatingFood
import com.example.se114_whatthefood_fe.model.FoodModel
import com.example.se114_whatthefood_fe.model.RatingModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FoodDetailViewModel(private val foodModel: FoodModel,
    private val ratingModel: RatingModel): ViewModel() {
    private val _foodItem = MutableStateFlow<FoodItemResponse?>(null)
    val foodItem: StateFlow<FoodItemResponse?> = _foodItem

    fun loadById(id: Int){
        viewModelScope.launch {
            _foodItem.value = foodModel.getFoodById(id = id).body()
        }
    }
    // rating
    private val _rating = MutableStateFlow<List<RatingFood>>(emptyList())
    val rating: StateFlow<List<RatingFood>> = _rating
    fun loadRating(id: Int){
        viewModelScope.launch {
            _rating.value = ratingModel.getRatings(id)
        }
    }
    // get summery rating
    private val _ratingFoodItem = MutableStateFlow<AverageRating?>(null)
    val ratingFoodItem: StateFlow<AverageRating?> = _ratingFoodItem
    fun loadRatingFoodItem(id: Int) {
        viewModelScope.launch {
            _ratingFoodItem.value = ratingModel.getAverageRatingFoodItem(id)
        }
    }

    suspend fun addToCart(idFoodItem: Int): Boolean{
        return foodModel.addToCart(idFoodItem)
    }
}