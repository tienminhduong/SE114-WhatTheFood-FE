package com.example.se114_whatthefood_fe.view_model

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.se114_whatthefood_fe.Api.CreateOrder
import com.example.se114_whatthefood_fe.data.remote.Address
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.data.remote.CartItem
import com.example.se114_whatthefood_fe.data.remote.NewOrder
import com.example.se114_whatthefood_fe.data.remote.ShippingInfoDetailDto
import com.example.se114_whatthefood_fe.model.AuthModel
import com.example.se114_whatthefood_fe.model.CartModel
import com.example.se114_whatthefood_fe.view.ScreenRoute
import kotlinx.coroutines.launch
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener

class ConfirmOrderViewModel(private val cartModel: CartModel,  val authModel: AuthModel,
    private val navController: NavHostController) : ViewModel(){
    var description by mutableStateOf("")
    var currentCardItem by mutableStateOf<CartItem?>(null)
    var payType by mutableIntStateOf(0)
    var address by mutableStateOf<Address?>(null)

    fun createOrder(){
        viewModelScope.launch {
        val shippingInfoDetail = mutableListOf<ShippingInfoDetailDto>()
        currentCardItem?.orderDetails?.forEach {
            shippingInfoDetail.add(ShippingInfoDetailDto(it.foodItem.id, it.amount))
        }
        val order = NewOrder(
            restaurantId = currentCardItem?.restaurant?.id?:0,
            shippingInfoDetails = shippingInfoDetail,
            userNote = description,
            paymentMethod = if (payType == 0) "Cash" else "Momo",
            address = address!!
        )

            cartModel.createNewOrder(order)
            cartModel.deleteItemInCart(id = currentCardItem?.restaurant?.id?:0)
            navController.navigate(ScreenRoute.OrderScreen)
        }
    }

    fun getCurrentCardItem(restaurantId: Int){
        viewModelScope.launch {
            val response = cartModel.getCartItemById(restaurantId)
            if(response.isSuccessful){
                currentCardItem = response.body()
            }
        }
    }

    suspend fun updateAmount(id: Int, amount: Int): Boolean {
        return cartModel.updateAmount(id, amount)
    }

    fun onPaymentClick(total: Int, activity: Activity) {
        val orderApi = CreateOrder()
        val data = orderApi.createOrder(total.toString())
        val code = data.getString("return_code")

        if (code != "1") {
            Toast.makeText(activity, "Bạn không đủ tiền trong ví ZaloPay", Toast.LENGTH_SHORT).show()
            return
        }


        ZaloPaySDK.getInstance().payOrder(activity, data.getString("zp_trans_token"), "demozpdk://app", object :
            PayOrderListener {
            override fun onPaymentCanceled(zpTransToken: String?, appTransID: String?) {
                //Handle User Canceled
                Log.d("Payment", "Payment canceled")
                Toast.makeText(activity, "Thanh toán đã bị hủy", Toast.LENGTH_SHORT).show()
            }
            override fun onPaymentError(zaloPayErrorCode: ZaloPayError?, zpTransToken: String?, appTransID: String?) {
                //Redirect to Zalo/ZaloPay Store when zaloPayError == ZaloPayError.PAYMENT_APP_NOT_FOUND
                //Handle Error
                //Log.e("Payment", "Payment error: ${zaloPayErrorCode}")
                Toast.makeText(activity, "Thanh toán thất bại: ${zaloPayErrorCode?.toString()}", Toast.LENGTH_SHORT).show()
            }
            override fun onPaymentSucceeded(transactionId: String, transToken: String, appTransID: String?) {

                Log.d("Payment", "Payment succeeded")
                Toast.makeText(activity, "Thanh toán thành công", Toast.LENGTH_SHORT).show()
                createOrder()
            }
        })
    }
}