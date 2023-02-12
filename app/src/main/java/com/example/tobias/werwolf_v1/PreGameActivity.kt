package com.example.tobias.werwolf_v1


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import maes.tech.intentanim.CustomIntent


class PreGameActivity: AppCompatActivity(R.layout.activity_pre_game) {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            if (savedInstanceState == null) {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<CharacterSelectionFragment>(R.id.fragment_container_view_pregame)
                }
            }
        }
    override fun finish() {
        super.finish()
        CustomIntent.customType(this, "right-to-left")
    }
}