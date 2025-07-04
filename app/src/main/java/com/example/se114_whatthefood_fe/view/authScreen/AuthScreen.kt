package com.example.se114_whatthefood_fe.view.authScreen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.ui.theme.White
import com.example.se114_whatthefood_fe.view.ScaffoldRoute
import com.example.se114_whatthefood_fe.view.ScreenRoute
import com.example.se114_whatthefood_fe.view_model.AuthViewModel
import com.example.se114_whatthefood_fe.view_model.UIState

@SuppressLint("ViewModelConstructorInComposable")
@Composable
@Preview
fun AuthScreenPreview() {
//    val authViewModel = AuthViewModel()
//    AuthScreen(authViewModel)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthScreen(authViewModel: AuthViewModel,
               modifier: Modifier = Modifier,
               navController: NavController) {
    // This is a placeholder for the AuthScreen composable.
    // The actual implementation will include the login and register forms,
    // as well as any other necessary UI components.
    // For now, we can just display a simple text or a placeholder.
    val isLogin by authViewModel.isLogin
    Column(modifier = modifier.fillMaxSize()
        .background(brush = Brush.verticalGradient(colors = listOf(LightGreen, White)))
        .systemBarsPadding())
    {
        Scaffold(topBar = {
            TopBar(
                isLogin = isLogin,
                onTabClick = { authViewModel.onTabClick(it) }
            )
        },
            containerColor = Color.Transparent,
            bottomBar = {
                BottomBar()
            }
            ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)

            ) {
                // Content of the AuthScreen will go here, such as LoginForm or RegisterForm
                Column(
                    modifier = Modifier.padding(
                        horizontal = 16.dp
                    )
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedContent(
                        targetState = isLogin,
                        transitionSpec = {
                            (fadeIn() + slideInHorizontally { it }).togetherWith(fadeOut() + slideOutHorizontally { -it })
                        }
                    ) { target ->
                        if (target) LoginForm(authViewModel = authViewModel,
                            navControllerRole = navController)
                        else RegisterForm(authViewModel)
                    }
                }
            }
        }
    }
    if(isLogin)
    {
        ScreenWhenLogin(authViewModel = authViewModel,
            navControllerRole = navController)
    }
    else
    {
        ScreenWhenRegister(authViewModel = authViewModel,
            navController = navController)
    }
}

@Composable
fun ScreenWhenRegister(authViewModel: AuthViewModel,
                       navController: NavController){
    val registerState = authViewModel.registerState
    when(registerState) {
        UIState.LOADING -> {
            Box(modifier = Modifier.fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.5f))
                .pointerInput(Unit){},
                contentAlignment = Alignment.Center){
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = Color.White
                )
            }
        }

        UIState.SUCCESS -> {
            // Handle success state, e.g., navigate to the main screen
            Toast.makeText(LocalView.current.context, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
            authViewModel.setLogin(true)
            authViewModel.registerState = UIState.IDLE // Reset register state
        }

        UIState.ERROR -> {
            // Handle error state, e.g., show an error message
            Toast.makeText(LocalView.current.context, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
            authViewModel.registerState = UIState.IDLE // Reset register state
        }
        else -> {
            // Do nothing, this is the initial state
        }
    }

}

@Composable
fun ScreenWhenLogin(authViewModel: AuthViewModel,
                    navControllerRole: NavController){
    val loginState = authViewModel.loginState
    when(loginState)
    {
        UIState.LOADING -> {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // Màu đen mờ
                // Modifier.pointerInput để chặn mọi tương tác chuột/chạm
                .pointerInput(Unit) {
                    // Khối này không làm gì cả, chỉ để consume (tiêu thụ) sự kiện
                    // và ngăn nó truyền xuống dưới
                },
                contentAlignment = Alignment.Center){
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = Color.White
                )
            }
        }
        UIState.SUCCESS -> {
            // Handle success state, e.g., navigate to the main screen
            authViewModel.loginSuccess?.let { isLoginSuccess ->
                if(isLoginSuccess) {
                    //authViewModel.loginSuccess = false
                    // Navigate to the main screen or show a success message
                    when (authViewModel.userInfo.value?.role) {
                        "User" -> navControllerRole.navigate(ScaffoldRoute.UserScaffold)
                        "Owner" -> navControllerRole.navigate(ScaffoldRoute.SellerScaffold)
                        "Admin" -> navControllerRole.navigate(ScaffoldRoute.SellerScaffold)
                    }
                }
                else {
                    Log.d("Login", "Login failed")
                }
                authViewModel.loginSuccess = null
                authViewModel.loginState = UIState.IDLE
            }
        }
        UIState.ERROR -> {
            // Handle error state, e.g., show an error message
            authViewModel.loginSuccess = false // Reset login success state
            authViewModel.loginState = UIState.ERROR
        }
        UIState.IDLE -> {
            // Do nothing, this is the initial state
        }
        UIState.NETWORK_ERROR -> {
            Toast.makeText(LocalView.current.context, "Network Error", Toast.LENGTH_SHORT).show()
            authViewModel.loginState = UIState.IDLE
        }
    }
}


@Composable
fun BottomBar(){
    Text(text = "Bằng cách đăng ký hoặc đăng nhập, bạn đồng ý với chính sách của Foody",
        fontSize = TextUnit(11f, TextUnitType.Sp),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 16.dp))
}

@Composable
fun TopBar(
    isLogin: Boolean,
    onTabClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier)
{
    Row {
        // tabs
        Row(modifier = modifier.weight(1f),
            horizontalArrangement = Arrangement.Center){
            // tab for login
            TabButton(text = "Đăng nhập",
                isSelected = isLogin,
                onClick = {
                    if(!isLogin)
                    onTabClick(true) },
                modifier = Modifier
            )
            // tab for register
            TabButton(text = "Đăng ký",
                isSelected = !isLogin,
                onClick = {
                    if(isLogin)
                    onTabClick(false) },
                modifier = Modifier
            )
        }

    }
}