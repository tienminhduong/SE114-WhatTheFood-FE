package com.example.se114_whatthefood_fe.SellerView_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se114_whatthefood_fe.data.remote.RatingFood
import com.example.se114_whatthefood_fe.model.RatingModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SellerRatedViewModel(
    private val ratingModel: RatingModel,
    private val sellerId: Int
) : ViewModel() {

    private val _ratings = MutableStateFlow<List<RatingFood>>(emptyList())
    val ratings: StateFlow<List<RatingFood>> = _ratings

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchRatings()
    }

    fun fetchRatings() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = ratingModel.getRatingsForSeller(sellerId.toString())
                _ratings.value = result
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Không thể tải đánh giá"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
