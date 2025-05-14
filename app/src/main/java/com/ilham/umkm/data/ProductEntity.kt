package com.ilham.umkm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ilham.umkm.R

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val price: Int,
    val imageUri: String? = null
)

