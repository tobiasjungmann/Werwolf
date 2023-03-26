package com.example.tobias.werwolf_v1

import android.view.View
import androidx.core.content.ContextCompat
import com.example.tobias.werwolf_v1.database.models.CharacterClass

interface NightListContract {
    // todo prevent the leakage
    interface Presenter {
        fun attachView(view: View)
    }

    interface View {
        fun updateUIForCharacter(currentCharacter: CharacterClass, nextActivated: Boolean, listVisible: Boolean)
        fun setDescription(text: String,append: Boolean)
        fun siegbildschirmOeffnen(character: String)
        fun activateWitchDialog(trankLebenEinsetzbar: Boolean, trankTodEinsetzbar: Boolean)
        fun setPlayerListVisibility(visibility: Int)
        fun setUrwolfVisibility(visibility: Int)
        fun deactivateHunter()

    }
}