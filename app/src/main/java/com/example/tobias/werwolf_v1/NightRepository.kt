package com.example.tobias.werwolf_v1

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.tobias.werwolf_v1.database.models.CharacterClass
import com.example.tobias.werwolf_v1.database.models.Player
import com.example.tobias.werwolf_v1.database.models.WerwolfDao
import com.example.tobias.werwolf_v1.database.models.WerwolfDatabase

class NightRepository(application: Application?) {

    private val werwolfDao: WerwolfDao
    val allPlayers: LiveData<List<Player>>

    fun generateCharacters(): ArrayList<CharacterClass> {       // todo load from db
        return arrayListOf(
            CharacterClass(
                "Werwolf",
                "blabla",
                R.color.werwolf,
                true,
                true,
                NightStages.WOLF,
                R.string.night_desc_werwolf
            ),
            CharacterClass(
                "Bürger",
                "blabla",
                R.color.citizen,
                true,
                false,
                NightStages.EVALUATE_NIGHT,
                R.string.night_desc_citizen
            ),
            CharacterClass(
                "Amor",
                "blabla",
                R.color.amor,
                false,
                false,
                NightStages.AMOR,
                R.string.night_desc_armor
            ),
            CharacterClass(
                "Hexe",
                "blabla",
                R.color.witch,
                false,
                false,
                NightStages.WITCH,
                R.string.night_desc_witch
            ),
            CharacterClass(
                "Wächter",
                "blabla",
                R.color.guradian,
                false,
                false,
                NightStages.GUARDIAN,
                R.string.night_desc_guardian
            ),
            CharacterClass(
                "Mädchen",
                "blabla",
                R.color.girl,
                false,
                false,
                NightStages.GIRL,
                R.string.night_desc_girl
            ),
            CharacterClass(
                "Seher",
                "blabla",
                R.color.seher,
                false,
                false,
                NightStages.SEHER,
                R.string.night_desc_seher
            ),
            CharacterClass(
                "Dieb",
                "blabla",
                R.color.thief,
                false,
                false,
                NightStages.THIEF,
                R.string.night_desc_thief
            ),
            CharacterClass(
                "Jäger",
                "blabla",
                R.color.hunter,
                false,
                false,
                NightStages.HUNTER,
                R.string.night_desc_hunter
            ),
            CharacterClass(
                "Ritter",
                "blabla",
                R.color.knight,
                false,
                false,
                NightStages.KNIGHT,
                R.string.night_desc_knight
            ),
            CharacterClass(
                "Flötenspieler",
                "blabla",
                R.color.flute,
                false,
                false,
                NightStages.FLUTE,
                R.string.night_desc_flute
            ),
            CharacterClass(
                "Freunde",
                "blabla",
                R.color.friends,
                true,
                false,
                NightStages.FRIENDS,
                R.string.night_desc_friends
            ),
            CharacterClass(
                "Weißer Werwolf",
                "blabla",
                R.color.wwolf,
                false,
                true,
                NightStages.WHITE_WOLF,
                R.string.night_desc_wolf_child
            ),
            CharacterClass(
                "Junges",
                "blabla",
                R.color.wchild,
                false,
                true,
                NightStages.W_CHILD,
                R.string.night_desc_wolf_child
            ),
            CharacterClass(
                "Urwolf",
                "blabla",
                R.color.urwolf,
                false,
                true,
                NightStages.URWOLF,
                R.string.night_desc_wolf_child
            )
        )
    }

    fun getPlayerAtPosition(position: Int): Player {
        TODO("Not yet implemented")
    }

    fun getCharacterByStage(stage: NightStages): CharacterClass {
        TODO("Not yet implemented")
    }

    fun decrementCharacterAmount(role: NightStages) {
        // decrement the count of the character which role was part of teh parameter
    }

    init {
        val database = WerwolfDatabase.getInstance(application!!)
        werwolfDao = database!!.playerDao()
        allPlayers = werwolfDao.allPlayers
    }
}