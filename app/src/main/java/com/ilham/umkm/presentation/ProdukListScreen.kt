package com.ilham.umkm.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilham.umkm.R
import com.ilham.umkm.data.Product
import com.ilham.umkm.data.mapper.toDomainModel
import com.ilham.umkm.ui.theme.UMKMTheme
import kotlinx.coroutines.flow.map

@ExperimentalMaterial3Api
@Composable
fun ProductListScreen(
//    products: List<Product>,
    viewModel: ProductViewModel,
    onOrderClick: (Product) -> Unit,
    onAddClick: () -> Unit,
    onEditClick: (Product) -> Unit
) {

    val products by viewModel.productList
        .map { list -> list.map { it.toDomainModel() } }
        .collectAsState(initial = emptyList())

    val context = LocalContext.current
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var showDialog by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Katalog Produk") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Produk")
            }
        }
    ) {
        LazyColumn(contentPadding = it) {
            items(products) { product ->
                ProductItem(
                    product = product, // kita bahas sebentar lagi
                    onOrderClick = {
                        selectedProduct = it
                        showDialog = true
                    },
                    onEditClick = { onEditClick(it) }
                )
            }
        }
    }
    if (showDialog && selectedProduct != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.whatsapp),
                    contentDescription = null,
                    tint = Color(0xFF25D366)
                )
            },
            title = {
                Text(
                    "Kirim Pesanan?",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            text = {
                Text("Kamu akan diarahkan ke WhatsApp untuk mengirim pesanan produk \"${selectedProduct!!.name}\".")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false

                        val message = """
                        Halo, saya ingin memesan:
                        - ${selectedProduct!!.name}
                        - Harga: Rp ${selectedProduct!!.price}
                    """.trimIndent()

                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("https://wa.me/?text=${Uri.encode(message)}")
                        }

                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF25D366) // WhatsApp green
                    )
                ) {
                    Text("Ya", color = Color.White)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

}


@Composable
fun ProductItem(
    product: Product,
    onOrderClick: (Product) -> Unit,
    onEditClick: (Product) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
//        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(product.name, fontWeight = FontWeight.Bold)
                    Text("Rp ${product.price}")
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { onOrderClick(product) }) {
                    Text("Pesan")
                }
                OutlinedButton(onClick = { onEditClick(product) }) {
                    Text("Edit")
                }
            }
        }
    }
}


@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewProductListScreen() {
    UMKMTheme {
//        ProductListScreen(
//            viewModel = ,
//            onOrderClick = {},
//            onAddClick = {},
//            onEditClick = {}
//        )
    }
}