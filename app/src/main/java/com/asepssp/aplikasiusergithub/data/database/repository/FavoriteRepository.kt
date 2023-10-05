package com.asepssp.aplikasiusergithub.data.database.repository

import androidx.lifecycle.LiveData
import com.asepssp.aplikasiusergithub.data.database.room.FavoriteDao
import com.asepssp.aplikasiusergithub.data.database.entity.FavoriteEntity


class FavoriteRepository(
    private val favoriteDao: FavoriteDao
) {

    companion object {
        private const val TAG = "FavoriteRepository"

        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            favoriteDao: FavoriteDao
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(favoriteDao)
            }.also { instance = it }
    }


    fun getAllFavorite(): LiveData<List<FavoriteEntity>> = favoriteDao.getAllFavorite()

    fun getFavorite(username: String): LiveData<FavoriteEntity> =
        favoriteDao.getFavorite(username)

    suspend fun insert(favoriteEntity: FavoriteEntity) {
        favoriteDao.insert(favoriteEntity)
    }

    suspend fun delete(favoriteEntity: FavoriteEntity) {
        favoriteDao.delete(favoriteEntity)
    }


}