package com.hardi.shopin.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.hardi.shopin.ui.navigation.AppNavigation
import com.hardi.shopin.ui.navigation.GlobalNavigation
import com.hardi.shopin.ui.navigation.RouteScreen
import com.hardi.shopin.ui.theme.ShopInTheme
import com.hardi.shopin.utils.AppUtil
import com.hardi.shopin.utils.PaymentMethods
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopInTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    AppNavigation(Modifier.padding(innerPadding))
                }
            }
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        AppUtil.clearCartAndAddToCart(PaymentMethods.CARD.toString())
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Payment Successful")
        builder.setMessage("Thank you for shopping with us")
        builder.setPositiveButton("OK") { dialog, _ ->
            val navController = GlobalNavigation.navController
            navController.popBackStack()
            navController.navigate(RouteScreen.ShopInMainScreen.name)
            dialog.dismiss()
        }.setCancelable(false)
        builder.show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        AppUtil.showToast(this, "Payment Failed")
    }
}
