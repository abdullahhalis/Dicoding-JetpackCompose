package com.dicoding.mygithubuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.dicoding.mygithubuser.ui.components.SplashScreen
import com.dicoding.mygithubuser.ui.theme.MyGithubUserTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyGithubUserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var timeOutSplash by remember {
                        mutableStateOf(false)
                    }
                    if (timeOutSplash) {
                        GithubUserApp()
                    } else {
                        SplashScreen()
                        CountDownSplash (
                            onTimeout = {
                                timeOutSplash = true
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CountDownSplash(onTimeout: () -> Unit) {
    var timer by remember {
        mutableStateOf(3)
    }
    val currentOnTimeout by rememberUpdatedState(onTimeout)
    LaunchedEffect(true) {
        while (timer > 0) {
            delay(1000)
            timer--
        }
        currentOnTimeout()
    }
}




