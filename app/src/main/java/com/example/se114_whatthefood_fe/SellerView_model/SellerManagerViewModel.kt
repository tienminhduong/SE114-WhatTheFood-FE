package com.example.se114_whatthefood_fe.SellerView_model

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.se114_whatthefood_fe.data.remote.ShippingInfo
import com.example.se114_whatthefood_fe.view.card.DealItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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

    // Pending
    fun addPendingDeal(deal: DealItem) {

    }

    fun updatePendingDeal(deal: DealItem) {

    }

    fun clearPendingDeals() {

    }

    // Approved
    fun addApprovedDeal(deal: DealItem) {

    }

    fun updateApprovedDeal(deal: DealItem) {

    }

    fun clearApprovedDeals() {
        _pendingDeals.value = emptyList()
    }

    // Delivering
    fun addDeliveringDeal(deal: DealItem) {

    }

    fun updateDeliveringDeal(deal: DealItem) {

    }

    fun clearDeliveringDeals() {

    }

    // Delivered
    fun addDeliveredDeal(deal: DealItem) {

    }

    fun updateDeliveredDeal(deal: DealItem) {

    }

    fun clearDeliveredDeals() {

    }

    fun loadTestApprovedDeals() {

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

    fun handleAcceptDeal(
        deal: DealItem,
        selectedDeal: MutableState<DealItem?>
    ) {
        //UpdateDealStatus(deal.id, "Approved")
        selectedDeal.value = null
    }


    fun ShippingInfo.toDealItem(): DealItem {
        return DealItem(
            id = this.id,
            imageLink = this.user.pfpUrl,
            title = "Đơn hàng của ${this.user.name}",
            status = this.status,
            userContact = this.user.phoneNumber,
            paymentMethod = this.paymentMethod,
            totalPrice = this.totalPrice,
            userNote = this.userNote,
            address = this.address,
            user = this.user
        )
    }

}