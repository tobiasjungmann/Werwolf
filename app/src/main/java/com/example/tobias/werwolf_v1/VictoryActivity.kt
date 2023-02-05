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

class VictoryActivity : AppCompatActivity(), View.OnClickListener {
    private var twoTimesBackPressed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_victory)
        val winner = intent.extras!!.getString("sieger")
        val sartScreenButton = findViewById<Button>(R.id.victoryStartScreenButton)
        sartScreenButton.setOnClickListener(this@VictoryActivity)
        val winnerText = findViewById<TextView>(R.id.siegerText)
        when (winner) {
            "floetenspieler" -> winnerText.setText(R.string.VictoryFlute)
            "buerger" -> winnerText.setText(R.string.VictoryCiticens)
            "werwoelfe" -> winnerText.setText(R.string.VictoryWolfes)
            "liebespaar" -> winnerText.setText(R.string.VictoryLovers)
            "ww" -> winnerText.setText(R.string.victoryWhiteWolf)
            else -> winnerText.setText(R.string.victoryError)
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
        val intent = Intent(this, StartScreen::class.java)
        startActivity(intent)
    }
}