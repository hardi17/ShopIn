package com.hardi.shopin.utils

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object AppUtil {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun addItemToCart(productId: String, context: Context) {
        val userDoc = Firebase.firestore.collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)

        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()
                val currentQuantity = currentCart[productId]?: 0
                val updateQuantity = currentQuantity + 1

                val updateCart = mapOf("cartItems.$productId" to updateQuantity)

                userDoc.update(updateCart)
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            showToast(context, "Item added to cart")
                        }else{
                            showToast(context, "Failed to add item to cart")
                        }
                    }
            }
        }
    }
}