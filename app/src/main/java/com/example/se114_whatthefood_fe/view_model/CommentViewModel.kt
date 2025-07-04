package com.example.se114_whatthefood_fe.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.se114_whatthefood_fe.data.remote.ShippingInfo
import com.example.se114_whatthefood_fe.model.OrderModel

class CommentViewModel(val orderModel: OrderModel) : ViewModel() {
    var comment = mutableStateOf("")
    suspend fun getItemCart(id: Int): ShippingInfo?{
        return try{
            val response = orderModel.getOrdersById(id).body()
            response
        }catch (e: Exception){
            null
        }
    }

    suspend fun pushComment(orderId: Int, star: Int, comment: String): Boolean {
        return orderModel.pushComment(orderId, star, comment)
    }

}