package com.hardi.shopin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hardi.shopin.ui.auth.AuthScreen
import com.hardi.shopin.ui.login.LoginScreen
import com.hardi.shopin.ui.signup.SignupScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RouteScreen.AuthScreen.name
    ) {
        composable(RouteScreen.AuthScreen.name) {
            AuthScreen(
                modifier,
                onLoginClick = { navController.navigate(RouteScreen.LoginScreen.name) },
                onSignupClick = { navController.navigate(RouteScreen.SignupScreen.name) })
        }
        composable(RouteScreen.LoginScreen.name) {
            LoginScreen(modifier)
        }
        composable(RouteScreen.SignupScreen.name) {
            SignupScreen(modifier)
        }

    }
}