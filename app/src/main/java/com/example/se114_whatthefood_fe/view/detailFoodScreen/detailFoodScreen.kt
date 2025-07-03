package com.example.se114_whatthefood_fe.view.detailFoodScreen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.se114_whatthefood_fe.data.remote.FoodCategory
import com.example.se114_whatthefood_fe.data.remote.FoodItemResponse
import com.example.se114_whatthefood_fe.data.remote.RatingFood
import com.example.se114_whatthefood_fe.data.remote.Restaurant
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.view.authScreen.ButtonIcon
import com.example.se114_whatthefood_fe.view.detailOrderScreen.MoneyFormat
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
        Box(modifier = Modifier.padding(innerPadding))
        {
            FoodItemDetail(
                foodItem = food,
                foodDetailViewModel = foodDetailViewModel,
                modifier = Modifier.padding(innerPadding) // <-- Áp dụng padding ở đây
                    .fillMaxSize()
            ) // <-- Đảm bảo FoodItemDetail chiếm đầy không gian còn lại)

            FloatingActionButton(
                onClick = {},
                modifier = Modifier.align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .clip(shape = CircleShape),
                containerColor = LightGreen,
                contentColor = Color.Black
            ) {
                Icon(imageVector = Icons.Filled.AddShoppingCart,
                    contentDescription = "Add to cart")
            }
        }
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
        item(key = foodItem.id){
            DetailFoodCard(foodItem = foodItem)
        }
        // card nha hang
        item(key = "Restaurant ${foodItem.restaurant.id}"){
            RestaurantCard(foodItem = foodItem)
        }
        // header
        stickyHeader {
            HeaderOfRating(foodItemIndex = foodItem.id,
                foodDetailViewModel = foodDetailViewModel)
        }
        // danh sach cac comment
        items(count = ratingList.size,
            key = {index -> ratingList[index].userName}){
                index ->
            RatingCard(ratingFood = ratingList[index])
        }

    }
}
@Preview
@Composable
fun detail(){
    DetailFoodCard(foodItem = FoodItemResponse(id = 1,
        foodName = "hello",
        description = "hello",
        soldAmount = 1,
        available = true,
        price = 1000,
        foodCategory = FoodCategory(id = 1, name = "hello"),
        restaurant = Restaurant(id = 1, name = "hello", cldnrUrl = "", address = null),
        cldnrUrl = ""
        ))
}

@Composable
fun DetailFoodCard(modifier: Modifier = Modifier, foodItem: FoodItemResponse?){
    if(foodItem == null)
        return
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {

        // Khối giá (đè lên phần trên card)
        Box(
            modifier = Modifier
                .background(Color(0xFFB9FBC0), shape = RoundedCornerShape(10.dp))
                .padding(top = 5.dp,
                    start = 10.dp,
                    bottom = 20.dp)
                .fillMaxWidth(0.8f)
        ) {
            Text(
                text = MoneyFormat(foodItem.price) + "đ",
                color = Color(0xFFFF5E5E),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Start
            )
        }
        // Card chính (màu trắng)
        Box(
            modifier = Modifier
                .padding(top = 40.dp) // chừa chỗ để khối giá nằm đè lên
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(vertical = 16.dp, horizontal = 12.dp)
                .fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = foodItem.foodName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color(0xFF007D28),
                    textAlign = TextAlign.Center)
                Text(
                    text = foodItem.description?:"",
                    fontSize = 18.sp,
                    color = Color(0xFF007D28),
                    textAlign = TextAlign.Center
                )
            }
        }


    }
}

@Composable
@Preview
fun previewRestaurant(){
    RestaurantCard(foodItem = FoodItemResponse(id =1,
        foodName = "hello",
        description = "hello",
        soldAmount = 1,
        available = false,
        price = 1000,
        foodCategory = FoodCategory(id = 1, name = "hello"),
        restaurant = Restaurant(id = 1, name = "hello", cldnrUrl = "", address = null),
        cldnrUrl = ""))
}
@Composable
fun RestaurantCard(modifier: Modifier = Modifier, foodItem: FoodItemResponse? = null){
    if(foodItem == null)
        return
    Column(horizontalAlignment = Alignment.Start,
        modifier = Modifier.clip(shape = RoundedCornerShape(10.dp))
            .background(color = Color.White)
            .padding(vertical = 5.dp,
                horizontal = 10.dp)){
        Text(text = "Tên nhà hàng: ${foodItem.restaurant.name}",
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(IntrinsicSize.Max)){
            Icon( imageVector = Icons.Filled.LocationOn,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
                    .fillMaxHeight()
                    .aspectRatio(1f),
                tint = Color.Red)
            Text(text = foodItem.restaurant.address?.name ?: "hello",
                fontSize = 15.sp)
        }
        HorizontalDivider(thickness = 1.dp,
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp))
        Text(text = "Số lượng đã bán: ${foodItem.soldAmount}")
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Mô tả: ${foodItem.description}",
            modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = if(foodItem.available) "Còn hàng" else "Hết hàng",
            color = if(foodItem.available) Color(0xFF259c3d) else Color.Red,
            fontWeight = FontWeight.W900)
        Spacer(modifier = Modifier.height(5.dp))
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun HeaderOfRating(modifier: Modifier = Modifier,
                   foodItemIndex: Int?,
                   foodDetailViewModel: FoodDetailViewModel){
    if(foodItemIndex == null)
        return
    val averageRating = foodDetailViewModel.ratingFoodItem.collectAsState().value
    LaunchedEffect(foodItemIndex) {
        foodDetailViewModel.loadRatingFoodItem(foodItemIndex)
    }
    if(averageRating == null)
        return
    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clip(shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(color = LightGreen).padding(vertical = 10.dp,
                horizontal = 10.dp)){

        Spacer(modifier = Modifier.width(5.dp))
        val averageRatingformat = String.format("%.1f", averageRating.avgRating)
        Text(text = "Đánh giá sản phẩm ",
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))

        Text(text = averageRatingformat,
            fontSize = 20.sp,
            color = Color.White)
        Icon(imageVector = Icons.Filled.StarRate,
            contentDescription = null,
            tint = Color(0xFFffe600))
        Text(text ="(${averageRating.number})",
            fontSize = 20.sp,
            color = Color.White)
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
                    tint = if(i<=ratingFood.star) Color(0xFFffe600)
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
            Text(text = ratingFood.comment?:"",
                fontSize = 15.sp,
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