package com.example.se114_whatthefood_fe.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.data.remote.LoginRequest
import com.example.se114_whatthefood_fe.data.remote.LoginResponse
import com.example.se114_whatthefood_fe.data.remote.NotificationTokenDto
import com.example.se114_whatthefood_fe.data.remote.RegisterRequest
import com.example.se114_whatthefood_fe.data.remote.UserInfo
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject


class AuthModel (
    private val api: ApiService,
    private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>
) {
    companion object{
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }


    suspend fun clearToken() {
        val token = dataStore.data.map { preferences -> preferences[TOKEN_KEY] }.first() ?: ""
        val deviceToken = FirebaseMessaging.getInstance().token.await()
        api.deleteDeviceToken("Bearer $token", deviceToken)

        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }

    }

    suspend fun register(phoneNumber: String, password: String, name: String, role: String): Boolean {
        var result: Boolean = false
        try {
            val response = api.register(RegisterRequest(phoneNumber, password, name, role))
            result = response.isSuccessful
        } catch (e: Exception) {
            result = false
        }
        return result
    }

    suspend fun login(phoneNumber: String, password: String): Response<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(phoneNumber, password))
            if (response.isSuccessful) {
                val token = response.body()?.token ?: ""
                saveToken(token)
                val deviceToken = FirebaseMessaging.getInstance().token.await()
                if (deviceToken.isNotEmpty()) {
                    api.registerDeviceToken("Bearer $token", NotificationTokenDto(deviceToken))
                }
            }
            response
        } catch (e: Exception) {
            Response.error(500, "".toResponseBody(null))
        }
    }

    suspend fun getUserInfo(): Response<UserInfo>{
        val token = "Bearer ${getToken()}"
        return try {
            if (token.isEmpty()) {
                Response.error(401, "".toResponseBody(null))
            } else {
                api.getUserInfo(token)
            }
        } catch (e: Exception) {
            Response.error(500, "".toResponseBody(null))
        }
    }

    suspend fun saveToken(token: String) {
        // Save the token to DataStore
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[TOKEN_KEY]
    }
}