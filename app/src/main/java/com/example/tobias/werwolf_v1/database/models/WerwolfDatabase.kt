package com.example.tobias.werwolf_v1.database.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Player::class],
    version = 1
)
abstract class WerwolfDatabase : RoomDatabase() {
    abstract fun playerDao(): WerwolfDao


    companion object {
        private var instance: WerwolfDatabase? = null

        @JvmStatic
        @Synchronized
        fun getInstance(context: Context): WerwolfDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext, WerwolfDatabase::class.java,
                    "player_database.db"
                ).fallbackToDestructiveMigration().build()
            }
            return instance
        }
    }
}