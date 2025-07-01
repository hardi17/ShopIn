package com.hardi.shopin.ui.navigation

import com.hardi.shopin.utils.AppConstant.CATEGORY_ID

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
}