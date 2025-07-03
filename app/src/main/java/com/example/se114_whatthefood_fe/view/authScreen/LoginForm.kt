package com.example.se114_whatthefood_fe.view.authScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.se114_whatthefood_fe.model.AuthModel
import androidx.navigation.NavController
import com.example.se114_whatthefood_fe.ui.theme.DarkBlue
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.view.ScaffoldRoute
import com.example.se114_whatthefood_fe.view_model.AuthViewModel
import com.example.se114_whatthefood_fe.view_model.UIState
import kotlin.math.log

@SuppressLint("ViewModelConstructorInComposable")
@Composable
@Preview
fun LoginFormPreview() {
}

@Composable
fun LoginForm(authViewModel: AuthViewModel, modifier: Modifier = Modifier,
              navControllerRole: NavController) {

    val loginState = authViewModel.loginState
    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,) {
        // text field for phone number
        RoundCornerTextFieldWithIcon(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Phone Icon",
                    tint = LightGreen
                )
            },
            placeholder = "Số điện thoại",
            value = authViewModel.phoneLogin,
            onValueChange = { authViewModel.phoneLogin = it },
            modifier = Modifier.fillMaxWidth(),
            keyBoardOption = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // spacer
        Spacer(Modifier.height(16.dp))
        // text field for password
        RoundCornerTextFieldWithIcon(value = authViewModel.passwordLogin,
            onValueChange = { authViewModel.passwordLogin = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password Icon",
                    tint = LightGreen
                )
            },
            placeholder = "Mật khẩu",
            isPassword = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(
                    onClick = { authViewModel.clickVisiblePasswordInLogin() }
                )
                {
                    Icon(
                        imageVector = if(authViewModel.isVisiblePasswordInLogin)
                            Icons.Default.VisibilityOff
                        else Icons.Default.Visibility,
                        contentDescription = "Toggle Password Visibility",
                        tint = LightGreen,
                    )
                }
            },
            isPasswordVisibility = authViewModel.isVisiblePasswordInLogin
        )
        if(loginState == UIState.ERROR)
        {
            Text(text = "Thông tin đăng nhập không chính xác",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp))
        }
        // spacer
        Spacer(Modifier.height(16.dp))

        // forgot password text
        Text(
            "Quên mật khẩu?",
            modifier = Modifier.align(Alignment.End)
                               .clickable(onClick = { authViewModel.onForgotPasswordClick() }),
            color = DarkBlue,
            style = MaterialTheme.typography.labelSmall,
        )
        Spacer(modifier = Modifier.height(16.dp))

        // login button
        RoundCornerButton(
            onClick = {
                authViewModel.onLoginClick()
            },
            modifier = Modifier,
            text = "Đăng nhập"
        )

    }
}