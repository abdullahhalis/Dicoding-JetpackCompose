package com.dicoding.mygithubuser.ui.screen.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dicoding.mygithubuser.R

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    val photoUrl = stringResource(R.string.about_photo)
    val name = stringResource(R.string.about_name)
    val email = stringResource(R.string.about_email)
    val github = stringResource(R.string.about_github)
    val linkedin = stringResource(R.string.about_linkedin)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = stringResource(id = R.string.user_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(16.dp)
                .size(300.dp)
                .clip(CircleShape)
        )
        Text(
            text = name,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
        Text(
            text = email,
            fontWeight = FontWeight.Light,
            fontSize = 16.sp
        )
        Text(
            text = github,
            fontWeight = FontWeight.Light,
            fontSize = 16.sp
        )
        Text(
            text = linkedin,
            fontWeight = FontWeight.Light,
            fontSize = 16.sp
        )

    }
}