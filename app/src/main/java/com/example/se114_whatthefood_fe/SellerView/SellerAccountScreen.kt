package com.example.se114_whatthefood_fe.SellerView

import android.net.Uri
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.view.ScaffoldRoute
import com.example.se114_whatthefood_fe.view.ScreenRoute
import com.example.se114_whatthefood_fe.view.SellerRoute
import com.example.se114_whatthefood_fe.view_model.AuthViewModel


@Composable
fun HeaderWhenLoggedIn(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel
) {
    // Get user data from authViewModel
    val user = authViewModel.userInfo.value
    val name = user?.name ?: ""

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // icon for user avatar
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(80.dp),
            contentAlignment = Alignment.Center,
        ) {
//            Icon(
//                imageVector =  Icons.Default.Person,
//                contentDescription = "User Avatar",
//                tint = LightGreen,
//                modifier = Modifier.fillMaxSize()
//            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(user?.pfpUrl)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)  // Cache trên ổ đĩa
                    .memoryCachePolicy(CachePolicy.ENABLED) // Cache trên RAM
                    //.size(100, 100) // Set kích thước ảnh
                    .placeholder(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
                    .error(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
                    .build(),
                contentDescription = "Card Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()//.clip(shape= RoundedCornerShape(8.dp))
            )
        }
        Text(
            text = name,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            color = com.example.se114_whatthefood_fe.ui.theme.White,
            fontSize = 30.sp
        )
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
        colors = ButtonDefaults.buttonColors(
            containerColor = com.example.se114_whatthefood_fe.ui.theme.White,
            contentColor = LightGreen
        ),
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = LightGreen,
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = text,
                modifier = Modifier.weight(1f),
                color = LightGreen,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Icon(
                imageVector = trailingIcon,
                contentDescription = null,
                tint = LightGreen,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}


@Composable
fun SellerAccountScreen(
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navControllerWhenLogout: NavHostController
) {


    // Gọi API khi vào màn
    LaunchedEffect(Unit) {
        authViewModel.getUserInfo()
    }

    // dung cho doi anh
    val context = LocalContext.current
    val activity = LocalActivity.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            authViewModel.uploadImage(context = context, uri = it)
        }
    }
    // add accountViewModel to handle user data and actions

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Gọi suspend function để lấy login status
        val isLoggedIn by produceState<Boolean?>(initialValue = null) {
            value = authViewModel.isLoggedIn()
        }

        com.example.se114_whatthefood_fe.view.deviceScreen.HeaderWhenLoggedIn(
            modifier = Modifier.padding(16.dp),
            authViewModel = authViewModel
        )
        Spacer(modifier = Modifier.height(16.dp))
        // vi voucher
        com.example.se114_whatthefood_fe.view.deviceScreen.ButtonWithLeadingAndTrailingIcon(
            "Đổi ảnh đại diện",
            leadingIcon = Icons.Default.AccountCircle,
            trailingIcon = Icons.Default.PlayArrow,
            onClick = { launcher.launch("image/*") },
            modifier = Modifier
        )
        // thanh toan
        com.example.se114_whatthefood_fe.view.deviceScreen.ButtonWithLeadingAndTrailingIcon(
            "Đăng kí nhà hàng",
            leadingIcon = Icons.Default.Restaurant,
            trailingIcon = Icons.Default.PlayArrow,
            onClick = { navController.navigate(SellerRoute.RegisterRestaurantScreen) },
            modifier = Modifier.padding(top = 5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                authViewModel.onLogoutClick()
                // Navigate to login or register screen after logout
                navControllerWhenLogout.navigate(ScaffoldRoute.LoginOrRegisterScaffold)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = com.example.se114_whatthefood_fe.ui.theme.White,
                contentColor = LightGreen
            ),
            shape = RoundedCornerShape(8.dp)
        )
        {
            Text(text = "Đăng xuất", color = LightGreen)
        }

    }

}