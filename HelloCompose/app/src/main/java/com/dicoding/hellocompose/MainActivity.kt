package com.dicoding.hellocompose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.data.Group
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.dicoding.hellocompose.ui.theme.HelloComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Jetpack Compose")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(
    name = "Single Preview",
    group = "Greeting",
    showBackground = true,
    widthDp = 200,
    heightDp = 200
)
@Preview(
    name = "Full Device Preview",
    group = "Greeting",
    device = Devices.PIXEL_3A,
    showSystemUi = true
)
@Preview(
    name = "Without Surface",
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun GreetingPreview() {
    HelloComposeTheme {
        Greeting("Jetpack Compose")
    }
}

@Preview(
    name = "With Surface",
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun GreetingSurfacePreview() {
    HelloComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Greeting("Jetpack Compose")
        }
    }
}
