package com.example.se114_whatthefood_fe.view.deviceScreen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.se114_whatthefood_fe.model.AuthModel
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.ui.theme.White
import com.example.se114_whatthefood_fe.view_model.AuthViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
@Preview
fun AccountScreenPreview() {
//    AccountScreen(authViewModel = AuthViewModel(AuthModel(
//        api = TODO(),
//        dataStore = TODO()
//    )))
}

@Composable
@Preview
fun AccountHeaderPreview() {
    //HeaderWhenLoggedIn()
}

@Composable
fun HeaderWhenLoggedIn(modifier: Modifier = Modifier,
                       avatar: ImageVector? = null,
                       name: String = "User Name",
                       authViewModel: AuthViewModel
                      ) {
    Row(modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,) {
        // icon for user avatar
        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(White).size(80.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = avatar ?: Icons.Default.Person,
                contentDescription = "User Avatar",
                tint = LightGreen,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(text = name,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            color = White,
            fontSize = 30.sp)
    }
}

@Composable
fun HeaderWhenNotLogIn(modifier: Modifier = Modifier,
                       authViewModel: AuthViewModel,
                       clickLoginOrRegister: () -> Unit) {
    Row(modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,) {
        // icon for user avatar
        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(White).size(80.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User Avatar",
                tint = LightGreen,
                modifier = Modifier.fillMaxSize()
            )
        }
        // spacer
        Spacer(modifier = Modifier.weight(1f))
        // register or login button
        Button(onClick = { clickLoginOrRegister() },
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(containerColor = White),
            shape = RoundedCornerShape(8.dp)
        )
        {
            Text(text = "Đăng nhập/Đăng ký", color = LightGreen)
        }
    }
}

@Composable
fun ButtonWithLeadingAndTrailingIcon(
    text: String,
    leadingIcon: ImageVector,
    trailingIcon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = LightGreen),
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = leadingIcon,
                contentDescription = null,
                tint = LightGreen,
                modifier = Modifier.size(30.dp))
            Text(text = text,
                modifier = Modifier.weight(1f),
                color = LightGreen,
                textAlign = TextAlign.Center,
                fontSize = 20.sp)
            Icon(imageVector = trailingIcon,
                contentDescription = null,
                tint = LightGreen,
                modifier = Modifier.size(30.dp))
        }
    }
}

@Composable
fun AccountScreen(authViewModel: AuthViewModel,
                  modifier: Modifier = Modifier,
                  navController: NavHostController) {
    // add accountViewModel to handle user data and actions

    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){

        // Gọi suspend function để lấy login status
        val isLoggedIn by produceState<Boolean?>(initialValue = null) {
            value = authViewModel.isLoggedIn()
        }

        // Khi chưa biết trạng thái → hiển thị loading hoặc trống
        if (isLoggedIn == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return
        }

        if (isLoggedIn == true) // check if user is logged in
            HeaderWhenLoggedIn(modifier = Modifier.padding(16.dp),
                authViewModel = authViewModel)
        else
            HeaderWhenNotLogIn(modifier = Modifier.padding(16.dp),
                authViewModel = authViewModel,
                clickLoginOrRegister = {
                    Log.d("AccountScreen", "Click login or register")
                    // Navigate to login or register screen
                    navController.navigate("LoginOrRegister")
                })
        Spacer(modifier = Modifier.height(16.dp))
        // vi voucher
        ButtonWithLeadingAndTrailingIcon(
            "Ví Voucher",
            leadingIcon = Icons.Default.ConfirmationNumber,
            trailingIcon = Icons.Default.PlayArrow,
            onClick = { /* TODO: Handle click */ },
            modifier = Modifier
        )
        // thanh toan
        ButtonWithLeadingAndTrailingIcon(
            "Thanh toán",
            leadingIcon = Icons.Default.Wallet,
            trailingIcon = Icons.Default.PlayArrow,
            onClick = { /* TODO: Handle click */ },
            modifier = Modifier.padding(top = 5.dp)
        )
        if(isLoggedIn == true) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = White,
                    contentColor = LightGreen
                ),
                shape = RoundedCornerShape(8.dp)
            )
            {
                Text(text = "Đăng xuất", color = LightGreen)
            }
        }
    }

}