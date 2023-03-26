package com.example.tobias.werwolf_v1.pregame

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tobias.werwolf_v1.NightStages
import com.example.tobias.werwolf_v1.R
import com.example.tobias.werwolf_v1.database.models.CharacterClass
import com.example.tobias.werwolf_v1.database.models.Player
import com.example.tobias.werwolf_v1.database.models.WerwolfRepository
import java.util.concurrent.atomic.AtomicInteger

class PreGameViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var currentPlayer: Player
    private lateinit var availableCharacters: List<CharacterClass>
    private var playersForMatching: MutableList<Player>? = null

    private val _currentPlayerIndex: MutableLiveData<Int> = MutableLiveData(-2)
    val currentPlayerIndex: LiveData<Int> get() = _currentPlayerIndex

    private var totalWolfs = 0
    private val totalCharacters = MutableLiveData(0)
    val amountCharacters: LiveData<Int> get() = totalCharacters

    private val repository: WerwolfRepository
    private val amountPlayers = AtomicInteger()

    private var characterClasses: ArrayList<CharacterClass>? = null

    fun generateCharacters(): ArrayList<CharacterClass> {
        if (characterClasses == null) {
            characterClasses = arrayListOf(
                CharacterClass(
                    "Werwolf",
                    "blabla",
                    R.color.werwolf,
                    true,
                    true,
                    NightStages.W_CHILD,
                    R.string.night_desc_wolf_child,
                    R.drawable.icon_wolf
                ),
                CharacterClass(
                    "Bürger",
                    "blabla",
                    R.color.citizen,
                    true,
                    false,
                    NightStages.W_CHILD,
                    R.string.night_desc_wolf_child,
                    R.drawable.icon_wolf
                ),
                CharacterClass(
                    "Amor",
                    "blabla",
                    R.color.amor,
                    false,
                    false,
                    NightStages.W_CHILD,
                    R.string.night_desc_wolf_child,
                    R.drawable.icon_wolf
                ),
                CharacterClass(
                    "Hexe",
                    "blabla",
                    R.color.witch,
                    false,
                    false,
                    NightStages.W_CHILD,
                    R.string.night_desc_wolf_child,
                    R.drawable.icon_wolf
                ),
                CharacterClass(
                    "Wächter",
                    "blabla",
                    R.color.guradian,
                    false,
                    false,
                    NightStages.W_CHILD,
                    R.string.night_desc_wolf_child,
                    R.drawable.icon_wolf
                ),
                CharacterClass(
                    "Mädchen",
                    "blabla",
                    R.color.girl,
                    false,
                    false,
                    NightStages.W_CHILD,
                    R.string.night_desc_wolf_child,
                    R.drawable.icon_wolf
                ),
                CharacterClass(
                    "Seher",
                    "blabla",
                    R.color.seher,
                    false,
                    false,
                    NightStages.W_CHILD,
                    R.string.night_desc_wolf_child,
                    R.drawable.icon_wolf
                ),
                CharacterClass(
                    "Dieb",
                    "blabla",
                    R.color.thief,
                    false,
                    false,
                    NightStages.W_CHILD,
                    R.string.night_desc_wolf_child,
                    R.drawable.icon_wolf
                ),
                CharacterClass(
                    "Jäger",
                    "blabla",
                    R.color.hunter,
                    false,
                    false,
                    NightStages.W_CHILD,
                    R.string.night_desc_wolf_child,
                    R.drawable.icon_wolf
                ),
                CharacterClass(
                    "Ritter",
                    "blabla",
                    R.color.knight,
                    false,
                    false,
                    NightStages.W_CHILD,
                    R.string.night_desc_wolf_child,
                    R.drawable.icon_wolf
                ),
                CharacterClass(
                    "Flötenspieler",
                    "blabla",
                    R.color.flute,
                    false,
                    false,
                    NightStages.W_CHILD,
                    R.string.night_desc_wolf_child,
                    R.drawable.icon_wolf
                ),
                CharacterClass(
                    "Freunde",
                    "blabla",
                    R.color.friends,
                    true,
                    false,
                    NightStages.W_CHILD,
                    R.string.night_desc_wolf_child,
                    R.drawable.icon_wolf
                ),
                CharacterClass(
                    "Weißer Werwolf",
                    "blabla",
                    R.color.wwolf,
                    false,
                    true,
                    NightStages.W_CHILD,
                    R.string.night_desc_wolf_child,
                    R.drawable.icon_wolf
                ),
                CharacterClass(
                    "Junges",
                    "blabla",
                    R.color.wchild,
                    false,
                    true,
                    NightStages.W_CHILD,
                    R.string.night_desc_wolf_child,
                    R.drawable.icon_wolf
                ),
                CharacterClass(
                    "Urwolf",
                    "blabla",
                    R.color.urwolf,
                    false,
                    true,
                    NightStages.W_CHILD,
                    R.string.night_desc_wolf_child,
                    R.drawable.icon_wolf
                )
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

    fun prepareNextPlayerMatching() {
        // _curentindex anpassen

        if (playersForMatching == null) {
            availableCharacters = characterClasses?.filter { a -> a.amount > 0 } ?: arrayListOf()
            playersForMatching = repository.allPlayers.value?.toMutableList()
            _currentPlayerIndex.value = repository.allPlayers.value?.size
        } else {
            availableCharacters = availableCharacters.filter { a -> a.amount > 0 }
            playersForMatching!!.removeFirst()
            _currentPlayerIndex.value = (_currentPlayerIndex.value ?: 0) - 1
        }
        currentPlayer = playersForMatching!!.first()
    }

    fun getCharactersForMatching(): List<CharacterClass> {
        return availableCharacters
    }

    fun getNextPlayerName(): String {
        return currentPlayer.name
    }

    fun clickedCharacter(bindingAdapterPosition: Int) {
        currentPlayer.characterClass = availableCharacters[bindingAdapterPosition].id
        availableCharacters[bindingAdapterPosition].amount--
        repository.updatePlayer(currentPlayer)
        if (_currentPlayerIndex.value == 1 || _currentPlayerIndex.value == 0) {
            _currentPlayerIndex.value = 0
        } else {
            prepareNextPlayerMatching()
        }
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