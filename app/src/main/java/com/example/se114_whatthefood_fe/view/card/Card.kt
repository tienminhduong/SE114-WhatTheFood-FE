package com.example.se114_whatthefood_fe.view.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.se114_whatthefood_fe.data.remote.FoodItemNearByResponse
import com.example.se114_whatthefood_fe.data.remote.FoodItemResponse
import com.example.se114_whatthefood_fe.ui.theme.White
import com.example.se114_whatthefood_fe.view.detailOrderScreen.MoneyFormat

@Immutable
data class Card(
    val id: Int,
    val imageLink: String? = null,
    val title: String? = null,
    val rate: Float? = null,
    val distance: Float? = null,
    val time: Float? = null

)

@Composable
@Preview
fun CardPreview() {

}

@Composable
fun NearByCardView(
    modifier: Modifier = Modifier,
    card: FoodItemNearByResponse
) {
    Row(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(8.dp),
                color = White
            )
            .fillMaxWidth()
            .height(88.dp)
            .padding(8.dp) // Thêm padding để nội dung không chạm sát mép
            .clip(RoundedCornerShape(8.dp)), // Áp dụng clip cho toàn bộ Row nếu muốn bo góc
        verticalAlignment = Alignment.CenterVertically
    ) {
        // image
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(card.imgUrl)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)  // Cache trên ổ đĩa
                .memoryCachePolicy(CachePolicy.ENABLED) // Cache trên RAM
                //.size(100, 100) // Set kích thước ảnh
                .placeholder(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
                .error(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
                .build(),
            contentDescription = "Card Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        )
        // information
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // title
            androidx.compose.material3.Text(
                text = card.name,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                softWrap = false,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            // ten nha hang
            Text(
                text = card.restaurantName,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                softWrap = false,
                textAlign = TextAlign.Center,
                fontSize = TextUnit(15f, androidx.compose.ui.unit.TextUnitType.Sp) // Set font size
            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = String.format("%.1f", card.rating.average),
                    )
                    // rate icon
                    Icon(
                        imageVector = Icons.Default.StarRate,
                        contentDescription = "Rate Icon",
                        tint = Color.Yellow
                    )
                }

                // distance
                Text(
                    text = String.format("%.1f", card.distanceInKm) + " km"
                )
                // time
                Text(
                    text = "~ ${card.distanceInTime} phút"
                )
            }
        }
    }
}

@Composable
fun BestSellerCardView(modifier: Modifier = Modifier, card: FoodItemNearByResponse) {
    Row(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(8.dp),
                color = White
            )
            .fillMaxWidth()
            .height(88.dp)
            .padding(8.dp) // Thêm padding để nội dung không chạm sát mép
            .clip(RoundedCornerShape(8.dp)), // Áp dụng clip cho toàn bộ Row nếu muốn bo góc
        verticalAlignment = Alignment.CenterVertically
    ) {
        // image
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(card.imgUrl)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)  // Cache trên ổ đĩa
                .memoryCachePolicy(CachePolicy.ENABLED) // Cache trên RAM
                //.size(100, 100) // Set kích thước ảnh
                .placeholder(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
                .error(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
                .build(),
            contentDescription = "Card Image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(72.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        )
        // information
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // title

            Text(
                text = card.name,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                softWrap = false,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            // ten nha hang
            Text(
                text = card.restaurantName,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                softWrap = false,
                textAlign = TextAlign.Center,
                fontSize = TextUnit(15f, androidx.compose.ui.unit.TextUnitType.Sp) // Set font size
            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    androidx.compose.material3.Text(
                        text = String.format("%.1f", card.rating.average),
                    )
                    // rate icon
                    Icon(
                        imageVector = Icons.Default.StarRate,
                        contentDescription = "Rate Icon",
                        tint = Color.Yellow
                    )
                }

                // so luong ban
                Text(text = "Đã bán: ${card.soldAmount}")
            }

        }
    }
}

@Composable
fun GoodRateCardView(modifier: Modifier = Modifier, card: FoodItemNearByResponse) {
    Row(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(8.dp),
                color = White
            )
            .fillMaxWidth()
            .height(88.dp)
            .padding(8.dp) // Thêm padding để nội dung không chạm sát mép
            .clip(RoundedCornerShape(8.dp)), // Áp dụng clip cho toàn bộ Row nếu muốn bo góc
        verticalAlignment = Alignment.CenterVertically
    ) {
        // image
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(card.imgUrl)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)  // Cache trên ổ đĩa
                .memoryCachePolicy(CachePolicy.ENABLED) // Cache trên RAM
                //.size(100, 100) // Set kích thước ảnh
                .placeholder(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
                .error(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
                .build(),
            contentDescription = "Card Image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(72.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        )
        // information
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // title

            Text(
                text = card.name,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                softWrap = false,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(15f, androidx.compose.ui.unit.TextUnitType.Sp) // Set font size
            )
            // ten nha hang
            Text(
                text = card.restaurantName,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                softWrap = false,
                textAlign = TextAlign.Center,
                fontSize = TextUnit(15f, androidx.compose.ui.unit.TextUnitType.Sp) // Set font size
            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = String.format("%.1f", card.rating.average),
                    )
                    // rate icon
                    Icon(
                        imageVector = Icons.Default.StarRate,
                        contentDescription = "Rate Icon",
                        tint = Color.Yellow
                    )
                }

                // so luong danh gia
                Text(text = "Số lượt đánh giá: ${card.rating.number}")
            }

        }
    }
}

@Composable
fun SearchResultCardView(
    card: FoodItemResponse,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ảnh món ăn
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(card.cldnrUrl)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)  // Cache trên ổ đĩa
                .memoryCachePolicy(CachePolicy.ENABLED) // Cache trên RAM
                //.size(100, 100) // Set kích thước ảnh
                .placeholder(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
                .error(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
                .build(),
            contentDescription = "Card Image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(72.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Thông tin món ăn
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = card.foodName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = card.description?:"",
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Giá: ${MoneyFormat(card.price)}đ",
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}


@Composable
@Preview
fun CardRecommendPreview() {
    CardRecommendView(
        card = Card(
            imageLink = "https://m.yodycdn.com/blog/anh-nen-naruto-yody-vn-95.jpg",
            title = "Gà rán và Mì Ý Jollibee", rate = 4.5f, distance = 2.0f, time = 30.0f, id = 1
        ),
        imageLoader = rememberOptimizedImageLoader()
    )
}

@Composable
fun rememberOptimizedImageLoader(): ImageLoader {
    val context = LocalContext.current
    return remember {
        ImageLoader.Builder(context)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .respectCacheHeaders(false)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
            .build()
    }
}

@Composable
@Stable
fun CardRecommendView(modifier: Modifier = Modifier, card: Card, imageLoader: ImageLoader) {
    val context = LocalContext.current

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(card.imageLink)
            .size(150) // critical to limit decode size
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build(),
        imageLoader = imageLoader
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            //.padding(8.dp)
            .background(shape = RoundedCornerShape(8.dp), color = Color.White)
            .width(150.dp)
    ) {
        // image
        Image(
            painter = painter,
            contentDescription = "Card Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        )
        // name
        androidx.compose.material3.Text(
            text = card.title ?: "No Title",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            softWrap = true,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ListRecommendFood(modifier: Modifier = Modifier, listCard: List<Card>) {
    LazyRow(
        modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = listCard, key = { it.id }) { card ->
            CardRecommendView(card = card, imageLoader = rememberOptimizedImageLoader())
        }
    }
}

@Composable
@Preview
fun ListRecommendFoodPreview() {

}