package com.ilham.umkm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ilham.umkm.navigation.NavGraph
import com.ilham.umkm.presentation.ProductViewModel
import com.ilham.umkm.presentation.ProductViewModelFactory
import com.ilham.umkm.ui.theme.UMKMTheme

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UMKMTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val context = LocalContext.current
                    val viewModel: ProductViewModel = viewModel(
                        factory = ProductViewModelFactory(context)
                    )
                    val product by viewModel.productList.collectAsState()

                    NavGraph()
                }
            }
        }
    }
}