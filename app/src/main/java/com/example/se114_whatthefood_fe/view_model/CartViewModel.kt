package com.example.se114_whatthefood_fe.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.se114_whatthefood_fe.data.remote.CartItem
import com.example.se114_whatthefood_fe.model.CartModel

class CartViewModel(private val cartModel: CartModel): ViewModel() {
    private val _listItemCard = mutableStateOf(listOf<CartItem>())
    val listItemsInCart: State<List<CartItem>> = _listItemCard

    suspend fun loadListItemsInCart() {
        _listItemCard.value = cartModel.getAllItemsInCart().body() ?: emptyList()
    }
}