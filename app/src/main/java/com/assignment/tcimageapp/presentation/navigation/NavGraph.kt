package com.assignment.tcimageapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assignment.tcimageapp.presentation.feature.photos.PhotosScreen

sealed class Screen(val route: String) {
    data object Photos : Screen("photos")
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Photos.route
    ) {
        composable(route = Screen.Photos.route) {
            PhotosScreen()
        }
    }
}
