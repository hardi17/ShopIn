package com.hardi.shopin.ui.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hardi.shopin.data.model.User
import com.hardi.shopin.ui.commonview.CartItemView

@Composable
fun CartScreen(
    modifier: Modifier
) {

    val userModel = remember {
        mutableStateOf(User())
    }

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener(){
                if (it.isSuccessful) {
                    val result = it.result.toObject(User::class.java)
                    if(result != null){
                        userModel.value = result
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
            text = "Cart Screen"
        )

        LazyColumn {
            items(userModel.value.cartItem.toList()){(productId, qty) ->
                CartItemView(modifier ,productId = productId, qty = qty)
            }
        }
    }
}