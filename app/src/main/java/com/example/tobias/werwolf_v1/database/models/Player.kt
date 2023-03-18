package com.example.tobias.werwolf_v1.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
class Player(val name:String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var characterClass =-1
}