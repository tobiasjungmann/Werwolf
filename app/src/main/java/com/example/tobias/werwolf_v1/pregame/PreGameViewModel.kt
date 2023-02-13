package com.example.tobias.werwolf_v1.pregame

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tobias.werwolf_v1.R

class PreGameViewModel(application: Application) : AndroidViewModel(application) {

    private var characters: ArrayList<Character>?=null

    fun generateCharacters(): ArrayList<Character> {
        if (characters==null){
            characters=arrayListOf(
                Character("Werwolf", "blabla", R.color.grau)
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