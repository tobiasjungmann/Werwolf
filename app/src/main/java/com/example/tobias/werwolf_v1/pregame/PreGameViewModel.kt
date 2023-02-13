package com.example.tobias.werwolf_v1.pregame

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tobias.werwolf_v1.R

class PreGameViewModel(application: Application) : AndroidViewModel(application) {

    private var characters: ArrayList<Character>?=null

    fun generateCharacters(): ArrayList<Character> {
        if (characters==null){
            characters=arrayListOf(
                Character("Werwolf", "blabla", R.color.werwolf),
                Character("Bürger", "blabla", R.color.citizen),
                Character("Amor", "blabla", R.color.amor),
                Character("Hexe", "blabla", R.color.witch),
                Character("Wächter", "blabla", R.color.guradian),
                Character("Mädchen", "blabla", R.color.girl),
                Character("Seher", "blabla", R.color.seher),
                Character("Dieb", "blabla", R.color.thief),
                Character("Jäger", "blabla", R.color.hunter),
                Character("Ritter", "blabla", R.color.knight),
                Character("Flötenspieler", "blabla", R.color.flute),
                Character("Freunde", "blabla", R.color.friends),
                Character("Weißer Werwolf", "blabla", R.color.wwolf),
                Character("Junges", "blabla", R.color.wchild),
                Character("Urwolf", "blabla", R.color.urwolf),
            )
        }
        return characters!!
    }

    fun invalidConfiguration(): Boolean {
        return anzahlWerwolf + anzahlWeisserWerwolf + anzahlUrwolf == 0 || anzahlWerwolf + anzahlWeisserWerwolf + anzahlUrwolf == gesamtPer
    }

    fun addToCharacterByPosition(bindingAdapterPosition: Int): Int {
        return ++(characters!![bindingAdapterPosition].amount)      // todo realism check
    }

    fun removeCharacterByPosition(bindingAdapterPosition: Int): Int {
        return --(characters!![bindingAdapterPosition].amount)      // todo realism check
    }

    fun getNumberOfUnmatchedPlayers(): Int {
        TODO("Not yet implemented")
    }

    private var anzahlAmor = 0
    private var anzahlWerwolf = 0
    private var anzahlHexe = 0
    private var anzahlDieb = 0
    private var anzahlSeher = 0
    private var anzahlJunges = 0
    private var anzahlJaeger = 0
    private var anzahlBuerger = 0
    private var anzahlWaechter = 0
    private var anzahlWeisserWerwolf = 0
    private var anzahlMaedchen = 0
    private var anzahlFloetenspieler = 0
    private var anzahlUrwolf = 0
    private var anzahlRitter = 0
    private var anzahlFreunde = 0
    private var gesamtPer = 0
}