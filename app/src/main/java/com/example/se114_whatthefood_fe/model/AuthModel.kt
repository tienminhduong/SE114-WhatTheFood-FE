package com.example.se114_whatthefood_fe.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.data.remote.LoginRequest
import com.example.se114_whatthefood_fe.data.remote.LoginResponse
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthModel (
    private val api: ApiService,
    private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>
) {
    companion object{
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    suspend fun login(phoneNumber: String, password: String): Result<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(phoneNumber, password))
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    // Save the token to DataStore
                    saveToken(loginResponse.token)
                    Result.success(loginResponse)
                } ?: Result.failure(Exception("Login failed: No response body"))
            } else {
                Result.failure(Exception("Login failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
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