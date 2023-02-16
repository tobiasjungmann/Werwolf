package com.example.tobias.werwolf_v1.database.models

import androidx.lifecycle.LifecycleOwner
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
class Character(
    val name: String,
    val info: String,
    val color: Int,
    val multipleAllowed: Boolean,
    val isWolf: Boolean
) {
    var amount: Int = 0
    @PrimaryKey(autoGenerate = true)
    var id = 0
}