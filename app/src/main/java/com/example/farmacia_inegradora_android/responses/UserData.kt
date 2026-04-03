package com.example.farmacia_inegradora_android.responses

import com.google.gson.annotations.SerializedName

data class UserData(
    val id: Int,
    val name: String,
    @SerializedName("last_name")
    val lastName: String,
    val email: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("role")
    val role: String
)
