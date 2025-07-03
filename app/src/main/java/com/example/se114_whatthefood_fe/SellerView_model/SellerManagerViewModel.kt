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
        loadPendingDeals()
        loadApprovedDeals()
        loadDeliveringDeals()
        loadDeliveredDeals()
        loadCompletedDeals()
    }


    fun loadPendingDeals() {
        viewModelScope.launch {
            try {
                val res = orderModel.getOwnerPendingOrders()
                if (res.isSuccessful) {
                    _pendingDeals.value = res.body()?.map { it.toDealItem() } ?: emptyList()
                } else {
                    errorMessage.value = "Lỗi tải đơn chờ: ${res.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Lỗi đơn chờ: ${e.message}"
            }
        }
    }

    fun loadApprovedDeals() {
        viewModelScope.launch {
            try {
                val res = orderModel.getOwnerApprovedOrders()
                if (res.isSuccessful) {
                    _approvedDeals.value = res.body()?.map { it.toDealItem() } ?: emptyList()
                } else {
                    errorMessage.value = "Lỗi tải đơn đã duyệt: ${res.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Lỗi đơn đã duyệt: ${e.message}"
            }
        }
    }

    fun loadDeliveringDeals() {
        viewModelScope.launch {
            try {
                val res = orderModel.getOwnerDeliveringOrders()
                if (res.isSuccessful) {
                    _deliveringDeals.value = res.body()?.map { it.toDealItem() } ?: emptyList()
                } else {
                    errorMessage.value = "Lỗi tải đơn đang giao: ${res.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Lỗi đơn đang giao: ${e.message}"
            }
        }
    }

    fun loadDeliveredDeals() {
        viewModelScope.launch {
            try {
                val res = orderModel.getOwnerDeliveredOrders()
                if (res.isSuccessful) {
                    _deliveredDeals.value = res.body()?.map { it.toDealItem() } ?: emptyList()
                } else {
                    errorMessage.value = "Lỗi tải đơn đã giao: ${res.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Lỗi đơn đã giao: ${e.message}"
            }
        }
    }

    fun loadCompletedDeals() {
        viewModelScope.launch {
            try {
                val res = orderModel.getOwnerCompletedOrders()
                if (res.isSuccessful) {
                    _completedDeals.value = res.body()?.map { it.toDealItem() } ?: emptyList()
                } else {
                    errorMessage.value = "Lỗi tải đơn hoàn tất: ${res.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Lỗi đơn hoàn tất: ${e.message}"
            }
        }
    }


    fun updateDealToNextStatus(deal: DealItem) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val status = deal.status?.lowercase()
                val response = when (status) {
                    "pending" -> orderModel.approveOrder(deal.id)
                    "approved" -> orderModel.startDelivery(deal.id)
                    "delivering" -> orderModel.markDelivered(deal.id)
                    //"delivered" -> orderModel.completeOrder(deal.id)
                    else -> null
                }

                if (response != null && response.isSuccessful) {
                    loadAllDeals()
                } else {
                    errorMessage.value = "Lỗi cập nhật trạng thái: ${response?.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Lỗi ngoại lệ: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
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