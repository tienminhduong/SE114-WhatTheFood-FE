package com.example.se114_whatthefood_fe.SellerView_model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.se114_whatthefood_fe.view.card.SellerNotification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SellerNotificationViewModel : ViewModel() {
    private val _notifications = MutableStateFlow<List<SellerNotification>>(
        listOf(
            SellerNotification(
                imageLink = "",
                title = "Đơn hàng mới",
                content = "Bạn vừa nhận được 1 đơn hàng mới từ khách hàng Nguyễn Văn A.",
                timestamp = "10:30 29/06/2025",
                status = false
            ),
            SellerNotification(
                imageLink = "",
                title = "Đơn hàng đã giao",
                content = "Đơn hàng #002 đã được giao thành công.",
                timestamp = "08:15 28/06/2025",
                status = true
            )
        )
    )
    val notifications: StateFlow<List<SellerNotification>> = _notifications

    fun onNotificationClicked(notification: SellerNotification) {
        // Cập nhật status thành true
        _notifications.value = _notifications.value.map {
            if (it.id == notification.id) it.copy(status = true) else it
        }

    }

}
