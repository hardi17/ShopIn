package com.hardi.shopin.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hardi.shopin.R
import com.hardi.shopin.data.model.Product
import com.hardi.shopin.data.model.User
import com.hardi.shopin.ui.navigation.GlobalNavigation
import com.hardi.shopin.ui.navigation.RouteScreen
import com.hardi.shopin.utils.AppUtil

@Composable
fun CheckoutScreen(modifier: Modifier) {

    val user = remember {
        mutableStateOf(User())
    }

    val productList = remember {
        mutableStateListOf(Product())
    }

    val subTotal = remember {
        mutableStateOf(0f)
    }
    val discount = remember {
        mutableStateOf(0f)
    }

    val tax = remember {
        mutableStateOf(0f)
    }

    val total = remember {
        mutableStateOf(0f)
    }


    fun calculateAndAssign() {
        productList.forEach {

            if (it.price.isNotEmpty()) {
                val qty = user.value.cartItems[it.id] ?: 0
                val price = it.price.toFloatOrNull() ?: 0f
                subTotal.value += price * qty
            }
        }

        discount.value = "%.2f".format(subTotal.value * (AppUtil.getDiscountValue()) / 100).toFloat()
        tax.value = "%.2f".format(subTotal.value * (AppUtil.getTaxValue() / 100)).toFloat()
        total.value = "%.2f".format(subTotal.value - discount.value + tax.value).toFloat()

    }

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(User::class.java)
                    if (result != null) {
                        user.value = result

                        Firebase.firestore.collection("data")
                            .document("stock").collection("products")
                            .whereIn("id", user.value.cartItems.keys.toList())
                            .get().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val resultList = task.result.toObjects(Product::class.java)

                                    productList.clear()
                                    productList.addAll(resultList)
                                    calculateAndAssign()
                                }
                            }
                    }
                }
            }

    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.checkout),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.deliverTo) + user.value.name ,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        )
        Text(
            text = stringResource(R.string.shippingAddress)+ "\n" + user.value.address,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
        CheckOutItemView(title = stringResource(R.string.subtotal), value = subTotal.value.toString())
        Spacer(modifier = Modifier.height(16.dp))
        CheckOutItemView(title = stringResource(R.string.discount), value = discount.value.toString())
        Spacer(modifier = Modifier.height(16.dp))
        CheckOutItemView(title = stringResource(R.string.tax), value = tax.value.toString())
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
        CheckOutItemView(title = stringResource(R.string.toPay), value = total.value.toString())
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                AppUtil.startPayment(total.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.payNow),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontFamily = FontFamily.Default
            )
        }
    }
}

@Composable
fun CheckOutItemView(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ){
        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
        )
        Text(
            text = "$$value",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
    }
}

