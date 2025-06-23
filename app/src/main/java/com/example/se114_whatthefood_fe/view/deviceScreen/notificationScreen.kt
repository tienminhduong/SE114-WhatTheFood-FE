package com.example.se114_whatthefood_fe.view.deviceScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.se114_whatthefood_fe.ui.theme.HeaderTextSize
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.ui.theme.White

@Composable
@Preview
fun NotificationScreenPreview() {
    NotificationScreen()
}

@Composable
fun NotificationScreen(modifier: Modifier = Modifier) {
    // Placeholder for notification screen content
    // This can be replaced with actual notification content later
    Column(
        modifier = modifier.fillMaxWidth()
            .fillMaxHeight()
            .background(Brush.verticalGradient(colors = listOf(LightGreen, White))),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeaderNotificationScreen(modifier = Modifier.fillMaxWidth().padding(16.dp))
    }
}

@Composable
fun HeaderNotificationScreen(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center){
        Text(text = "Thông báo",
            textAlign = TextAlign.Center,
            fontWeight = Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            fontSize = HeaderTextSize,
            color = White)
        IconButton(onClick = { /* TODO: Implement setting functionality */ },
            modifier = Modifier.align(Alignment.CenterEnd))
        {
            Icon(imageVector = Icons.Default.Settings,
                contentDescription = "Search Icon",
                modifier = Modifier.size(30.dp),
                tint = White)
        }
    }
}