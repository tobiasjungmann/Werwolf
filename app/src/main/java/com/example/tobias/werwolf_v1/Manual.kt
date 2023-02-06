package com.example.tobias.werwolf_v1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import maes.tech.intentanim.CustomIntent

class Manual : AppCompatActivity(), View.OnClickListener {
    private var manualScrollView: NestedScrollView? = null
    private var characterHeadline: TextView? = null
    private var withoutCardsHeadline: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_manual)
        val backButton = findViewById<Button>(R.id.backmanual)
        backButton.setOnClickListener(this@Manual)
        manualScrollView = findViewById(R.id.manualScrollView)
        characterHeadline = findViewById(R.id.characterHeadline)
        withoutCardsHeadline = findViewById(R.id.withoutCardsHeadline)
        val characterText = findViewById<TextView>(R.id.characterText)
        val withoutCardsText = findViewById<TextView>(R.id.withoutCardsText)
        val withCardsText = findViewById<TextView>(R.id.withCardsText)
        characterText.setOnClickListener(this@Manual)
        withoutCardsText.setOnClickListener(this@Manual)
        withCardsText.setOnClickListener(this@Manual)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.backmanual) {
            val intent = Intent(this@Manual, StartScreenActivity::class.java)
            startActivity(intent)
            CustomIntent.customType(this, "up-to-bottom")
        } else if (v.id == R.id.characterText) {
            manualScrollView!!.smoothScrollTo(0, characterHeadline!!.top)
        } else if (v.id == R.id.withoutCardsText) {
            manualScrollView!!.smoothScrollTo(0, withoutCardsHeadline!!.top)
        } else if (v.id == R.id.withCardsText) {
            manualScrollView!!.smoothScrollTo(0, withoutCardsHeadline!!.top)
        }
    }

    override fun finish() {
        super.finish()
        CustomIntent.customType(this, "up-to-bottom")
    }
}