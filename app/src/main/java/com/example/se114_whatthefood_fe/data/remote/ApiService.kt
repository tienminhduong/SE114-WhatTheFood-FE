package com.example.se114_whatthefood_fe.data.remote

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
import java.time.LocalDateTime


data class CartItem(
    @SerializedName("restaurant")
    val restaurant: Restaurant,
    @SerializedName("orderDetails")
    val orderDetails: List<ShippingInfoDetail>,
    @SerializedName("totalAmount")
    val totalAmount: Int
)

data class AverageRating(
    @SerializedName("number")
    val number: Int,
    @SerializedName("avgRating")
    val avgRating: Float
)
data class RatingFood(
    @SerializedName("userName")
    val userName: String,
    @SerializedName("userPfp")
    val userPfp: String?,
    @SerializedName("star")
    val star: Int,
    @SerializedName("comment")
    val comment: String?
)

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
    @SerializedName("address")
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
    suspend fun getFoodItemById(@Header("Authorization") token: String,
                                @Path("id") id: Int): Response<FoodItemResponse>

    @GET("fooditems/{id}/ratings")
    suspend fun getRatingFood(@Header("Authorization") token: String,
                                @Path("id") id: Int): Response<List<RatingFood>>

    @GET("fooditems/{id}/ratings/summary")
    suspend fun getSummaryRatingFoodItem(@Header("Authorization") token: String,
                                            @Path("id") id: Int) : Response<AverageRating>

    @GET("cart")
    suspend fun getAllItemsInCart(@Header("Authorization") token: String): Response<List<CartItem>>

    // id truyen vao
    @GET("cart/{restaurantId}")
    suspend fun getCartById(@Header("Authorization") token: String,
                            @Path("restaurantId") restaurantId: Int): Response<CartItem>

    @POST("cart")
    suspend fun addToCart(@Header("Authorization") token: String,
                          @Query("foodItemId") foodItemId: Int,
                          @Query("amount") amount: Int = 0): Response<Unit>
}
