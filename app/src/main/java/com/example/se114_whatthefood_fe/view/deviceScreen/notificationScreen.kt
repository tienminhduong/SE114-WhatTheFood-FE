package com.example.se114_whatthefood_fe.view.deviceScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.se114_whatthefood_fe.SellerView.SellerNotificationDetailScreen
import com.example.se114_whatthefood_fe.data.remote.Notification
import com.example.se114_whatthefood_fe.ui.theme.HeaderTextSize
import com.example.se114_whatthefood_fe.ui.theme.White
import com.example.se114_whatthefood_fe.view_model.NotiViewModel
import kotlinx.coroutines.launch
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
                       viewModel: NotiViewModel,
                       ) {

    viewModel.fetchNotifications()
    val scope = rememberCoroutineScope()

    val selectedNotification = viewModel.seeDetailNotification

    Column(
        modifier = modifier.fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeaderNotificationScreen(modifier = Modifier.fillMaxWidth().padding(16.dp))

        if (selectedNotification != null) {
            NotiDetail(
                notification = selectedNotification,
                onBack = { viewModel.seeDetailNotification = null }
            )
        }
        else{
            Box(modifier = Modifier.fillMaxSize()){
                when{
                    viewModel.notificationsState.value.loading -> {
                        CircularProgressIndicator(modifier.align(Alignment.Center))
                    }
                    viewModel.notificationsState.value.error != null -> {
                        Text(text = "ERROR OCCURRED")
                    }
                    else -> {
                        LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                            items(viewModel.notificationsState.value.list) {
                                NotiItem(it) { scope.launch { viewModel.onNotificationClicked(it) } }
                            }
                        }
                    }
                }
            }
        }


    }
}

@Composable
fun NotiItem(
    item: Notification,
    onclick: () -> Unit
){
    val inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", Locale.getDefault())

    val dateTime = LocalDateTime.parse(item.dateTime, inputFormatter)
    val formatted = dateTime.format(outputFormatter)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp,12.dp,12.dp)
            .clickable{onclick()},
        elevation = 10.dp,
        backgroundColor = if (!item.isRead) Color.White else Color.LightGray,
        shape = RoundedCornerShape(20),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                if (item.isRead == false) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(
                                Color.Red,
                                shape = androidx.compose.foundation.shape.CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(text = item.title, fontWeight = FontWeight.ExtraBold)
            }

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
    }
}