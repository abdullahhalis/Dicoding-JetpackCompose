package com.dicoding.mygithubuser.ui.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.mygithubuser.R
import com.dicoding.mygithubuser.data.local.entity.FavoriteUser
import com.dicoding.mygithubuser.data.remote.response.DetailResponse
import com.dicoding.mygithubuser.ui.common.UiState
import com.dicoding.mygithubuser.ui.components.ErrorScreen
import com.dicoding.mygithubuser.ui.components.ItemLoading
import com.dicoding.mygithubuser.utils.ViewModelFactory

@Composable
fun DetailScreen(
    username: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val detailViewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context)
    )
    detailViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                ItemLoading()
                detailViewModel.getDetailUser(username)
            }
            is UiState.Success -> {
                val favoriteUser = FavoriteUser(
                    avatarUrl = uiState.data.avatarUrl,
                    username = uiState.data.login
                )
                detailViewModel.setFavoriteUser(favoriteUser)
                val favoriteStatus by detailViewModel.favoriteStatus.observeAsState(false)
                DetailContent(
                    user = uiState.data,
                    favoriteStatus = favoriteStatus,
                    updateFavoriteStatus = {
                        detailViewModel.changeFavorite(favoriteUser)
                    },
                    onBackClick = onBackClick
                )
            }
            is UiState.Error -> {
                ErrorScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    user: DetailResponse,
    favoriteStatus: Boolean,
    modifier: Modifier = Modifier,
    updateFavoriteStatus: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { onBackClick() },
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                title = { Text(text = stringResource(id = R.string.detail_user)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = updateFavoriteStatus,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.testTag("favoriteButton")
            ) {
                Icon(
                    imageVector = if (favoriteStatus) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                    contentDescription = stringResource(id = R.string.favorite_page),
                )
            }
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = stringResource(id = R.string.user_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(16.dp)
                    .size(300.dp)
                    .clip(CircleShape)
            )
            Text(
                text = user.name.toString(),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
            Text(
                text = user.login,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(64.dp)
            ) {
                Text(
                    text = stringResource(R.string.followers, user.followers),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(
                    text = stringResource(R.string.following, user.following),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
        }
    }
}