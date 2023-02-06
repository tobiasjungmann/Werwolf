package com.example.tobias.werwolf_v1.multipleDevices

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tobias.werwolf_v1.R
import com.example.tobias.werwolf_v1.StartScreenActivity

class PlayerCard : AppCompatActivity(), OnTouchListener {
    private var charakterKarte: TextView? = null
    private var layoutKarteSpieler: SquareLayout? = null
    private var rolle: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_playercard)
        charakterKarte = findViewById(R.id.charakterKarte)
        layoutKarteSpieler = findViewById(R.id.layoutKarteSpieler)
        charakterKarte.setOnTouchListener(this)
        rolle = intent.extras!!.getString("rolle")
        rolle = if (rolle == null || rolle!!.compareTo("") == 0) {
            "Fehler: keine Rolle übermittelt"
        } else {
            when (rolle) {
                "werwolf" -> "Werwolf"
                "buerger" -> "Bürger"
                "amor" -> "Amor"
                "hexe" -> "Hexe"
                "waechter" -> "Wächter"
                "maedchen" -> "Mädchen"
                "seher" -> "Seher"
                "dieb" -> "Dieb"
                "jaeger" -> "Jäger"
                "freunde" -> "Freunde"
                "junges" -> "Junges"
                "urwolf" -> "Urwolf"
                "weisserwerwolf" -> "weißer Werwolf"
                "floetenspieler" -> "Flötenspieler"
                "ritter" -> "Ritter"
                else -> "Fehler: keine Rolle übermittelt"
            }
        }
    }

    //todo rolle von SpielerAuswahl übergeben
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (v.id == R.id.charakterKarte) {
            return if (event.action == MotionEvent.ACTION_UP) {
                charakterKarte!!.text = ""
                charakterKarte!!.background = resources.getDrawable(R.drawable.knopf_orange)
                super.onTouchEvent(event)
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                charakterKarte!!.text = rolle
                charakterKarte!!.background = resources.getDrawable(R.drawable.knopf_grau)
                super.onTouchEvent(event)
            } else {
                false
            }
        }
        return if (v.id == R.id.victoryStartScreenButton) {
            startbildschirmOeffnen()
            super.onTouchEvent(event)
        } else {
            false
        }
    }

    private fun startbildschirmOeffnen() {
        val intent = Intent(this, StartScreenActivity::class.java)
        startActivity(intent)
    }
}