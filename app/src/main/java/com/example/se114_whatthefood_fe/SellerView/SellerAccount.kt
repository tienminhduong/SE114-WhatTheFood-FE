package com.example.se114_whatthefood_fe.SellerView

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun SellerAccountPreview() {
    //val sellerHomeViewModel = SellerViewModel()
    //AuthScreen(authViewModel)
}

@Composable
fun SellerAccount(
    //SellerAccountViewModel: SellerAccountViewModel,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Nội dung của SELLER",
            fontSize = 24.sp
        )
    }
}