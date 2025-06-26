package com.example.se114_whatthefood_fe.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.data.remote.LoginRequest
import com.example.se114_whatthefood_fe.data.remote.LoginResponse
import com.example.se114_whatthefood_fe.data.remote.UserInfo
import kotlinx.coroutines.flow.first
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
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    suspend fun login(phoneNumber: String, password: String): Response<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(phoneNumber, password))
            if (response.isSuccessful) {
                saveToken(response.body()?.token ?: "")
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