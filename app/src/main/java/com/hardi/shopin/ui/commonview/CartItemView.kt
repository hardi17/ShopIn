package com.hardi.shopin.ui.commonview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hardi.shopin.data.model.Product
import com.hardi.shopin.ui.navigation.GlobalNavigation
import com.hardi.shopin.ui.navigation.RouteScreen
import com.hardi.shopin.utils.AppUtil

@Composable
fun CartItemView(productId: String, qty: Long) {

    var product by remember {
        mutableStateOf(Product())
    }

    var context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("data")
            .document("stock").collection("products")
            .document(productId).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(Product::class.java)
                    if (result != null) {
                        product = result
                    }
                }
            }
    }

    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable {
                GlobalNavigation.navController.navigate(RouteScreen.ProductDetailsScreen.passArg(productId))
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.images.firstOrNull(),
                contentDescription = "Product image",
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
            )

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = product.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = "$" +product.price,
                    fontWeight = FontWeight.SemiBold,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            AppUtil.removeItemFromCart(context, productId, false)
                        }
                    ) {
                        Text(
                            text = "-",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                        )
                    }

                    Text(
                        text = qty.toString(),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )

                    IconButton(
                        onClick = {
                            AppUtil.addItemToCart(productId, context)
                        }
                    ) {
                        Text(
                            text = "+",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                        )
                    }
                }
            }

            IconButton(
                onClick = {
                    AppUtil.removeItemFromCart(context, productId, true)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove from cart"
                )
            }
        }
    }

}