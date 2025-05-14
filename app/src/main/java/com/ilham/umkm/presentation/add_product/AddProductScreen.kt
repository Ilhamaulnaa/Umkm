package com.ilham.umkm.presentation.add_product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilham.umkm.R
import com.ilham.umkm.data.Product
import com.ilham.umkm.data.ProductEntity
import com.ilham.umkm.data.repository.ProductRepository
import com.ilham.umkm.presentation.ProductViewModel
import com.ilham.umkm.ui.theme.UMKMTheme

@ExperimentalMaterial3Api
@Composable
fun AddProductScreen(
    viewModel: ProductViewModel,
    onProductAdded: () -> Unit,
    onSave: (Product) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Tambah Produk", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nama Produk") })
        OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Harga") })
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Deskripsi") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
//            onSave(
//                Product(
//                    id = 0,
//                    name = name,
//                    description = description,
//                    price = price.toIntOrNull() ?: 0,
//                    imageRes = R.drawable.bg_product // sementara
//                )
//            )
            val product = ProductEntity(
                name = name,
                price = price.toIntOrNull() ?: 0,
                description = description
            )
            viewModel.addProduct(product)
            onProductAdded()
        }) {
            Text("Simpan Produk")
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewAddProductScreen() {
    UMKMTheme {
//        AddProductScreen(
//            onSave = {},
//            viewModel = ProductViewModel()
//        )
    }
}