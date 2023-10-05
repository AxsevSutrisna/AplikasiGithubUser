package com.asepssp.aplikasiusergithub.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asepssp.aplikasiusergithub.data.database.entity.FavoriteEntity
import com.asepssp.aplikasiusergithub.data.database.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> = favoriteRepository.getAllFavorite()

    fun getFavorite(username: String): LiveData<FavoriteEntity> =
        favoriteRepository.getFavorite(username)

    fun insert(favoriteEntity: FavoriteEntity) = viewModelScope.launch {
        favoriteRepository.insert(favoriteEntity)
    }

    fun delete(favoriteEntity: FavoriteEntity) = viewModelScope.launch {
        favoriteRepository.delete(favoriteEntity)
    }
}