package com.hardi.shopin.ui.navigation

import com.hardi.shopin.utils.AppConstant.CATEGORY_ID
import com.hardi.shopin.utils.AppConstant.PRODUCT_ID

sealed class RouteScreen(
    val name: String
) {
    object AuthScreen : RouteScreen("Auth")
    object LoginScreen : RouteScreen("Login")
    object SignupScreen : RouteScreen("Signup")
    object ShopInMainScreen : RouteScreen("ShopIn")
    object CategoryProductScreen : RouteScreen("CategoryProduct/{$CATEGORY_ID}"){
        fun passArg(categoryId: String) = "CategoryProduct/$categoryId"
    }
    object ProductDetailsScreen : RouteScreen("ProductDetailsScreen/{$PRODUCT_ID}") {
        fun passArg(productId: String) = "ProductDetailsScreen/$productId"
    }
}