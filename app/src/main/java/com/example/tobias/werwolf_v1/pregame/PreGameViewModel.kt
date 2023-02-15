package com.example.tobias.werwolf_v1.pregame

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tobias.werwolf_v1.R

class PreGameViewModel(application: Application) : AndroidViewModel(application) {

    private var totalWolfes = 0
    private val totalPers = MutableLiveData(0)
    val amountPers: LiveData<Int> get() = totalPers
    private var characters: ArrayList<Character>? = null
    //private var gesamtPer = 0

    fun generateCharacters(): ArrayList<Character> {
        if (characters == null) {
            characters = arrayListOf(
                Character("Werwolf", "blabla", R.color.werwolf, true, true),
                Character("Bürger", "blabla", R.color.citizen, true, false),
                Character("Amor", "blabla", R.color.amor, false, false),
                Character("Hexe", "blabla", R.color.witch, false, false),
                Character("Wächter", "blabla", R.color.guradian, false, false),
                Character("Mädchen", "blabla", R.color.girl, false, false),
                Character("Seher", "blabla", R.color.seher, false, false),
                Character("Dieb", "blabla", R.color.thief, false, false),
                Character("Jäger", "blabla", R.color.hunter, false, false),
                Character("Ritter", "blabla", R.color.knight, false, false),
                Character("Flötenspieler", "blabla", R.color.flute, false, false),
                Character("Freunde", "blabla", R.color.friends, true, false),
                Character("Weißer Werwolf", "blabla", R.color.wwolf, false, true),
                Character("Junges", "blabla", R.color.wchild, false, true),
                Character("Urwolf", "blabla", R.color.urwolf, false, true)
            )
        }
        return characters!!
    }

    fun invalidConfiguration(): Boolean {
        return totalWolfes== 0 || totalWolfes > totalPers.value!!/2
    }

    fun addToCharacterByPosition(bindingAdapterPosition: Int): Int {
        val current = characters!![bindingAdapterPosition]
        if (!current.multipleAllowed && 1 == current.amount) {
            return current.amount
        }
        totalPers.value = totalPers.value?.plus(1)
        if (current.isWolf) {
            totalWolfes++
        }
        return ++(current.amount)
    }

    fun removeCharacterByPosition(bindingAdapterPosition: Int): Int {
        val current = characters!![bindingAdapterPosition]
        if (0 >= current.amount) {
            return current.amount
        }
        totalPers.value = totalPers.value?.minus(1)
        if (current.isWolf) {
            totalWolfes--
        }
        return --(current.amount)
    }

    fun getNumberOfUnmatchedPlayers(): Int {
        TODO("Not yet implemented")
    }
}