package com.ilham.umkm.data.mapper

import com.ilham.umkm.R
import com.ilham.umkm.data.Product
import com.ilham.umkm.data.ProductEntity

fun ProductEntity.toDomainModel(): Product {
    return Product(
        id = id,
        name = name,
        description = description,
        price = price,
        imageUri = null
    )
}