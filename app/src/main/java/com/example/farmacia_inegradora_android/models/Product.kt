package com.example.farmacia_inegradora_android.models

class Product(
    val id: Int,
    val codigo: String,
    val name : String,
    val presentation: String,
    val purchase_price : Double,
    val sale_price : Double,
    val location : String,
    val min_stock : Int,
    val max_stock : Int,
    val description : String,
    val category_id : Int,
    val supplier_id: Int
)