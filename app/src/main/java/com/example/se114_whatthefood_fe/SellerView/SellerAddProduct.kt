package com.example.se114_whatthefood_fe.SellerView

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.se114_whatthefood_fe.view.card.Product
import com.example.se114_whatthefood_fe.view_model.AuthViewModel

@Composable
fun AddProductScreen(
    product: Product,
    viewModel: AuthViewModel,
    onSave: (Product) -> Unit,
    onCancel: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    var name by remember { mutableStateOf(product.name ?: "") }
    var price by remember { mutableStateOf(product.price?.toString() ?: "") }
    var imgUrl by remember { mutableStateOf(product.imgUrl ?: "") }
    var isAvailable by remember { mutableStateOf(product.isAvailable ?: false) }
    var description by remember { mutableStateOf(product.description ?: "") } // ← thêm mô tả

    val context = LocalContext.current
    //val authViewModel: AuthViewModel = hiltViewModel() // nếu dùng Hilt

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.uploadImageCustom(context, it)
        }
    }

    viewModel.imageCustomUrl?.let {
        imgUrl = it.value ?: ""
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Thêm sản phẩm",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = imgUrl.ifEmpty { imgUrl },
            contentDescription = name,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Nút đổi ảnh (placeholder)
        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF228B22) // Xanh lá, bạn có thể thay bằng màu xanh khác
            )
        ) {
            Text("Đổi hình ảnh")
        }
        
        Spacer(modifier = Modifier.height(8.dp))



        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Tên sản phẩm") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Giá (VND)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )


        Spacer(modifier = Modifier.height(8.dp))

        // Thêm mô tả món ăn
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Mô tả món ăn") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
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
                    val newProduct = Product(
                        id = product.id, // hoặc tạo ID mới nếu cần
                        name = name,
                        price = price.toDoubleOrNull(),
                        imgUrl = imgUrl,
                        isAvailable = isAvailable,
                        description = description, // ← thêm mô tả
                    )
                    onSave(newProduct)
                    focusManager.clearFocus()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF228B22)
                )
            ) {
                Text("Thêm")
            }

            OutlinedButton(onClick = onCancel) {
                Text(
                    "Huỷ",
                    color = Color(0xFF228B22)
                )
            }
        }
    }
}
