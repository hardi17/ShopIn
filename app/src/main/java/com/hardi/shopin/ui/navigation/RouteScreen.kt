package com.hardi.shopin.ui.navigation

sealed class RouteScreen(
    val name: String
) {
    object AuthScreen : RouteScreen("Auth")
    object LoginScreen : RouteScreen("Login")
    object SignupScreen : RouteScreen("Signup")
    object ShopInMainScreen : RouteScreen("ShopIn")
}