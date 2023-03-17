package com.example.tobias.werwolf_v1.database.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WerwolfDao {
    @get:Query("SELECT * FROM player")
    val allPlayers: LiveData<List<Player>>


    @Insert
    fun insert(player: Player): Long
}