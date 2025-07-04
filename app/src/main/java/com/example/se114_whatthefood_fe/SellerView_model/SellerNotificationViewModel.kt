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
                val token = getToken()
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
        viewModelScope.launch {
            try {
                val token = getToken()
                if (token.isNullOrBlank()) {
                    return@launch
                }

                val response = api.readNotification("Bearer $token", notification.id)
                if (response.isSuccessful) {
                    val updatedNotification = response.body()
                    // Cập nhật danh sách hiển thị với bản đã cập nhật
                    _notificationsState.value = _notificationsState.value.copy(
                        list = _notificationsState.value.list.map {
                            if (it.id == notification.id) updatedNotification ?: it else it
                        }
                    )
                    seeDetailNotification = notification
                }


            } catch (e: Exception) {

            }
        }
    }


    data class SellerNotiState(
        val loading: Boolean = true,
        val list: List<Notification> = emptyList(),
        val error: String? = null
    )
}
