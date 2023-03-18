package com.example.tobias.werwolf_v1.database.models

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WerwolfDao {
    @get:Query("SELECT * FROM player")
    val allPlayers: LiveData<List<Player>>


    @Insert
    fun insert(player: Player): Long

    @Query("SELECT COUNT(id) FROM player")
    fun getCount(): Int

    @Delete
    fun delete(player: Player)

    @Update
    fun update(player: Player)
}