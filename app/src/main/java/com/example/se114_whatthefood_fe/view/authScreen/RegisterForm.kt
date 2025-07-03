package com.example.se114_whatthefood_fe.view.authScreen


import android.app.Activity
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.datastore.dataStore
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.model.AuthModel
import com.example.se114_whatthefood_fe.ui.theme.DarkBlue
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.view_model.AuthViewModel

@Composable
@Preview
fun RegisterFormPreview() {
    //RegisterForm()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterForm(authViewModel: AuthViewModel, modifier: Modifier = Modifier) {
    val activity = LocalActivity.current
    Column {
        if (!authViewModel.otpSent) {
            // text tield for full name
            RoundCornerTextFieldWithIcon(
                placeholder = "Tên của bạn",
                value = authViewModel.nameRegister,
                onValueChange = { authViewModel.nameRegister = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Phone Icon",
                        tint = LightGreen
                    )
                },
                modifier = modifier
            )

            Spacer(modifier.height(16.dp))

            // text field for phone number
            RoundCornerTextFieldWithIcon(
                placeholder = "Số điện thoại",
                value = authViewModel.phoneRegister,
                onValueChange = { authViewModel.phoneRegister = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone Icon",
                        tint = LightGreen
                    )
                },
                modifier = modifier,
                keyBoardOption = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier.height(16.dp))
            // text field for password
            RoundCornerTextFieldWithIcon(
                placeholder = "Mật khẩu",
                value = authViewModel.passwordRegister,
                onValueChange = { authViewModel.passwordRegister = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon",
                        tint = LightGreen
                    )
                },
                isPassword = true,
                modifier = modifier,
                trailingIcon = {
                    IconButton(
                        onClick = { authViewModel.clickVisiblePasswordInRegister() }
                    )
                    {
                        Icon(
                            imageVector = if (authViewModel.isVisiblePasswordInRegister)
                                Icons.Default.VisibilityOff
                            else Icons.Default.Visibility,
                            contentDescription = "Toggle Password Visibility",
                            tint = LightGreen,
                        )
                    }
                },
                isPasswordVisibility = authViewModel.isVisiblePasswordInRegister
            )
            // text field for confirm password
            Spacer(modifier.height(16.dp))
            RoundCornerTextFieldWithIcon(
                placeholder = "Nhập lại mật khẩu",
                value = authViewModel.confirmPassword,
                onValueChange = { authViewModel.confirmPassword = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Confirm Password Icon",
                        tint = LightGreen
                    )
                },
                isPassword = true,
                modifier = modifier,
                trailingIcon = {
                    IconButton(
                        onClick = { authViewModel.clickVisibleConfirmPassword() }
                    )
                    {
                        Icon(
                            imageVector = if (authViewModel.isVisibleConfirmPasswordInRegister)
                                Icons.Default.VisibilityOff
                            else Icons.Default.Visibility,
                            contentDescription = "Toggle Password Visibility",
                            tint = LightGreen,
                        )
                    }
                },
                isPasswordVisibility = authViewModel.isVisibleConfirmPasswordInRegister
            )
            // spacer
            Spacer(modifier.height(16.dp))

            // combobox chon quyen
            CustomComboBox(authViewModel = authViewModel)
            Spacer(modifier.height(16.dp))
            // button dang ki
//        RoundCornerButton(
//            text = "Đăng ký",
//            onClick = { authViewModel.onRegisterClick() },
//            modifier = modifier
//        )
            RoundCornerButton(
                text = "Đăng ký",
                onClick = { authViewModel.sendVerificationCode(activity!!) },
                modifier = modifier
            )
        }
        else {
            RoundCornerTextFieldWithIcon(
                placeholder = "Nhập OTP",
                value = authViewModel.otpCode,
                onValueChange = { authViewModel.otpCode = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone Icon",
                        tint = LightGreen
                    )
                },
                modifier = modifier,
                keyBoardOption = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            RoundCornerButton(
                text = "Xác thực",
                onClick = { authViewModel.verifyOtp(authViewModel.otpCode)},
                modifier = modifier
            )
            RoundCornerButton(
                text = "Trở về đăng ký",
                onClick = { authViewModel.otpSent = false },
                modifier = modifier
            )
        }
        authViewModel.authResult?.let {
            Text("Signed in as: ${it.phoneNumber}")
            Text("Role: ${authViewModel.optionRole.value}")
            Text("Name: ${authViewModel.nameRegister}")
        }
    }

}
data class RoleOption(
    val label: String,
    val value: String
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomComboBox(authViewModel: AuthViewModel) {
    val options = listOf(
        RoleOption( "Khách hàng","User"),
        RoleOption("Chủ cửa hàng","Owner"))
    var expanded by remember { mutableStateOf(false) }
    val optionRole by authViewModel.optionRole
    Log.i("test", "optionRole: $optionRole")
    var selectedOption by remember { mutableStateOf(options.find({ it.value == optionRole }) ?: options[0])}
    Column(){
        //label
        Text(text = "Bạn là",
            color = Color.White)
        Spacer(modifier = Modifier.height(3.dp))
        //combobox
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(20.dp))
        ) {
            TextField(
                readOnly = true,
                value = selectedOption.label,
                onValueChange = {},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor() // QUAN TRỌNG: để DropdownMenu định vị đúng chỗ
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = Color.White
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(text = selectionOption.label,
                            textAlign = TextAlign.Center) },
                        onClick = {
                            authViewModel.optionRole.value = selectionOption.value
                            selectedOption = selectionOption
                            expanded = false
                        },
                        colors = MenuDefaults.itemColors(),
                    )
                }
            }
        }
    }

}

