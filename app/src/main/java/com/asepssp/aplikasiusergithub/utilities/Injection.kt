package com.asepssp.aplikasiusergithub.utilities

import android.content.Context
import com.asepssp.aplikasiusergithub.data.database.repository.FavoriteRepository
import com.asepssp.aplikasiusergithub.data.database.room.FavoriteDatabase

object Injection {

    fun providerRepository(context: Context): FavoriteRepository {
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.favoriteDao()
        return FavoriteRepository.getInstance(dao)
    }
}