package com.example.se114_whatthefood_fe.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se114_whatthefood_fe.data.remote.ShippingInfo
import com.example.se114_whatthefood_fe.model.OrderModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderDetailViewModel(private val orderModel: OrderModel) : ViewModel(){
    private val _order = MutableStateFlow<ShippingInfo?>(null)
    val order: StateFlow<ShippingInfo?> = _order

    fun LoadById(orderId: Int){
        viewModelScope.launch {
            _order.value = orderModel.getOrdersById(orderId = orderId).body()
        }
    }

    suspend fun setCompleted(orderId: Int): Boolean{
       return orderModel.setCompleted(orderId = orderId)
    }
}