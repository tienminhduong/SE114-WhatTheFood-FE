package com.example.se114_whatthefood_fe.data.remote

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

// auth API
data class LoginRequest(
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("password")
    val password: String
)

data class LoginResponse(
    @SerializedName("accessToken")
    val token: String
)
// user info API
data class UserInfo(
    @SerializedName("name")
    val name: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("pfpUrl")
    val pfpUrl: String?
)
// food API
data class FoodCategory(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
data class Restaurant(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("addressDto")
    val address: String?,
)
data class FoodItemResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("foodName")
    val foodName: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("soldAmount")
    val soldAmount: Int,
    @SerializedName("available")
    val available: Boolean,
    @SerializedName("price")
    val price: Int,
    @SerializedName("foodCategory")
    val foodCategory: FoodCategory,
    @SerializedName("restaurant")
    val restaurant: Restaurant
)

interface ApiService {
    // auth API
    @Headers("Content-Type: application/json")
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>


    @GET("users/info")
    suspend fun getUserInfo(@Header("Authorization") token: String): Response<UserInfo>

    // food API
    @Headers("Content-Type: application/json")
    @GET("fooditems")
    suspend fun getFoodItems(@Header("Authorization") token: String,
                             @Query("pageNumber") pageNumber: Int = 0,
                             @Query("pageSize") pageSize: Int = 30,
                             @Query("categoryId") categoryId: Int = -1,
                             @Query("nameContains") nameContains: String = "",
                             @Query("restaurantId") restaurantId: Int = -1,
                             @Query("isAvailableOnly") isAvailableOnly: Boolean = true,
                             @Query("priceLowerThan") priceLowerThan: Int = Int.MAX_VALUE,
                             @Query("priceHigherThan") priceHigherThan: Int = 0,
                             @Query("sortBy") sortBy: String = "priceAsc"
    ): Response<List<FoodItemResponse>>
}
