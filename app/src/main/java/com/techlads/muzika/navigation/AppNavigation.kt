package com.techlads.muzika.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.techlads.muzika.features.presentation.home.HomeScreen
import com.techlads.muzika.features.presentation.liked_songs.LikedSongsScreen
import com.techlads.muzika.features.presentation.player.PlayerScreen

@ExperimentalAnimationApi
@Composable
fun AppNavigation(navController: NavHostController) {
    AnimatedNavHost(navController = navController, startDestination =Screens.HomeScreen.route) {

        composable(route = Screens.HomeScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) {
            HomeScreen(
                onMenuClick = { navController.navigate(Screens.LikedSongsScreen.route) },
                onSongItemClick = { navController.navigate(Screens.PlayerScreen.route) }
            )
        }

        composable(route = Screens.LikedSongsScreen.route,
                enterTransition = { tabEnterTransition() },
            exitTransition = { tabExitTransition() }) {
            LikedSongsScreen(
                onNavigationClick = { navController.popBackStack() },
                onSongItemClick = { navController.navigate(Screens.PlayerScreen.route)}
            )
        }

        composable(route = Screens.PlayerScreen.route,
            enterTransition = { tabEnterTransition() },
            exitTransition = { tabExitTransition() }) {
            PlayerScreen {
                navController.popBackStack()
            }
        }

    }

}

private fun tabExitTransition() = slideOutHorizontally(targetOffsetX = { 4000 })
private fun tabEnterTransition() = slideInHorizontally( initialOffsetX = { 4000 })