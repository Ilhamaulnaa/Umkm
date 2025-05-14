package com.ilham.umkm.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ilham.umkm.data.Product
import com.ilham.umkm.presentation.add_product.AddProductScreen
import com.ilham.umkm.presentation.edit_product.EditProductScreen
import com.ilham.umkm.presentation.ProductListScreen
import com.ilham.umkm.presentation.ProductViewModel
import com.ilham.umkm.presentation.ProductViewModelFactory

@ExperimentalMaterial3Api
@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {

    val navHostController = navController.currentDestination

    val context = LocalContext.current
    val productViewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory(context))


    val products = remember { mutableStateListOf<Product>() }
    val editingProduct = remember { mutableStateOf<Product?>(null) }


    NavHost(
        navController = navController,
        startDestination = NavRoutes.PRODUCT_LIST
    ) {
        composable(NavRoutes.PRODUCT_LIST) {
            ProductListScreen(
                viewModel = productViewModel,
                onOrderClick = { product ->
                    // TODO: implement WhatsApp intent
                },
                onAddClick = {
                    navController.navigate(NavRoutes.ADD_PRODUCT)
                },
                onEditClick = { products ->
                    editingProduct.value = products
                    navController.navigate(NavRoutes.EDIT_PRODUCT)
                }
            )
        }
        composable(NavRoutes.ADD_PRODUCT) {
            AddProductScreen(
                onSave = { product ->
                    products.add(product)
                    navController.popBackStack() // kembali ke list
                },
                viewModel = productViewModel,
                onProductAdded = { navController.popBackStack() }
            )
        }
        composable(NavRoutes.EDIT_PRODUCT) {
            val product = editingProduct.value
            if (product != null) {
                EditProductScreen(
                    product = product,
//                    onUpdate = { updatedProduct ->
//                        val index = products.indexOfFirst { it.id == updatedProduct.id }
//                        if (index != -1) {
//                            products[index] = updatedProduct
//                        }
//                        navController.popBackStack()
//                    },
                    viewModel = productViewModel,
                    onProductUpdated = { navController.popBackStack() }
                )
            }
        }
    }

}