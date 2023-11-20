package com.dicoding.mygithubuser.ui.screen.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.dicoding.mygithubuser.data.UserRepository
import com.dicoding.mygithubuser.data.local.entity.FavoriteUser
import com.dicoding.mygithubuser.data.remote.response.DetailResponse
import com.dicoding.mygithubuser.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: UserRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<DetailResponse>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DetailResponse>> get() = _uiState

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            repository.getDetailUser(username)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { user ->
                    _uiState.value = UiState.Success(user)
                }
        }
    }

    private val favoriteUserData = MutableLiveData<FavoriteUser>()

    fun setFavoriteUser(favoriteUser: FavoriteUser) {
        favoriteUserData.value = favoriteUser
    }

    val favoriteStatus = favoriteUserData.switchMap {
        repository.isUserFavorite(it.username)
    }

    fun changeFavorite(favoriteUser: FavoriteUser) {
        viewModelScope.launch {
            if (favoriteStatus.value as Boolean) {
                repository.deleteFavorite(favoriteUser.username)
            } else {
                repository.addFavorite(favoriteUser)
            }
        }
    }
}