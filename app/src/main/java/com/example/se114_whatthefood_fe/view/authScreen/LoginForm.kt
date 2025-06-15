package com.example.se114_whatthefood_fe.view.authScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.unit.dp
import com.example.se114_whatthefood_fe.ui.theme.DarkBlue
import com.example.se114_whatthefood_fe.ui.theme.LightGreen

@Composable
@Preview
fun LoginFormPreview() {
    LoginForm()
}
@Composable
fun LoginForm(modifier: Modifier = Modifier) {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column{
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
            value = phone,
            onValueChange = { phone = it },
            modifier = Modifier.fillMaxWidth()
        )

        // spacer
        Spacer(modifier.height(16.dp))
        // text field for password
        RoundCornerTextFieldWithIcon(value = password,
            onValueChange = { password = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password Icon",
                    tint = LightGreen
                )
            },
            placeholder = "Mật khẩu",
            isPassword = true,
            modifier = Modifier.fillMaxWidth()
        )

        // spacer
        Spacer(modifier.height(16.dp))

        // forgot password text
        Text(
            "Quên mật khẩu?",
            modifier = Modifier.align(Alignment.End)
                               .clickable(onClick = { /* TODO: Handle forgot password */ }),
            color = DarkBlue,
            style = MaterialTheme.typography.labelSmall,
        )
        Spacer(modifier = Modifier.height(16.dp))

        // login button
        RoundCornerButton(
            onClick = { TODO() },
            modifier = modifier,
            text = "Đăng nhập"
        )
        // Divider
        DividerWithCenterText("Hoặc", modifier)

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