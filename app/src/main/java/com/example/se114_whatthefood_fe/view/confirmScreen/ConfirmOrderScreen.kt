package com.example.se114_whatthefood_fe.view.confirmScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HeaderConfirmOrder(modifier: Modifier = Modifier){
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth().background(color = Color.Transparent)){
        Text(text = "Xác nhận đơn hàng",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White)
    }
}
