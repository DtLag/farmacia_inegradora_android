package com.example.farmacia_inegradora_android.data

import com.example.farmacia_inegradora_android.models.Category
import com.example.farmacia_inegradora_android.models.Product
import com.example.farmacia_inegradora_android.models.Supplier
import com.example.farmacia_inegradora_android.requests.ProductRequest
import com.example.farmacia_inegradora_android.responses.CategoryResponse
import com.example.farmacia_inegradora_android.responses.ProductResponse
import com.example.farmacia_inegradora_android.responses.SupplierResponse

object PharmacyRepository {

    private val Api = RetrofitInstance.API

    /* --------------------
        suspend functions
     -------------------- */

   /* ------ Aun no se bien como jala ------ */

    suspend fun addProducts(product : ProductRequest) {
        val response = Api.addProduct(product)
        //return
    }
    suspend fun getCategories(): List<Category>{
        val response : CategoryResponse = Api.getCategory()
        return response.categories
    }
    suspend fun getSupplier(): List<Supplier>{
        val response : SupplierResponse = Api.getSuppliers()
        return response.suppliers

    }
}