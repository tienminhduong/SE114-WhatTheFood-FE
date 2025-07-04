package com.example.se114_whatthefood_fe.view_model

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.se114_whatthefood_fe.Api.CreateOrder
import com.example.se114_whatthefood_fe.data.remote.UserInfo
import com.example.se114_whatthefood_fe.model.AuthModel
import com.example.se114_whatthefood_fe.model.ImageModel
import com.example.se114_whatthefood_fe.view.ScreenRoute
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener
import java.util.concurrent.TimeUnit

class AuthViewModel(
    private val authModel: AuthModel,
    private val navController: NavController,
    private val imageModel: ImageModel? = null
) : ViewModel() {
    // upload image
    var isUploading by mutableStateOf(false)
    var uploadResult by mutableStateOf<Boolean?>(null)

    fun uploadImage(context: Context, uri: Uri) {
        viewModelScope.launch {
            isUploading = true
            val result = imageModel?.PushImageAndGetUrl(context, uri)
            uploadResult = result != null
            if (uploadResult == true) {
                _userInfo.value = userInfo.value?.copy(pfpUrl = result?.pfpUrl)
            }
            isUploading = false
        }
    }

    fun uploadImageCustom(context: Context, uri: Uri) {
        val imgUrlStr: String
        viewModelScope.launch {
            isUploading = true
            val result = imageModel?.PushImageAndGetUrlCustom(context, uri)
            uploadResult = result != null
            if (uploadResult == true)
                imageCustomUrl.value = result?.imgUrl ?: ""
            isUploading = false
        }
    }

    var imageCustomUrl = mutableStateOf<String?>(null)

//    fun uploadProductImage(context: Context, uri: Uri, foodId: Int?) {
//        viewModelScope.launch {
//            isUploading = true
//            val result = foodId?.let { imageModel?.PushImageProductAndGetUrl(context, uri, it) }
//            uploadResult = result != null
//            if (uploadResult == true) {
//                _foodImageUrl.value = result?.cldnrUrl // ← cập nhật state đúng cách
//            }
//            isUploading = false
//        }
//    }

    fun uploadProductImage(context: Context, uri: Uri, foodId: Int) {
        viewModelScope.launch {
            isUploading = true
            val result = imageModel?.PushImageProductAndGetUrl(context, uri, foodId)
            _foodImageUrl.value = result?.cldnrUrl
            isUploading = false
        }
    }


    // This ViewModel handles the state and logic for authentication screens (login and register)
    // register
    var nameRegister by mutableStateOf("")
    var phoneRegister by mutableStateOf("")
    var passwordRegister by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var optionRole = mutableStateOf("User")
    var isVisiblePasswordInRegister by mutableStateOf(false)
    var isVisibleConfirmPasswordInRegister by mutableStateOf(false)
    var registerState by mutableStateOf<UIState>(UIState.IDLE)
    var otpCode by mutableStateOf("")

    fun clickVisiblePasswordInRegister() {
        isVisiblePasswordInRegister = !isVisiblePasswordInRegister
    }

    fun clickVisibleConfirmPassword() {
        isVisibleConfirmPasswordInRegister = !isVisibleConfirmPasswordInRegister
    }

    fun onRegisterClick() {
        registerState = UIState.LOADING
        viewModelScope.launch {
            if (nameRegister.isBlank() || phoneRegister.isBlank() ||
                passwordRegister.isBlank() || confirmPassword.isBlank() ||
                optionRole.value.isBlank() ||
                passwordRegister != confirmPassword
            ) {
                // Handle empty fields, e.g., show an error message
                registerState = UIState.ERROR
                return@launch
            }
            val result = authModel.register(
                phoneNumber = phoneRegister,
                password = passwordRegister,
                name = nameRegister,
                role = optionRole.value
            )
            if (result == true)
                registerState = UIState.SUCCESS
            else
                registerState = UIState.ERROR
        }
    }

    // login

    var phoneLogin by mutableStateOf("")
    var passwordLogin by mutableStateOf("")
    var isVisiblePasswordInLogin by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var loginSuccess by mutableStateOf<Boolean?>(null) // null is for initial state, true for success, false for failure

    //var errorMessage by mutableStateOf<String?>(null)
    var loginState by mutableStateOf(UIState.IDLE)

    private var _userInfo = mutableStateOf<UserInfo?>(null)
    val userInfo: State<UserInfo?> get() = _userInfo

    private var _foodImageUrl = mutableStateOf<String?>(null)
    val foodImageUrl: State<String?> get() = _foodImageUrl


    fun clickVisiblePasswordInLogin() {
        isVisiblePasswordInLogin = !isVisiblePasswordInLogin
    }

    fun onForgotPasswordClick() {
        // Handle forgot password logic here, e.g., navigate to a reset password screen
        // This could be implemented using a navigation controller or similar mechanism
    }

    suspend fun getUserInfo() {
        // Fetch user info from authModel
        val result = authModel.getUserInfo()
        if (result.isSuccessful)
            _userInfo.value = result.body()
        else
            _userInfo.value = null
    }

    fun onLoginClick() {
        loginState = UIState.LOADING
        viewModelScope.launch {
            // neu de trong thong tin
            if (phoneLogin.isBlank() || passwordLogin.isBlank()) {
                // Handle empty fields, e.g., show an error message
                loginSuccess = false
                loginState = UIState.ERROR
                return@launch
            } else {
                isLoading = true
                // ket qua tra ve tu authModel
                val result = authModel.login(phoneLogin, passwordLogin)
                // neu thanh cong
                if (result.isSuccessful) {
                    getUserInfo()
                    if (userInfo.value != null) {
                        loginSuccess = true
                        loginState = UIState.SUCCESS
                    }


                }
                // neu that bai
                else {
                    val statusCode = result.code()
                    // sai mat khau sdt hoac khong ton tai
                    loginState = if (statusCode == 400)
                        UIState.ERROR
                    else
                        UIState.NETWORK_ERROR
                    loginSuccess = false
                }
            }
        }

    }

    fun onLogoutClick() {
        viewModelScope.launch {
            // Handle logout logic here, e.g., clear the token in DataStore
            authModel.clearToken()
            // Reset login state
            loginSuccess = null
            // Reset user info
            _userInfo.value = null
            //
            loginState = UIState.IDLE
        }
    }

    suspend fun isLoggedIn(): Boolean {
        // Check if the user is logged in by checking the token in DataStore
        return authModel.getToken() != null
    }

    // dung trong authScreen de xem nguoi dung da dang nhap hay dang ky
    private val _isLogin = mutableStateOf(true)
    val isLogin: State<Boolean> = _isLogin
    fun setLogin(isLoginState: Boolean) {
        _isLogin.value = isLoginState
    }

    fun onTabClick(isLoginState: Boolean) {
        // Handle tab click logic here, e.g., switch between login and register forms
        // This could be implemented using a navigation controller or similar mechanism
        setLogin((isLoginState))

    }

    fun onBackClick() {
        // Handle back click logic here, e.g., navigate to the previous screen
        // This could be implemented using a navigation controller or similar mechanism
    }

    fun onHelpClick() {
        // Handle help click logic here, e.g., show a dialog or navigate to a help screen
        // This could be implemented using a dialog or navigation controller
    }

    fun onPaymentClick(total: Int, activity: Activity) {
        val orderApi = CreateOrder()
        val data = orderApi.createOrder(total.toString())
        val code = data.getString("return_code")

        if (code != "1") {
            Toast.makeText(activity, "Bạn không đủ tiền trong ví ZaloPay", Toast.LENGTH_SHORT)
                .show()
            return
        }


        ZaloPaySDK.getInstance()
            .payOrder(activity, data.getString("zp_trans_token"), "demozpdk://app", object :
                PayOrderListener {
                override fun onPaymentCanceled(zpTransToken: String?, appTransID: String?) {
                    //Handle User Canceled
                    Log.d("Payment", "Payment canceled")
                    Toast.makeText(activity, "Thanh toán đã bị hủy", Toast.LENGTH_SHORT).show()
                }

                override fun onPaymentError(
                    zaloPayErrorCode: ZaloPayError?,
                    zpTransToken: String?,
                    appTransID: String?
                ) {
                    //Redirect to Zalo/ZaloPay Store when zaloPayError == ZaloPayError.PAYMENT_APP_NOT_FOUND
                    //Handle Error
                    //Log.e("Payment", "Payment error: ${zaloPayErrorCode}")
                    Toast.makeText(
                        activity,
                        "Thanh toán thất bại: ${zaloPayErrorCode?.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPaymentSucceeded(
                    transactionId: String,
                    transToken: String,
                    appTransID: String?
                ) {

                    Log.d("Payment", "Payment succeeded")
                    Toast.makeText(activity, "Thanh toán thành công", Toast.LENGTH_SHORT).show()
                }
            })
    }

    var verificationId by mutableStateOf<String?>(null)
    var otpSent by mutableStateOf(false)
    var authResult by mutableStateOf<FirebaseUser?>(null)

    private val auth = FirebaseAuth.getInstance()


    fun sendVerificationCode(activity: Activity) {
        val phoneNumber = "+84${phoneRegister.removePrefix("0")}"
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential, activity)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Log.e("Auth", "Verification Failed: ${e.message}")
                    Toast.makeText(
                        activity,
                        "Xác thực thất bại, vui lòng thử lại hoặc đăng kí lại",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    this@AuthViewModel.verificationId = verificationId
                    otpSent = true
                }

                override fun onCodeAutoRetrievalTimeOut(p0: String) {
                    super.onCodeAutoRetrievalTimeOut(p0)
                    Toast.makeText(activity, "Thời gian xác thực đã hết, vui lòng đăng kí lại", Toast.LENGTH_SHORT).show()
                    otpSent = false
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(code: String, activity: Activity) {
        val credential = PhoneAuthProvider.getCredential(verificationId ?: "", code)
        signInWithPhoneAuthCredential(credential, activity)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, activity: Activity) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    authResult = task.result?.user
                    onRegisterClick()
                } else {
                    Log.e("Auth", "Sign in failed: ${task.exception?.message}")
                    Toast.makeText(activity, "Mã OTP chưa đúng, vui lòng thử lại", Toast.LENGTH_SHORT).show()
                }
            }
    }

}