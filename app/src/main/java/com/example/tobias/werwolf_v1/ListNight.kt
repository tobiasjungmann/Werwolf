package com.example.tobias.werwolf_v1

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tobias.werwolf_v1.database.models.CharacterClass
import com.example.tobias.werwolf_v1.database.models.DatabaseHelper
import com.example.tobias.werwolf_v1.databinding.ActivityManualBinding
import com.example.tobias.werwolf_v1.databinding.ActivityNightlistBinding
import com.example.tobias.werwolf_v1.pregame.PreGameViewModel

class ListNight : AppCompatActivity(), View.OnClickListener {

    private var nightListAdapter: NightListAdapter? = null
    private lateinit var binding: ActivityNightlistBinding
    private lateinit var nightListViewModel: NightListViewModel

    //todo fixme remove
    private var mDatabaseHelper: DatabaseHelper? = null
    private var data: Cursor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNightlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nightListViewModel =
            ViewModelProvider(this)[NightListViewModel::class.java]

        binding.weiterNacht.setOnClickListener(this@ListNight)
        binding.description.setOnClickListener(this@ListNight)
        binding.personen.visibility = View.INVISIBLE


        //Witch
        binding.opferRettenHexe.setOnClickListener(this)
        binding.personToetenHexe.setOnClickListener(this)
        binding.textViewOpferRetten.setOnClickListener(this)
        binding.textViewPersonToeten.setOnClickListener(this)


        //Urwolf
        binding.textViewUrwolfVeto.setOnClickListener(this)


        //Liste Initialisieren
        mDatabaseHelper = DatabaseHelper(this)
        data = mDatabaseHelper!!.data

        // arrayList = new ArrayList<String>();

        nightListAdapter = NightListAdapter(data!!)
        binding.personen.adapter = nightListAdapter
        binding.personen.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            nightListViewmodel.handleClickAtPosition(position)
            setStatusNextButton(true)
            nightListAdapter.notifyDataSetChanged()

        }
    }

    override fun onBackPressed() {
        val builder: AlertDialog.Builder
        builder =
            AlertDialog.Builder(this@ListNight, android.R.style.Theme_Material_Dialog_Alert)
        builder.setTitle("Warnung")
            .setMessage("Möchtest du die App wirklich verlassen? Der gesamte Spielstand geht verloren.")
            .setNegativeButton("Ja, App verlassen") { dialog, which ->
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
                System.exit(0)
            }
            .setPositiveButton("weiterspielen") { dialog, which -> }
            .show()
    }


    fun setStatusNextButton(status: Boolean) {
        binding.weiterNacht.isClickable = status
        var help = R.color.blue_unclickable
        if (status) {
            help = R.color.blue
        }
        binding.weiterNacht.background.setTint(
            ContextCompat.getColor(
                this,
                help
            )
        )
    }

    private fun charakterPositionBestimmen() {
        // Log.d(ContentValues.TAG, "charakterposition  case $charakterPosition")
        when (charakterPosition) {
            0 -> if (anzahlAmor > 0) {
                changeUIToNewCharacter(nightListViewModel.generateCharacters()[0])      // todo change to amor
                setStatusNextButton(false)

                nightListAdapter.notifyDataSetChanged()
                binding.personen.visibility = View.VISIBLE
                anzahlAmor = 0
                anzahlBuerger++
            } else {
                charakterPosition++
                charakterPositionBestimmen()
            }
            1 -> if (anzahlFreunde > 0) {
                changeUIToNewCharacter(nightListViewModel.generateCharacters()[0])      // todo change to firends

                anzahlBuerger = anzahlBuerger + anzahlFreunde
                anzahlFreunde = 0 //todo Bürgerzahl +2
                charakterPosition++
                binding.personen.visibility = View.INVISIBLE
            } else {
                charakterPosition++
                charakterPositionBestimmen()
            }
            2 -> if (anzahlDieb > 0) {
                changeUIToNewCharacter(nightListViewModel.generateCharacters()[0])      // todo change to thief
                setStatusNextButton(false)
                nightListAdapter.notifyDataSetChanged()
                binding.personen.visibility = View.VISIBLE
            } else {
                charakterPosition++
                charakterPositionBestimmen()
            }
            3 -> if (anzahlWaechter > 0) {
                changeUIToNewCharacter(nightListViewModel.generateCharacters()[0])      // todo change to guardian
                setStatusNextButton(false)
                nightListAdapter.notifyDataSetChanged()
                binding.personen.visibility = View.VISIBLE
            } else {
                charakterPosition++
                charakterPositionBestimmen()
            }
            4 -> if (anzahlJunges > 0) {
                changeUIToNewCharacter(nightListViewModel.generateCharacters()[0])      // todo change to child
                anzahlBuerger = anzahlBuerger + anzahlJunges
                anzahlJunges = 0
                setStatusNextButton(false)
                nightListAdapter.notifyDataSetChanged()
                binding.personen.visibility = View.VISIBLE
            } else {
                charakterPosition++
                charakterPositionBestimmen()
            }
            5 -> if (anzahlSeher > 0) {
                changeUIToNewCharacter(nightListViewModel.generateCharacters()[0])      // todo change to seher
                charakterPosition++
                binding.personen.visibility = View.INVISIBLE
            } else {
                charakterPosition++
                charakterPositionBestimmen()
            }
            6 -> if (anzahlFloetenspieler > 0) {
                changeUIToNewCharacter(nightListViewModel.generateCharacters()[0])      // todo change to flute
                setStatusNextButton(false)
                nightListAdapter.notifyDataSetChanged()
                binding.personen.visibility = View.VISIBLE
            } else {
                charakterPosition++
                charakterPositionBestimmen()
            }
            7 -> if (ritterAktiv) {
                ritterDialogVorbereiten()
            } else {
                if (anzahlWerwolf + anzahlUrwolf + anzahlWeisserWerwolf > 0) {
                    hangeUIToNewCharacter(nightListViewModel.generateCharacters()[0])      // todo change to wolf
                    setStatusNextButton(false)
                    nightListAdapter.notifyDataSetChanged()
                    binding.personen.visibility = View.VISIBLE
                } else {
                    binding.description.text = "Fehler: Es sind keine Werwölfe mehr im Spiel!"
                }
            }
            8 -> if (anzahlWeisserWerwolf > 0 && !wwletzteRundeAktiv) {
                changeUIToNewCharacter(nightListViewModel.generateCharacters()[0]) // todo change to whiteWolf
                setStatusNextButton(false)
                nightListAdapter.notifyDataSetChanged()
                binding.personen.visibility = View.VISIBLE
                wwletzteRundeAktiv = true
            } else {
                wwletzteRundeAktiv = false
                charakterPosition++
                charakterPositionBestimmen()
            }
            9 -> if (anzahlUrwolf > 0 && urwolfVeto != -1) {
                binding.personen.visibility = View.GONE
                binding.layoutUrwolf.visibility = View.VISIBLE
                hangeUIToNewCharacter(nightListViewModel.generateCharacters()[0])      // todo change to urwolf
                charakterPosition++
            } else {
                charakterPosition++
                charakterPositionBestimmen()
            }
            10 -> {
                binding.layoutUrwolf.visibility = View.GONE
                if (anzahlHexe > 0) {
                    if (trankLebenEinsetzbar || trankTodEinsetzbar) {
                        werwolfOpferIDBackupHexe = werwolfOpferID
                        hangeUIToNewCharacter(nightListViewModel.generateCharacters()[0])      // todo change to witch
                        binding.personen.visibility = View.INVISIBLE

                        //todo, wenn nur eine Option übrig ist eien entsprechenden Rand einfügen
                        if (trankLebenEinsetzbar || trankTodEinsetzbar) {
                            binding.layoutHexeNacht.visibility = View.VISIBLE
                            if (!(trankLebenEinsetzbar && trankTodEinsetzbar)) {
                                if (trankLebenEinsetzbar) //leben anzaeigen, sonst nur tod
                                {
                                    binding.rettenLayoutHexe.visibility = View.VISIBLE
                                    binding.toetenLayoutHexe.visibility = View.GONE
                                } else {
                                    val params = LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                    )
                                    params.setMargins(16, 16, 16, 16)
                                    binding.toetenLayoutHexe.layoutParams = params
                                    binding.layoutRettenHexe.visibility = View.GONE
                                    binding.toetenLayoutHexe.visibility = View.VISIBLE
                                }
                            }
                        }
                        charakterPosition++
                    } else {
                        charakterPosition++
                        charakterPositionBestimmen()
                    }
                } else {
                    charakterPosition++
                    charakterPositionBestimmen()
                }
            }
            11 -> {
                nachtAuswerten()

                //Opfer des weißen werwolfes töten
                if (weisserWerwolfOpferID != -1 && weisserWerwolfOpferID != werwolfOpferID) {
                    weisserWerwolfAuswerten()
                    weisserWerwolfOpferID = -1
                }

                //Opfer des Ritters töten
                if (ritterOpfer != -1) {
                    tot = ""
                    sicherToeten(ritterOpfer, nameRitterOpfer, charakterRitterOpfer)
                    val s = binding.description.text.toString()
                    binding.description.text = """
                        $s
                        $tot
                        """.trimIndent()
                    ritterOpfer = -1
                    nameRitterOpfer = ""
                    charakterRitterOpfer = ""
                }
                auswerten()
                if (tot!!.compareTo("") == 0) {
                    binding.description.text = "Das ganze Dorf erwacht, alle haben überlebt"
                } else {
                    binding.description.text = "Das ganze Dorf erwacht außer: $tot"
                }
                tot = ""

                //wenn Jäger gestorben, entsprechende charakterposition
                if (!jaegerAktiv) {
                    binding.layoutHexeNacht.visibility = View.GONE
                    setStatusNextButton(true)
                    binding.personen.visibility = View.INVISIBLE
                    binding.textSpielstand.setText(R.string.village)
                    binding.layoutSpielstand.background?.setTint(
                        ContextCompat.getColor(
                            this,
                            R.color.green
                        )
                    )
                    charakterPosition++
                } else {
                    val s = binding.description.text.toString()
                    binding.description.text =
                        s + "Der Jäger ist gestorben. Er darf eine weitere Person töten:"
                }
                if (ritterAktiv) {
                    val s = binding.description.text.toString()
                    binding.description.text =
                        "$s\n\nDer Ritter ist verstorben. In der nächsten Nacht stirbt der Nächste Werwolf zur Rechten des Ritters."
                }
            }
            12 -> {
                auswerten()
                binding.description.text = "Abstimmphase, wen wählt das Dorf als Schuldigen aus? "
                //todo: Möglichkeit keinen zu töten, weiter immer klickbar, aber dann dialog der nachfrägt
                setStatusNextButton(false)
                nightListAdapter.notifyDataSetChanged()
                binding.personen.visibility = View.VISIBLE


                //was macht der folgende Abschnitt????
                if (verzaubertAktuell != -1) {
                    mDatabaseHelper!!.deleteName("" + verzaubertAktuell)
                    mDatabaseHelper!!.addVerzaubert(
                        verzaubertName ?: "Invalid",
                        verzaubertCharakter
                    )
                    data = mDatabaseHelper!!.data
                    if (verzaubertAktuell == vorbildID) {
                        data?.moveToFirst()
                        var vorbildGefunden = false
                        if (data?.getString(1)?.compareTo(verzaubertName!!) == 0) {
                            vorbildGefunden = true
                            vorbildID = data?.getInt(0) ?: -1
                        }
                        while (data?.moveToNext() == true && !vorbildGefunden) {
                            if (data?.getString(1)?.compareTo(verzaubertName!!) == 0) {
                                vorbildGefunden = true
                                vorbildID = data?.getInt(0) ?: -1
                            }
                        }
                    }
                    if (liebenderEinsID == verzaubertAktuell) {
                        data?.moveToFirst()
                        var vorbildGefunden = false
                        if (data?.getString(1)?.compareTo(verzaubertName!!) == 0) {
                            vorbildGefunden = true
                            liebenderEinsID = data?.getInt(0) ?: -1
                        }
                        while (data?.moveToNext() == true && !vorbildGefunden) {
                            if (data?.getString(1)?.compareTo(verzaubertName!!) == 0) {
                                vorbildGefunden = true
                                liebenderEinsID = data?.getInt(0) ?: -1
                            }
                        }
                    }
                    if (liebenderZweiID == verzaubertAktuell) {
                        data?.moveToFirst()
                        var vorbildGefunden = false
                        if (data?.getString(1)?.compareTo(verzaubertName!!) == 0) {
                            vorbildGefunden = true
                            liebenderZweiID = data?.getInt(0) ?: -1
                        }
                        while (data?.moveToNext() == true && !vorbildGefunden) {
                            if (data?.getString(1)?.compareTo(verzaubertName!!) == 0) {
                                vorbildGefunden = true
                                liebenderZweiID = data?.getInt(0) ?: -1
                            }
                        }
                    }
                    verzaubertAktuell = -1
                    verzaubertCharakter = ""
                    verzaubertName = ""
                }
            }
            13 -> {
                buergeropferToeten()
                if (!jaegerAktiv) {
                    binding.personen.visibility = View.GONE
                    binding.description.text =
                        "Das ganze Dorf schläft ein.\n\nHinweis: Die Reihenfolge der Personen in der Liste hat sich geändert."
                    charakterPosition = 1
                    werwolfOpferID = -1
                    weisserWerwolfOpferID = -1
                    schlafplatzWaechterID = -1
                    schlafplatzDiebID = -1
                    tot = ""
                    // totErweiterungWW = "";
                } else {
                    val s = binding.description.text.toString()
                    binding.description.text =
                        "$s\n\nDer Jäger ist gestorben. Er darf eine weitere Person töten:"
                }
            }
        }
    }

    private fun changeUIToNewCharacter(characterClass: CharacterClass) {
        binding.textSpielstand.setText(characterClass.name)
        binding.layoutSpielstand.background?.setTint(
            ContextCompat.getColor(
                this,
                characterClass.color
            )
        )
        binding.description.text =
            nightListViewModel.getDescToCharacter(characterClass, applicationContext)
    }


    private fun siegbildschirmOeffnen(sieger: String) {
        val intent = Intent(this, VictoryActivity::class.java)
        intent.putExtra("sieger", sieger)
        startActivity(intent)
    }


    private fun ritterDialogVorbereiten() {
        charakterPosition = 21
        changeUIToNewCharacter(nightListViewModel.generateCharacters()[0]) // todo change to knight

        setStatusNextButton(false)
        nightListAdapter.notifyDataSetChanged()
        binding.personen.visibility = View.VISIBLE
    }

    private fun ritterDialogToeten() {
        ritterAktiv = false
        charakterPosition = 7
        charakterPositionBestimmen()
    }

    private fun jaegerDialog() {
        charakterPositionJaegerBackup = charakterPosition
        charakterPosition = 20
        jaegerAktiv = true
        changeUIToNewCharacter(nightListViewModel.generateCharacters()[0]) // todo change to hunter
        setStatusNextButton(false)
        nightListAdapter?.notifyDataSetChanged()
        binding.personen.visibility = View.VISIBLE
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.personToetenHexe, R.id.textViewPersonToeten -> {
                val executeWitchKill = nightListViewModel.witchKill()
                binding.personToetenHexe.isChecked = executeWitchKill

                if (executeWitchKill) {
                    binding.personen.visibility = View.VISIBLE
                    setStatusNextButton(false)
                } else {
                    binding.personen.visibility = View.INVISIBLE

                    setStatusNextButton(true)
                }
                nightListAdapter?.notifyDataSetChanged()
            }
            R.id.opferRettenHexe, R.id.textViewOpferRetten -> {
                binding.opferRettenHexe.isChecked = nightListViewModel.witchSaveVictim()
            }
            R.id.weiterNacht -> {
                nightListViewModel.nexButtonClicked()

            }
            R.id.textViewUrwolfVeto, R.id.checkboxUrwolfVeto -> if (urwolfVeto == 1) {
                binding.checkboxUrwolfVeto.isChecked = false
                urwolfVeto = 0
            } else {
                binding.checkboxUrwolfVeto.isChecked = true
                urwolfVeto = 1
            }
            R.id.description -> {
                binding.description=nightListViewModel.toggleDescriptionLength(applicationContext)
            }
            else -> {}
        }
    }
}