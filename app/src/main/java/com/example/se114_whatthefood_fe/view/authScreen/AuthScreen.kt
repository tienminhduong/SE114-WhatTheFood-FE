package com.example.se114_whatthefood_fe.view.authScreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.ui.theme.White
import com.example.se114_whatthefood_fe.view_model.AuthViewModel

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
    Column(modifier = Modifier.fillMaxSize()
        .background(
            brush = Brush.verticalGradient(colors = listOf(LightGreen, White))
        ))
    {
        Scaffold(topBar = {
            TopBar(
                isLogin = isLogin,
                onTabClick = { authViewModel.onTabClick(it) },
                onBack = { navController.popBackStack() },
                onHelp = { authViewModel.onHelpClick() }
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
                        top = 100.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                ) {


                    AnimatedContent(
                        targetState = isLogin,
                        transitionSpec = {
                            (fadeIn() + slideInHorizontally { it }).togetherWith(fadeOut() + slideOutHorizontally { -it })
                        }
                    ) { target ->
                        if (target) LoginForm(authViewModel)
                        else RegisterForm(authViewModel)
                    }
                }
            }
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
    onBack: () -> Unit,
    onHelp: () -> Unit,
    modifier: Modifier = Modifier)
{
    Row {
        // Back button
        ButtonIcon(icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = onBack,
            colorBackGround = Color.Transparent,
            colorIcon = White,
            modifier = modifier
        )
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
        // Help button
        ButtonIcon(
            icon = Icons.AutoMirrored.Filled.Help,
            onClick = onHelp,
            colorBackGround = Color.Transparent,
            colorIcon = White,
            modifier = modifier,
        )

    }
}