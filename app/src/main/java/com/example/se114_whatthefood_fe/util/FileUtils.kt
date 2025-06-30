package com.example.se114_whatthefood_fe.util

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object FileUtils {
    fun createMultipartFromUri(context: Context, uri: Uri, partName: String): MultipartBody.Part? {

        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: return null

        val requestBody = inputStream.readBytes().toRequestBody(
            contentResolver.getType(uri)?.toMediaTypeOrNull() ?: "image/*".toMediaTypeOrNull()
        )

        val fileName = uri.lastPathSegment ?: "image.png"
        return MultipartBody.Part.createFormData(partName, fileName, requestBody)
    }
}
