package com.example.se114_whatthefood_fe.view.deviceScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.se114_whatthefood_fe.data.remote.Notification
import com.example.se114_whatthefood_fe.ui.theme.HeaderTextSize
import com.example.se114_whatthefood_fe.ui.theme.White
import com.example.se114_whatthefood_fe.view_model.NotiViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.LocalDateTime

//@Composable
//@Preview
//fun NotificationScreenPreview() {
//    NotificationScreen()
//}

@Composable
fun NotificationScreen(modifier: Modifier = Modifier,
                       viewModel: NotiViewModel
                       ) {
    // Placeholder for notification screen content
    // This can be replaced with actual notification content later
    viewModel.fetchNotifications()

    Column(
        modifier = modifier.fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeaderNotificationScreen(modifier = Modifier.fillMaxWidth().padding(16.dp))
        Box(modifier = Modifier.fillMaxSize()){
            when{
                viewModel.notificationsState.value.loading -> {
                    CircularProgressIndicator(modifier.align(Alignment.Center))
                }
                viewModel.notificationsState.value.error != null -> {
                    Text(text = "ERROR OCCURRED")
                }
                else -> {
                    NotiScreen(viewModel.notificationsState.value.list)
                }
            }
        }
    }
}

@Composable
fun NotiScreen(notifications: List<Notification>){
    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(notifications) {
            NotiItem(it)
        }
    }
}

@Composable
fun NotiItem(
    item: Notification
){
    val inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", Locale.getDefault())

    val dateTime = LocalDateTime.parse(item.dateTime, inputFormatter)
    val formatted = dateTime.format(outputFormatter)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp,12.dp,12.dp),
        elevation = 10.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(20)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.title, fontWeight = FontWeight.ExtraBold)
            Text(text = item.content)
            Text(text = formatted)
        }
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