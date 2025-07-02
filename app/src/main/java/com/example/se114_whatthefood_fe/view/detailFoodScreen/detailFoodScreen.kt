package com.example.se114_whatthefood_fe.view.detailFoodScreen

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.se114_whatthefood_fe.data.remote.Address
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.data.remote.FoodCategory
import com.example.se114_whatthefood_fe.data.remote.FoodItemResponse
import com.example.se114_whatthefood_fe.data.remote.Restaurant
import com.example.se114_whatthefood_fe.data.remote.RetrofitInstance
import com.example.se114_whatthefood_fe.data.remote.ShippingInfo
import com.example.se114_whatthefood_fe.data.remote.ShippingInfoDetail
import com.example.se114_whatthefood_fe.dataStore
import com.example.se114_whatthefood_fe.model.FoodModel
import com.example.se114_whatthefood_fe.view.authScreen.ButtonIcon
import com.example.se114_whatthefood_fe.view.card.StatusOrder
import com.example.se114_whatthefood_fe.view.card.getColorOrderStatus
import com.example.se114_whatthefood_fe.view_model.FoodDetailViewModel
import com.example.se114_whatthefood_fe.view_model.FoodViewModel
import com.example.se114_whatthefood_fe.view_model.OrderDetailViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun DetailFoodScreenPreview()
{
    val food : FoodItemResponse = FoodItemResponse(
        id = 0,
        foodName = "hello 123 ",
        description = "",
        soldAmount = 1,
        available = true,
        price = 200000,
        foodCategory = FoodCategory(2,"hi 1345234"),
        restaurant = Restaurant(0,"","",null),
        cldnrUrl = ""
    )
    DetailFoodScreen(foodDetailViewModel = FoodDetailViewModel(foodModel = FoodModel(api = RetrofitInstance.instance,
        dataStore = LocalContext.current.dataStore)))
}

@Composable
fun DetailFoodScreen(modifier: Modifier = Modifier,
                      navController: NavHostController? = null,
                      orderId: Int? = null,
                     foodDetailViewModel: FoodDetailViewModel
)
{
    val foodtest : FoodItemResponse = FoodItemResponse(
        id = 0,
        foodName = "hello 123 ",
        description = "",
        soldAmount = 1,
        available = true,
        price = 200000,
        foodCategory = FoodCategory(2,"hi 1345234"),
        restaurant = Restaurant(0,"","",null),
        cldnrUrl = ""
    )
    val food =  foodDetailViewModel.foodItem.collectAsState().value
    LaunchedEffect(orderId) {
        if (orderId != null) {
            foodDetailViewModel.LoadById(orderId)
        }
    }
    FoodItemDetail(foodItem = foodtest!!)

}

@Composable
fun FoodItemDetail(foodItem: FoodItemResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.White)
    ) {
        // Ảnh món ăn
        foodItem.cldnrUrl?.let { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = foodItem.foodName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Tên món
        Text(
            text = foodItem.foodName,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Mô tả
        if (!foodItem.description.isNullOrBlank()) {
            Text(
                text = foodItem.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Thông tin khác
        Text("Giá: ${foodItem.price} đ", fontWeight = FontWeight.Medium)
        Text("Đã bán: ${foodItem.soldAmount}")
        Text("Còn hàng: ${if (foodItem.available) "✔ Có" else "✘ Hết"}")

        Spacer(modifier = Modifier.height(16.dp))

        Divider()

        Spacer(modifier = Modifier.height(16.dp))

        // Thông tin nhà hàng
        Text(
            text = "Nhà hàng: ${foodItem.restaurant.name}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Text("Địa chỉ: ${foodItem.restaurant.address}", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Danh mục
        Text("Danh mục: ${foodItem.foodCategory.name}", style = MaterialTheme.typography.bodyMedium)
    }
}

