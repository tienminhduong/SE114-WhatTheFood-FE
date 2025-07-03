package com.example.se114_whatthefood_fe.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import kotlinx.android.parcel.Parcelize

data class ShippingInfoDetail(
    @SerializedName("foodItem")
    val foodItem: FoodItemResponse,
    @SerializedName("amount")
    val amount: Int
)
data class Address(
    @SerializedName("name")
    val name: String,
    @SerializedName("longitude")
    val longitude: Float,
    @SerializedName("latitude")
    val latitude: Float
)
// shipping info API
data class ShippingInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("orderTime")
    val orderTime: String,
    @SerializedName("arrivedTime")
    val arrivedTime: String,
    @SerializedName("totalPrice")
    val totalPrice: Int,
    @SerializedName("userNote")
    val userNote: String,
    @SerializedName("restaurant")
    val restaurant: Restaurant,
    @SerializedName("status")
    val status: String,
    @SerializedName("paymentMethod")
    val paymentMethod: String,
    @SerializedName("address")
    val address: Address,
    @SerializedName("shippingInfoDetails")
    val shippingInfoDetails: List<ShippingInfoDetail>
)

// auth API
// login
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
// register
data class RegisterRequest(
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("role")
    val role: String
)
data class RegisterResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("pfpUrl")
    val pfpUrl: String? = null
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
    var pfpUrl: String?
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
    @SerializedName("cldnrUrl")
    val cldnrUrl: String?,
    @SerializedName("addressDto")
    val address: Address?,
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
    val restaurant: Restaurant,
    @SerializedName("cldnrUrl")
    val cldnrUrl: String?
)
// cac quan an gan day
data class Rating(
    @SerializedName("avgRating")
    val average: Float,
    @SerializedName("number")
    val number: Int
)
data class FoodItemNearByResponse(
    @SerializedName("foodId")
    val foodId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: Rating,
    @SerializedName("distanceInKm")
    val distanceInKm: Float,
    @SerializedName("distanceInTime")
    val distanceInTime: Int,
    @SerializedName("imgUrl")
    var imgUrl: String? = null,
    @SerializedName("soldAmount")
    val soldAmount: Int,
    @SerializedName("restaurantName")
    val restaurantName: String
)
data class NotificationTokenDto(
    @SerializedName("deviceToken")
    val deviceToken: String
)

@Parcelize
data class Notification(
    val id: Int,
    val title: String,
    val content: String,
    val dateTime: String,
    val isRead: Boolean = false
): Parcelable

interface ApiService {
    // auth API
    @Headers("Content-Type: application/json")
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("users/device-token")
    suspend fun registerDeviceToken(@Header("Authorization") token: String,
                                    @Body request: NotificationTokenDto): Response<Unit>

    @Headers("Content-Type: application/json")
    @DELETE("users/device-token")
    suspend fun deleteDeviceToken(@Header("Authorization") token: String,
                                  @Query("deviceToken") deviceToken: String): Response<Unit>

    @GET("users/info")
    suspend fun getUserInfo(@Header("Authorization") token: String): Response<UserInfo>

    @Headers("Content-Type: application/json")
    @POST("users/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    // user's image
    @Multipart
    @POST("images/profile")
    suspend fun uploadProfileImage(@Header("Authorization") token: String?,
                                   @Part image: MultipartBody.Part
    ): Response<UserInfo>

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

    // get food item nearby
    @GET("fooditems/recommended/bylocation")
    suspend fun getFoodItemsNearBy(@Query("latitude") latitude: Float,
                                   @Query("longitude") longitude: Float,
                                   @Query("pageNumber") pageNumber: Int = 0,
                                   @Query("pageSize") pageSize: Int = 10)
    : Response<List<FoodItemNearByResponse>>

    @GET("fooditems/recommended/bysoldamount")
    suspend fun getFoodItemsBySoldAmount(@Query("pageNumber") pageNumber: Int = 0,
                                         @Query("pageSize") pageSize: Int = 10,
    ): Response<List<FoodItemNearByResponse>>

    @GET("fooditems/recommended/byrating")
    suspend fun getFoodItemsByRating(@Query("pageNumber") pageNumber: Int = 0,
                                         @Query("pageSize") pageSize: Int = 10,
    ): Response<List<FoodItemNearByResponse>>

    // order API
    @GET("shippinginfo")
    suspend fun getAllOrder(@Header("Authorization") token: String,
                            @Query("pageNumber") pageNumber: Int = 0,
                            @Query("pageSize") pageSize: Int = 10): Response<List<ShippingInfo>>

    @GET("shippinginfo/pending")
    suspend fun getPendingOrder(@Header("Authorization") token: String,
                            @Query("pageNumber") pageNumber: Int = 0,
                            @Query("pageSize") pageSize: Int = 10): Response<List<ShippingInfo>>
    @GET("shippinginfo/delivering")
    suspend fun getDeliveringOrder(@Header("Authorization") token: String,
                            @Query("pageNumber") pageNumber: Int = 0,
                            @Query("pageSize") pageSize: Int = 10): Response<List<ShippingInfo>>
    @GET("shippinginfo/completed")
    suspend fun getCompletedOrder(@Header("Authorization") token: String,
                            @Query("pageNumber") pageNumber: Int = 0,
                            @Query("pageSize") pageSize: Int = 10): Response<List<ShippingInfo>>

    @GET("shippinginfo/detail/{id}")
    suspend fun getOrderById(@Header("Authorization") token: String,
                             @Path("id") id: Int): Response<ShippingInfo>

    @GET("fooditems/{id}")
    suspend fun getFoodItemById(@Path("id") id: Int): Response<FoodItemResponse>

    @GET("users/notifications")
    suspend fun getAllNotifications(@Header("Authorization") token: String): List<Notification>
}
