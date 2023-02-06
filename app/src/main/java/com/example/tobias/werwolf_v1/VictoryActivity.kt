package com.example.tobias.werwolf_v1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tobias.werwolf_v1.databinding.ActivityStartscreenBinding
import com.example.tobias.werwolf_v1.databinding.ActivityVictoryBinding

class VictoryActivity : AppCompatActivity(), View.OnClickListener {
    private var twoTimesBackPressed = false
    private lateinit var binding: ActivityVictoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVictoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.victoryStartScreenButton.setOnClickListener(this@VictoryActivity)

        val winner = intent.extras!!.getString("sieger")
        when (winner) {
            "floetenspieler" -> binding.textViewWinner.setText(R.string.VictoryFlute)
            "buerger" -> binding.textViewWinner.setText(R.string.VictoryCiticens)
            "werwoelfe" -> binding.textViewWinner.setText(R.string.VictoryWolfes)
            "liebespaar" -> binding.textViewWinner.setText(R.string.VictoryLovers)
            "ww" -> binding.textViewWinner.setText(R.string.victoryWhiteWolf)
            else -> binding.textViewWinner.setText(R.string.victoryError)
        }
    }

    override fun onBackPressed() {
        if (twoTimesBackPressed) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
            System.exit(0)
        }
        Toast.makeText(this@VictoryActivity, R.string.pressBackAgain, Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ twoTimesBackPressed = false }, 2000)
        twoTimesBackPressed = true
    }

    override fun onClick(v: View) {
        if (v.id == R.id.victoryStartScreenButton) openStartScreen()
    }

    private fun openStartScreen() {
        val intent = Intent(this, StartScreenActivity::class.java)
        startActivity(intent)
    }
}