package com.example.se114_whatthefood_fe.SellerView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.ui.theme.White


data class BottomSellerBarItem(
    val route: String,
    val icon: ImageVector,
    val label: String,
    var badgeCount: Int? = null,
    val selectedColor: Color = LightGreen,
    val unselectedColor: Color = White,
    val selectedColorBackground: Color = Color.Transparent,
    val unselectedColorBackground: Color = LightGreen
)


@Composable
@Preview
fun SellerBottomBarPreview() {

}

@Composable
fun SellerBottomBar(
    navController: NavHostController,
    currentRoute: String?,
    modifier: Modifier = Modifier
) {
    var items = listOf(
        BottomSellerBarItem(
            icon = Icons.Filled.Home,
            label = "Home",
            route = "SellerHome"
        ),
        BottomSellerBarItem(
            icon = Icons.Filled.Inventory,
            label = "Quản lý đơn",
            route = "SellerManager"
        ),
        BottomSellerBarItem(
            icon = Icons.Filled.Star,
            label = "Đánh giá",
            route = "SellerRated"
        ),
        BottomSellerBarItem(
            icon = Icons.Filled.Notifications,
            label = "Thông Báo",
            route = "SellerNotification"
        ),
        BottomSellerBarItem(
            icon = Icons.Filled.Person,
            label = "Tài Khoản",
            route = "SellerAccount"
        ),
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        horizontalArrangement = Arrangement.spacedBy((-0.2).dp) // Adjust spacing to avoid gaps between items
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = item.route == currentRoute

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        if (isSelected) item.selectedColorBackground else item.unselectedColorBackground
                    )
                    .clickable {
                        //selectedIndex = index
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount != null && item.badgeCount!! > 0) {
                                Badge {
                                    Text(
                                        text = item.badgeCount.toString(),
                                        color = Color.White,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (isSelected) item.selectedColor else item.unselectedColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = item.label,
                        color = if (isSelected) item.selectedColor else item.unselectedColor,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}