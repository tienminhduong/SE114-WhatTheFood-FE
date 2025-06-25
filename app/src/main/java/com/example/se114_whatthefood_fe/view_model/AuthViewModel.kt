package com.example.se114_whatthefood_fe.view_model

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se114_whatthefood_fe.model.AuthModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel(private val authModel: AuthModel) : ViewModel() {
    // This ViewModel handles the state and logic for authentication screens (login and register)
    // register
    var phoneRegister by mutableStateOf("")
    var passwordRegister by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var isVisiblePasswordInRegister by mutableStateOf(false)
    var isVisibleConfirmPasswordInRegister by mutableStateOf(false)

    fun clickVisiblePasswordInRegister() {
        isVisiblePasswordInRegister = !isVisiblePasswordInRegister
    }

    fun clickVisibleConfirmPassword() {
        isVisibleConfirmPasswordInRegister = !isVisibleConfirmPasswordInRegister
    }

    fun onRegisterClick(){
        // Handle register click logic here, e.g., validate input and perform registration
        // This could be implemented using a repository or similar mechanism
    }

    // login

    var phoneLogin by mutableStateOf("")
    var passwordLogin by mutableStateOf("")
    var isVisiblePasswordInLogin by mutableStateOf(false)
    var isAgreeToTerms by mutableStateOf(true)
    var isLoading by mutableStateOf(false)
    var loginSuccess by mutableStateOf<Boolean?>(null) // null is for initial state, true for success, false for failure
    //var errorMessage by mutableStateOf<String?>(null)

    fun clickVisiblePasswordInLogin() {
        isVisiblePasswordInLogin = !isVisiblePasswordInLogin
    }
    fun onForgotPasswordClick() {
        // Handle forgot password click logic here, e.g., navigate to a reset password screen
        // This could be implemented using a navigation controller or similar mechanism
    }
    fun onLoginClick() {
        viewModelScope.launch {
            // neu de trong thong tin
            if( phoneLogin.isBlank() || passwordLogin.isBlank() || isAgreeToTerms == false) {
                // Handle empty fields, e.g., show an error message
                loginSuccess = false
                return@launch
            }
            else{
                isLoading = true
                authModel.login(phoneLogin, passwordLogin)
            }
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