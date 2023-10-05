package com.asepssp.aplikasiusergithub.data.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asepssp.aplikasiusergithub.data.database.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favourite: FavoriteEntity)

    @Delete
    suspend fun delete(favourite: FavoriteEntity)

    @Query("SELECT * FROM FavoriteEntity")
    fun getAllFavorite(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM FavoriteEntity WHERE username = :username")
    fun getFavorite(username: String): LiveData<FavoriteEntity>

}