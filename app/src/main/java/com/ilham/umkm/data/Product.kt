package com.ilham.umkm.data

import android.net.Uri

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val imageUri: Uri? = null // Bisa diganti ke URI atau path nanti
)
