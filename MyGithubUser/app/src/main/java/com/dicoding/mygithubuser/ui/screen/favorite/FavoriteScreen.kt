package com.dicoding.mygithubuser.ui.screen.favorite

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.mygithubuser.data.local.entity.FavoriteUser
import com.dicoding.mygithubuser.ui.common.UiState
import com.dicoding.mygithubuser.ui.components.ErrorScreen
import com.dicoding.mygithubuser.ui.components.ItemLoading
import com.dicoding.mygithubuser.ui.components.ItemUser
import com.dicoding.mygithubuser.utils.ViewModelFactory

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    val context = LocalContext.current
    val favoriteViewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context)
    )
    favoriteViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                ItemLoading()
                favoriteViewModel.getAllFavoriteUser()
            }
            is UiState.Success -> {
                if (uiState.data.isEmpty()) {
                    ErrorScreen()
                } else {
                    FavoriteContent(
                        listUser = uiState.data,
                        navigateToDetail = navigateToDetail
                    )
                }
            }
            is UiState.Error -> {
                ErrorScreen()
            }
        }
    }
}

@Composable
fun FavoriteContent(
    listUser: List<FavoriteUser>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.testTag("listUserFavorite")
    ){
        items(listUser, key = { it.username }) { user ->
            ItemUser(
                photoUrl = user.avatarUrl,
                username = user.username,
                onClick = { navigateToDetail(user.username) }
            )
        }
    }
}