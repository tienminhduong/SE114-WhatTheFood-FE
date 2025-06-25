package com.example.se114_whatthefood_fe.view.authScreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.se114_whatthefood_fe.ui.theme.DarkBlue
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.view_model.AuthViewModel

@Composable
@Preview
fun RegisterFormPreview() {
//    val authViewModel = AuthViewModel()
//    RegisterForm(authViewModel)
}

@Composable
fun RegisterForm(authViewModel: AuthViewModel, modifier: Modifier = Modifier) {
    Column {
        // text field for phone number
        RoundCornerTextFieldWithIcon(
            placeholder = "Số điện thoại",
            value = authViewModel.phoneRegister,
            onValueChange = { authViewModel.phoneRegister = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Phone Icon",
                    tint = LightGreen
                )
            },
            modifier = modifier
        )

        Spacer(modifier.height(16.dp))
        // text field for password
        RoundCornerTextFieldWithIcon(
            placeholder = "Mật khẩu",
            value = authViewModel.passwordRegister,
            onValueChange = { authViewModel.passwordRegister = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password Icon",
                    tint = LightGreen
                )
            },
            isPassword = true,
            modifier = modifier,
            trailingIcon = {
                IconButton(
                    onClick = { authViewModel.clickVisiblePasswordInRegister() }
                )
                {
                    Icon(
                        imageVector = if(authViewModel.isVisiblePasswordInRegister)
                                        Icons.Default.VisibilityOff
                                      else Icons.Default.Visibility,
                        contentDescription = "Toggle Password Visibility",
                        tint = LightGreen,
                    )
                }
            },
            isPasswordVisibility = authViewModel.isVisiblePasswordInRegister
        )
        // text field for confirm password
        Spacer(modifier.height(16.dp))
        RoundCornerTextFieldWithIcon(
            placeholder = "Nhập lại mật khẩu",
            value = authViewModel.confirmPassword,
            onValueChange = { authViewModel.confirmPassword = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Confirm Password Icon",
                    tint = LightGreen
                )
            },
            isPassword = true,
            modifier = modifier,
            trailingIcon = {
                IconButton(
                    onClick = { authViewModel.clickVisibleConfirmPassword() }
                )
                {
                    Icon(
                        imageVector = if(authViewModel.isVisibleConfirmPasswordInRegister)
                            Icons.Default.VisibilityOff
                        else Icons.Default.Visibility,
                        contentDescription = "Toggle Password Visibility",
                        tint = LightGreen,
                    )
                }
            },
            isPasswordVisibility = authViewModel.isVisibleConfirmPasswordInRegister
        )
        // spacer
        Spacer(modifier.height(16.dp))
        // text đồng ý điều khoản
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()) {
            Text(
                text = "Tôi đồng ý với các điều khoản của WhatTheFood",
                modifier = modifier,
                color = DarkBlue,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = TextUnit(13f, TextUnitType.Sp),
            )
            // check box
            Checkbox(checked = authViewModel.isAgreeToTerms,
                onCheckedChange = {authViewModel.isAgreeToTerms = it},
                colors = CheckboxDefaults.colors(
                    checkedColor = LightGreen,
                    uncheckedColor = Color.Gray,
                    checkmarkColor = Color.White
                )
            )
        }
        // button dang ki
        RoundCornerButton(
            text = "Đăng ký",
            onClick = { authViewModel.onRegisterClick() },
            modifier = modifier
        )
        // divider with center text
        DividerWithCenterText(
            text = "Hoặc",
            modifier = modifier
        )
        // Spacer
        Spacer(modifier.height(16.dp))
        // cac lua chon dang ky khac
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            // google button
            ButtonWithIcon("Tiếp tục với Google",
                onClick = { /* TODO: Handle Google login */ },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = com.example.se114_whatthefood_fe.R.drawable.google__g__logo),
                        contentDescription = "Google Icon",
                        tint = Color.Unspecified, // Use default color for the icon
                        modifier = modifier.size(20.dp).align(Alignment.CenterHorizontally)// Adjust size as needed
                    )
                },
                modifier = modifier)

            // facebook button
            ButtonWithIcon("Tiếp tục với Facebook",
                onClick = { /* TODO: Handle facebook login */ },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = com.example.se114_whatthefood_fe.R.drawable.google__g__logo),
                        contentDescription = "FaceBook Icon",
                        tint = Color.Unspecified, // Use default color for the icon
                        modifier = modifier.size(20.dp).align(Alignment.CenterHorizontally)// Adjust size as needed
                    )
                },
                modifier = modifier)
        }
    }

}

