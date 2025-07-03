package com.example.se114_whatthefood_fe.SellerView_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se114_whatthefood_fe.data.remote.NewFoodItemRequest
import com.example.se114_whatthefood_fe.data.remote.UpdateFoodItemRequest
import com.example.se114_whatthefood_fe.model.FoodModel
import com.example.se114_whatthefood_fe.view.card.Product
import kotlinx.coroutines.launch

class SellerHomeViewModel(
    private val foodModel: FoodModel,
    private val sellerId: Int // ID của nhà hàng/người bán hiện tại
) : ViewModel() {

    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = foodModel.getFoodItemsBySeller(sellerId)
                if (response.isSuccessful) {
                    val foodItems = response.body() ?: emptyList()
                    products = foodItems.map {
                        Product(
                            id = it.id.toString(),
                            name = it.foodName,
                            price = it.price.toDouble(),
                            soldAmount = it.soldAmount,
                            isAvailable = it.available,
                            imgUrl = it.cldnrUrl ?: "",
                            categoryId = it.foodCategory.id,
                            restaurantId = it.restaurant.id
                        )
                    }
                } else {
                    errorMessage = "Lỗi khi tải dữ liệu: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Đã xảy ra lỗi: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun updateProduct(updated: Product) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = foodModel.updateFoodItem(
                    id = updated.id ?: return@launch,
                    updated = UpdateFoodItemRequest(
                        name = updated.name ?: "",
                        description = "Cập nhật từ app", // Có thể nhận từ UI
                        categoryName = "Đồ ăn",          // Ánh xạ từ categoryId nếu cần
                        price = updated.price?.toInt() ?: 0,
                        soldAmount = updated.soldAmount ?: 0,
                        available = updated.isAvailable ?: true
                    )
                )
                if (response.isSuccessful) {
                    // Cập nhật trong danh sách local
                    products = products.map {
                        if (it.id == updated.id) updated else it
                    }
                } else {
                    errorMessage = "Lỗi cập nhật: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Đã xảy ra lỗi: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }


    fun addProduct(newProduct: Product) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = foodModel.createFoodItem(
                    NewFoodItemRequest(
                        restaurantId = newProduct.restaurantId!!,
                        name = newProduct.name ?: "Tên món",
                        description = "Thêm từ app",
                        categoryName = "Đồ ăn", // hoặc ánh xạ từ categoryId
                        price = newProduct.price?.toInt() ?: 0
                    )
                )
                if (response != null) {
                    if (response.isSuccessful) {

                        loadProducts()
                    } else {
                        errorMessage = "Lỗi thêm món: ${response.code()}"
                    }
                }
            } catch (e: Exception) {
                errorMessage = "Lỗi ngoại lệ khi thêm món: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

}