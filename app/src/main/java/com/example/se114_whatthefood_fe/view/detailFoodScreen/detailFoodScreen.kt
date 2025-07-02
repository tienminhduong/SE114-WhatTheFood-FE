package com.example.se114_whatthefood_fe.view.detailFoodScreen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.se114_whatthefood_fe.data.remote.FoodItemResponse
import com.example.se114_whatthefood_fe.data.remote.RatingFood
import com.example.se114_whatthefood_fe.view.authScreen.ButtonIcon
import com.example.se114_whatthefood_fe.view_model.FoodDetailViewModel

@Composable
fun TopBarFoodDetailScreen(modifier: Modifier = Modifier,
                           navController: NavHostController? = null){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().background(color = Color.Transparent)){
        ButtonIcon(icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = { navController?.popBackStack() },
            colorBackGround = Color.Transparent,
            colorIcon = Color.White,
            modifier = modifier)
        Text(text = "Thông tin món ăn",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailFoodScreen(modifier: Modifier = Modifier,
                      navController: NavHostController? = null,
                      orderId: Int? = null,
                     foodDetailViewModel: FoodDetailViewModel
)
{
    val food =  foodDetailViewModel.foodItem.collectAsState().value
    LaunchedEffect(Unit) {
        if (orderId != null) {
            foodDetailViewModel.loadById(orderId)
        }
    }
    if (food == null) {
        CircularProgressIndicator()
        return
    }
    Scaffold(topBar = {TopBarFoodDetailScreen(navController = navController)},
        containerColor = Color.Transparent) { innerPadding->
        FoodItemDetail(foodItem = food,
                        foodDetailViewModel = foodDetailViewModel,
            modifier = Modifier.padding(innerPadding) // <-- Áp dụng padding ở đây
                .fillMaxSize()) // <-- Đảm bảo FoodItemDetail chiếm đầy không gian còn lại)
    }
}

@Composable
fun FoodItemDetail(modifier: Modifier = Modifier,
                   foodItem: FoodItemResponse?,
                   foodDetailViewModel: FoodDetailViewModel) {
    if(foodItem == null) return
    val context = LocalContext.current
    val ratingList = foodDetailViewModel.rating.collectAsState().value
    LaunchedEffect(foodItem.id) {
        foodDetailViewModel.loadRating(foodItem.id)
    }
    LazyColumn(modifier = modifier.fillMaxSize()
        .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)){
        // anh cua mon an
        item(key = foodItem.cldnrUrl) {
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data(foodItem.cldnrUrl)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)  // Cache trên ổ đĩa
                    .memoryCachePolicy(CachePolicy.ENABLED) // Cache trên RAM
                    //.size(100, 100) // Set kích thước ảnh
                    .placeholder(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
                    .error(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
                    .build(),
                contentDescription = "Card Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(300.dp).clip(shape= RoundedCornerShape(8.dp))
            )
        }
        // ten mon an
        item(key = foodItem.foodName){
            Text(text = foodItem.foodName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
        }
        // ten cua hang
        item(key = foodItem.restaurant.name){
            Text(text = foodItem.restaurant.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold)
        }
        // danh sach cac comment
        items(count = ratingList.size,
            key = {index -> ratingList[index].userName}){
            index ->
            RatingCard(ratingFood = ratingList[index])
        }
    }
}

@Composable
fun RatingCard(modifier: Modifier = Modifier,
               ratingFood: RatingFood){
    val context = LocalContext.current
    Column(modifier = modifier.clip(shape = RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .fillMaxWidth()
        .padding(10.dp)){
        // chuoi sao
        Row {
            for (i in 1..5){
                Icon(imageVector = Icons.Outlined.Star,
                    contentDescription = "star",
                    tint = if(i<=ratingFood.star) Color.Yellow
                                else Color.LightGray,
                    modifier = Modifier.height(30.dp))
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        // ten nguoi danh gia
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)){
            GetImageFromURL(url = ratingFood.userPfp?:"",
                            context = context,
                            modifier = Modifier.size(20.dp))
            Text(text = ratingFood.userName,
                fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(10.dp))
        // comment
        Row(){
            Text(text = ("Đánh giá: " + ratingFood.comment),
                fontSize = 25.sp,
                modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun GetImageFromURL(url: String, context: Context, modifier: Modifier = Modifier){
    AsyncImage(
        model = ImageRequest.Builder(context = context)
            .data(url)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)  // Cache trên ổ đĩa
            .memoryCachePolicy(CachePolicy.ENABLED) // Cache trên RAM
            .size(100, 100) // Set kích thước ảnh
            .placeholder(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
            .error(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
            .build(),
        contentDescription = "Card Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.clip(shape= RoundedCornerShape(8.dp))
                            .size(30.dp)
    )
}

@Preview
@Composable
fun Preview(){
    RatingCard(ratingFood = RatingFood(userName = "hello",
        userPfp = "",
        star = 5,
        comment = "hellojjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj"))
}