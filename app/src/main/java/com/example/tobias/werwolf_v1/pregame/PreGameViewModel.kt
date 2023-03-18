package com.example.tobias.werwolf_v1.pregame

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tobias.werwolf_v1.R
import com.example.tobias.werwolf_v1.database.models.CharacterClass
import com.example.tobias.werwolf_v1.database.models.Player
import com.example.tobias.werwolf_v1.database.models.WerwolfRepository
import java.util.concurrent.atomic.AtomicInteger

class PreGameViewModel(application: Application) : AndroidViewModel(application) {

    private var totalWolfs = 0
    private val totalCharacters = MutableLiveData(0)
    val amountCharacters: LiveData<Int> get() = totalCharacters

    private val repository: WerwolfRepository
    private val amountPlayers = AtomicInteger()

    private var characterClasses: ArrayList<CharacterClass>? = null

    fun generateCharacters(): ArrayList<CharacterClass> {
        if (characterClasses == null) {
            characterClasses = arrayListOf(
                CharacterClass("Werwolf", "blabla", R.color.werwolf, true, true),
                CharacterClass("Bürger", "blabla", R.color.citizen, true, false),
                CharacterClass("Amor", "blabla", R.color.amor, false, false),
                CharacterClass("Hexe", "blabla", R.color.witch, false, false),
                CharacterClass("Wächter", "blabla", R.color.guradian, false, false),
                CharacterClass("Mädchen", "blabla", R.color.girl, false, false),
                CharacterClass("Seher", "blabla", R.color.seher, false, false),
                CharacterClass("Dieb", "blabla", R.color.thief, false, false),
                CharacterClass("Jäger", "blabla", R.color.hunter, false, false),
                CharacterClass("Ritter", "blabla", R.color.knight, false, false),
                CharacterClass("Flötenspieler", "blabla", R.color.flute, false, false),
                CharacterClass("Freunde", "blabla", R.color.friends, true, false),
                CharacterClass("Weißer Werwolf", "blabla", R.color.wwolf, false, true),
                CharacterClass("Junges", "blabla", R.color.wchild, false, true),
                CharacterClass("Urwolf", "blabla", R.color.urwolf, false, true)
            )
        }
        return characterClasses!!
    }

    fun invalidConfiguration(): Boolean {
        return totalWolfs == 0 || totalWolfs > totalCharacters.value!! / 2
    }

    fun addToCharacterByPosition(bindingAdapterPosition: Int): Int {
        val current = characterClasses!![bindingAdapterPosition]
        if (!current.multipleAllowed && 1 == current.amount) {
            return current.amount
        }
        totalCharacters.value = totalCharacters.value?.plus(1)
        if (current.isWolf) {
            totalWolfs++
        }
        return ++(current.amount)
    }

    fun removeCharacterByPosition(bindingAdapterPosition: Int): Int {
        val current = characterClasses!![bindingAdapterPosition]
        if (0 >= current.amount) {
            return current.amount
        }
        totalCharacters.value = totalCharacters.value?.minus(1)
        if (current.isWolf) {
            totalWolfs--
        }
        return --(current.amount)
    }

    fun getNumberOfUnmatchedPlayers(): Int {
        TODO("Not yet implemented")
    }

    fun getAmountOfPlayers(): Int {
        return totalCharacters.value!!
    }


    /**
     * @return false if this exact name is already part of the list
     */
    fun insertPlayer(name: String): Boolean {
        // todo check if exists
        repository.insert(name)
        amountPlayers.getAndIncrement()
        return true
    }

    fun insertPlayer(player: Player) {
        repository.insert(player)
        amountPlayers.getAndIncrement()
    }


    fun getPlayerList(): LiveData<List<Player>> {
        return repository.allPlayers
    }

    fun amountUnmatchedPlayers(): Int {
        return totalCharacters.value!! - amountPlayers.get()// totalCharacters.value!!-totalPlayers.value!!
    }

    fun deletePlayer(player: Player) {
        repository.delete(player)
        amountPlayers.getAndDecrement()
    }

    fun getCharactersForMatching(): List<CharacterClass> {
        return characterClasses?.filter { a -> a.amount>0 } ?: arrayListOf()
    }

    init {
        repository = WerwolfRepository(application)
        val t = Thread {
            val num: Int = repository.getPlayerAmount()
            amountPlayers.set(num)
        }
        t.priority = 10
        t.start()
        t.join()
    }
}