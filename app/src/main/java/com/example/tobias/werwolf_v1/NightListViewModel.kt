package com.example.tobias.werwolf_v1

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.AndroidViewModel
import com.example.tobias.werwolf_v1.database.models.CharacterClass
import com.example.tobias.werwolf_v1.database.models.Player

class NightListViewModel(application: Application) : AndroidViewModel(application),
    NightListContract.Presenter {
    private var repository: NightRepository
    private lateinit var nightListActivity: NightListContract.View
    private lateinit var currentCharacter: CharacterClass
    private var currentStage: NightStages = NightStages.AMOR

    //private var mDatabaseHelper: DatabaseHelper? = null
    //private var data: Cursor? = null


    /* private var anzahlAmor = 0
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
     private var anzahlFreunde = 0*/
    private var liebenderEinsID = -1
    private var liebenderZweiID = -1
    private var schlafplatzDiebID = -1
    private var schlafplatzWaechterID = -1
    private var vorbildID = -1
    private var werwolfOpferID = -1
    private var weisserWerwolfOpferID = -1
    private var hexeOpferID = -1
    private var urwolfVeto = 0
    private var buergerOpfer = -1
    private var tot: String = ""
    private var liebespaarEntdeckt = false
    private var ritterletzteRundeGetoetet = false
    private var CharakterpositionErstInkementieren = false
    private var listeAuswahlGenuegend = 0
    private var verzaubertAktuell = -1
    private var verzaubertCharakter: String = ""
    private var verzaubertName: String = ""
    private var charakterPosition = 0
    private var wwletzteRundeAktiv = true

    //für Hexe
    private var trankLebenEinsetzbar = true
    private var trankTodEinsetzbar = true
    private var hexeToetenGedrueckt = false
    private var hexeRettenGedrueckt = false
    private var werwolfOpferIDBackupHexe = -1

    //Urwolf
    private var werwolfDurchUrwolfID = -1

    //Jaeger
    private var charakterPositionJaegerBackup = 0
    private var jaegerOpfer = -1
    private var jaegerAktiv = false

    //Ritter
    private var ritterAktiv = false
    private var ritterOpfer = -1
    private var nameRitterOpfer: String = ""
    private var charakterRitterOpfer: String = ""

    private lateinit var characterClasses: ArrayList<CharacterClass>


    fun handleClickAtPosition(position: Int, nextButton: Button) {
        val player: Player = repository.getPlayerAtPosition(position)

        when (player.role) {
            NightStages.AMOR -> handleAmorListClick(player.id)
            NightStages.THIEF -> {
                schlafplatzDiebID = player.id
                CharakterpositionErstInkementieren = true

            }
            NightStages.GUARDIAN -> {
                schlafplatzWaechterID = player.id
                CharakterpositionErstInkementieren = true

            }
            NightStages.W_CHILD -> {
                vorbildID = player.id
                CharakterpositionErstInkementieren = true

            }
            NightStages.FLUTE -> {
                verzaubertAktuell = player.id
                /* verzaubertCharakter = player.name
                 verzaubertName = player.charmed todo add charmed to the database once finished and teh next stage is selected*/
                CharakterpositionErstInkementieren = true

            }
            NightStages.WOLF -> {
                werwolfOpferID = player.id
                CharakterpositionErstInkementieren = true

            }
            NightStages.WHITE_WOLF -> {
                weisserWerwolfOpferID = player.id
                CharakterpositionErstInkementieren = true

            }
            NightStages.WITCH -> {
                hexeOpferID = player.id
                CharakterpositionErstInkementieren = true

            }
            NightStages.KILL_DAY -> {
                buergerOpfer = player.id
                CharakterpositionErstInkementieren = true
            }
            NightStages.HUNTER -> {         // todo potentially change stage incrementation
                jaegerOpfer = player.id

            }
            NightStages.KNIGHT -> {
                ritterOpfer = player.id
                /*   nameRitterOpfer = name
                   charakterRitterOpfer = charakter*/
            }
            else -> {}
        }
    }

    private fun handleAmorListClick(id: Int) {
        if (listeAuswahlGenuegend == 0) {
            if (liebenderEinsID == -1) {
                liebenderEinsID = id
            } else {
                liebenderZweiID = id
                CharakterpositionErstInkementieren = true
                listeAuswahlGenuegend = 1
            }
        } else {
            if (listeAuswahlGenuegend % 2 == 1) {
                if (id != liebenderZweiID) {
                    liebenderEinsID = id
                    listeAuswahlGenuegend++
                }
            } else {
                if (id != liebenderEinsID) {
                    liebenderZweiID = id
                    listeAuswahlGenuegend++
                }
            }
        }
    }

    private fun forwardStage() {
        currentStage = NightStages.fromInt((currentStage.value + 1) % NightStages.HUNTER.value)
    }


    /**
     * Can only be used for the beginning stages -> stages are simply incremented
     */
    private fun testIfCharacterExits(stage: NightStages): Boolean {
        if (currentCharacter.amount <= 0) {
            forwardStage()
            startNextStage()
            return false
        } else {
            currentCharacter = repository.getCharacterByStage(stage)
        }
        return true
    }


    private fun startNextStage() {

        when (currentStage) {
            NightStages.AMOR -> if (testIfCharacterExits(NightStages.AMOR)) {
                nightListActivity.updateUIForCharacter(currentCharacter, false, true)
                repository.convertClassToCitizen(NightStages.AMOR)
            }
            NightStages.FRIENDS -> if (testIfCharacterExits(NightStages.FRIENDS)) {
                nightListActivity.updateUIForCharacter(currentCharacter, false, false)
                repository.convertClassToCitizen(NightStages.FRIENDS)
                forwardStage()
            }
            NightStages.THIEF -> if (testIfCharacterExits(NightStages.THIEF)) {
                nightListActivity.updateUIForCharacter(currentCharacter, false, true)
            }
            NightStages.GUARDIAN -> if (testIfCharacterExits(NightStages.GUARDIAN)) {
                nightListActivity.updateUIForCharacter(currentCharacter, false, true)
            }
            NightStages.W_CHILD -> if (testIfCharacterExits(NightStages.W_CHILD)) {
                nightListActivity.updateUIForCharacter(currentCharacter, false, true)
                repository.convertClassToCitizen(NightStages.W_CHILD)
            }
            NightStages.SEHER -> if (testIfCharacterExits(NightStages.SEHER)) {
                nightListActivity.updateUIForCharacter(currentCharacter, true, false)
                forwardStage()
            }
            NightStages.FLUTE -> if (testIfCharacterExits(NightStages.FLUTE)) {
                nightListActivity.updateUIForCharacter(currentCharacter, false, true)
            }
            NightStages.KNIGHT -> {
                if (testIfCharacterExits(NightStages.FLUTE) && ritterAktiv) {
                    nightListActivity.updateUIForCharacter(currentCharacter, false, true)
                }
                forwardStage()
            }
            NightStages.WOLF -> {
                if (repository.getNumberOfWolfes() > 0) {
                    nightListActivity.updateUIForCharacter(currentCharacter, false, true)
                } else {
                    nightListActivity.setDescription(
                        "Fehler: Es sind keine Werwölfe mehr im Spiel!",
                        false
                    )
                }
            }
            NightStages.WHITE_WOLF -> if (currentCharacter.amount > 0 && !wwletzteRundeAktiv) {
                nightListActivity.updateUIForCharacter(currentCharacter, false, true)
                wwletzteRundeAktiv = true
            } else {
                wwletzteRundeAktiv = false
                forwardStage()
                startNextStage()
            }
            NightStages.URWOLF -> if (currentCharacter.amount > 0 && urwolfVeto != -1) {
                nightListActivity.updateUIForCharacter(currentCharacter, false, false)
                nightListActivity.setUrwolfVisibility(View.VISIBLE)
                forwardStage()
            } else {
                forwardStage()
                startNextStage()
            }
            NightStages.WITCH -> {
                nightListActivity.setUrwolfVisibility(View.GONE)
                if (currentCharacter.amount > 0) {
                    if (trankLebenEinsetzbar || trankTodEinsetzbar) {
                        werwolfOpferIDBackupHexe = werwolfOpferID
                        nightListActivity.updateUIForCharacter(currentCharacter, true, false)
                        nightListActivity.activateWitchDialog(
                            trankLebenEinsetzbar,
                            trankTodEinsetzbar
                        )
                        forwardStage()
                    } else {
                        forwardStage()
                        startNextStage()
                    }
                } else {
                    forwardStage()
                    startNextStage()
                }
            }
            NightStages.EVALUATE_NIGHT -> evaluateNight()
            NightStages.ELECTION_DAY -> {
                auswerten()
                nightListActivity.updateUIForCharacter(currentCharacter, false, true)
            }
            NightStages.KILL_DAY -> {
                buergeropferToeten()
                if (!jaegerAktiv) {
                    nightListActivity.setPlayerListVisibility(View.GONE)

                    nightListActivity.setDescription(
                        "Das ganze Dorf schläft ein.\n\nHinweis: Die Reihenfolge der Personen in der Liste hat sich geändert.",
                        false
                    )
                    charakterPosition = 1
                    werwolfOpferID = -1
                    weisserWerwolfOpferID = -1
                    schlafplatzWaechterID = -1
                    schlafplatzDiebID = -1
                    tot = ""
                    // totErweiterungWW = "";
                } else {
                    nightListActivity.setDescription(
                        "\n\nDer Jäger ist gestorben. Er darf eine weitere Person töten:", true
                    )
                }
            }
            else -> {}
        }
    }


    fun nextButtonClicked() {
        Log.d(ContentValues.TAG, "weiternacht Position: $charakterPosition")
        if (charakterPosition == 20) {
            jaegerToeten()
        }
        if (charakterPosition == 21) {
            ritterDialogToeten()
        } else {
            if (CharakterpositionErstInkementieren) {
                forwardStage()
                CharakterpositionErstInkementieren = false
            }
            startNextStage()
        }
    }

    private fun ritterDialogToeten() {
        ritterAktiv = false
        charakterPosition = 7
        startNextStage()
    }

    fun jaegerDialog() {
        charakterPositionJaegerBackup = charakterPosition
        charakterPosition = 20
        jaegerAktiv = true      // todo adapt to hunter
        nightListActivity.updateUIForCharacter(currentCharacter, false, true)
    }

    fun evaluateNight() {
        nachtAuswerten()

        //Opfer des weißen werwolfes töten
        if (weisserWerwolfOpferID != -1 && weisserWerwolfOpferID != werwolfOpferID) {
            weisserWerwolfAuswerten()
            weisserWerwolfOpferID = -1
        }

        //Opfer des Ritters töten
        if (ritterOpfer != -1) {
            tot = ""
            sicherToeten(repository.getPlayerById(ritterOpfer))
            nightListActivity.setDescription(tot, true)
            ritterOpfer = -1
            nameRitterOpfer = ""
            charakterRitterOpfer = ""
        }
        auswerten()
        if (tot.compareTo("") == 0) {
            nightListActivity.setDescription("Das ganze Dorf erwacht, alle haben überlebt", false)
        } else {
            nightListActivity.setDescription("Das ganze Dorf erwacht außer: $tot", false)
        }
        tot = ""

        //wenn Jäger gestorben, entsprechende charakterposition
        if (!jaegerAktiv) {
            nightListActivity.deactivateHunter()

            forwardStage()
        } else {
            nightListActivity.setDescription(
                "Der Jäger ist gestorben. Er darf eine weitere Person töten:",
                true
            )
        }
        if (ritterAktiv) {
            nightListActivity.setDescription(
                "\n\nDer Ritter ist verstorben. In der nächsten Nacht stirbt der nächste Werwolf zur Rechten des Ritters.",
                true
            )
        }
    }

    fun killVictimPartOfLovers(victim: Player) {
        if (hexeOpferID == liebenderEinsID || hexeOpferID == liebenderZweiID) {
            hexeOpferID = -1
        }

        killPlayer(repository.getPlayerById(liebenderEinsID))
        killPlayer(repository.getPlayerById(liebenderZweiID))

        liebespaarEntdeckt = true
        if (vorbildID == liebenderEinsID || vorbildID == liebenderZweiID) {
            vorbildGestorbenDialog()
        }
    }

    private fun sicherToeten(victim: Player) {
        if ((victim.id == liebenderEinsID || victim.id == liebenderZweiID) && !liebespaarEntdeckt) {
            killVictimPartOfLovers(victim)
        } else {
            killPlayer(victim)
            if (vorbildID == victim.id) {
                vorbildGestorbenDialog()
            }
        }
    }

    private fun killPlayer(victim: Player) {
        anzahlMindern(victim)
        addNameToDeadString(victim.name)
        repository.deletePlayerById(victim.id)
    }

    private fun addNameToDeadString(name: String) {
        tot = tot + "\n" + name
    }

    private fun anzahlMindern(player: Player) {
        if (player.role == NightStages.HUNTER) {
            Log.d(ContentValues.TAG, "jager dekrementiert")
            jaegerDialog()
        }
        repository.decrementCharacterAmount(player.role)
        if (player.role == NightStages.KNIGHT) {
            ritterDialog()
            ritterletzteRundeGetoetet = true
        }
    }

    private fun ritterDialog() {
        ritterAktiv = true
    }

    private fun weisserWerwolfAuswerten() {
        if (weisserWerwolfOpferID != -1) //Werwolf existiert
        {
            val victim: Player = repository.getPlayerById(weisserWerwolfOpferID)
            val thief: Player = repository.getPlayerByCharacter(NightStages.THIEF)
            val guardian: Player = repository.getPlayerByCharacter(NightStages.GUARDIAN)

            // todo auf den wwolf abstimmen - hat eine andere ID
            victimIdHelper=weisserWerwolfOpferID
            checkForGuardianPlacement(victim, guardian.id, thief.id)
            val opferZweiID = checkForThiefPlacement(victim, guardian.id, thief.id)
            weisserWerwolfOpferID=victimIdHelper


            //Attribute des Finalen Opfers bestimmen, dann töten
            if (weisserWerwolfOpferID != -1) {
                if (weisserWerwolfOpferID == hexeOpferID) {
                    hexeOpferID = -1
                }
                sicherToeten(victim)
            }

            //Opfer 2 wird ermittelt und getötet
            if (opferZweiID != -1) {
                if (opferZweiID == hexeOpferID) {
                    hexeOpferID = -1
                }
                sicherToeten(repository.getPlayerById(opferZweiID))
            }
        }
    }

    private fun auswerten() {
        if (liebenderEinsID != -1) {
            if (repository.getRemainigPlayerCount() <= 3 && !liebespaarEntdeckt) {
                nightListActivity.siegbildschirmOeffnen("liebespaar")
            }
        }
        if (repository.moreCitizensThanWolfs()) {
            if (repository.getAmountSpecialWolfes() > 0) {
                nightListActivity.siegbildschirmOeffnen("werwoelfe")
            } else {
                nightListActivity.siegbildschirmOeffnen("ww")
            }
        }

        if (repository.getNumberOfWolfes() == 0) {
            nightListActivity.siegbildschirmOeffnen("buerger")
        }

        if (repository.everybodyCharmed()) {
            nightListActivity.siegbildschirmOeffnen("floetenspieler")
        }
    }


    private fun jaegerToeten() {
        nightListActivity.setPlayerListVisibility(View.GONE)
        jaegerAktiv = false
        sicherToeten(repository.getPlayerById(jaegerOpfer))
        charakterPosition = charakterPositionJaegerBackup

        startNextStage()
    }

    private fun buergeropferToeten() {
        sicherToeten(repository.getPlayerById(buergerOpfer))
        auswerten()
    }

    fun witchSaveVictim(): Boolean {
        hexeRettenGedrueckt = !hexeRettenGedrueckt
        hexeRettenGedrueckt = !hexeRettenGedrueckt
        if (hexeRettenGedrueckt) {
            trankLebenEinsetzbar = false
            werwolfOpferID = -1
        } else {
            trankLebenEinsetzbar = true
            werwolfOpferID = werwolfOpferIDBackupHexe
        }
        return hexeRettenGedrueckt
    }

    private fun vorbildGestorbenDialog() {
        nightListActivity.setDescription(
            "Das Vorbild ist verstorben. Ab der nächsten Nacht wacht das Junge mit den Werwölfen gemeinsam auf.",
            true
        )
        repository.convertChildToWolf()
    }

    fun witchKill(): Boolean {
        hexeToetenGedrueckt = !hexeToetenGedrueckt
        if (hexeToetenGedrueckt) {
            trankTodEinsetzbar = false
            CharakterpositionErstInkementieren = true
            charakterPosition--
        } else {
            trankTodEinsetzbar = true
            CharakterpositionErstInkementieren = false
            forwardStage()
        }
        return hexeToetenGedrueckt
    }

    private fun nachtAuswerten() {
        if (werwolfOpferID != -1) //Werwolf existiert
        {
            val werwolfOpfer: Player = repository.getPlayerById(werwolfOpferID)
            if (urwolfVeto == 1) //Von Urwolf gerettet -> verwandelt sich in einen Werwolf
            {
                werwolfDurchUrwolfID = werwolfOpferID
                urwolfVeto = -1
                repository.addCharacterToWolfes(werwolfOpfer)

                werwolfOpferID = -1
            } else {            //Auswertung aller Opfer
                val waechterID = repository.getPlayerByCharacter(NightStages.GUARDIAN)
                val diebID = repository.getPlayerByCharacter(NightStages.GUARDIAN)

                //wächter ist das Ofer
                victimIdHelper=werwolfOpferID
                checkForGuardianPlacement(werwolfOpfer, waechterID.id, diebID.id)
                val opferZweiID = checkForThiefPlacement(werwolfOpfer, waechterID.id, diebID.id)
                werwolfOpferID=victimIdHelper

                //Attribute des Finalen Opfers bestimmen, dann töten
                if (werwolfOpferID != -1) {
                    if (werwolfOpferID == hexeOpferID) {
                        hexeOpferID = -1
                    }
                    sicherToeten(werwolfOpfer)
                }

                //Opfer 2 wird ermittelt und getötet
                if (opferZweiID != -1) {
                    if (opferZweiID == hexeOpferID) {
                        hexeOpferID = -1
                    }
                    sicherToeten(repository.getPlayerById(opferZweiID))
                }
            }
        }
        if (hexeOpferID != -1) {
            sicherToeten(repository.getPlayerById(hexeOpferID))
        }
    }

    private var victimIdHelper=-1
    private fun checkForGuardianPlacement(werwolfOpfer: Player, waechterID: Int, diebID: Int) {
        if (werwolfOpfer.role == NightStages.GUARDIAN) {
            if (schlafplatzWaechterID != waechterID) // hier wird noch geprüft werden ob der dieb beim wächter schläft
            {
                if (waechterID != schlafplatzDiebID) {
                    victimIdHelper = -1
                    Log.d(ContentValues.TAG, "Werwolf tötet wächter, aber niemand daheim")
                } else {
                    victimIdHelper = diebID
                    Log.d(ContentValues.TAG, "dieb bei wächter wächter aber nicht daheim")
                }
            }
        }

        //wächter rettet das Opfer
        if (victimIdHelper != -1) {
            if (schlafplatzWaechterID == victimIdHelper) {
                victimIdHelper = waechterID
                Log.d(ContentValues.TAG, "Wächter hat das Opfer gerettet")
            }
        }
    }

    private fun checkForThiefPlacement(werwolfOpfer: Player, waechterID: Int, diebID: Int): Int {
        //Hat der Dieb was mit dem Opfer zu tun?
        if (victimIdHelper != -1 && schlafplatzDiebID != -1) {
            if (diebID != schlafplatzDiebID) //Dieb ist nicht daheim -> gilt als nicht normaler bürger
            {
                if (schlafplatzDiebID == waechterID && waechterID != schlafplatzWaechterID) {
                    victimIdHelper = diebID
                } else if (schlafplatzDiebID == victimIdHelper) {  //dieb ist beim Opfer -> muss auch sterben
                    return diebID
                }

                if (diebID == victimIdHelper) {
                    victimIdHelper = -1
                }
            }
        }
        return -1
    }

    fun toggleDescriptionLength(context: Context): String {
        showLongTexts = !showLongTexts
        return getDescToCharacter(currentCharacter, context)
    }

    private var showLongTexts = true
    fun getDescToCharacter(characterClass: CharacterClass, context: Context): String {
        var desc = context.getString(characterClass.descStringId)
        if (!showLongTexts) {
            desc = desc.substring(0, 30) + "..."
        }
        return desc
    }

    fun urwolfClicked(): Boolean {
        if (urwolfVeto == 1) {
            urwolfVeto = 0
        } else {
            urwolfVeto = 1
        }
        return urwolfVeto == 0
    }

    init {
        repository = NightRepository(application)
        repository.generateCharacters()
        urwolfVeto = if (repository.urwolfExists()) 0 else -1
    }

    override fun attachView(view: NightListContract.View) {
        this.nightListActivity = view
    }
}