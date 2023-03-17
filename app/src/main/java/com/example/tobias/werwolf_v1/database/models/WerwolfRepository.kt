package com.example.tobias.werwolf_v1.database.models

import android.app.Application
import android.content.ContentValues.TAG
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.tobias.werwolf_v1.database.models.WerwolfDatabase.Companion.getInstance

class WerwolfRepository(application: Application?) {

    private val werwolfDao: WerwolfDao
     val allPlayers: LiveData<List<Player>>

    fun insert(name: String) {
        InsertNameThread(
            werwolfDao,
            name
        ).start()
    }

   private class InsertNameThread(private val werwolfDao: WerwolfDao,private val name: String) :
       Thread() {
       override fun run() {
           try {
               val id = werwolfDao.insert(Player(name))
           } catch (e: SQLiteConstraintException) {
               Log.e(TAG, "run: add Player", null)
           }
       }
    }


    init {
        val database = getInstance(application!!)
        werwolfDao = database!!.playerDao()
        allPlayers = werwolfDao.allPlayers
    }
}
