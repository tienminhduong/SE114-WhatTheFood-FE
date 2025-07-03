package com.example.se114_whatthefood_fe.SellerView_model

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.data.remote.Notification
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SellerNotificationViewModel(
    private val api: ApiService,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _notificationsState = mutableStateOf(SellerNotiState())
    val notificationsState: State<SellerNotiState> = _notificationsState

    var seeDetailNotification by mutableStateOf<Notification?>(null)

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    private suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[TOKEN_KEY]
    }

    fun fetchNotifications() {
        viewModelScope.launch {
            _notificationsState.value = _notificationsState.value.copy(loading = true)
            try {
                //val token = getToken()
                val token =
                    "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjAxMjM0NTY3ODkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiQWRtaW5BY2MiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJPd25lciIsImV4cCI6MTc1MTc4MjcxOSwiaXNzIjoiVGhlRm9vZCIsImF1ZCI6IkZvb2RBdWRpZW5jZSJ9.UgkK4txrLDDOoCEQonKOR27OFXSzZmE8zpSyZLuATnmjm4kMjJMnA4OBnDAShryGlyQvKextfgiKje7nBnmnkQ"
                if (token.isNullOrBlank()) {
                    _notificationsState.value = _notificationsState.value.copy(
                        loading = false,
                        error = "Không tìm thấy token"
                    )
                    return@launch
                }

                val notifications = api.getAllNotifications("Bearer $token")
                _notificationsState.value = _notificationsState.value.copy(
                    list = notifications,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                _notificationsState.value = _notificationsState.value.copy(
                    loading = false,
                    error = "Lỗi khi tải thông báo: ${e.message}"
                )
            }
        }
    }

    fun onNotificationClicked(notification: Notification) {
        _notificationsState.value = _notificationsState.value.copy(
            list = _notificationsState.value.list.map {
                if (it.id == notification.id) it.copy(isRead = true) else it
            }
        )
        seeDetailNotification = notification // ← Gán để hiển thị chi tiết
    }


    data class SellerNotiState(
        val loading: Boolean = true,
        val list: List<Notification> = emptyList(),
        val error: String? = null
    )
}
