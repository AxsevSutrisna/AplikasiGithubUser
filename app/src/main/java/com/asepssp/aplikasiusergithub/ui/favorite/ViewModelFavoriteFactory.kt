package com.asepssp.aplikasiusergithub.ui.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asepssp.aplikasiusergithub.data.database.repository.FavoriteRepository
import com.asepssp.aplikasiusergithub.utilities.Injection

class ViewModelFavoriteFactory(private val favoriteRepository: FavoriteRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFavoriteFactory? = null
        fun getInstance(context: Context): ViewModelFavoriteFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFavoriteFactory(Injection.providerRepository(context))
            }.also { instance = it }
    }


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}