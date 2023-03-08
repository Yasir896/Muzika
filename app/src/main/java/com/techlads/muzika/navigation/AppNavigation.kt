package com.techlads.muzika.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.techlads.muzika.features.presentation.home.HomeScreen
import com.techlads.muzika.features.presentation.liked_songs.LikedSongsScreen

@ExperimentalAnimationApi
@Composable
fun AppNavigation(navController: NavHostController) {
    AnimatedNavHost(navController = navController, startDestination =Screens.HomeScreen.route) {

        composable(route = Screens.HomeScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) {
            HomeScreen() {
                navController.navigate(Screens.LikedSongsScreen.route)
            }
        }

        composable(route = Screens.LikedSongsScreen.route,
                enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) {
            LikedSongsScreen()
        }

    }

}