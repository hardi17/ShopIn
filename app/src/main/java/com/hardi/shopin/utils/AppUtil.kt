package com.hardi.shopin.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hardi.shopin.data.model.Order
import com.hardi.shopin.ui.navigation.GlobalNavigation
import com.razorpay.Checkout
import org.json.JSONObject
import java.util.UUID

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
                val currentQuantity = currentCart[productId] ?: 0
                val updateQuantity = currentQuantity + 1

                val updateCart = mapOf("cartItems.$productId" to updateQuantity)

                userDoc.update(updateCart)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showToast(context, "Item added to cart")
                        } else {
                            showToast(context, "Failed to add item to cart")
                        }
                    }
            }
        }
    }

    fun removeItemFromCart(context: Context, productId: String, removeAll: Boolean = false) {
        val userDoc = Firebase.firestore.collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)

        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()
                val currentQuantity = currentCart[productId] ?: 0
                val updateQuantity = currentQuantity - 1

                val updateCart =
                    if (updateQuantity <= 0 || removeAll) {
                        mapOf("cartItems.$productId" to FieldValue.delete())
                    } else {
                        mapOf("cartItems.$productId" to updateQuantity)
                    }

                userDoc.update(updateCart)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showToast(context, "Item removed from cart")
                        } else {
                            showToast(context, "Failed to remove from cart")
                        }
                    }
            }
        }
    }

    fun clearCartAndAddToCart(paymentMethod:  String) {
        val userDoc = Firebase.firestore.collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)

        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()

                val order = Order(
                    id = UUID.randomUUID().toString().replace("-", ""),
                    date = Timestamp.now(),
                    userId = FirebaseAuth.getInstance().currentUser?.uid!!,
                    items = currentCart,
                    status = "ORDERED",
                    address = it.result.get("address").toString(),
                    paymentMethod = paymentMethod
                )

                Firebase.firestore.collection("orders")
                    .document(order.id).set(order)
                    .addOnCompleteListener{
                    if(it.isSuccessful){
                        userDoc.update("cartItems", FieldValue.delete())
                    }
                }
            }
        }
    }

    fun getDiscountValue(): Float {
        return 10.0f
    }

    fun getTaxValue(): Float {
        return 12.0f
    }

    fun razorApiKey(): String {
        return "rzp_test_5WgA34F9ljiXAX"
    }

    fun startPayment(amount: Float) {
        val checkOut = Checkout()
        checkOut.setKeyID(razorApiKey())

        val options = JSONObject()
        options.put("name", "Easy shop")
        options.put("description", " ")
        options.put("amount", amount * 100)
        options.put("currency", "USD")


        checkOut.open(GlobalNavigation.navController.context as Activity, options)
    }
}