package com.example.tobias.werwolf_v1

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tobias.werwolf_v1.R.id
import com.example.tobias.werwolf_v1.multipleDevices.HostConnectWithPlayers
import maes.tech.intentanim.CustomIntent

class CharacterSelection : AppCompatActivity(), View.OnClickListener {
    private var nummerAmor: TextView? = null
    private var nummerHexe: TextView? = null
    private var nummerWaechter: TextView? = null
    private var nummerDieb: TextView? = null
    private var nummerSeher: TextView? = null
    private var nummerBuerger: TextView? = null
    private var nummerWerwolf: TextView? = null
    private var nummerJunges: TextView? = null
    private var nummerJaeger: TextView? = null
    private var nummerWeisserWerwolf: TextView? = null
    private var nummerMaedchen: TextView? = null
    private var nummerFloetenspieler: TextView? = null
    private var nummerUrwolf: TextView? = null
    private var nummerRitter: TextView? = null
    private var nummerFreunde: TextView? = null
    private var infoTextAmor: TextView? = null
    private var infoTextHexe: TextView? = null
    private var infoTextWaechter: TextView? = null
    private var infoTextDieb: TextView? = null
    private var infoTextSeher: TextView? = null
    private var infoTextBuerger: TextView? = null
    private var infoTextWerwolf: TextView? = null
    private var infoTextJunges: TextView? = null
    private var infoTextJaeger: TextView? = null
    private var infoTextWeisserWerwolf: TextView? = null
    private var infoTextMaedchen: TextView? = null
    private var infoTextFloetenspieler: TextView? = null
    private var infoTextUrwolf: TextView? = null
    private var infoTextRitter: TextView? = null
    private var infoTextFreunde: TextView? = null
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
    private var vibrationsdauer = 0
    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_charakterselection)
        einGeraet = intent.extras!!.getBoolean("EinGeraet", true)
        anzahlAmor = 0
        anzahlBuerger = 0
        anzahlWaechter = 0
        anzahlDieb = 0
        anzahlHexe = 0
        anzahlJaeger = 0
        anzahlJunges = 0
        anzahlSeher = 0
        anzahlWerwolf = 0
        anzahlWeisserWerwolf = 0
        anzahlMaedchen = 0
        anzahlFloetenspieler = 0
        anzahlUrwolf = 0
        anzahlRitter = 0
        anzahlFreunde = 0
        val textModus = findViewById<TextView>(id.textModus)
        val layoutModus = findViewById<LinearLayout>(id.layoutModus)
        if (einGeraet) {
            layoutModus.setBackgroundResource(R.drawable.leiste_hellgruen)
        } else {
            layoutModus.setBackgroundResource(R.drawable.leiste_orange)
        }
        textModus.setText(R.string.spielleiter)
        vibrationsdauer = 8
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        gesamtPer = 0
        gesamtTxt = findViewById(id.gesamtTxt)
        gesamtTxt?.setTextColor(Color.WHITE)
        val spiel_starten = findViewById<Button>(id.spiel_starten)
        spiel_starten.setOnClickListener(this@CharacterSelection)
        val iconAmor = findViewById<TextView>(id.iconAmor)
        iconAmor.setOnClickListener(this@CharacterSelection)
        val iconHexe = findViewById<TextView>(id.iconHexe)
        iconHexe.setOnClickListener(this@CharacterSelection)
        val iconWaechter = findViewById<TextView>(id.iconWaechter)
        iconWaechter.setOnClickListener(this@CharacterSelection)
        val iconDieb = findViewById<TextView>(id.iconDieb)
        iconDieb.setOnClickListener(this@CharacterSelection)
        val iconSeher = findViewById<TextView>(id.iconSeher)
        iconSeher.setOnClickListener(this@CharacterSelection)
        val iconBuerger = findViewById<TextView>(id.iconBuerger)
        iconBuerger.setOnClickListener(this@CharacterSelection)
        val iconWerwolf = findViewById<TextView>(id.iconWerwolf)
        iconWerwolf.setOnClickListener(this@CharacterSelection)
        val iconJunges = findViewById<TextView>(id.iconJunges)
        iconJunges.setOnClickListener(this@CharacterSelection)
        val iconJaeger = findViewById<TextView>(id.iconJaeger)
        iconJaeger.setOnClickListener(this@CharacterSelection)
        val iconWeisserWerwolf = findViewById<TextView>(id.iconWeisserWerwolf)
        iconWeisserWerwolf.setOnClickListener(this@CharacterSelection)
        val iconMaedchen = findViewById<TextView>(id.iconMaedchen)
        iconMaedchen.setOnClickListener(this@CharacterSelection)
        val iconFloetenspieler = findViewById<TextView>(id.iconFloetenspieler)
        iconFloetenspieler.setOnClickListener(this@CharacterSelection)
        val iconUrwolf = findViewById<TextView>(id.iconUrwolf)
        iconUrwolf.setOnClickListener(this@CharacterSelection)
        val iconRitter = findViewById<TextView>(id.iconRitter)
        iconRitter.setOnClickListener(this@CharacterSelection)
        val iconFreunde = findViewById<TextView>(id.iconFreunde)
        iconFreunde.setOnClickListener(this@CharacterSelection)
        val minusAmor = findViewById<ImageView>(id.minusAmor)
        minusAmor.setOnClickListener(this@CharacterSelection)
        val plusAmor = findViewById<ImageView>(id.plusAmor)
        plusAmor.setOnClickListener(this@CharacterSelection)
        nummerAmor = findViewById(id.nummerAmor)
        nummerAmor?.setTextColor(Color.WHITE)
        val minusHexe = findViewById<ImageView>(id.minusHexe)
        minusHexe.setOnClickListener(this@CharacterSelection)
        val plusHexe = findViewById<ImageView>(id.plusHexe)
        plusHexe.setOnClickListener(this@CharacterSelection)
        nummerHexe = findViewById(id.nummerHexe)
        nummerHexe?.setTextColor(Color.WHITE)
        val minusWaechter = findViewById<ImageView>(id.minusWaechter)
        minusWaechter.setOnClickListener(this@CharacterSelection)
        val plusWaechter = findViewById<ImageView>(id.plusWaechter)
        plusWaechter.setOnClickListener(this@CharacterSelection)
        nummerWaechter = findViewById(id.nummerWaechter)
        nummerWaechter?.setTextColor(Color.WHITE)
        val minusDieb = findViewById<ImageView>(id.minusDieb)
        minusDieb.setOnClickListener(this@CharacterSelection)
        val plusDieb = findViewById<ImageView>(id.plusDieb)
        plusDieb.setOnClickListener(this@CharacterSelection)
        nummerDieb = findViewById(id.nummerDieb)
        nummerDieb?.setTextColor(Color.WHITE)
        val minusSeher = findViewById<ImageView>(id.minusSeher)
        minusSeher.setOnClickListener(this@CharacterSelection)
        val plusSeher = findViewById<ImageView>(id.plusSeher)
        plusSeher.setOnClickListener(this@CharacterSelection)
        nummerSeher = findViewById(id.nummerSeher)
        nummerSeher?.setTextColor(Color.WHITE)
        val minusBuerger = findViewById<ImageView>(id.minusBuerger)
        minusBuerger.setOnClickListener(this@CharacterSelection)
        val plusBuerger = findViewById<ImageView>(id.plusBuerger)
        plusBuerger.setOnClickListener(this@CharacterSelection)
        nummerBuerger = findViewById(id.nummerBuerger)
        nummerBuerger?.setTextColor(Color.WHITE)
        val minusWerwolf = findViewById<ImageView>(id.minusWerwolf)
        minusWerwolf.setOnClickListener(this@CharacterSelection)
        val plusWerwolf = findViewById<ImageView>(id.plusWerwolf)
        plusWerwolf.setOnClickListener(this@CharacterSelection)
        nummerWerwolf = findViewById(id.nummerWerwolf)
        nummerWerwolf?.setTextColor(Color.WHITE)
        val minusJunges = findViewById<ImageView>(id.minusJunges)
        minusJunges.setOnClickListener(this@CharacterSelection)
        val plusJunges = findViewById<ImageView>(id.plusJunges)
        plusJunges.setOnClickListener(this@CharacterSelection)
        nummerJunges = findViewById(id.nummerJunges)
        nummerJunges?.setTextColor(Color.WHITE)
        val minusJaeger = findViewById<ImageView>(id.minusJaeger)
        minusJaeger.setOnClickListener(this@CharacterSelection)
        val plusJaeger = findViewById<ImageView>(id.plusJaeger)
        plusJaeger.setOnClickListener(this@CharacterSelection)
        nummerJaeger = findViewById(id.nummerJaeger)
        nummerJaeger?.setTextColor(Color.WHITE)
        val minusMaedchen = findViewById<ImageView>(id.minusMaedchen)
        minusMaedchen.setOnClickListener(this@CharacterSelection)
        val plusMaedchen = findViewById<ImageView>(id.plusMaedchen)
        plusMaedchen.setOnClickListener(this@CharacterSelection)
        nummerMaedchen = findViewById(id.nummerMaedchen)
        nummerMaedchen?.setTextColor(Color.WHITE)
        val minusRitter = findViewById<ImageView>(id.minusRitter)
        minusRitter.setOnClickListener(this@CharacterSelection)
        val plusRitter = findViewById<ImageView>(id.plusRitter)
        plusRitter.setOnClickListener(this@CharacterSelection)
        nummerRitter = findViewById(id.nummerRitter)
        nummerRitter?.setTextColor(Color.WHITE)
        val minusUrwolf = findViewById<ImageView>(id.minusUrwolf)
        minusUrwolf.setOnClickListener(this@CharacterSelection)
        val plusUrwolf = findViewById<ImageView>(id.plusUrwolf)
        plusUrwolf.setOnClickListener(this@CharacterSelection)
        nummerUrwolf = findViewById(id.nummerUrwolf)
        nummerUrwolf?.setTextColor(Color.WHITE)
        val minusWeisserWerwolf = findViewById<ImageView>(id.minusWeisserWerwolf)
        minusWeisserWerwolf.setOnClickListener(this@CharacterSelection)
        val plusWeisserWerwolf = findViewById<ImageView>(id.plusWeisserWerwolf)
        plusWeisserWerwolf.setOnClickListener(this@CharacterSelection)
        nummerWeisserWerwolf = findViewById(id.nummerWeisserWerwolf)
        nummerWeisserWerwolf?.setTextColor(Color.WHITE)
        val minusFloetenspieler = findViewById<ImageView>(id.minusFloetenspieler)
        minusFloetenspieler.setOnClickListener(this@CharacterSelection)
        val plusFloetenspieler = findViewById<ImageView>(id.plusFloetenspieler)
        plusFloetenspieler.setOnClickListener(this@CharacterSelection)
        nummerFloetenspieler = findViewById(id.nummerFloetenspieler)
        nummerFloetenspieler?.setTextColor(Color.WHITE)
        val minusFreunde = findViewById<ImageView>(id.minusFreunde)
        minusFreunde.setOnClickListener(this@CharacterSelection)
        val plusFreunde = findViewById<ImageView>(id.plusFreunde)
        plusFreunde.setOnClickListener(this@CharacterSelection)
        nummerFreunde = findViewById(id.nummerFreunde)
        nummerFreunde?.setTextColor(Color.WHITE)
        infoTextAmor = findViewById(id.infoTextAmor)
        infoTextHexe = findViewById(id.infoTextHexe)
        infoTextWaechter = findViewById(id.infoTextWaechter)
        infoTextDieb = findViewById(id.infoTextDieb)
        infoTextSeher = findViewById(id.infoTextSeher)
        infoTextBuerger = findViewById(id.infoTextBuerger)
        infoTextWerwolf = findViewById(id.infoTextWerwolf)
        infoTextJunges = findViewById(id.infoTextJunges)
        infoTextJaeger = findViewById(id.infoTextJaeger)
        infoTextWeisserWerwolf = findViewById(id.infoTextWeisserWerwolf)
        infoTextMaedchen = findViewById(id.infoTextMaedchen)
        infoTextFloetenspieler = findViewById(id.infoTextFloetenspieler)
        infoTextUrwolf = findViewById(id.infoTextUrwolf)
        infoTextRitter = findViewById(id.infoTextRitter)
        infoTextFreunde = findViewById(id.infoTextFreunde)
    }

    override fun onClick(v: View) {
        if (v.id == id.minusAmor) {
            anzahlAmor--
            if (anzahlAmor == -1) {
                anzahlAmor = 0
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerAmor!!.text = getString(R.string.numberWrapper, anzahlAmor)
        } else if (v.id == id.plusAmor) {
            anzahlAmor++
            nummerAmor!!.text = getString(R.string.numberWrapper, anzahlAmor)
        } else if (v.id == id.minusHexe) {
            anzahlHexe--
            if (anzahlHexe == -1) {
                anzahlHexe = 0
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerHexe!!.text = getString(R.string.numberWrapper, anzahlHexe)
        } else if (v.id == id.plusHexe) {
            anzahlHexe++
            if (anzahlHexe == 2) {
                anzahlHexe = 1
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerHexe!!.text = getString(R.string.numberWrapper, anzahlHexe)
        } else if (v.id == id.minusWaechter) {
            anzahlWaechter--
            if (anzahlWaechter == -1) {
                anzahlWaechter = 0
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerWaechter!!.text = getString(R.string.numberWrapper, anzahlWaechter)
        } else if (v.id == id.plusWaechter) {
            anzahlWaechter++
            if (anzahlWaechter == 2) {
                anzahlWaechter = 1
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerWaechter!!.text = getString(R.string.numberWrapper, anzahlWaechter)
        } else if (v.id == id.minusSeher) {
            anzahlSeher--
            if (anzahlSeher == -1) {
                anzahlSeher = 0
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerSeher!!.text = getString(R.string.numberWrapper, anzahlSeher)
        } else if (v.id == id.plusSeher) {
            anzahlSeher++
            nummerSeher!!.text = getString(R.string.numberWrapper, anzahlSeher)
        } else if (v.id == id.minusDieb) {
            anzahlDieb--
            if (anzahlDieb == -1) {
                anzahlDieb = 0
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerDieb!!.text = getString(R.string.numberWrapper, anzahlDieb)
        } else if (v.id == id.plusDieb) {
            anzahlDieb++
            if (anzahlDieb == 2) {
                anzahlDieb = 1
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerDieb!!.text = getString(R.string.numberWrapper, anzahlDieb)
        } else if (v.id == id.minusBuerger) {
            anzahlBuerger--
            if (anzahlBuerger == -1) {
                anzahlBuerger = 0
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerBuerger!!.text = getString(R.string.numberWrapper, anzahlBuerger)
        } else if (v.id == id.plusBuerger) {
            anzahlBuerger++
            nummerBuerger!!.text = getString(R.string.numberWrapper, anzahlBuerger)
        } else if (v.id == id.minusWerwolf) {
            anzahlWerwolf--
            if (anzahlWerwolf == -1) {
                anzahlWerwolf = 0
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerWerwolf!!.text = getString(R.string.numberWrapper, anzahlWerwolf)
        } else if (v.id == id.plusWerwolf) {
            anzahlWerwolf++
            nummerWerwolf!!.text = getString(R.string.numberWrapper, anzahlWerwolf)
        } else if (v.id == id.minusJunges) {
            anzahlJunges--
            if (anzahlJunges == -1) {
                anzahlJunges = 0
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerJunges!!.text = getString(R.string.numberWrapper, anzahlJunges)
        } else if (v.id == id.plusJunges) {
            anzahlJunges++
            if (anzahlJunges == 2) {
                anzahlJunges = 1
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerJunges!!.text = getString(R.string.numberWrapper, anzahlJunges)
        } else if (v.id == id.minusMaedchen) {
            anzahlMaedchen--
            if (anzahlMaedchen == -1) {
                anzahlMaedchen = 0
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerMaedchen!!.text = getString(R.string.numberWrapper, anzahlMaedchen)
        } else if (v.id == id.plusMaedchen) {
            anzahlMaedchen++
            nummerMaedchen!!.text = getString(R.string.numberWrapper, anzahlMaedchen)
        } else if (v.id == id.minusJaeger) {
            anzahlJaeger--
            if (anzahlJaeger == -1) {
                anzahlJaeger = 0
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerJaeger!!.text = getString(R.string.numberWrapper, anzahlJaeger)
        } else if (v.id == id.plusJaeger) {
            anzahlJaeger++
            if (anzahlJaeger == 2) {
                anzahlJaeger = 1
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerJaeger!!.text = getString(R.string.numberWrapper, anzahlJaeger)
        } else if (v.id == id.minusFreunde) {
            anzahlFreunde--
            if (anzahlFreunde == -1 || anzahlFreunde == 1) {
                anzahlFreunde = 0
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerFreunde!!.text = getString(R.string.numberWrapper, anzahlFreunde)
        } else if (v.id == id.plusFreunde) {
            anzahlFreunde++
            if (anzahlFreunde == 1) {
                anzahlFreunde = 2
            }
            nummerFreunde!!.text = getString(R.string.numberWrapper, anzahlFreunde)
        } else if (v.id == id.minusFloetenspieler) {
            anzahlFloetenspieler--
            if (anzahlFloetenspieler == -1) {
                anzahlFloetenspieler = 0
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerFloetenspieler!!.text = getString(R.string.numberWrapper, anzahlFloetenspieler)
        } else if (v.id == id.plusFloetenspieler) {
            anzahlFloetenspieler++
            if (anzahlFloetenspieler == 2) {
                anzahlFloetenspieler = 1
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerFloetenspieler!!.text = getString(R.string.numberWrapper, anzahlFloetenspieler)
        } else if (v.id == id.minusRitter) {
            anzahlRitter--
            if (anzahlRitter == -1) {
                anzahlRitter = 0
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerRitter!!.text = getString(R.string.numberWrapper, anzahlRitter)
        } else if (v.id == id.plusRitter) {
            anzahlRitter++
            if (anzahlRitter == 2) {
                anzahlRitter = 1
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerRitter!!.text = getString(R.string.numberWrapper, anzahlRitter)
        } else if (v.id == id.minusUrwolf) {
            anzahlUrwolf--
            if (anzahlUrwolf == -1) {
                anzahlUrwolf = 0
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerUrwolf!!.text = getString(R.string.numberWrapper, anzahlUrwolf)
        } else if (v.id == id.plusUrwolf) {
            anzahlUrwolf++
            if (anzahlUrwolf == 2) {
                anzahlUrwolf = 1
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerUrwolf!!.text = getString(R.string.numberWrapper, anzahlUrwolf)
        } else if (v.id == id.minusWeisserWerwolf) {
            anzahlWeisserWerwolf--
            if (anzahlWeisserWerwolf == -1) {
                anzahlWeisserWerwolf = 0
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerWeisserWerwolf!!.text = getString(R.string.numberWrapper, anzahlWeisserWerwolf)
        } else if (v.id == id.plusWeisserWerwolf) {
            anzahlWeisserWerwolf++
            if (anzahlWeisserWerwolf == 2) {
                anzahlWeisserWerwolf = 1
                vibrator!!.vibrate(vibrationsdauer.toLong())
            }
            nummerWeisserWerwolf!!.text = getString(R.string.numberWrapper, anzahlWeisserWerwolf)
        } else if (v.id == id.spiel_starten) {
            weiter()
        } else if (v.id == id.iconAmor) {
            if (infoTextAmor!!.visibility == View.VISIBLE) infoTextAmor!!.visibility =
                View.GONE else infoTextAmor!!.visibility = View.VISIBLE
        } else if (v.id == id.iconHexe) {
            if (infoTextHexe!!.visibility == View.VISIBLE) infoTextHexe!!.visibility =
                View.GONE else infoTextHexe!!.visibility = View.VISIBLE
        } else if (v.id == id.iconWaechter) {
            if (infoTextWaechter!!.visibility == View.VISIBLE) infoTextWaechter!!.visibility =
                View.GONE else infoTextWaechter!!.visibility = View.VISIBLE
        } else if (v.id == id.iconDieb) {
            if (infoTextDieb!!.visibility == View.VISIBLE) infoTextDieb!!.visibility =
                View.GONE else infoTextDieb!!.visibility = View.VISIBLE
        } else if (v.id == id.iconSeher) {
            if (infoTextSeher!!.visibility == View.VISIBLE) infoTextSeher!!.visibility =
                View.GONE else infoTextSeher!!.visibility = View.VISIBLE
        } else if (v.id == id.iconBuerger) {
            if (infoTextBuerger!!.visibility == View.VISIBLE) infoTextBuerger!!.visibility =
                View.GONE else infoTextBuerger!!.visibility = View.VISIBLE
        } else if (v.id == id.iconWerwolf) {
            if (infoTextWerwolf!!.visibility == View.VISIBLE) infoTextWerwolf!!.visibility =
                View.GONE else infoTextWerwolf!!.visibility = View.VISIBLE
        } else if (v.id == id.iconJunges) {
            if (infoTextJunges!!.visibility == View.VISIBLE) infoTextJunges!!.visibility =
                View.GONE else infoTextJunges!!.visibility = View.VISIBLE
        } else if (v.id == id.iconJaeger) {
            if (infoTextJaeger!!.visibility == View.VISIBLE) infoTextJaeger!!.visibility =
                View.GONE else infoTextJaeger!!.visibility = View.VISIBLE
        } else if (v.id == id.iconWeisserWerwolf) {
            if (infoTextWeisserWerwolf!!.visibility == View.VISIBLE) infoTextWeisserWerwolf!!.visibility =
                View.GONE else infoTextWeisserWerwolf!!.visibility = View.VISIBLE
        } else if (v.id == id.iconMaedchen) {
            if (infoTextMaedchen!!.visibility == View.VISIBLE) infoTextMaedchen!!.visibility =
                View.GONE else infoTextMaedchen!!.visibility = View.VISIBLE
        } else if (v.id == id.iconFloetenspieler) {
            if (infoTextFloetenspieler!!.visibility == View.VISIBLE) infoTextFloetenspieler!!.visibility =
                View.GONE else infoTextFloetenspieler!!.visibility = View.VISIBLE
        } else if (v.id == id.iconUrwolf) {
            if (infoTextUrwolf!!.visibility == View.VISIBLE) infoTextUrwolf!!.visibility =
                View.GONE else infoTextUrwolf!!.visibility = View.VISIBLE
        } else if (v.id == id.iconRitter) {
            if (infoTextRitter!!.visibility == View.VISIBLE) infoTextRitter!!.visibility =
                View.GONE else infoTextRitter!!.visibility = View.VISIBLE
        } else if (v.id == id.iconFreunde) {
            if (infoTextFreunde!!.visibility == View.VISIBLE) infoTextFreunde!!.visibility =
                View.GONE else infoTextFreunde!!.visibility = View.VISIBLE
        }
        gesamtPer =
            anzahlAmor + anzahlWaechter + anzahlBuerger + anzahlJunges + anzahlJaeger + anzahlWerwolf + anzahlSeher + anzahlHexe + anzahlDieb + anzahlWeisserWerwolf + anzahlRitter + anzahlFloetenspieler + anzahlFreunde + anzahlMaedchen + anzahlUrwolf
        gesamtTxt!!.text = getString(R.string.combinedWrapper, gesamtPer)
    }

    private fun weiter() {
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