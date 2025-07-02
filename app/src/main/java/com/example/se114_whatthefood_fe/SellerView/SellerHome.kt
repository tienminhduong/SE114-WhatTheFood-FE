package com.example.se114_whatthefood_fe.SellerView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se114_whatthefood_fe.SellerView_model.SellerHomeViewModel
import com.example.se114_whatthefood_fe.view.card.Product
import com.example.se114_whatthefood_fe.view.card.ProductItem


//@SuppressLint("ViewModelConstructorInComposable")
@Composable
@Preview
fun SellerHomePreview() {
    //val sellerHomeViewModel = SellerViewModel()
}

@Composable
fun SellerHome(
    viewModel: SellerHomeViewModel
) {
    // Trạng thái: đang chỉnh sản phẩm nào?
    var editingProduct by remember { mutableStateOf<Product?>(null) }

    var isAddingProduct by remember { mutableStateOf(false) }



    if (editingProduct != null) {
        // Mở màn hình chỉnh sửa
        EditProductScreen(
            product = editingProduct!!,
            onSave = { updatedProduct ->
                viewModel.updateProduct(updatedProduct)
                editingProduct = null // đóng màn
            },
            onCancel = {
                editingProduct = null // hủy sửa
            }
        )
    } else if (isAddingProduct) {
        // ✅ Màn thêm sản phẩm mới
        AddProductScreen(
            product = Product(), // Khởi tạo rỗng
            onSave = { newProduct ->
                viewModel.addProduct(newProduct)
                isAddingProduct = false // quay về danh sách
            },
            onCancel = {
                isAddingProduct = false
            }
        )
    } else {
        // Màn hình danh sách sản phẩm
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color(0xFF00FF7F), Color.White)
                    )
                )
        ) {
            SellerHeaderHome(
                viewModel,
                onAddClick = { isAddingProduct = true }
            )

            LazyColumn {
                items(viewModel.products) { product ->
                    ProductItem(
                        item = product,
                        onClick = { editingProduct = product } // Khi click vào sẽ mở chỉnh sửa
                    )
                }
            }
        }
    }
}


@Composable
fun SellerHeaderHome(
    viewModel: SellerHomeViewModel,
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        // Tiêu đề căn giữa
        Text(
            text = "Quản lý sản phẩm",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )

        // Nút thêm nằm bên phải
        IconButton(
            onClick = onAddClick,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Thêm sản phẩm",
                modifier = Modifier.size(30.dp),
                tint = Color.White
            )
        }
    }
}
