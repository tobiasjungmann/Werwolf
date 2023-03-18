package com.example.tobias.werwolf_v1

import android.app.Application
import android.database.Cursor
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.example.tobias.werwolf_v1.database.models.DatabaseHelper

class NightListViewModel(application: Application) : AndroidViewModel(application)  {
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
    private var verzaubertCharakter: String=""
    private var verzaubertName: String =""
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

    fun handleClickAtPosition(position: Int, nextButton: Button){
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

    private fun adaptNextButton(nextButton: Button, clickable: Boolean) {

    }

    init{
        werwolfDurchUrwolfID = -1

        //Jaeger
        jaegerAktiv = false

        //Ritter
        ritterAktiv = false
        ritterOpfer = -1
        nameRitterOpfer = ""
        charakterRitterOpfer = ""

        urwolfVeto = if (anzahlUrwolf > 0) 0 else -1
    }
}