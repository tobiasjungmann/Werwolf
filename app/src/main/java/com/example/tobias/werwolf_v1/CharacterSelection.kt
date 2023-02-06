package com.example.tobias.werwolf_v1

import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tobias.werwolf_v1.R.id
import com.example.tobias.werwolf_v1.databinding.ActivityCharakterselectionBinding
import com.example.tobias.werwolf_v1.multipleDevices.HostConnectWithPlayers
import maes.tech.intentanim.CustomIntent

class CharacterSelection : AppCompatActivity(), View.OnClickListener {
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
    private var gesamtTxt: TextView? = null
    private var einGeraet = false
    private var vibrator: Vibrator? = null
    private var vibrationsdauer = 8

    private lateinit var binding: ActivityCharakterselectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharakterselectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initHeader()

        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        binding.spielStarten.setOnClickListener(this@CharacterSelection)
        val adapter = CharacterListAdapter(generateCharacters())
        binding.recyclerviewCharacterSelection.adapter = adapter
        binding.recyclerviewCharacterSelection.layoutManager = LinearLayoutManager(this)
    }

    private fun initHeader() {
        einGeraet = intent.extras!!.getBoolean("EinGeraet", true)
        binding.textModus.setText(R.string.spielleiter)
        var color = R.color.orange
        if (einGeraet) {
            color = R.color.gruen
        }
        binding.layoutModus.background.setTint(
            ContextCompat.getColor(
                this,
                color
            )
        )
    }

    private fun generateCharacters(): ArrayList<Character> {
        return arrayListOf(Character("Werwolf", "blabla",R.color.grau))
    }

    override fun onClick(v: View) {
        if (v.id == id.spiel_starten) {
            next()
        }
    }

    private fun next() {
        if (anzahlWerwolf + anzahlWeisserWerwolf + anzahlUrwolf == 0 || anzahlWerwolf + anzahlWeisserWerwolf + anzahlUrwolf == gesamtPer) {
            Toast.makeText(this@CharacterSelection, "ungültige Eingabe", Toast.LENGTH_LONG).show()
            vibrator!!.vibrate(vibrationsdauer.toLong())
        } else {
            val intent: Intent
            intent = if (einGeraet) {
                Intent(this, PlayerManagement::class.java)
            } else {
                Intent(this, HostConnectWithPlayers::class.java)
            }
            intent.putExtra("anzahlAmor", anzahlAmor)
            intent.putExtra("anzahlBuerger", anzahlBuerger)
            intent.putExtra("anzahlWaechter", anzahlWaechter)
            intent.putExtra("anzahlDieb", anzahlDieb)
            intent.putExtra("anzahlHexe", anzahlHexe)
            intent.putExtra("anzahlJaeger", anzahlJaeger)
            intent.putExtra("anzahlJunges", anzahlJunges)
            intent.putExtra("anzahlSeher", anzahlSeher)
            intent.putExtra("anzahlWerwolf", anzahlWerwolf)
            intent.putExtra("anzahlWeisserWerwolf", anzahlWeisserWerwolf)
            intent.putExtra("anzahlRitter", anzahlRitter)
            intent.putExtra("anzahlFloetenspieler", anzahlFloetenspieler)
            intent.putExtra("anzahlFreunde", anzahlFreunde)
            intent.putExtra("anzahlMaedchen", anzahlMaedchen)
            intent.putExtra("anzahlUrwolf", anzahlUrwolf)
            intent.putExtra("gesamtPer", gesamtPer)
            intent.putExtra("EinGeraet", einGeraet)
            startActivity(intent)
            CustomIntent.customType(this, "left-to-right")
        }
    }

    override fun finish() {
        super.finish()
        CustomIntent.customType(this, "right-to-left")
    }
}