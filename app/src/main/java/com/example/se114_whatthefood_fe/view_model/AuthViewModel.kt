package com.example.se114_whatthefood_fe.view_model

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se114_whatthefood_fe.data.remote.UserInfo
import com.example.se114_whatthefood_fe.model.AuthModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel(private val authModel: AuthModel) : ViewModel() {

    init{
        // Initialize any necessary data or state here
        // For example, you could check if the user is already logged in
        viewModelScope.launch {
            if (isLoggedIn()) {
                getUserInfo() // Fetch user info if already logged in
            }
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

    fun clickVisiblePasswordInRegister() {
        isVisiblePasswordInRegister = !isVisiblePasswordInRegister
    }

    fun clickVisibleConfirmPassword() {
        isVisibleConfirmPasswordInRegister = !isVisibleConfirmPasswordInRegister
    }

    fun onRegisterClick(){
        registerState = UIState.LOADING
        viewModelScope.launch {
            if( nameRegister.isBlank() || phoneRegister.isBlank() ||
                passwordRegister.isBlank() || confirmPassword.isBlank() ||
                optionRole.value.isBlank() ||
                passwordRegister != confirmPassword) {
                // Handle empty fields, e.g., show an error message
                registerState = UIState.ERROR
                return@launch
            }
            val result = authModel.register(phoneNumber = phoneRegister,
                                            password = passwordRegister,
                                            name = nameRegister,
                                            role = optionRole.value)
            if(result == true)
                registerState = UIState.SUCCESS
            else
                registerState = UIState.ERROR
        }
    }

    // login

    var phoneLogin by mutableStateOf("")
    var passwordLogin by mutableStateOf("")
    var isVisiblePasswordInLogin by mutableStateOf(false)
    var isAgreeToTerms by mutableStateOf(true)
    var isLoading by mutableStateOf(false)
    var loginSuccess by mutableStateOf<Boolean?>(null) // null is for initial state, true for success, false for failure
    //var errorMessage by mutableStateOf<String?>(null)
    var loginState by mutableStateOf(UIState.IDLE)

    private val _userInfo = mutableStateOf<UserInfo?>(null)
    val userInfo: State<UserInfo?> get() = _userInfo

    fun clickVisiblePasswordInLogin() {
        isVisiblePasswordInLogin = !isVisiblePasswordInLogin
    }
    fun onForgotPasswordClick() {
        // Handle forgot password click logic here, e.g., navigate to a reset password screen
        // This could be implemented using a navigation controller or similar mechanism
    }

    fun getUserInfo(){
        viewModelScope.launch {
            // Fetch user info from authModel
            val result = authModel.getUserInfo()
            if(result.isSuccessful)
                _userInfo.value = result.body()
            else
                _userInfo.value = null
        }
    }

    fun onLoginClick() {
        loginState = UIState.LOADING
        viewModelScope.launch {
            // neu de trong thong tin
            if( phoneLogin.isBlank() || passwordLogin.isBlank() || isAgreeToTerms == false) {
                // Handle empty fields, e.g., show an error message
                loginSuccess = false
                loginState = UIState.ERROR
                return@launch
            }
            else{
                isLoading = true
                // ket qua tra ve tu authModel
                val result =  authModel.login(phoneLogin, passwordLogin)
                // neu thanh cong
                if(result.isSuccessful)
                {
                    loginState = UIState.SUCCESS
                    loginSuccess = true
                    getUserInfo()
                }
                // neu that bai
                else
                {
                    val statusCode = result.code()
                    // sai mat khau sdt hoac khong ton tai
                    loginState = if(statusCode == 400)
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
        }
    }

    suspend fun isLoggedIn(): Boolean {
        // Check if the user is logged in by checking the token in DataStore
        return authModel.getToken() != null
    }
    // dung trong authScreen de xem nguoi dung da dang nhap hay dang ky
    private val _isLogin = mutableStateOf(true)
    val isLogin: State<Boolean> = _isLogin
    fun setLogin(isLoginState: Boolean)
    {
        _isLogin.value = isLoginState
    }

    fun onTabClick(isLoginState: Boolean) {
        // Handle tab click logic here, e.g., switch between login and register forms
        // This could be implemented using a navigation controller or similar mechanism
        setLogin((isLoginState))

    }

    fun onBackClick(){
        // Handle back click logic here, e.g., navigate to the previous screen
        // This could be implemented using a navigation controller or similar mechanism
    }

    fun onHelpClick() {
        // Handle help click logic here, e.g., show a dialog or navigate to a help screen
        // This could be implemented using a dialog or navigation controller
    }
}