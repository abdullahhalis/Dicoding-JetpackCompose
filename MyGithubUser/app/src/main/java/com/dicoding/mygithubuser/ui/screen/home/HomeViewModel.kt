package com.dicoding.mygithubuser.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mygithubuser.data.UserRepository
import com.dicoding.mygithubuser.data.remote.response.ItemsItem
import com.dicoding.mygithubuser.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<ItemsItem>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<ItemsItem>>> get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    init {
        getListUsers("abdullah")
    }

    fun getListUsers(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            repository.getListUsers(_query.value)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{ listUsers ->
                    _uiState.value = UiState.Success(listUsers)
                }
        }
    }
}