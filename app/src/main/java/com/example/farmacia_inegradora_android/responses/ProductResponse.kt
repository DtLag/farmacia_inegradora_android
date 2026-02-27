package com.example.farmacia_inegradora_android.responses

import android.R
import android.os.Message
import com.example.farmacia_inegradora_android.models.Product

data class ProductResponse(
    val success : Boolean,
    val data: Product?,
    val message: String,
    val error: Error?,
    val code:Int
)