package com.hardi.shopin.ui.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hardi.shopin.data.model.Product
import com.hardi.shopin.ui.navigation.GlobalNavigation
import com.hardi.shopin.ui.navigation.RouteScreen
import com.hardi.shopin.utils.AppUtil

@Composable
fun CategoryProductScreen(modifier: Modifier, categoryId: String) {

    var productList = remember {
        mutableStateOf<List<Product>>(emptyList())
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data")
            .document("stock")
            .collection("products")
            .whereEqualTo("category", categoryId)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val resultList = it.result.documents.mapNotNull { doc ->
                        doc.toObject(Product::class.java)
                    }
                    productList.value = resultList
                }
            }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        items(productList.value.chunked(2)) { rowItem ->
            Row {
                rowItem.forEach { item ->
                    ProductItem(item, modifier = Modifier.weight(1f))
                }
                if (rowItem.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }

}

@Composable
fun ProductItem(item: Product, modifier: Modifier) {
    var context = LocalContext.current

    Card(
        modifier = modifier
            .padding(5.dp)
            .clickable {
                GlobalNavigation.navController.navigate(
                    RouteScreen.ProductDetailsScreen.passArg(item.id)
                )
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            AsyncImage(
                model = item.images.firstOrNull(),
                contentDescription = "Product image",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Text(
                text = item.title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.actualPrice,
                    fontWeight = FontWeight.SemiBold,
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.price,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        AppUtil.addItemToCart(item.id,context)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Add to cart"
                    )
                }
            }
        }
    }

}
