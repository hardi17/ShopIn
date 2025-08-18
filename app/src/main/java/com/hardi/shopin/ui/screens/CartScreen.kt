package com.hardi.shopin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hardi.shopin.R
import com.hardi.shopin.data.model.User
import com.hardi.shopin.ui.commonview.CartItemView
import com.hardi.shopin.ui.navigation.GlobalNavigation
import com.hardi.shopin.ui.navigation.RouteScreen

@Composable
fun CartScreen(
    modifier: Modifier
) {

    val userModel = remember {
        mutableStateOf(User())
    }

    DisposableEffect(key1 = Unit) {
        var listener = Firebase.firestore.collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .addSnapshotListener() { it, any ->
                if (it != null) {
                    val result = it.toObject(User::class.java)
                    if (result != null) {
                        userModel.value = result
                    }
                }
            }

        onDispose {
            listener.remove()
        }

    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.yourCart),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        if (userModel.value.cartItems.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(userModel.value.cartItems.toList(), key = { it.first }) { (productId, qty) ->
                    CartItemView(productId = productId, qty = qty)
                }
            }

            Button(
                onClick = {
                    GlobalNavigation.navController.navigate(RouteScreen.CheckoutScreen.name)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.checkout),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Default
                )
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.empty_cart_img),
                    contentDescription = "empty cart banner",
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.emptyCart),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}