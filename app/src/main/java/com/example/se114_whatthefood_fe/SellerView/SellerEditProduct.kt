package com.example.se114_whatthefood_fe.SellerView


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.se114_whatthefood_fe.view.card.Product

@Composable
fun EditProductScreen(
    product: Product,
    onSave: (Product) -> Unit,
    onCancel: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    var name by remember { mutableStateOf(product.name ?: "") }
    var price by remember { mutableStateOf(product.price?.toString() ?: "") }
    var imgUrl by remember { mutableStateOf(product.imgUrl ?: "") }
    var isAvailable by remember { mutableStateOf(product.isAvailable ?: false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Chỉnh sửa sản phẩm",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Ảnh sản phẩm
        AsyncImage(
            model = imgUrl.ifEmpty { "https://via.placeholder.com/150" },
            contentDescription = name,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = imgUrl,
            onValueChange = { imgUrl = it },
            label = { Text("Link hình ảnh") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Tên sản phẩm") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Giá (VND)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isAvailable, onCheckedChange = { isAvailable = it })
            Text("Còn hàng")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    val updated = product.copy(
                        name = name,
                        price = price.toDoubleOrNull() ?: 0.0,
                        imgUrl = imgUrl,
                        isAvailable = isAvailable
                    )
                    onSave(updated)
                    focusManager.clearFocus()
                }
            ) {
                Text("Lưu")
            }

            OutlinedButton(onClick = onCancel) {
                Text("Huỷ")
            }
        }
    }
}