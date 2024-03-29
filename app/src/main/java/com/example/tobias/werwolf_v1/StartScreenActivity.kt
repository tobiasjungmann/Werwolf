package com.example.tobias.werwolf_v1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tobias.werwolf_v1.databinding.ActivityStartscreenBinding
import com.example.tobias.werwolf_v1.multipleDevices.PlayerConnectToHost
import com.example.tobias.werwolf_v1.pregame.PreGameActivity
import maes.tech.intentanim.CustomIntent

class StartScreenActivity : AppCompatActivity(), View.OnClickListener {
    private var backPressedTwice = false
    private lateinit var binding: ActivityStartscreenBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spieler.setOnClickListener(this)
        binding.handbuchKnopf.setOnClickListener(this)
        binding.spielleiterEinzeln.setOnClickListener(this)
        binding.spielleiterServer.setOnClickListener(this)
    }

    override fun onBackPressed() {
        if (backPressedTwice) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
            System.exit(0)
        }
        Toast.makeText(
            this@StartScreenActivity,
            "Um die App zu schließen Taste erneut drücken.",
            Toast.LENGTH_SHORT
        ).show()
        Handler().postDelayed({ backPressedTwice = false }, 2000)
        backPressedTwice = true
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.spielleiterEinzeln -> openCharacterSelection(true)
            R.id.spielleiterServer -> openCharacterSelection(false)
            R.id.spieler -> openPlayerSelection()
            R.id.handbuch_knopf -> openManual()
        }
    }

    private fun openPlayerSelection() {
        val intent = Intent(this@StartScreenActivity, PlayerConnectToHost::class.java)
        intent.putExtra("Karten", "nein")
        startActivity(intent)
        CustomIntent.customType(this, "left-to-right")
    }

    private fun openCharacterSelection(oneDevice: Boolean) {
        val intent = Intent(this, PreGameActivity::class.java)
        intent.putExtra("EinGeraet", oneDevice)
        startActivity(intent)
        CustomIntent.customType(this, "left-to-right")
    }

    private fun openManual() {
        val intent = Intent(this, ManualActivity::class.java)
        startActivity(intent)
        CustomIntent.customType(this, "bottom-to-up")
    }
}