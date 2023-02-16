package com.example.tobias.werwolf_v1.database.models

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.tobias.werwolf_v1.database.models.WerwolfDatabase.Companion.getInstance

class WerwolfRepository(application: Application?) {
    private val comparingElementDao: WerwolfDao
    private val allPlayers: LiveData<List<Player>>

    init {
        val database = getInstance(application!!)
        comparingElementDao = database!!.playerDao()
        allPlayers=comparingElementDao.allPlayers
    }
}
