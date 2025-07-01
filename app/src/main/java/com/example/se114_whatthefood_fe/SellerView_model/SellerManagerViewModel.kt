package com.example.se114_whatthefood_fe.SellerView_model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import androidx.lifecycle.ViewModel
import com.example.se114_whatthefood_fe.view.card.DealItem

class SellerManagerViewModel : ViewModel() {

    private val _pendingDeals = MutableStateFlow<List<DealItem>>(emptyList())
    val pendingDeals: StateFlow<List<DealItem>> = _pendingDeals

    private val _approvedDeals = MutableStateFlow<List<DealItem>>(emptyList())
    val approvedDeals: StateFlow<List<DealItem>> = _approvedDeals

    private val _deliveringDeals = MutableStateFlow<List<DealItem>>(emptyList())
    val deleveredDeals: StateFlow<List<DealItem>> = _deliveringDeals

    private val _deliveredDeals = MutableStateFlow<List<DealItem>>(emptyList())
    val deliveredDeals: StateFlow<List<DealItem>> = _deliveredDeals

    private val _completedDeals = MutableStateFlow<List<DealItem>>(emptyList())
    val completedDeals: StateFlow<List<DealItem>> = _completedDeals

    // Confirming
    fun addConfirmingDeal(deal: DealItem) {

    }

    fun updateConfirmingDeal(deal: DealItem) {

    }

    fun clearConfirmingDeals() {

    }

    // Preparing
    fun addPreparingDeal(deal: DealItem) {

    }

    fun updatePreparingDeal(deal: DealItem) {

    }

    fun clearPreparingDeals() {
        _pendingDeals.value = emptyList()
    }

    // Shipping
    fun addShippingDeal(deal: DealItem) {

    }

    fun updateShippingDeal(deal: DealItem) {

    }

    fun clearShippingDeals() {

    }

    // Delivered
    fun addDeliveredDeal(deal: DealItem) {

    }

    fun updateDeliveredDeal(deal: DealItem) {

    }

    fun clearDeliveredDeals() {

    }

    fun loadTestPreparingDeals() {
        _pendingDeals.value = List(6) {
            DealItem(
                imageLink = "https://via.placeholder.com/150",
                title = "Đơn hàng #00${it + 1}",
                status = "Approved",
                userId = "user_0${it % 2 + 1}",
                userContact = if (it % 2 == 0) "0912345678" else "0987654321"
            )
        }
    }

    fun getNextStatus(currentStatus: String): String {
        return when (currentStatus.lowercase()) {
            "Pending" -> "Approved"
            "Approved" -> "Delivering"
            "Delivering" -> "Delivered"
            "Delivered" -> "Completed"
            else -> currentStatus
        }
    }

    fun acceptDeal(deal: DealItem) {
        val nextStatus = deal.status?.let { getNextStatus(it) }
        val updatedDeal = deal.copy(status = nextStatus)

        removeFromCurrentList(deal)
        addToListByStatus(updatedDeal)

    }

    fun removeFromCurrentList(deal: DealItem) {
        when (deal.status?.lowercase()) {
            "Pending" -> _pendingDeals.value = _pendingDeals.value - deal
            "Approved" -> _approvedDeals.value = _approvedDeals.value - deal
            "Delivering" -> _deliveringDeals.value = _deliveringDeals.value - deal
            "Delivered" -> _deliveredDeals.value = _deliveredDeals.value - deal
        }
    }

    fun addToListByStatus(deal: DealItem) {
        when (deal.status?.lowercase()) {
            "Approved" -> _approvedDeals.value = _approvedDeals.value + deal
            "Delivering" -> _deliveringDeals.value = _deliveringDeals.value + deal
            "Delivered" -> _deliveredDeals.value = _deliveredDeals.value + deal
            "Completed" -> _completedDeals.value = _completedDeals.value + deal
        }
    }



}