package com.example.farmacia_inegradora_android.api

import com.example.farmacia_inegradora_android.requests.LoginRequest
import com.example.farmacia_inegradora_android.requests.ProductRequest
import com.example.farmacia_inegradora_android.responses.CategoryResponse
import com.example.farmacia_inegradora_android.responses.InventoryResponse
import com.example.farmacia_inegradora_android.responses.LoginResponse
import com.example.farmacia_inegradora_android.responses.ProductResponse
import com.example.farmacia_inegradora_android.responses.RestockResponse
import com.example.farmacia_inegradora_android.responses.SalesResponse
import com.example.farmacia_inegradora_android.responses.SupplierResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface PharmacyApi {

    /* --------------
     *   endpoints
     -------------- */

    @POST("api/login/staff")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("api/products/register")
    suspend fun addProduct(@Body product : ProductRequest): ProductResponse

    @GET("api/suppliers")
    suspend fun getSuppliers(): SupplierResponse

    @GET("api/categories")
    suspend fun getCategory(): CategoryResponse

    @GET("api/products/search")
    suspend fun getInventory(): InventoryResponse


    @Headers("Accept: application/json")
    @GET("api/reports/sales-and-orders")
    suspend fun getSalesReport(@Header("Authorization") token: String): SalesResponse

    @GET("api/report/restock-projection")
    suspend fun getStockRecommendations(): RestockResponse
}
