package com.dicoding.mygithubuser

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.IdlingRegistry
import com.dicoding.mygithubuser.ui.navigation.Screen
import com.dicoding.mygithubuser.ui.theme.MyGithubUserTheme
import com.dicoding.mygithubuser.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GithubUserAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MyGithubUserTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                GithubUserApp(navController = navController)
            }
        }
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun verify_Start_Destination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun verify_All_Button_Navigation_Destination() {
        composeTestRule.onNodeWithStringId(R.string.favorite_page).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.about_page).performClick()
        navController.assertCurrentRouteName(Screen.About.route)
        composeTestRule.onNodeWithStringId(R.string.home_page).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navigate_to_favorite_page_have_no_data() {
        composeTestRule.onNodeWithStringId(R.string.favorite_page).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.no_data).assertIsDisplayed()
    }

    @Test
    fun verify_insert_remove_favorite_data() {
        composeTestRule.onNodeWithTag("listUser").onChildAt(0).performClick()
        navController.assertCurrentRouteName(Screen.DetailUser.route)
        composeTestRule.onNodeWithTag("favoriteButton").performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        composeTestRule.onNodeWithStringId(R.string.favorite_page).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithTag("listUserFavorite").isDisplayed()
        composeTestRule.onNodeWithTag("listUserFavorite").onChildAt(0).performClick()
        navController.assertCurrentRouteName(Screen.DetailUser.route)
        composeTestRule.onNodeWithTag("favoriteButton").performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.no_data).assertIsDisplayed()
    }
}