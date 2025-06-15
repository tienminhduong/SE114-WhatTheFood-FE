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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
@Preview
fun RegisterFormPreview() {
    RegisterForm()
}

@Composable
fun RegisterForm(modifier: Modifier = Modifier) {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    Column {
        // text field for phone number
        RoundCornerTextFieldWithIcon(
            placeholder = "Số điện thoại",
            value = phone,
            onValueChange = { phone = it },
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
            value = password,
            onValueChange = { password = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password Icon",
                    tint = LightGreen
                )
            },
            isPassword = true,
            modifier = modifier
        )
        // text file for confirm password
        Spacer(modifier.height(16.dp))
        RoundCornerTextFieldWithIcon(
            placeholder = "Nhập lại mật khẩu",
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Confirm Password Icon",
                    tint = LightGreen
                )
            },
            isPassword = true,
            modifier = modifier
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
            var checked by remember { mutableStateOf(false)}
            Checkbox(checked = checked,
                onCheckedChange = {checked = it},
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
            onClick = { /* TODO: Handle register */ },
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

