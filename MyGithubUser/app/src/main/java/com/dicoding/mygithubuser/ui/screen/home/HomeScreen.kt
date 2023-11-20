package com.dicoding.mygithubuser.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.mygithubuser.R
import com.dicoding.mygithubuser.data.remote.response.ItemsItem
import com.dicoding.mygithubuser.ui.common.UiState
import com.dicoding.mygithubuser.ui.components.ErrorScreen
import com.dicoding.mygithubuser.ui.components.ItemLoading
import com.dicoding.mygithubuser.ui.components.ItemUser
import com.dicoding.mygithubuser.utils.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    val context = LocalContext.current
    val homeViewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context)
    )
    val query by homeViewModel.query
    homeViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                ItemLoading()
            }
            is UiState.Success -> {
                HomeContent(
                    listUser = uiState.data,
                    query = query,
                    onQueryChange = homeViewModel::getListUsers,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {
                ErrorScreen()
            }
        }
    }
}

@Composable
fun HomeContent(
    listUser: List<ItemsItem>,
    query: String,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit,
    navigateToDetail: (String) -> Unit
) {
    Column {
        MySearchBar(
            query = query,
            onQueryChange = onQueryChange
        )
        LazyColumn(
            modifier = Modifier.testTag("listUser"),
        ){
            items(listUser, key = {it.login} ) { user ->
                ItemUser(
                    photoUrl = user.avatarUrl,
                    username = user.login,
                    onClick = { navigateToDetail(user.login) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(text = stringResource(R.string.search_user))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        content = {}
    )
}
