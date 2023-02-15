package com.example.tobias.werwolf_v1.pregame

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tobias.werwolf_v1.R

class PreGameViewModel(application: Application) : AndroidViewModel(application) {

    private val totalPers = MutableLiveData(0)
    val amountPers: LiveData<Int> get() = totalPers
    private var characters: ArrayList<Character>?=null
    private var gesamtPer = 0

    fun generateCharacters(): ArrayList<Character> {
        if (characters==null){
            characters=arrayListOf(
                Character("Werwolf", "blabla", R.color.werwolf, true),
                Character("Bürger", "blabla", R.color.citizen,true),
                Character("Amor", "blabla", R.color.amor,false),
                Character("Hexe", "blabla", R.color.witch,false),
                Character("Wächter", "blabla", R.color.guradian,false),
                Character("Mädchen", "blabla", R.color.girl,false),
                Character("Seher", "blabla", R.color.seher,false),
                Character("Dieb", "blabla", R.color.thief,false),
                Character("Jäger", "blabla", R.color.hunter,false),
                Character("Ritter", "blabla", R.color.knight,false),
                Character("Flötenspieler", "blabla", R.color.flute,false),
                Character("Freunde", "blabla", R.color.friends,true),
                Character("Weißer Werwolf", "blabla", R.color.wwolf,false),
                Character("Junges", "blabla", R.color.wchild,false),
                Character("Urwolf", "blabla", R.color.urwolf,false),
            )
        }
        return characters!!
    }

    fun invalidConfiguration(): Boolean {
        return true;//anzahlWerwolf + anzahlWeisserWerwolf + anzahlUrwolf == 0 || anzahlWerwolf + anzahlWeisserWerwolf + anzahlUrwolf > gesamtPer/2
    }

    fun addToCharacterByPosition(bindingAdapterPosition: Int): Int {
        val current=characters!![bindingAdapterPosition]
        if (!current.multipleAllowed && 1==current.amount){
            return current.amount
        }
        totalPers.value= totalPers.value?.plus(1)
        return ++(current.amount)
    }

    fun removeCharacterByPosition(bindingAdapterPosition: Int): Int {
        val current=characters!![bindingAdapterPosition]
        if (0>=current.amount){
            return current.amount
        }
        totalPers.value= totalPers.value?.minus(1)

        return --(current.amount)
    }

    fun getNumberOfUnmatchedPlayers(): Int {
        TODO("Not yet implemented")
    }
}