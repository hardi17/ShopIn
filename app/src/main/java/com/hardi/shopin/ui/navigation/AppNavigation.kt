package com.hardi.shopin.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hardi.shopin.ui.screens.CategoryProductScreen
import com.hardi.shopin.ui.screens.CheckoutScreen
import com.hardi.shopin.ui.screens.ProductDetailsScreen
import com.hardi.shopin.ui.screens.ShopInMainScreen
import com.hardi.shopin.ui.auth.AuthScreen
import com.hardi.shopin.ui.auth.LoginScreen
import com.hardi.shopin.ui.auth.SignupScreen
import com.hardi.shopin.ui.screens.OrderScreen
import com.hardi.shopin.utils.AppConstant.CATEGORY_ID
import com.hardi.shopin.utils.AppConstant.PRODUCT_ID

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    GlobalNavigation.navController = navController

    val isLoggedIn = Firebase.auth.currentUser != null
    val firstPage =
        if (isLoggedIn) {
            RouteScreen.ShopInMainScreen.name
        } else {
            RouteScreen.AuthScreen.name
        }

    NavHost(
        navController = navController,
        startDestination = firstPage
    ) {
        composable(RouteScreen.AuthScreen.name) {
            AuthScreen(
                modifier,
                onLoginClick = { navController.navigate(RouteScreen.LoginScreen.name) },
                onSignupClick = { navController.navigate(RouteScreen.SignupScreen.name) })
        }
        composable(RouteScreen.LoginScreen.name) {
            LoginScreen(modifier, onSuccessLogin = {
                navController.navigate(RouteScreen.ShopInMainScreen.name) {
                    popUpTo(RouteScreen.AuthScreen.name) {
                        inclusive = true
                    }
                }
            })
        }
        composable(RouteScreen.SignupScreen.name) {
            SignupScreen(modifier, onSuccessSignUp = {
                navController.navigate(RouteScreen.ShopInMainScreen.name) {
                    popUpTo(RouteScreen.AuthScreen.name) {
                        inclusive = true
                    }
                }
            })
        }

        composable(RouteScreen.ShopInMainScreen.name) {
            ShopInMainScreen(modifier)
        }

        composable(
            RouteScreen.CategoryProductScreen.name,
            arguments = listOf(navArgument(CATEGORY_ID) { type = NavType.StringType })
        ) {
            var categoryId = it.arguments?.getString(CATEGORY_ID) ?: ""
            CategoryProductScreen(modifier, categoryId)
        }

        composable(
            RouteScreen.ProductDetailsScreen.name,
            arguments = listOf(navArgument(PRODUCT_ID) { type = NavType.StringType })
        ) {
            var productId = it.arguments?.getString(PRODUCT_ID) ?: ""
            ProductDetailsScreen(modifier, productId)
        }

        composable(RouteScreen.CheckoutScreen.name) {
            CheckoutScreen(modifier)
        }

        composable(RouteScreen.OrderScreen.name) {
            OrderScreen(modifier)
        }
    }

}

object GlobalNavigation {
    @SuppressLint("StaticFieldLeak")
    lateinit var navController: NavHostController
}