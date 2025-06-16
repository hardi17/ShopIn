package com.hardi.shopin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hardi.shopin.ui.auth.AuthScreen
import com.hardi.shopin.ui.auth.LoginScreen
import com.hardi.shopin.ui.auth.SignupScreen
import com.hardi.shopin.ui.home.ShopInMainScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

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
                navController.navigate(RouteScreen.ShopInMainScreen.name)
            })
        }
        composable(RouteScreen.SignupScreen.name) {
            SignupScreen(modifier, onSuccessSignUp = {
                navController.navigate(RouteScreen.ShopInMainScreen.name)
            })
        }
        composable(RouteScreen.ShopInMainScreen.name) {
            ShopInMainScreen(modifier)
        }

    }
}