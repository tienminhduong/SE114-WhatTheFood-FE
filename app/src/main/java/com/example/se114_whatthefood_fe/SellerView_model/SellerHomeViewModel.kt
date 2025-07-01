package com.example.se114_whatthefood_fe.SellerView_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.se114_whatthefood_fe.view.card.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class SellerHomeViewModel : ViewModel() {

    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    init {
        loadProducts()
    }

    private fun loadProducts() {
        products = listOf(
            Product(
                id = "1",
                name = "Pizza",
                price = 100.0,
                soldAmount = 200,
                isAvailable = true,
                imgUrl = "https://cdn.tgdd.vn/2021/03/CookProduct/pizza-hai-san-thumbnail-1200x676.jpg",
                categoryId = 1,
                restaurantId = 1
            ),
            Product(
                id = "2",
                name = "Burger",
                price = 80.0,
                soldAmount = 300,
                isAvailable = true,
                imgUrl = "https://cdn.tgdd.vn/2021/06/CookProduct/cach-lam-burger-thit-bo-thumbnail.jpg",
                categoryId = 2,
                restaurantId = 1
            ),
            Product(
                id = "3",
                name = "Salad",
                price = 50.0,
                soldAmount = 120,
                isAvailable = false,
                imgUrl = "https://cdn.tgdd.vn/2021/05/CookProduct/salad-ca-ngu-thumbnail.jpg",
                categoryId = 3,
                restaurantId = 2
            )
        )
    }
    fun updateProduct(updated: Product) {
        products = products.map {
            if (it.id == updated.id) updated else it
        }
    }

}
