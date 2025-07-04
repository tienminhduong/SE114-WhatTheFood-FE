package com.example.se114_whatthefood_fe.view.confirmScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavHostController
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.data.remote.RetrofitInstance
import com.example.se114_whatthefood_fe.data.remote.ShippingInfo
import com.example.se114_whatthefood_fe.model.OrderModel
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.view.ScreenRoute
import com.example.se114_whatthefood_fe.view_model.CommentViewModel
import kotlinx.coroutines.launch

@SuppressLint("ViewModelConstructorInComposable")
@Composable
@Preview
fun preview(){

}

@Composable
fun CommentScreen(modifier: Modifier = Modifier,
                  viewModel: CommentViewModel,
                  id: Int,
                  navHostController: NavHostController){
    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Row(modifier = Modifier.padding(top = 16.dp)){
            Text(text = "Đánh giá đơn hàng",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
        }

        // thong tin don hang
        val order = remember {
            mutableStateOf<ShippingInfo?>(null)
        }

        LaunchedEffect(order.value?.id) {
            order.value = viewModel.getItemCart(id)
        }

        if(order.value == null){
            return@Column
        }

        var rating by remember { mutableStateOf(0) }
        StarRating(rating = rating,
            onRatingChanged = {rating = it},
            modifier = Modifier)
        Spacer(modifier = Modifier.height(50.dp))
        TextField(value = viewModel.comment.value,
            onValueChange = {viewModel.comment.value = it})
        val coroutine = rememberCoroutineScope()
        Button(onClick = {
            coroutine.launch {
                viewModel.pushComment(orderId = order.value!!.id, star = rating,
                    comment = viewModel.comment.value)
                navHostController.navigate(ScreenRoute.HomeScreen)
            }
        },
            colors = ButtonDefaults.buttonColors(contentColor = Color.White,
                containerColor = LightGreen)){
            Text(text = "Gửi")
        }
    }
}

@Preview
@Composable
fun CommentScreenPreview(){
    var rating by remember{ mutableStateOf(0) }
    StarRating(rating = rating,
        onRatingChanged = {rating = it},
        modifier = Modifier.fillMaxSize())
}

@Composable
fun StarRating(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
    maxRating: Int = 5
) {
    Row(modifier = modifier) {
        for (i in 1..maxRating) {
            Icon(
                imageVector = if (i <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = "Star $i",
                tint = Color.Yellow,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onRatingChanged(i) }
            )
        }
    }
}