package com.example.tobias.werwolf_v1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tobias.werwolf_v1.multipleDevices.PlayerConnectToHost
import maes.tech.intentanim.CustomIntent

class StartScreen : AppCompatActivity(), View.OnClickListener {
    private var zweiMalzurueck = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startscreen)
        val mitKarten = findViewById<Button>(R.id.spielleiterEinzeln)
        mitKarten.setOnClickListener(this@StartScreen)
        val ohneKarten = findViewById<Button>(R.id.spieler)
        ohneKarten.setOnClickListener(this@StartScreen)
        val handbuch_knopf = findViewById<Button>(R.id.handbuch_knopf)
        handbuch_knopf.setOnClickListener(this@StartScreen)
        val einzelspieler = findViewById<Button>(R.id.spielleiterServer)
        einzelspieler.setOnClickListener(this@StartScreen)
    }

    override fun onBackPressed() {
        if (zweiMalzurueck) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
            System.exit(0)
        }
        Toast.makeText(
            this@StartScreen,
            "Um die App zu schließen Taste erneut drücken.",
            Toast.LENGTH_SHORT
        ).show()
        Handler().postDelayed({ zweiMalzurueck = false }, 2000)
        zweiMalzurueck = true
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.spielleiterEinzeln -> charakterauswahlOeffnenEinGeraet()
            R.id.spielleiterServer -> charakterauswahlOeffnen()
            R.id.spieler -> spielerAuswahlOeffnen()
            R.id.handbuch_knopf -> handbuchOeffnen()
        }
    }

    private fun spielerAuswahlOeffnen() {
        val intent = Intent(this@StartScreen, PlayerConnectToHost::class.java)
        intent.putExtra("Karten", "nein")
        startActivity(intent)
        CustomIntent.customType(this, "left-to-right")
    }

    private fun charakterauswahlOeffnenEinGeraet() {
        val intent = Intent(this, CharacterSelection::class.java)
        intent.putExtra("EinGeraet", true)
        startActivity(intent)
        CustomIntent.customType(this, "left-to-right")
    }

    private fun charakterauswahlOeffnen() {
        val intent = Intent(this, CharacterSelection::class.java)
        intent.putExtra("EinGeraet", false)
        startActivity(intent)
        CustomIntent.customType(this, "left-to-right")
    }

    private fun handbuchOeffnen() {
        val intent = Intent(this, Manual::class.java)
        startActivity(intent)
        CustomIntent.customType(this, "bottom-to-up")
    }
}