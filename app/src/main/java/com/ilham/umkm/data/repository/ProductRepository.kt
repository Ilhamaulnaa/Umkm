package com.ilham.umkm.data.repository

import com.ilham.umkm.data.Product
import com.ilham.umkm.data.ProductDao
import com.ilham.umkm.data.ProductEntity
import kotlinx.coroutines.flow.Flow

class ProductRepository(
    val dao: ProductDao
) {

    val getAllProduct: Flow<List<ProductEntity>> = dao.getAllProduct()

    suspend fun insert(product: ProductEntity){
        dao.insert(product)
    }

    suspend fun update(product: ProductEntity){
        dao.update(product)
    }

    suspend fun delete(product: ProductEntity){
        dao.delete(product)
    }

}