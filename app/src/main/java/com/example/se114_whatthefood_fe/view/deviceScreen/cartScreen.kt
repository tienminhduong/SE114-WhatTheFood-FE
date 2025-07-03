package com.example.se114_whatthefood_fe.view.deviceScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.se114_whatthefood_fe.view.authScreen.ButtonIcon

@Composable
@Preview
fun test(){
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun cartScreen(modifier: Modifier = Modifier,
               navController: NavController){
    Scaffold(
        topBar = {
            //header
            TopBarCartScreen(navController = navController)
        }
    ) {
        LazyColumn {
            
        }
    }
}

@Composable
fun TopBarCartScreen(navController: NavController, modifier: Modifier = Modifier){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().background(color = Color.Transparent)){
        ButtonIcon(icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = { navController.popBackStack() },
            colorBackGround = Color.Transparent,
            colorIcon = Color.White,
            modifier = modifier)
        Text(text = "Giỏ hàng",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White)
    }
}