package com.example.farmacia_inegradora_android.models

data class RestockItem(
    val id: Int,
    val codigo: String,
    val name: String,
    val stock_actual: Int,
    val stock_minimo: Int,
    val stock_maximo: Int,
    val movimiento_ultimo_mes: Int,
    val porcentaje: Double,
    val importancia: String,
    val sugerencia: Int
)
