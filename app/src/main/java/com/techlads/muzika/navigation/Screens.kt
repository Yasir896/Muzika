package com.techlads.muzika.navigation

sealed class Screens(val route: String) {
    object HomeScreen: Screens("home_screen")
    object LikedSongsScreen: Screens("liked_songs_screen")
}
