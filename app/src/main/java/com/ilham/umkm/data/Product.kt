package com.ilham.umkm.data

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val imageRes: Int // Bisa diganti ke URI atau path nanti
)
