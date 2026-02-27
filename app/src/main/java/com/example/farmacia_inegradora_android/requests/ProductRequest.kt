package com.example.farmacia_inegradora_android.requests

data class ProductRequest(
    val codigo: String="",
    val name : String="",
    val presentation: String="",
    val purchase_price : Double=0.0,
    val sale_price : Double=0.0,
    val stock : Int = 0,
    val location : String="",
    val min_stock : Int=0,
    val max_stock : Int=0,
    val description : String="",
    val image : String="",
    val category_id : Int=0,
    val supplier_id : Int=0
)
