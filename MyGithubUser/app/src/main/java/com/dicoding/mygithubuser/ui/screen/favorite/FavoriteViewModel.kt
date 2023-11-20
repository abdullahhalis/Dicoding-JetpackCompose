package com.dicoding.mygithubuser.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mygithubuser.data.UserRepository
import com.dicoding.mygithubuser.data.local.entity.FavoriteUser
import com.dicoding.mygithubuser.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: UserRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<FavoriteUser>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<FavoriteUser>>> get() = _uiState

    fun getAllFavoriteUser() {
        viewModelScope.launch {
            repository.getAllFavorite()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{ listUser ->
                    _uiState.value = UiState.Success(listUser)
                }
        }
    }
}