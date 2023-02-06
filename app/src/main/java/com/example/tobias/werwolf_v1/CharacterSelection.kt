package com.example.tobias.werwolf_v1

import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tobias.werwolf_v1.R.id
import com.example.tobias.werwolf_v1.databinding.ActivityCharakterselectionBinding
import com.example.tobias.werwolf_v1.multipleDevices.HostConnectWithPlayers
import maes.tech.intentanim.CustomIntent

class CharacterSelection : AppCompatActivity(), View.OnClickListener {

    private var einGeraet = false
    private var vibrator: Vibrator? = null
    private var vibrationsdauer = 8

    private lateinit var binding: ActivityCharakterselectionBinding
    private lateinit var preGameViewModel: PreGameViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharakterselectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initHeader()
        preGameViewModel = ViewModelProvider(this)[PreGameViewModel::class.java]

        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        binding.spielStarten.setOnClickListener(this@CharacterSelection)
        val adapter = CharacterListAdapter(preGameViewModel.generateCharacters(),preGameViewModel)
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

    override fun onClick(v: View) {
        if (v.id == id.spiel_starten) {
            next()
        }
    }

    private fun next() {
        if(preGameViewModel.invalidConfiguration()){
            Toast.makeText(this@CharacterSelection, "ungültige Eingabe", Toast.LENGTH_LONG).show()
            vibrator!!.vibrate(vibrationsdauer.toLong())
        } else {
            // todo add as fragment -> all three stages with the same viewmodel
            val intent: Intent
            intent = if (einGeraet) {
                Intent(this, PlayerManagement::class.java)
            } else {
                Intent(this, HostConnectWithPlayers::class.java)
            }
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