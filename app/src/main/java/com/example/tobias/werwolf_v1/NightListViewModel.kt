package com.example.tobias.werwolf_v1

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.example.tobias.werwolf_v1.database.models.CharacterClass
import com.example.tobias.werwolf_v1.database.models.DatabaseHelper

class NightListViewModel(application: Application) : AndroidViewModel(application) {
    private var mDatabaseHelper: DatabaseHelper? = null
    private var data: Cursor? = null

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
    private var tot: String? = null
    private var liebespaarEntdeckt = false
    private var ritterletzteRundeGetoetet = false
    private var CharakterpositionErstInkementieren = false
    private var listeAuswahlGenuegend = 0
    private var verzaubertAktuell = -1
    private var verzaubertCharakter: String = ""
    private var verzaubertName: String = ""
    private var charakterPosition = 0
    private var wwletzteRundeAktiv = true
    private var langerText = false

    //für Hexe
    private var trankLebenEinsetzbar = true
    private var trankTodEinsetzbar = true
    private var hexeToetenGedrueckt = false
    private var hexeRettenGedrueckt = false
    private var werwolfOpferIDBackupHexe = -1

    //Urwolf
    private var werwolfDurchUrwolfID = 0

    //Jaeger
    private var charakterPositionJaegerBackup = 0
    private var jaegerOpfer = 0
    private var jaegerAktiv = false

    //Ritter
    private var ritterAktiv = false
    private var ritterOpfer = 0
    private var nameRitterOpfer: String? = null
    private var charakterRitterOpfer: String? = null

    private lateinit var characterClasses: ArrayList<CharacterClass>


    fun handleClickAtPosition(position: Int, nextButton: Button) {
        //Hier kmmt eine Möglichkeit zum Löschen eines Eintrages
        var itemID = -1
        data?.moveToPosition(position)
        val name = data?.getString(1)
        val charakter = data?.getString(2)
        val hilfedata = mDatabaseHelper!!.getItemID(name ?: "invalid")
        while (hilfedata.moveToNext()) {
            itemID = hilfedata.getInt(0)
        }
        if (itemID > -1) {
            when (charakterPosition) {
                0 -> if (listeAuswahlGenuegend == 0) {
                    if (liebenderEinsID == -1) {
                        liebenderEinsID = itemID
                    } else {
                        liebenderZweiID = itemID
                        CharakterpositionErstInkementieren = true


                        listeAuswahlGenuegend = 1
                    }
                } else {
                    if (listeAuswahlGenuegend % 2 == 1) {
                        if (itemID != liebenderZweiID) {
                            liebenderEinsID = itemID

                            listeAuswahlGenuegend++
                        }
                    } else {
                        if (itemID != liebenderEinsID) {
                            liebenderZweiID = itemID
                            listeAuswahlGenuegend++
                        }
                    }
                }
                2 -> {
                    schlafplatzDiebID = itemID
                    CharakterpositionErstInkementieren = true

                }
                3 -> {
                    schlafplatzWaechterID = itemID
                    CharakterpositionErstInkementieren = true

                }
                4 -> {
                    vorbildID = itemID
                    CharakterpositionErstInkementieren = true

                }
                6 -> {
                    verzaubertAktuell = itemID
                    verzaubertCharakter = charakter
                    verzaubertName = name
                    CharakterpositionErstInkementieren = true

                }
                7 -> {
                    werwolfOpferID = itemID
                    CharakterpositionErstInkementieren = true

                }
                8 -> {
                    weisserWerwolfOpferID = itemID
                    CharakterpositionErstInkementieren = true

                }
                10 -> {
                    hexeOpferID = itemID
                    CharakterpositionErstInkementieren = true

                }
                12 -> {
                    buergerOpfer = itemID
                    CharakterpositionErstInkementieren = true
                }
                20 -> {
                    jaegerOpfer = itemID

                }
                21 -> {
                    ritterOpfer = itemID
                    nameRitterOpfer = name
                    charakterRitterOpfer = charakter
                }
            }
        }
    }

    private fun sicherToeten(ID: Int, nameOpfer: String?, charakterOpfer: String?) {
        val jaegerGefunden = false
        var charaktername = ""
        var charakterID = -1
        if ((ID == liebenderEinsID || ID == liebenderZweiID) && !liebespaarEntdeckt) //Das ganze auch für sich nicht liebende
        {
            if (hexeOpferID == liebenderEinsID || hexeOpferID == liebenderZweiID) {
                hexeOpferID = -1
            }
            //todo: muss hier auch opfer 2 beachtet werden???
            var nameGefunden = false
            data!!.moveToFirst()
            if (data!!.getInt(0) == liebenderZweiID) {
                nameGefunden = true
                tot = """
                    $tot
                    ${data!!.getString(1)}
                    """.trimIndent()
                charaktername = data!!.getString(2)
                anzahlMindern(charaktername, data!!.getInt(0))
            }
            while (data!!.moveToNext() && !nameGefunden) {
                if (data!!.getInt(0) == liebenderZweiID) {
                    nameGefunden = true
                    tot = """
                        $tot
                        ${data!!.getString(1)}
                        """.trimIndent()
                    charaktername = data!!.getString(2)
                    anzahlMindern(charaktername, data!!.getInt(0))
                }
            }
            if (nameGefunden) {
                nameGefunden = false
                data!!.moveToFirst()
                if (data!!.getInt(0) == liebenderEinsID) {
                    nameGefunden = true
                    charaktername = data!!.getString(2)
                    tot = """
                        $tot
                        ${data!!.getString(1)}
                        """.trimIndent()
                    anzahlMindern(charaktername, data!!.getInt(0))
                }
                while (data!!.moveToNext() && !nameGefunden) {
                    if (data!!.getInt(0) == liebenderEinsID) {
                        nameGefunden = true
                        charaktername = data!!.getString(2)
                        tot = """
                            $tot
                            ${data!!.getString(1)}
                            """.trimIndent()
                        anzahlMindern(charaktername, data!!.getInt(0))
                    }
                }
                if (nameGefunden) {
                    liebespaarEntdeckt = true
                    if (vorbildID == liebenderEinsID || vorbildID == liebenderZweiID) {
                        vorbildGestorbenDialog()
                    }
                    mDatabaseHelper!!.deleteName("" + liebenderEinsID)
                }
                mDatabaseHelper!!.deleteName("" + liebenderZweiID)
            }
        } else  //keiner des Liebespaares getötet
        {
            //Attribute des zu tötenden ermitteln
            var nameGefunden = false
            data!!.moveToFirst()
            if (data!!.getInt(0) == ID) {
                nameGefunden = true
                charaktername = data!!.getString(2)
                tot = """
                    $tot
                    ${data!!.getString(1)}
                    """.trimIndent()
                charakterID = data!!.getInt(0)
            }
            while (data!!.moveToNext() && !nameGefunden) {
                if (data!!.getInt(0) == ID) {
                    nameGefunden = true
                    charaktername = data!!.getString(2)
                    tot = """
                        $tot
                        ${data!!.getString(1)}
                        """.trimIndent()
                    charakterID = data!!.getInt(0)
                }
            }
            if (nameGefunden) {
                anzahlMindern(charaktername, charakterID)

                //sollte das Opfer das Vorbild es Werwolfjungen sein; Dialog entsprechend anpassen
                if (vorbildID == ID) {
                    vorbildGestorbenDialog()
                }
                mDatabaseHelper!!.deleteName("" + ID)
            }
        }
        data = mDatabaseHelper!!.data
    }

    private fun anzahlMindern(charaktername: String, charakterID: Int) {
        if (charakterID == werwolfDurchUrwolfID) {
            anzahlWerwolf--
        }
        when (charaktername) {
            "werwolf" -> if (anzahlWerwolf > 0) {
                anzahlWerwolf--
                //Toast.makeText(Nacht_Liste.this, "mindern anzahlWerwolf:" + anzahlWerwolf, Toast.LENGTH_SHORT).show();
            }
            "buerger" -> if (anzahlBuerger > 0) {
                anzahlBuerger--
                //Toast.makeText(Nacht_Liste.this, "mindern anzahlBuerger:" + anzahlBuerger, Toast.LENGTH_SHORT).show();
            }
            "seher" -> if (anzahlSeher > 0) {
                anzahlSeher--
            }
            "hexe" -> if (anzahlHexe > 0) {
                anzahlHexe--
                //Toast.makeText(Nacht_Liste.this, "mindern anzahlHexe:" + anzahlHexe, Toast.LENGTH_SHORT).show();
            }
            "floetenspieler" -> if (anzahlFloetenspieler > 0) {
                anzahlFloetenspieler--
            }
            "freunde" -> if (anzahlFreunde > 0) {
                anzahlFreunde--
            }
            "amor" -> if (anzahlAmor > 0) {
                anzahlAmor--
            }
            "urwolf" -> if (anzahlUrwolf > 0) {
                anzahlUrwolf--
            }
            "weisserwerwolf" -> if (anzahlWeisserWerwolf > 0) {
                anzahlWeisserWerwolf--
            }
            "waechter" -> if (anzahlWaechter > 0) {
                anzahlWaechter--
            }
            "junges" -> if (anzahlJunges > 0) {
                anzahlJunges--
            }
            "jaeger" -> if (anzahlJaeger > 0) {
                Log.d(ContentValues.TAG, "jager inkrementiert")
                jaegerDialog()
                anzahlJaeger--
            }
            "maedchen" -> if (anzahlMaedchen > 0) {
                anzahlMaedchen--
            }
            "dieb" -> if (anzahlDieb > 0) {
                anzahlDieb--
            }
            "ritter" -> if (anzahlRitter > 0) {
                anzahlRitter--
                ritterDialog()
                ritterletzteRundeGetoetet = true
            }
        }
    }

    private fun ritterDialog() {
        ritterAktiv = true
    }

    private fun weisserWerwolfAuswerten() {
        var opferZweiID = -1
        if (weisserWerwolfOpferID != -1) //Werwolf existiert
        {
            //Werte des Opfers ermitteln:
            data!!.moveToFirst() //Daten des Werwolfopfers werden ermittelt
            var werwolfopferGefunden = false
            var werwolfOpferName: String? = ""
            var werwolfOpferVerzaubert: String? = ""
            var werwolfOpferCharakter = ""
            if (data!!.getInt(0) == werwolfOpferID) {
                werwolfopferGefunden = true
                werwolfOpferName = data!!.getString(1)
                werwolfOpferCharakter = data!!.getString(2)
                werwolfOpferVerzaubert = data!!.getString(4)
            }
            while (data!!.moveToNext() && !werwolfopferGefunden) {
                if (data!!.getInt(0) == werwolfOpferID) {
                    werwolfopferGefunden = true
                    werwolfOpferName = data!!.getString(1)
                    werwolfOpferCharakter = data!!.getString(2)
                    werwolfOpferVerzaubert = data!!.getString(4)
                }
            }
            var waechterID = -1
            var diebID = -1
            var waechterGefunden = false
            var diebGefunden = false


            //Werte wächter bestimmen
            if (anzahlWaechter > 0) {
                data!!.moveToFirst()
                if (data!!.getString(2).compareTo("waechter") == 0) {
                    waechterGefunden = true
                    waechterID = data!!.getInt(0)
                }
                while (data!!.moveToNext() && !waechterGefunden) {
                    if (data!!.getString(2).compareTo("waechter") == 0) {
                        waechterGefunden = true
                        waechterID = data!!.getInt(0)
                    }
                }
            }

            //werte Dieb bestimmen
            if (anzahlDieb > 0) {
                data!!.moveToFirst()
                if (data!!.getString(2).compareTo("dieb") == 0) {
                    diebGefunden = true
                    diebID = data!!.getInt(0)
                }
                while (data!!.moveToNext() && !diebGefunden) {
                    if (data!!.getString(2).compareTo("dieb") == 0) {
                        diebGefunden = true
                        diebID = data!!.getInt(0)
                    }
                }
            }

            //wächter ist das Ofer
            if (werwolfOpferCharakter.compareTo("waechter") == 0) {
                if (schlafplatzWaechterID != waechterID) //hier muss noch geprüft werden ob der dieb beim wächter schläft
                {
                    if (waechterID != schlafplatzDiebID) {
                        weisserWerwolfOpferID = -1
                        Log.d(ContentValues.TAG, "Werwolf tötet wächter, aber niemand daheim")
                    } else {
                        werwolfOpferID = diebID
                        Log.d(ContentValues.TAG, "dieb bei wächter wächter aber nicht daheim")
                    }
                }
            }

            //wächter rettet das Opfer
            if (weisserWerwolfOpferID != -1) {
                if (schlafplatzWaechterID == werwolfOpferID) {
                    weisserWerwolfOpferID = -1
                    Log.d(ContentValues.TAG, "Wächter hat das Opfer gerettet")
                }
            }


            //Hat der Dieb was mit dem Opfer zu tun?
            if (weisserWerwolfOpferID != -1 && anzahlDieb > 0) {
                if (diebID != schlafplatzDiebID) //Dieb ist nicht daheim -> gilt als nicht normaler bürger
                {
                    if (schlafplatzDiebID == weisserWerwolfOpferID) {  //dieb ist beim Opfer -> muss auch sterben
                        opferZweiID = diebID
                    }
                    if (anzahlWaechter > 0) {
                        if (schlafplatzDiebID == waechterID) {
                            weisserWerwolfOpferID = diebID
                        }
                    }
                    if (diebID == weisserWerwolfOpferID) {
                        weisserWerwolfOpferID = -1
                    }
                }
            }

            //Attribute des Finalen Opfers bestimmen, dann töten
            if (weisserWerwolfOpferID != -1) {
                data!!.moveToFirst() //Die werte müssen neu bestimmt werden, da der Dieb jetzt zum Opfergeworden sein kann.
                werwolfopferGefunden = false
                if (data!!.getInt(0) == weisserWerwolfOpferID) {
                    werwolfopferGefunden = true
                    werwolfOpferName = data!!.getString(1)
                    werwolfOpferCharakter = data!!.getString(2)
                }
                while (data!!.moveToNext() && !werwolfopferGefunden) {
                    if (data!!.getInt(0) == weisserWerwolfOpferID) {
                        werwolfopferGefunden = true
                        werwolfOpferName = data!!.getString(1)
                        werwolfOpferCharakter = data!!.getString(2)
                    }
                }
                if (weisserWerwolfOpferID == hexeOpferID) {
                    hexeOpferID = -1
                }
                sicherToeten(weisserWerwolfOpferID, werwolfOpferName, werwolfOpferCharakter)
            }

            //Opfer 2 wird ermittelt und getötet
            if (opferZweiID != -1) {
                data!!.moveToFirst()
                werwolfopferGefunden = false
                if (data!!.getInt(0) == opferZweiID) {
                    werwolfopferGefunden = true
                    werwolfOpferName = data!!.getString(1)
                    werwolfOpferCharakter = data!!.getString(2)
                }
                while (data!!.moveToNext() && !werwolfopferGefunden) {
                    if (data!!.getInt(0) == opferZweiID) {
                        werwolfopferGefunden = true
                        werwolfOpferName = data!!.getString(1)
                        werwolfOpferCharakter = data!!.getString(2)
                    }
                }
                if (opferZweiID == hexeOpferID) {
                    hexeOpferID = -1
                }
                sicherToeten(opferZweiID, werwolfOpferName, werwolfOpferCharakter)
            }
        }
    }

    private fun auswerten() {
        data = mDatabaseHelper!!.data
        data?.moveToFirst()

        //Liebespar lebt; nur noch drei persoenn übrig
        var verliebtGefundenAnzahl = 1
        while (data?.moveToNext() == true) {
            verliebtGefundenAnzahl++
        }
        if (liebenderEinsID != -1) {
            if (verliebtGefundenAnzahl <= 3 && !liebespaarEntdeckt) {
                siegbildschirmOeffnen("liebespaar")
            }
        }
        var anzahlNichtWerwolf =
            anzahlRitter + anzahlDieb + anzahlMaedchen + anzahlJaeger + anzahlWaechter + anzahlFreunde + anzahlHexe + anzahlSeher + anzahlBuerger + anzahlFloetenspieler + anzahlAmor + anzahlJunges
        if (werwolfDurchUrwolfID != -1) {
            anzahlNichtWerwolf--
        }
        if (anzahlNichtWerwolf < anzahlWeisserWerwolf + anzahlUrwolf + anzahlWerwolf) {
            if (anzahlUrwolf + anzahlWerwolf > 0) {
                siegbildschirmOeffnen("werwoelfe")
            } else {
                siegbildschirmOeffnen("ww")
            }
        }
        val anzahltest = anzahlWeisserWerwolf + anzahlUrwolf + anzahlWerwolf
        //keine Werwölfe mehr da
        if (anzahlWeisserWerwolf + anzahlUrwolf + anzahlWerwolf == 0) {
            siegbildschirmOeffnen("buerger")
        }
        data?.moveToFirst()
        var nichtverzaubertGefunden = false
        if (data?.getString(4)?.compareTo("ja") != 0) {
            nichtverzaubertGefunden = true
        }
        while (data?.moveToNext() == true && !nichtverzaubertGefunden) {
            if (data?.getString(4)?.compareTo("ja") != 0) {
                nichtverzaubertGefunden = true
            }
        }
        if (!nichtverzaubertGefunden) {
            siegbildschirmOeffnen("floetenspieler")
        }
    }


    private fun jaegerToeten() {
        binding.personen.visibility = View.GONE
        jaegerAktiv = false
        var jaegerOpferGefunden = false
        var jaegerOpferName: String? = ""
        var jaegerOpferCharakter: String? = ""
        if (data!!.getInt(0) == jaegerOpfer) {
            jaegerOpferGefunden = true
            jaegerOpferName = data!!.getString(1)
            jaegerOpferCharakter = data!!.getString(2)
        }
        while (data!!.moveToNext() && !jaegerOpferGefunden) {
            if (data!!.getInt(0) == hexeOpferID) {
                jaegerOpferGefunden = true
                jaegerOpferName = data!!.getString(1)
                jaegerOpferCharakter = data!!.getString(2)
            }
        }
        sicherToeten(jaegerOpfer, jaegerOpferName, jaegerOpferCharakter)
        charakterPosition = charakterPositionJaegerBackup
        // charakterPosition++;
        charakterPositionBestimmen()
    }

    private fun buergeropferToeten() {
        var charaktername: String? = ""
        var persname: String? = ""
        var persGefunden = false
        data = mDatabaseHelper!!.data
        data?.moveToFirst()
        if (data?.getInt(0) == buergerOpfer) {
            persGefunden = true
            charaktername = data?.getString(2)
            persname = data?.getString(1)
        }
        while (data?.moveToNext() == true && !persGefunden) {
            if (data?.getInt(0) == buergerOpfer) {
                persGefunden = true
                charaktername = data?.getString(2)
                persname = data?.getString(1)
            }
        }
        sicherToeten(buergerOpfer, persname, charaktername)
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
        val s = binding.description.text.toString()
        binding.description.text =
            """
            ${s}Das Vorbild ist verstorben. Ab der nächsten Nacht wacht das Junge mit den Werwölfen gemeinsam auf.
            
            
            """.trimIndent()
        var jungesGefunden = false
        var jungesID = -1
        var jungesName: String? = ""
        var jungesVerzaubert: String? = ""
        anzahlWerwolf++
        anzahlJunges--

        //junges als Eintrag finden und durch einen werwolf ersetzten
        data!!.moveToFirst()
        if (data!!.getString(2).compareTo("junges") == 0) {
            jungesGefunden = true
            jungesID = data!!.getInt(0)
            jungesName = data!!.getString(1)
            jungesVerzaubert = data!!.getString(4)
            mDatabaseHelper!!.deleteName("" + jungesID)
            mDatabaseHelper!!.addjungesExtra(jungesName, jungesVerzaubert)
        }
        while (data!!.moveToNext() && !jungesGefunden) {
            if (data!!.getString(2).compareTo("junges") == 0) {
                jungesGefunden = true
                jungesID = data!!.getInt(0)
                jungesName = data!!.getString(1)
                jungesVerzaubert = data!!.getString(4)
                mDatabaseHelper!!.deleteName("" + jungesID)
                mDatabaseHelper!!.addjungesExtra(jungesName, jungesVerzaubert)
            }
        }

        //Wenn jungesID gleich mit einer der liebenden ID, so muss diese die neue ID des Elementes erhalten Wird anhand des Namen ermittelt
        if (liebenderEinsID == jungesID) {
            data!!.moveToFirst()
            var vorbildGefunden = false
            if (data!!.getString(1).compareTo(jungesName!!) == 0) {
                vorbildGefunden = true
                liebenderEinsID = data!!.getInt(0)
            }
            while (data!!.moveToNext() && !vorbildGefunden) {
                if (data!!.getString(1).compareTo(jungesName) == 0) {
                    vorbildGefunden = true
                    liebenderEinsID = data!!.getInt(0)
                }
            }
        }
        if (liebenderZweiID == jungesID) {
            data!!.moveToFirst()
            var vorbildGefunden = false
            if (data!!.getString(1).compareTo(jungesName!!) == 0) {
                vorbildGefunden = true
                liebenderZweiID = data!!.getInt(0)
            }
            while (data!!.moveToNext() && !vorbildGefunden) {
                if (data!!.getString(1).compareTo(jungesName) == 0) {
                    vorbildGefunden = true
                    liebenderZweiID = data!!.getInt(0)
                }
            }
        }
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
            charakterPosition++
        }
        return hexeToetenGedrueckt
    }

    private fun nachtAuswerten() {
        var opferZweiID = -1
        var werwolfopferGefunden = false
        var werwolfOpferName: String? = ""
        var werwolfOpferVerzaubert: String? = ""
        var werwolfOpferCharakter = ""
        if (werwolfOpferID != -1) //Werwolf existiert
        {
            //Werte des Opfers ermitteln:
            data!!.moveToFirst() //Daten des Werwolfopfers werden ermittelt
            if (data!!.getInt(0) == werwolfOpferID) {
                werwolfopferGefunden = true
                werwolfOpferName = data!!.getString(1)
                werwolfOpferCharakter = data!!.getString(2)
                werwolfOpferVerzaubert = data!!.getString(4)
            }
            while (data!!.moveToNext() && !werwolfopferGefunden) {
                if (data!!.getInt(0) == werwolfOpferID) {
                    werwolfopferGefunden = true
                    werwolfOpferName = data!!.getString(1)
                    werwolfOpferCharakter = data!!.getString(2)
                    werwolfOpferVerzaubert = data!!.getString(4)
                }
            }
            if (urwolfVeto == 1) //Von Urwolf gerettet -> verwandelt sich in einen Werwolf
            {
                werwolfDurchUrwolfID = werwolfOpferID
                urwolfVeto = -1
                anzahlWerwolf++
                werwolfOpferID = -1
            } else {            //Auswertung aller Opfer
                var waechterID = -1
                var diebID = -1
                var waechterGefunden = false
                var diebGefunden = false


                //Werte wächter bestimmen
                if (anzahlWaechter > 0) {
                    data!!.moveToFirst()
                    if (data!!.getString(2).compareTo("waechter") == 0) {
                        waechterGefunden = true
                        waechterID = data!!.getInt(0)
                    }
                    while (data!!.moveToNext() && !waechterGefunden) {
                        if (data!!.getString(2).compareTo("waechter") == 0) {
                            waechterGefunden = true
                            waechterID = data!!.getInt(0)
                        }
                    }
                }

                //werte Dieb bestimmen
                if (anzahlDieb > 0) //todo auswetung das Diebes kann aussetzen, wenn der Wächter das eigentliche Werwolfopfer beschützt, sodass dieses überlebt.
                {
                    data!!.moveToFirst()
                    if (data!!.getString(2).compareTo("dieb") == 0) {
                        diebGefunden = true
                        diebID = data!!.getInt(0)
                    }
                    while (data!!.moveToNext() && !diebGefunden) {
                        if (data!!.getString(2).compareTo("dieb") == 0) {
                            diebGefunden = true
                            diebID = data!!.getInt(0)
                        }
                    }
                }

                //wächter ist das Ofer
                if (werwolfOpferCharakter.compareTo("waechter") == 0) {
                    if (schlafplatzWaechterID != waechterID) //hier muss noch geprüft werden ob der dieb beim wächter schläft
                    {
                        if (waechterID != schlafplatzDiebID) {
                            werwolfOpferID = -1
                            Log.d(ContentValues.TAG, "Werwolf tötet wächter, aber niemand daheim")
                        } else {
                            werwolfOpferID = diebID
                            Log.d(ContentValues.TAG, "dieb bei wächter wächter aber nicht daheim")
                        }
                    }
                }

                //wächter rettet das Opfer
                if (werwolfOpferID != -1) {
                    if (schlafplatzWaechterID == werwolfOpferID) {
                        werwolfOpferID = waechterID
                        werwolfOpferID = -1
                        Log.d(ContentValues.TAG, "Wächter hat das Opfer gerettet")
                    }
                }


                //Hat der Dieb was mit dem Opfer zu tun?
                if (werwolfOpferID != -1 && anzahlDieb > 0) {
                    if (diebID != schlafplatzDiebID) //Dieb ist nicht daheim -> gilt als nicht normaler bürger
                    {
                        if (schlafplatzDiebID == werwolfOpferID) {  //dieb ist beim Opfer -> muss auch sterben
                            opferZweiID = diebID
                        }
                        if (anzahlWaechter > 0) {
                            if (schlafplatzDiebID == waechterID) {
                                werwolfOpferID = diebID
                            }
                        }
                        if (diebID == werwolfOpferID) {
                            werwolfOpferID = -1
                        }
                    }
                }

                //Attribute des Finalen Opfers bestimmen, dann töten
                if (werwolfOpferID != -1) {
                    data!!.moveToFirst() //Die werte müssen neu bestimmt werden, da der Dieb jetzt zum Opfergeworden sein kann.
                    werwolfopferGefunden = false
                    if (data!!.getInt(0) == werwolfOpferID) {
                        werwolfopferGefunden = true
                        werwolfOpferName = data!!.getString(1)
                        werwolfOpferCharakter = data!!.getString(2)
                    }
                    while (data!!.moveToNext() && !werwolfopferGefunden) {
                        if (data!!.getInt(0) == werwolfOpferID) {
                            werwolfopferGefunden = true
                            werwolfOpferName = data!!.getString(1)
                            werwolfOpferCharakter = data!!.getString(2)
                        }
                    }
                    if (werwolfOpferID == hexeOpferID) {
                        hexeOpferID = -1
                    }
                    sicherToeten(werwolfOpferID, werwolfOpferName, werwolfOpferCharakter)
                }

                //Opfer 2 wird ermittelt und getötet
                if (opferZweiID != -1) {
                    data!!.moveToFirst()
                    werwolfopferGefunden = false
                    if (data!!.getInt(0) == opferZweiID) {
                        werwolfopferGefunden = true
                        werwolfOpferName = data!!.getString(1)
                        werwolfOpferCharakter = data!!.getString(2)
                    }
                    while (data!!.moveToNext() && !werwolfopferGefunden) {
                        if (data!!.getInt(0) == opferZweiID) {
                            werwolfopferGefunden = true
                            werwolfOpferName = data!!.getString(1)
                            werwolfOpferCharakter = data!!.getString(2)
                        }
                    }
                    if (opferZweiID == hexeOpferID) {
                        hexeOpferID = -1
                    }
                    sicherToeten(opferZweiID, werwolfOpferName, werwolfOpferCharakter)
                }

                //Schlafplätze spielen dabei keien Rolle mehr sollte es ein hexen opfer geben wird es hier getötet
            }
        }
        if (hexeOpferID != -1) {
            data!!.moveToFirst()
            werwolfopferGefunden = false
            if (data!!.getInt(0) == hexeOpferID) {
                werwolfopferGefunden = true
                werwolfOpferName = data!!.getString(1)
                werwolfOpferCharakter = data!!.getString(2)
            }
            while (data!!.moveToNext() && !werwolfopferGefunden) {
                if (data!!.getInt(0) == hexeOpferID) {
                    werwolfopferGefunden = true
                    werwolfOpferName = data!!.getString(1)
                    werwolfOpferCharakter = data!!.getString(2)
                }
            }
            sicherToeten(hexeOpferID, werwolfOpferName, werwolfOpferCharakter)
        }
    }


    fun toggle descriptionLength(context: Context): String
    {

    }

    private var showLongTexts = true
    fun getDescToCharacter(characterClass: CharacterClass, context: Context): String {
        var desc = context.getString(characterClass.descStringId)
        if (!showLongTexts) {
            desc = desc.substring(0, 30) + "..."
        }
        return desc
    }

    fun generateCharacters(): ArrayList<CharacterClass> {       // todo load from db

        if (characterClasses == null) {
            characterClasses = arrayListOf(
                CharacterClass(
                    "Werwolf",
                    "blabla",
                    R.color.werwolf,
                    true,
                    true,
                    R.string.night_desc_werwolf
                ),
                CharacterClass(
                    "Bürger",
                    "blabla",
                    R.color.citizen,
                    true,
                    false,
                    R.string.night_desc_citizen
                ),
                CharacterClass(
                    "Amor",
                    "blabla",
                    R.color.amor,
                    false,
                    false,
                    R.string.night_desc_armor
                ),
                CharacterClass(
                    "Hexe",
                    "blabla",
                    R.color.witch,
                    false,
                    false,
                    R.string.night_desc_witch
                ),
                CharacterClass(
                    "Wächter",
                    "blabla",
                    R.color.guradian,
                    false,
                    false,
                    R.string.night_desc_guardian
                ),
                CharacterClass(
                    "Mädchen",
                    "blabla",
                    R.color.girl,
                    false,
                    false,
                    R.string.night_desc_girl
                ),
                CharacterClass(
                    "Seher",
                    "blabla",
                    R.color.seher,
                    false,
                    false,
                    R.string.night_desc_seher
                ),
                CharacterClass(
                    "Dieb",
                    "blabla",
                    R.color.thief,
                    false,
                    false,
                    R.string.night_desc_thief
                ),
                CharacterClass(
                    "Jäger",
                    "blabla",
                    R.color.hunter,
                    false,
                    false,
                    R.string.night_desc_hunter
                ),
                CharacterClass(
                    "Ritter",
                    "blabla",
                    R.color.knight,
                    false,
                    false,
                    R.string.night_desc_knight
                ),
                CharacterClass(
                    "Flötenspieler",
                    "blabla",
                    R.color.flute,
                    false,
                    false,
                    R.string.night_desc_flute
                ),
                CharacterClass(
                    "Freunde",
                    "blabla",
                    R.color.friends,
                    true,
                    false,
                    R.string.night_desc_friends
                ),
                CharacterClass(
                    "Weißer Werwolf",
                    "blabla",
                    R.color.wwolf,
                    false,
                    true,
                    R.string.night_desc_wolf_child
                ),
                CharacterClass(
                    "Junges",
                    "blabla",
                    R.color.wchild,
                    false,
                    true,
                    R.string.night_desc_wolf_child
                ),
                CharacterClass(
                    "Urwolf",
                    "blabla",
                    R.color.urwolf,
                    false,
                    true,
                    R.string.night_desc_wolf_child
                )
            )
        }
        return characterClasses
    }

    init {
        werwolfDurchUrwolfID = -1

        //Jaeger
        jaegerAktiv = false

        //Ritter
        ritterAktiv = false
        ritterOpfer = -1
        nameRitterOpfer = ""
        charakterRitterOpfer = ""

        urwolfVeto = if (anzahlUrwolf > 0) 0 else -1
        generateCharacters()


    }
}