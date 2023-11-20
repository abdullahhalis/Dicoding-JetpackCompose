package com.dicoding.mygithubuser

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.mygithubuser.ui.navigation.NavigationItem
import com.dicoding.mygithubuser.ui.navigation.Screen
import com.dicoding.mygithubuser.ui.screen.about.AboutScreen
import com.dicoding.mygithubuser.ui.screen.detail.DetailScreen
import com.dicoding.mygithubuser.ui.screen.favorite.FavoriteScreen
import com.dicoding.mygithubuser.ui.screen.home.HomeScreen
import com.dicoding.mygithubuser.ui.theme.MyGithubUserTheme

@Composable
fun GithubUserApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            when (currentRoute) {
                Screen.Home.route -> MyTopBar(title = stringResource(id = R.string.app_name))
                Screen.Favorite.route -> MyTopBar(title = stringResource(id = R.string.favorite_page))
                Screen.About.route -> MyTopBar(title = stringResource(id = R.string.about_page))
            }
        },
        bottomBar = {
            if (currentRoute != Screen.DetailUser.route) {
                BottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { username ->
                        navController.navigate(Screen.DetailUser.createRoute(username))
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateToDetail = { username ->
                        navController.navigate(Screen.DetailUser.createRoute(username))
                    }
                )
            }
            composable(Screen.About.route) {
                AboutScreen()
            }
            composable(
                route = Screen.DetailUser.route,
                arguments = listOf(navArgument("username") { type = NavType.StringType}),
            ) {
                val username =it.arguments?.getString("username") ?: ""
                DetailScreen(
                    username = username,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    title: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { 
            Text(text = title)
        },
    )
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(id = R.string.home_page),
                icon = Icons.Default.Home,
                screen = Screen.Home,
            ),
            NavigationItem(
                title = stringResource(R.string.favorite_page),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.about_page),
                icon = Icons.Default.AccountCircle,
                screen = Screen.About
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon ,
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title)}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GithubUserAppPreview() {
    MyGithubUserTheme {
        GithubUserApp()
    }
}