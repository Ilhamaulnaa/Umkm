package com.ilham.umkm.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilham.umkm.data.ProductEntity
import com.ilham.umkm.data.repository.ProductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository
): ViewModel() {

    val productList: StateFlow<List<ProductEntity>> = repository
        .getAllProduct
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addProduct(product: ProductEntity){
        viewModelScope.launch {
            repository.insert(product)
        }
    }

    fun updateProduct(product: ProductEntity){
        viewModelScope.launch {
            repository.update(product)
        }
    }

    fun deleteProduct(product: ProductEntity){
        viewModelScope.launch {
            repository.delete(product)
        }
    }

}