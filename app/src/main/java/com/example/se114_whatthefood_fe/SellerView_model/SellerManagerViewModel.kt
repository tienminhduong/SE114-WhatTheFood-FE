package com.example.se114_whatthefood_fe.SellerView_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se114_whatthefood_fe.data.remote.ShippingInfo
import com.example.se114_whatthefood_fe.model.OrderModel
import com.example.se114_whatthefood_fe.view.card.DealItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SellerManagerViewModel(
    private val orderModel: OrderModel,
    private val sellerId: Int
) : ViewModel() {

    private val _pendingDeals = MutableStateFlow<List<DealItem>>(emptyList())
    val pendingDeals: StateFlow<List<DealItem>> = _pendingDeals

    private val _approvedDeals = MutableStateFlow<List<DealItem>>(emptyList())
    val approvedDeals: StateFlow<List<DealItem>> = _approvedDeals

    private val _deliveringDeals = MutableStateFlow<List<DealItem>>(emptyList())
    val deliveringDeals: StateFlow<List<DealItem>> = _deliveringDeals

    private val _deliveredDeals = MutableStateFlow<List<DealItem>>(emptyList())
    val deliveredDeals: StateFlow<List<DealItem>> = _deliveredDeals

    private val _completedDeals = MutableStateFlow<List<DealItem>>(emptyList())
    val completedDeals: StateFlow<List<DealItem>> = _completedDeals

    val isLoading = MutableStateFlow(false)
    val errorMessage = MutableStateFlow<String?>(null)

    init {
        loadAllDeals()
    }

    fun loadAllDeals() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val response = orderModel.getAllOrders()
                if (response.isSuccessful) {
                    val apiDeals = response.body() ?: emptyList()
                    loadDealsFromApi(apiDeals)
                } else {
                    errorMessage.value = "Lỗi khi tải đơn hàng: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Lỗi ngoại lệ khi tải đơn hàng: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun loadDealsFromApi(apiDeals: List<ShippingInfo>) {
        val pending = mutableListOf<DealItem>()
        val approved = mutableListOf<DealItem>()
        val delivering = mutableListOf<DealItem>()
        val delivered = mutableListOf<DealItem>()
        val completed = mutableListOf<DealItem>()

        apiDeals.forEach { info ->
            val deal = info.toDealItem()
            when (deal.status?.lowercase()) {
                "pending" -> pending.add(deal)
                "approved" -> approved.add(deal)
                "delivering" -> delivering.add(deal)
                "delivered" -> delivered.add(deal)
                "completed" -> completed.add(deal)
            }
        }

        _pendingDeals.value = pending
        _approvedDeals.value = approved
        _deliveringDeals.value = delivering
        _deliveredDeals.value = delivered
        _completedDeals.value = completed
    }

    fun getNextStatus(currentStatus: String): String {
        return when (currentStatus.lowercase()) {
            "pending" -> "Approved"
            "approved" -> "Delivering"
            "delivering" -> "Delivered"
            "delivered" -> "Completed"
            else -> currentStatus
        }
    }

    fun ShippingInfo.toDealItem(): DealItem {
        return DealItem(
            id = this.id,
            imageLink = this.user.pfpUrl ?: "",
            title = "Đơn hàng của ${this.user.name ?: "Khách"}",
            status = this.status,
            userContact = this.user.phoneNumber ?: "Không rõ",
            paymentMethod = this.paymentMethod ?: "Không rõ",
            totalPrice = this.totalPrice,
            userNote = this.userNote ?: "",
            address = this.address,
            user = this.user
        )
    }
}