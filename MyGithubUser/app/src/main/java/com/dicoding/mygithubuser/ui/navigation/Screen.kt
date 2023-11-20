package com.dicoding.mygithubuser.ui.navigation

sealed class Screen(val route: String){
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("profile")
    object DetailUser : Screen("home/{username}") {
        fun createRoute(username: String) = "home/$username"
    }
}
