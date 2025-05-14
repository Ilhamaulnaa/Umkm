package com.ilham.umkm.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ilham.umkm.data.db.AppDatabase
import com.ilham.umkm.data.repository.ProductRepository

class ProductViewModelFactory(context: Context): ViewModelProvider.Factory {
    private val repository: ProductRepository

    init {
        val db = AppDatabase.getDatabase(context)
        repository = ProductRepository(db.productDao())
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(repository) as T
    }

}