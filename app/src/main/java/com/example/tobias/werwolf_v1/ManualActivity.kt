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
import com.example.tobias.werwolf_v1.databinding.ActivityManualBinding
import com.example.tobias.werwolf_v1.databinding.ActivityStartscreenBinding
import maes.tech.intentanim.CustomIntent

class ManualActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityManualBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManualBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backmanual.setOnClickListener(this@ManualActivity)
        binding.characterText.setOnClickListener(this@ManualActivity)
        binding.withoutCardsText.setOnClickListener(this@ManualActivity)
        binding.withCardsText.setOnClickListener(this@ManualActivity)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.backmanual) {
            val intent = Intent(this@ManualActivity, StartScreenActivity::class.java)
            startActivity(intent)
            CustomIntent.customType(this, "up-to-bottom")
        } else if (v.id == R.id.characterText) {
            binding.manualScrollView.smoothScrollTo(0, binding.characterHeadline.top)
        } else if (v.id == R.id.withoutCardsText) {
            binding.manualScrollView.smoothScrollTo(0, binding.withoutCardsHeadline.top)
        } else if (v.id == R.id.withCardsText) {
            binding.manualScrollView.smoothScrollTo(0, binding.withoutCardsHeadline.top)
        }
    }

    override fun finish() {
        super.finish()
        CustomIntent.customType(this, "up-to-bottom")
    }
}