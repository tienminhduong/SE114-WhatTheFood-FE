package com.example.se114_whatthefood_fe.view_model

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

class NotiViewModel(
    private val api: ApiService,
    private val dataStore: DataStore<Preferences>
): ViewModel() {

    private val _notificationsState = mutableStateOf(NotiState())
    val notificationsState: State<NotiState> = _notificationsState

    var seeDetailNotification by mutableStateOf<Notification?>(null)

    companion object{
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[TOKEN_KEY]
    }

    fun fetchNotifications(){
        viewModelScope.launch {
            try {
                val response = api.getAllNotifications("Bearer ${getToken() ?: ""}")
                _notificationsState.value = _notificationsState.value.copy(
                    list = response,
                    loading = false,
                    error = null
                )
            }catch (e: Exception){
                _notificationsState.value =_notificationsState.value.copy(
                    loading = false,
                    error = "Error fetching Notifications ${e.message}"
                )
            }
        }
    }

     suspend fun onNotificationClicked(notification: Notification) {
        _notificationsState.value = _notificationsState.value.copy(
            list = _notificationsState.value.list.map {
                if (it.id == notification.id)
                {
                    it.copy(isRead = true)
                    api.ReadNotification("Bearer ${getToken() ?: ""}",it.id)
                }
                else it
            }
        )
        seeDetailNotification = notification
    }


    data class NotiState(
        val loading: Boolean = true,
        val list: List<Notification> = emptyList<Notification>(),
        val error: String? = null
    )
}