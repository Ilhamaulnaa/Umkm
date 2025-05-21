package com.ilham.umkm.presentation.edit_product

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ilham.umkm.data.Product
import com.ilham.umkm.data.ProductEntity
import com.ilham.umkm.presentation.ProductViewModel

@ExperimentalMaterial3Api
@Composable
fun EditProductScreen(
    product: Product,
    viewModel: ProductViewModel,
    onProductUpdated: () -> Unit,
//    onUpdate: (Product) -> Unit
) {
    var name by remember { mutableStateOf(product.name) }
    var price by remember { mutableStateOf(product.price.toString()) }
    var description by remember { mutableStateOf(product.description) }

    var showDialog by remember { mutableStateOf(false) }
    
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){ uri: Uri? -> 
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp))
    {

        Text("Edit Produk", style = MaterialTheme.typography.titleLarge)
        
        Button(onClick = {
            launcher.launch("image/*")
        }) {
            Text(text = "Pilih Gambar")
        }

        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(model = it),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nama Produk") }
        )
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Harga") }
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Deskripsi") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val updateProduct = ProductEntity(
                id = product.id,
                name = name,
                description = description,
                price = price.toIntOrNull() ?: product.price,
                imageUri = imageUri?.toString()
            )
            viewModel.updateProduct(updateProduct)
            onProductUpdated()
        }) {
            Text("Update")
        }
        Button(onClick = {
             showDialog = true
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red
        )
        ) {
            Text("Delete Product", color = Color.White)
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Konfirmasi") },
                text = { Text("Yakin ingin menghapus produk ini?") },
                confirmButton = {
                    TextButton(onClick = {
                        val productToDelete = ProductEntity(
                            id = product.id,
                            name = product.name,
                            description = product.description,
                            price = product.price
                        )
                        viewModel.deleteProduct(productToDelete)
                        showDialog = false
                        onProductUpdated()
                    }) {
                        Text("Hapus", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Batal")
                    }
                }
            )
        }

    }
}
