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
            ViewModelProvider(requireActivity()).get(NightListViewModel::class.java)

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
            binding.weiterNacht.isClickable = true
            binding.weiterNacht.background?.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.blue
                )
            )
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

    private fun charakterPositionBestimmen() {
        Log.d(ContentValues.TAG, "charakterposition  case $charakterPosition")
        when (charakterPosition) {
            0 -> if (anzahlAmor > 0) {
                binding.textSpielstand.setText(R.string.amor)
                binding.layoutSpielstand.background.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.amor
                    )
                )
                if (langerText) {
                    binding.description.text =
                        "Als erstes erwacht der Amor und zeigt auf zwei Personen, die sich augenblicklich unsterblich ineinander verlieben. - Der Amor schläft wieder ein. Ich tippe die beiden verliebten jetzt an un sie schauen sich tief in die Augen. - Auch sie schlafen jetzt wieder ein."
                } else {
                    binding.description.text =
                        "Als erstes erwacht der Amor und zeigt auf zwei Personen..."
                }
                binding.weiterNacht.isClickable = false
                binding.weiterNacht.background.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.blue_unclickable
                    )
                )
                nightListAdapter.notifyDataSetChanged()
                binding.personen.visibility = View.VISIBLE
                anzahlAmor = 0
                anzahlBuerger++
            } else {
                charakterPosition++
                charakterPositionBestimmen()
            }
            1 -> if (anzahlFreunde > 0) {
                binding.textSpielstand.setText(R.string.freunde)
                binding.layoutSpielstand.background?.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.friends
                    )
                )
                if (langerText) {
                    binding.description.text =
                        "Ich tippe alle Freunde kurz an. - Jetzt erwachen die Freunde und schauen sich an, um sich später wiederzuerkennen. - Die Freunde schlafen beruhigt wieder ein, da sie wisssen, dass sie nicht alleine sind."
                } else {
                    binding.description.text =
                        "Ich tippe alle Freunde kurz an. - Jetzt erwachen die Freunde..."
                }
                anzahlBuerger = anzahlBuerger + anzahlFreunde
                anzahlFreunde = 0 //todo Bürgerzahl +2
                charakterPosition++
                binding.personen.visibility = View.INVISIBLE
            } else {
                charakterPosition++
                charakterPositionBestimmen()
            }
            2 -> if (anzahlDieb > 0) {
                binding.textSpielstand.setText(R.string.dieb)
                binding.layoutSpielstand.background?.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.thief
                    )
                )
                if (langerText) {
                    binding.description.text =
                        "Jetzt erwacht der Dieb und sucht sich eine Person aus, bei der er oder sie die Nacht verbringen möchten. - Er zeigt auf diese Person und schläft danach wieder ein."
                } else {
                    binding.description.text =
                        "Jetzt erwacht der Dieb und sucht sich eine Person aus..."
                }
                binding.weiterNacht.isClickable = false
                binding.weiterNacht.background.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.blue_unclickable
                    )
                )
                nightListAdapter.notifyDataSetChanged()
                binding.personen.visibility = View.VISIBLE
            } else {
                charakterPosition++
                charakterPositionBestimmen()
            }
            3 -> if (anzahlWaechter > 0) {
                binding.textSpielstand.setText(R.string.waechter)
                binding.layoutSpielstand.background?.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.guradian
                    )
                )
                if (langerText) {
                    binding.description.text =
                        "Der Wächter wählt eine Person, die er diese Nacht beschützen möchte. - Der Wächter schläft wieder ein."
                } else {
                    binding.description.text =
                        "Der Wächter wählt eine Person, die er diese Nacht beschützen..."
                }
                binding.weiterNacht.isClickable = false
                binding.weiterNacht.background.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.blue_unclickable
                    )
                )
                nightListAdapter.notifyDataSetChanged()
                binding.personen.visibility = View.VISIBLE
            } else {
                charakterPosition++
                charakterPositionBestimmen()
            }
            4 -> if (anzahlJunges > 0) {
                binding.textSpielstand.setText(R.string.junges)
                binding.layoutSpielstand.background?.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.wchild
                    )
                )
                if (langerText) {
                    binding.description.text =
                        "Das Werwolfjunge erwacht und sucht sich ein Vorbild aus. Sollte dieses Vorbild sterben, wirst du auch ein Werwolf und wachst gemeinsam mit ihnen auf. -\n Das Junge schläft wieder."
                } else {
                    binding.description.text =
                        "Das Werwolfjunge erwacht und sucht sich ein Vorbild aus..."
                }
                anzahlBuerger = anzahlBuerger + anzahlJunges
                anzahlJunges = 0
                binding.weiterNacht.isClickable = false
                binding.weiterNacht.background.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.blue_unclickable
                    )
                )
                nightListAdapter.notifyDataSetChanged()
                binding.personen.visibility = View.VISIBLE
            } else {
                charakterPosition++
                charakterPositionBestimmen()
            }
            5 -> if (anzahlSeher > 0) {
                binding.textSpielstand.setText(R.string.seher)
                binding.layoutSpielstand.background?.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.seher
                    )
                )
                if (langerText) {
                    binding.description.text =
                        "Als nächstes wacht der Seher auf und zeigt auf eine Person, deren Karte er sehen möchte. Wenn er sie gesehen hat schläft er wieder ein."
                } else {
                    binding.description.text =
                        "Als nächstes wacht der Seher auf und zeigt auf eine Person..."
                }
                charakterPosition++
                binding.personen.visibility = View.INVISIBLE
            } else {
                charakterPosition++
                charakterPositionBestimmen()
            }
            6 -> if (anzahlFloetenspieler > 0) {
                binding.textSpielstand.setText(R.string.floetenspieler)
                binding.layoutSpielstand.background?.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.flute
                    )
                )
                if (langerText) {
                    binding.description.text =
                        "Zuletzt erwacht der bezaubernde Flötenspieler und darf eine Person seiner Wahl verzaubern. Hat er alle Mitspieler verzaubert gewinnt er."
                } else {
                    binding.description.text =
                        "Zuletzt erwacht der bezaubernde Flötenspieler und darf..."
                }
                binding.weiterNacht.isClickable = false
                binding.weiterNacht.background.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.blue_unclickable
                    )
                )
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
                    binding.textSpielstand.setText(R.string.werwolf)
                    binding.layoutSpielstand.setBackgroundResource(R.drawable.leiste_werwolf)
                    if (langerText) {
                        binding.description.text =
                            "Jetzt wachen die Werwölfe auf und suchen sich ihr Opfer für diese Nacht aus. Haben sie sich entschieden schlafen sie auch schon wieder ein."
                    } else {
                        binding.description.text =
                            "Jetzt wachen die Werwölfe auf und suchen sich ihr Opfer..."
                    }
                    binding.weiterNacht.isClickable = false
                    binding.weiterNacht.background?.setTint(
                        ContextCompat.getColor(
                            this,
                            R.color.blue_unclickable
                        )
                    )
                    nightListAdapter.notifyDataSetChanged()
                    binding.personen.visibility = View.VISIBLE
                } else {
                    binding.description.text = "Fehler: Es sind keine Werwölfe mehr im Spiel!"
                }
            }
            8 -> if (anzahlWeisserWerwolf > 0 && !wwletzteRundeAktiv) {
                binding.textSpielstand.setText(R.string.weisser_werwolf)
                binding.layoutSpielstand.background?.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.wwolf
                    )
                )
                if (langerText) {
                    binding.description.text = "Text Weißer Werwolf"
                } else {
                    binding.description.text = "Text Weißer Werwolf..."
                }
                binding.weiterNacht.isClickable = false
                binding.weiterNacht.background.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.blue_unclickable
                    )
                )
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
                binding.textSpielstand.setText(R.string.urwolf)
                binding.layoutSpielstand.background?.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.urwolf
                    )
                )
                if (langerText) {
                    binding.description.text = "Text Urwolf"
                } else {
                    binding.description.text = "Text Urwolf..."
                }
                charakterPosition++
            } else {
                charakterPosition++
                charakterPositionBestimmen()
            }
            10 -> {
                binding.layoutUrwolf.visibility = View.GONE
                if (anzahlHexe > 0) {
                    if (trankLebenEinsetzbar || trankTodEinsetzbar) {
                        binding.textSpielstand.setText(R.string.hexe)
                        werwolfOpferIDBackupHexe = werwolfOpferID
                        binding.layoutSpielstand.background?.setTint(
                            ContextCompat.getColor(
                                this,
                                R.color.witch
                            )
                        )
                        if (langerText) {
                            binding.description.text =
                                "Als nächstes wacht die Hexe auf. Sie kann das Opfer -auf Opfer zeigen- retten, nichtstun, oder  eine weitere Person mit in den Tod reißen. -Dabei die drei Handzeichen für die Hexe sichtbar vormachen."
                        } else {
                            binding.description.text =
                                "Als nächstes wacht die Hexe auf. Sie kann das Opfer..."
                        }
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
                    binding.weiterNacht.isClickable = true
                    binding.weiterNacht.background?.setTint(
                        ContextCompat.getColor(
                            this,
                            R.color.blue
                        )
                    )
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
                binding.weiterNacht.isClickable = false
                binding.weiterNacht.background.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.blue_unclickable
                    )
                )
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

    private fun nachtAuswerten() {
        var opferZweiID = -1
        var werwolfopferGefunden = false
        var werwolfOpferName: String? = ""
        var werwolfOpferVerzaubert: String? = ""
        var werwolfOpferCharakter = ""
        if (werwolfOpferID != -1) //Werwolf existiert
        {
            //Werte des Opfers ermitteln:
            data!!.moveToFirst() //Daten des Werwolfopfers werden ermittelt
            if (data!!.getInt(0) == werwolfOpferID) {
                werwolfopferGefunden = true
                werwolfOpferName = data!!.getString(1)
                werwolfOpferCharakter = data!!.getString(2)
                werwolfOpferVerzaubert = data!!.getString(4)
            }
            while (data!!.moveToNext() && !werwolfopferGefunden) {
                if (data!!.getInt(0) == werwolfOpferID) {
                    werwolfopferGefunden = true
                    werwolfOpferName = data!!.getString(1)
                    werwolfOpferCharakter = data!!.getString(2)
                    werwolfOpferVerzaubert = data!!.getString(4)
                }
            }
            if (urwolfVeto == 1) //Von Urwolf gerettet -> verwandelt sich in einen Werwolf
            {
                werwolfDurchUrwolfID = werwolfOpferID
                urwolfVeto = -1
                anzahlWerwolf++
                werwolfOpferID = -1
            } else {            //Auswertung aller Opfer
                var waechterID = -1
                var diebID = -1
                var waechterGefunden = false
                var diebGefunden = false


                //Werte wächter bestimmen
                if (anzahlWaechter > 0) {
                    data!!.moveToFirst()
                    if (data!!.getString(2).compareTo("waechter") == 0) {
                        waechterGefunden = true
                        waechterID = data!!.getInt(0)
                    }
                    while (data!!.moveToNext() && !waechterGefunden) {
                        if (data!!.getString(2).compareTo("waechter") == 0) {
                            waechterGefunden = true
                            waechterID = data!!.getInt(0)
                        }
                    }
                }

                //werte Dieb bestimmen
                if (anzahlDieb > 0) //todo auswetung das Diebes kann aussetzen, wenn der Wächter das eigentliche Werwolfopfer beschützt, sodass dieses überlebt.
                {
                    data!!.moveToFirst()
                    if (data!!.getString(2).compareTo("dieb") == 0) {
                        diebGefunden = true
                        diebID = data!!.getInt(0)
                    }
                    while (data!!.moveToNext() && !diebGefunden) {
                        if (data!!.getString(2).compareTo("dieb") == 0) {
                            diebGefunden = true
                            diebID = data!!.getInt(0)
                        }
                    }
                }

                //wächter ist das Ofer
                if (werwolfOpferCharakter.compareTo("waechter") == 0) {
                    if (schlafplatzWaechterID != waechterID) //hier muss noch geprüft werden ob der dieb beim wächter schläft
                    {
                        if (waechterID != schlafplatzDiebID) {
                            werwolfOpferID = -1
                            Log.d(ContentValues.TAG, "Werwolf tötet wächter, aber niemand daheim")
                        } else {
                            werwolfOpferID = diebID
                            Log.d(ContentValues.TAG, "dieb bei wächter wächter aber nicht daheim")
                        }
                    }
                }

                //wächter rettet das Opfer
                if (werwolfOpferID != -1) {
                    if (schlafplatzWaechterID == werwolfOpferID) {
                        werwolfOpferID = waechterID
                        werwolfOpferID = -1
                        Log.d(ContentValues.TAG, "Wächter hat das Opfer gerettet")
                    }
                }


                //Hat der Dieb was mit dem Opfer zu tun?
                if (werwolfOpferID != -1 && anzahlDieb > 0) {
                    if (diebID != schlafplatzDiebID) //Dieb ist nicht daheim -> gilt als nicht normaler bürger
                    {
                        if (schlafplatzDiebID == werwolfOpferID) {  //dieb ist beim Opfer -> muss auch sterben
                            opferZweiID = diebID
                        }
                        if (anzahlWaechter > 0) {
                            if (schlafplatzDiebID == waechterID) {
                                werwolfOpferID = diebID
                            }
                        }
                        if (diebID == werwolfOpferID) {
                            werwolfOpferID = -1
                        }
                    }
                }

                //Attribute des Finalen Opfers bestimmen, dann töten
                if (werwolfOpferID != -1) {
                    data!!.moveToFirst() //Die werte müssen neu bestimmt werden, da der Dieb jetzt zum Opfergeworden sein kann.
                    werwolfopferGefunden = false
                    if (data!!.getInt(0) == werwolfOpferID) {
                        werwolfopferGefunden = true
                        werwolfOpferName = data!!.getString(1)
                        werwolfOpferCharakter = data!!.getString(2)
                    }
                    while (data!!.moveToNext() && !werwolfopferGefunden) {
                        if (data!!.getInt(0) == werwolfOpferID) {
                            werwolfopferGefunden = true
                            werwolfOpferName = data!!.getString(1)
                            werwolfOpferCharakter = data!!.getString(2)
                        }
                    }
                    if (werwolfOpferID == hexeOpferID) {
                        hexeOpferID = -1
                    }
                    sicherToeten(werwolfOpferID, werwolfOpferName, werwolfOpferCharakter)
                }

                //Opfer 2 wird ermittelt und getötet
                if (opferZweiID != -1) {
                    data!!.moveToFirst()
                    werwolfopferGefunden = false
                    if (data!!.getInt(0) == opferZweiID) {
                        werwolfopferGefunden = true
                        werwolfOpferName = data!!.getString(1)
                        werwolfOpferCharakter = data!!.getString(2)
                    }
                    while (data!!.moveToNext() && !werwolfopferGefunden) {
                        if (data!!.getInt(0) == opferZweiID) {
                            werwolfopferGefunden = true
                            werwolfOpferName = data!!.getString(1)
                            werwolfOpferCharakter = data!!.getString(2)
                        }
                    }
                    if (opferZweiID == hexeOpferID) {
                        hexeOpferID = -1
                    }
                    sicherToeten(opferZweiID, werwolfOpferName, werwolfOpferCharakter)
                }

                //Schlafplätze spielen dabei keien Rolle mehr sollte es ein hexen opfer geben wird es hier getötet
            }
        }
        if (hexeOpferID != -1) {
            data!!.moveToFirst()
            werwolfopferGefunden = false
            if (data!!.getInt(0) == hexeOpferID) {
                werwolfopferGefunden = true
                werwolfOpferName = data!!.getString(1)
                werwolfOpferCharakter = data!!.getString(2)
            }
            while (data!!.moveToNext() && !werwolfopferGefunden) {
                if (data!!.getInt(0) == hexeOpferID) {
                    werwolfopferGefunden = true
                    werwolfOpferName = data!!.getString(1)
                    werwolfOpferCharakter = data!!.getString(2)
                }
            }
            sicherToeten(hexeOpferID, werwolfOpferName, werwolfOpferCharakter)
        }
    }

    private fun weisserWerwolfAuswerten() {
        var opferZweiID = -1
        if (weisserWerwolfOpferID != -1) //Werwolf existiert
        {
            //Werte des Opfers ermitteln:
            data!!.moveToFirst() //Daten des Werwolfopfers werden ermittelt
            var werwolfopferGefunden = false
            var werwolfOpferName: String? = ""
            var werwolfOpferVerzaubert: String? = ""
            var werwolfOpferCharakter = ""
            if (data!!.getInt(0) == werwolfOpferID) {
                werwolfopferGefunden = true
                werwolfOpferName = data!!.getString(1)
                werwolfOpferCharakter = data!!.getString(2)
                werwolfOpferVerzaubert = data!!.getString(4)
            }
            while (data!!.moveToNext() && !werwolfopferGefunden) {
                if (data!!.getInt(0) == werwolfOpferID) {
                    werwolfopferGefunden = true
                    werwolfOpferName = data!!.getString(1)
                    werwolfOpferCharakter = data!!.getString(2)
                    werwolfOpferVerzaubert = data!!.getString(4)
                }
            }
            var waechterID = -1
            var diebID = -1
            var waechterGefunden = false
            var diebGefunden = false


            //Werte wächter bestimmen
            if (anzahlWaechter > 0) {
                data!!.moveToFirst()
                if (data!!.getString(2).compareTo("waechter") == 0) {
                    waechterGefunden = true
                    waechterID = data!!.getInt(0)
                }
                while (data!!.moveToNext() && !waechterGefunden) {
                    if (data!!.getString(2).compareTo("waechter") == 0) {
                        waechterGefunden = true
                        waechterID = data!!.getInt(0)
                    }
                }
            }

            //werte Dieb bestimmen
            if (anzahlDieb > 0) {
                data!!.moveToFirst()
                if (data!!.getString(2).compareTo("dieb") == 0) {
                    diebGefunden = true
                    diebID = data!!.getInt(0)
                }
                while (data!!.moveToNext() && !diebGefunden) {
                    if (data!!.getString(2).compareTo("dieb") == 0) {
                        diebGefunden = true
                        diebID = data!!.getInt(0)
                    }
                }
            }

            //wächter ist das Ofer
            if (werwolfOpferCharakter.compareTo("waechter") == 0) {
                if (schlafplatzWaechterID != waechterID) //hier muss noch geprüft werden ob der dieb beim wächter schläft
                {
                    if (waechterID != schlafplatzDiebID) {
                        weisserWerwolfOpferID = -1
                        Log.d(ContentValues.TAG, "Werwolf tötet wächter, aber niemand daheim")
                    } else {
                        werwolfOpferID = diebID
                        Log.d(ContentValues.TAG, "dieb bei wächter wächter aber nicht daheim")
                    }
                }
            }

            //wächter rettet das Opfer
            if (weisserWerwolfOpferID != -1) {
                if (schlafplatzWaechterID == werwolfOpferID) {
                    weisserWerwolfOpferID = -1
                    Log.d(ContentValues.TAG, "Wächter hat das Opfer gerettet")
                }
            }


            //Hat der Dieb was mit dem Opfer zu tun?
            if (weisserWerwolfOpferID != -1 && anzahlDieb > 0) {
                if (diebID != schlafplatzDiebID) //Dieb ist nicht daheim -> gilt als nicht normaler bürger
                {
                    if (schlafplatzDiebID == weisserWerwolfOpferID) {  //dieb ist beim Opfer -> muss auch sterben
                        opferZweiID = diebID
                    }
                    if (anzahlWaechter > 0) {
                        if (schlafplatzDiebID == waechterID) {
                            weisserWerwolfOpferID = diebID
                        }
                    }
                    if (diebID == weisserWerwolfOpferID) {
                        weisserWerwolfOpferID = -1
                    }
                }
            }

            //Attribute des Finalen Opfers bestimmen, dann töten
            if (weisserWerwolfOpferID != -1) {
                data!!.moveToFirst() //Die werte müssen neu bestimmt werden, da der Dieb jetzt zum Opfergeworden sein kann.
                werwolfopferGefunden = false
                if (data!!.getInt(0) == weisserWerwolfOpferID) {
                    werwolfopferGefunden = true
                    werwolfOpferName = data!!.getString(1)
                    werwolfOpferCharakter = data!!.getString(2)
                }
                while (data!!.moveToNext() && !werwolfopferGefunden) {
                    if (data!!.getInt(0) == weisserWerwolfOpferID) {
                        werwolfopferGefunden = true
                        werwolfOpferName = data!!.getString(1)
                        werwolfOpferCharakter = data!!.getString(2)
                    }
                }
                if (weisserWerwolfOpferID == hexeOpferID) {
                    hexeOpferID = -1
                }
                sicherToeten(weisserWerwolfOpferID, werwolfOpferName, werwolfOpferCharakter)
            }

            //Opfer 2 wird ermittelt und getötet
            if (opferZweiID != -1) {
                data!!.moveToFirst()
                werwolfopferGefunden = false
                if (data!!.getInt(0) == opferZweiID) {
                    werwolfopferGefunden = true
                    werwolfOpferName = data!!.getString(1)
                    werwolfOpferCharakter = data!!.getString(2)
                }
                while (data!!.moveToNext() && !werwolfopferGefunden) {
                    if (data!!.getInt(0) == opferZweiID) {
                        werwolfopferGefunden = true
                        werwolfOpferName = data!!.getString(1)
                        werwolfOpferCharakter = data!!.getString(2)
                    }
                }
                if (opferZweiID == hexeOpferID) {
                    hexeOpferID = -1
                }
                sicherToeten(opferZweiID, werwolfOpferName, werwolfOpferCharakter)
            }
        }
    }

    private fun auswerten() {
        data = mDatabaseHelper!!.data
        data?.moveToFirst()

        //Liebespar lebt; nur noch drei persoenn übrig
        var verliebtGefundenAnzahl = 1
        while (data?.moveToNext() == true) {
            verliebtGefundenAnzahl++
        }
        if (liebenderEinsID != -1) {
            if (verliebtGefundenAnzahl <= 3 && !liebespaarEntdeckt) {
                siegbildschirmOeffnen("liebespaar")
            }
        }
        var anzahlNichtWerwolf =
            anzahlRitter + anzahlDieb + anzahlMaedchen + anzahlJaeger + anzahlWaechter + anzahlFreunde + anzahlHexe + anzahlSeher + anzahlBuerger + anzahlFloetenspieler + anzahlAmor + anzahlJunges
        if (werwolfDurchUrwolfID != -1) {
            anzahlNichtWerwolf--
        }
        if (anzahlNichtWerwolf < anzahlWeisserWerwolf + anzahlUrwolf + anzahlWerwolf) {
            if (anzahlUrwolf + anzahlWerwolf > 0) {
                siegbildschirmOeffnen("werwoelfe")
            } else {
                siegbildschirmOeffnen("ww")
            }
        }
        val anzahltest = anzahlWeisserWerwolf + anzahlUrwolf + anzahlWerwolf
        //keine Werwölfe mehr da
        if (anzahlWeisserWerwolf + anzahlUrwolf + anzahlWerwolf == 0) {
            siegbildschirmOeffnen("buerger")
        }
        data?.moveToFirst()
        var nichtverzaubertGefunden = false
        if (data?.getString(4)?.compareTo("ja") != 0) {
            nichtverzaubertGefunden = true
        }
        while (data?.moveToNext() == true && !nichtverzaubertGefunden) {
            if (data?.getString(4)?.compareTo("ja") != 0) {
                nichtverzaubertGefunden = true
            }
        }
        if (!nichtverzaubertGefunden) {
            siegbildschirmOeffnen("floetenspieler")
        }
    }

    private fun siegbildschirmOeffnen(sieger: String) {
        val intent = Intent(this, VictoryActivity::class.java)
        intent.putExtra("sieger", sieger)
        startActivity(intent)
    }

    private fun sicherToeten(ID: Int, nameOpfer: String?, charakterOpfer: String?) {
        val jaegerGefunden = false
        var charaktername = ""
        var charakterID = -1
        if ((ID == liebenderEinsID || ID == liebenderZweiID) && !liebespaarEntdeckt) //Das ganze auch für sich nicht liebende
        {
            if (hexeOpferID == liebenderEinsID || hexeOpferID == liebenderZweiID) {
                hexeOpferID = -1
            }
            //todo: muss hier auch opfer 2 beachtet werden???
            var nameGefunden = false
            data!!.moveToFirst()
            if (data!!.getInt(0) == liebenderZweiID) {
                nameGefunden = true
                tot = """
                    $tot
                    ${data!!.getString(1)}
                    """.trimIndent()
                charaktername = data!!.getString(2)
                anzahlMindern(charaktername, data!!.getInt(0))
            }
            while (data!!.moveToNext() && !nameGefunden) {
                if (data!!.getInt(0) == liebenderZweiID) {
                    nameGefunden = true
                    tot = """
                        $tot
                        ${data!!.getString(1)}
                        """.trimIndent()
                    charaktername = data!!.getString(2)
                    anzahlMindern(charaktername, data!!.getInt(0))
                }
            }
            if (nameGefunden) {
                nameGefunden = false
                data!!.moveToFirst()
                if (data!!.getInt(0) == liebenderEinsID) {
                    nameGefunden = true
                    charaktername = data!!.getString(2)
                    tot = """
                        $tot
                        ${data!!.getString(1)}
                        """.trimIndent()
                    anzahlMindern(charaktername, data!!.getInt(0))
                }
                while (data!!.moveToNext() && !nameGefunden) {
                    if (data!!.getInt(0) == liebenderEinsID) {
                        nameGefunden = true
                        charaktername = data!!.getString(2)
                        tot = """
                            $tot
                            ${data!!.getString(1)}
                            """.trimIndent()
                        anzahlMindern(charaktername, data!!.getInt(0))
                    }
                }
                if (nameGefunden) {
                    liebespaarEntdeckt = true
                    if (vorbildID == liebenderEinsID || vorbildID == liebenderZweiID) {
                        vorbildGestorbenDialog()
                    }
                    mDatabaseHelper!!.deleteName("" + liebenderEinsID)
                }
                mDatabaseHelper!!.deleteName("" + liebenderZweiID)
            }
        } else  //keiner des Liebespaares getötet
        {
            //Attribute des zu tötenden ermitteln
            var nameGefunden = false
            data!!.moveToFirst()
            if (data!!.getInt(0) == ID) {
                nameGefunden = true
                charaktername = data!!.getString(2)
                tot = """
                    $tot
                    ${data!!.getString(1)}
                    """.trimIndent()
                charakterID = data!!.getInt(0)
            }
            while (data!!.moveToNext() && !nameGefunden) {
                if (data!!.getInt(0) == ID) {
                    nameGefunden = true
                    charaktername = data!!.getString(2)
                    tot = """
                        $tot
                        ${data!!.getString(1)}
                        """.trimIndent()
                    charakterID = data!!.getInt(0)
                }
            }
            if (nameGefunden) {
                anzahlMindern(charaktername, charakterID)

                //sollte das Opfer das Vorbild es Werwolfjungen sein; Dialog entsprechend anpassen
                if (vorbildID == ID) {
                    vorbildGestorbenDialog()
                }
                mDatabaseHelper!!.deleteName("" + ID)
            }
        }
        data = mDatabaseHelper!!.data
    }

    private fun anzahlMindern(charaktername: String, charakterID: Int) {
        if (charakterID == werwolfDurchUrwolfID) {
            anzahlWerwolf--
        }
        when (charaktername) {
            "werwolf" -> if (anzahlWerwolf > 0) {
                anzahlWerwolf--
                //Toast.makeText(Nacht_Liste.this, "mindern anzahlWerwolf:" + anzahlWerwolf, Toast.LENGTH_SHORT).show();
            }
            "buerger" -> if (anzahlBuerger > 0) {
                anzahlBuerger--
                //Toast.makeText(Nacht_Liste.this, "mindern anzahlBuerger:" + anzahlBuerger, Toast.LENGTH_SHORT).show();
            }
            "seher" -> if (anzahlSeher > 0) {
                anzahlSeher--
            }
            "hexe" -> if (anzahlHexe > 0) {
                anzahlHexe--
                //Toast.makeText(Nacht_Liste.this, "mindern anzahlHexe:" + anzahlHexe, Toast.LENGTH_SHORT).show();
            }
            "floetenspieler" -> if (anzahlFloetenspieler > 0) {
                anzahlFloetenspieler--
            }
            "freunde" -> if (anzahlFreunde > 0) {
                anzahlFreunde--
            }
            "amor" -> if (anzahlAmor > 0) {
                anzahlAmor--
            }
            "urwolf" -> if (anzahlUrwolf > 0) {
                anzahlUrwolf--
            }
            "weisserwerwolf" -> if (anzahlWeisserWerwolf > 0) {
                anzahlWeisserWerwolf--
            }
            "waechter" -> if (anzahlWaechter > 0) {
                anzahlWaechter--
            }
            "junges" -> if (anzahlJunges > 0) {
                anzahlJunges--
            }
            "jaeger" -> if (anzahlJaeger > 0) {
                Log.d(ContentValues.TAG, "jager inkrementiert")
                jaegerDialog()
                anzahlJaeger--
            }
            "maedchen" -> if (anzahlMaedchen > 0) {
                anzahlMaedchen--
            }
            "dieb" -> if (anzahlDieb > 0) {
                anzahlDieb--
            }
            "ritter" -> if (anzahlRitter > 0) {
                anzahlRitter--
                ritterDialog()
                ritterletzteRundeGetoetet = true
            }
        }
    }

    private fun ritterDialog() {
        ritterAktiv = true
    }

    private fun ritterDialogVorbereiten() {
        charakterPosition = 21
        binding.description.text =
            "Der Ritter ist letzte Nacht gestorben. Wähle jetzt den nächsten Werwolf zur rechten das verstorbenen Jägers aus."
        binding.textSpielstand.text = "Ritter"
        binding.layoutSpielstand.background?.setTint(
            ContextCompat.getColor(
                this,
                R.color.knight
            )
        )
        binding.weiterNacht.isClickable = false
        binding.weiterNacht.background.setTint(
            ContextCompat.getColor(
                this,
                R.color.blue_unclickable
            )
        )
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
        binding.description.text = "Der Jäger ist gestorben. Er darf eine weitere Person töten:"
        binding.textSpielstand.text = "Jäger"
        binding.layoutSpielstand.background?.setTint(
            ContextCompat.getColor(
                this,
                R.color.hunter
            )
        )
        binding.weiterNacht.isClickable = false
        binding.weiterNacht.background.setTint(
            ContextCompat.getColor(
                this,
                R.color.blue_unclickable
            )
        )
        nightListAdapter.notifyDataSetChanged()
        binding.personen.visibility = View.VISIBLE
    }

    private fun jaegerToeten() {
        binding.personen.visibility = View.GONE
        jaegerAktiv = false
        var jaegerOpferGefunden = false
        var jaegerOpferName: String? = ""
        var jaegerOpferCharakter: String? = ""
        if (data!!.getInt(0) == jaegerOpfer) {
            jaegerOpferGefunden = true
            jaegerOpferName = data!!.getString(1)
            jaegerOpferCharakter = data!!.getString(2)
        }
        while (data!!.moveToNext() && !jaegerOpferGefunden) {
            if (data!!.getInt(0) == hexeOpferID) {
                jaegerOpferGefunden = true
                jaegerOpferName = data!!.getString(1)
                jaegerOpferCharakter = data!!.getString(2)
            }
        }
        sicherToeten(jaegerOpfer, jaegerOpferName, jaegerOpferCharakter)
        charakterPosition = charakterPositionJaegerBackup
        // charakterPosition++;
        charakterPositionBestimmen()
    }

    private fun vorbildGestorbenDialog() {
        val s = binding.description.text.toString()
        binding.description.text =
            """
            ${s}Das Vorbild ist verstorben. Ab der nächsten Nacht wacht das Junge mit den Werwölfen gemeinsam auf.
            
            
            """.trimIndent()
        var jungesGefunden = false
        var jungesID = -1
        var jungesName: String? = ""
        var jungesVerzaubert: String? = ""
        anzahlWerwolf++
        anzahlJunges--

        //junges als Eintrag finden und durch einen werwolf ersetzten
        data!!.moveToFirst()
        if (data!!.getString(2).compareTo("junges") == 0) {
            jungesGefunden = true
            jungesID = data!!.getInt(0)
            jungesName = data!!.getString(1)
            jungesVerzaubert = data!!.getString(4)
            mDatabaseHelper!!.deleteName("" + jungesID)
            mDatabaseHelper!!.addjungesExtra(jungesName, jungesVerzaubert)
        }
        while (data!!.moveToNext() && !jungesGefunden) {
            if (data!!.getString(2).compareTo("junges") == 0) {
                jungesGefunden = true
                jungesID = data!!.getInt(0)
                jungesName = data!!.getString(1)
                jungesVerzaubert = data!!.getString(4)
                mDatabaseHelper!!.deleteName("" + jungesID)
                mDatabaseHelper!!.addjungesExtra(jungesName, jungesVerzaubert)
            }
        }

        //Wenn jungesID gleich mit einer der liebenden ID, so muss diese die neue ID des Elementes erhalten Wird anhand des Namen ermittelt
        if (liebenderEinsID == jungesID) {
            data!!.moveToFirst()
            var vorbildGefunden = false
            if (data!!.getString(1).compareTo(jungesName!!) == 0) {
                vorbildGefunden = true
                liebenderEinsID = data!!.getInt(0)
            }
            while (data!!.moveToNext() && !vorbildGefunden) {
                if (data!!.getString(1).compareTo(jungesName) == 0) {
                    vorbildGefunden = true
                    liebenderEinsID = data!!.getInt(0)
                }
            }
        }
        if (liebenderZweiID == jungesID) {
            data!!.moveToFirst()
            var vorbildGefunden = false
            if (data!!.getString(1).compareTo(jungesName!!) == 0) {
                vorbildGefunden = true
                liebenderZweiID = data!!.getInt(0)
            }
            while (data!!.moveToNext() && !vorbildGefunden) {
                if (data!!.getString(1).compareTo(jungesName) == 0) {
                    vorbildGefunden = true
                    liebenderZweiID = data!!.getInt(0)
                }
            }
        }
    }

    private fun buergeropferToeten() {
        var charaktername: String? = ""
        var persname: String? = ""
        var persGefunden = false
        data = mDatabaseHelper!!.data
        data?.moveToFirst()
        if (data?.getInt(0) == buergerOpfer) {
            persGefunden = true
            charaktername = data?.getString(2)
            persname = data?.getString(1)
        }
        while (data?.moveToNext() == true && !persGefunden) {
            if (data?.getInt(0) == buergerOpfer) {
                persGefunden = true
                charaktername = data?.getString(2)
                persname = data?.getString(1)
            }
        }
        sicherToeten(buergerOpfer, persname, charaktername)
        auswerten()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.personToetenHexe, R.id.textViewPersonToeten -> {
                personToetenHexe!!.isChecked = !hexeToetenGedrueckt
                hexeToetenGedrueckt = !hexeToetenGedrueckt
                if (hexeToetenGedrueckt) {
                    binding.personen.visibility = View.VISIBLE
                    nightListAdapter.notifyDataSetChanged()
                    binding.weiterNacht.isClickable = false
                    binding.weiterNacht.background?.setTint(
                        ContextCompat.getColor(
                            this,
                            R.color.blue_unclickable
                        )
                    )
                    trankTodEinsetzbar = false
                    CharakterpositionErstInkementieren = true
                    charakterPosition--
                } else {
                    binding.personen.visibility = View.INVISIBLE
                    nightListAdapter.notifyDataSetChanged()
                    binding.weiterNacht.isClickable = true
                    binding.weiterNacht.background?.setTint(
                        ContextCompat.getColor(
                            this,
                            R.color.blue
                        )
                    )
                    trankTodEinsetzbar = true
                    CharakterpositionErstInkementieren = false
                    charakterPosition++
                }
            }
            R.id.opferRettenHexe, R.id.textViewOpferRetten -> {
                binding.opferRettenHexe.isChecked = !hexeRettenGedrueckt
                hexeRettenGedrueckt = !hexeRettenGedrueckt
                if (hexeRettenGedrueckt) {
                    trankLebenEinsetzbar = false
                    werwolfOpferID = -1
                } else {
                    trankLebenEinsetzbar = true
                    werwolfOpferID = werwolfOpferIDBackupHexe
                }
            }
            R.id.weiterNacht -> {
                Log.d(ContentValues.TAG, "weiternacht Position: $charakterPosition")
                if (charakterPosition == 20) {
                    jaegerToeten()
                }
                if (charakterPosition == 21) {
                    ritterDialogToeten()
                } else {
                    if (CharakterpositionErstInkementieren) {
                        charakterPosition++
                        CharakterpositionErstInkementieren = false
                    }
                    charakterPositionBestimmen()
                }
            }
            R.id.textViewUrwolfVeto, R.id.checkboxUrwolfVeto -> if (urwolfVeto == 1) {
                binding.checkboxUrwolfVeto.isChecked = false
                urwolfVeto = 0
            } else {
                binding.checkboxUrwolfVeto.isChecked = true
                urwolfVeto = 1
            }
            R.id.description -> {
                langerText = !langerText
                when (charakterPosition) {
                    0 -> if (binding.personen.visibility == View.VISIBLE) {
                        if (langerText) {
                            binding.description.text =
                                "Als erstes erwacht der Amor und zeigt auf zwei Personen, die sich augenblicklich unsterblich ineinander verlieben. - Amor schläft wieder ein. Ich tippe die beiden verliebten jetzt an un sie schauen sich tief in die Augen. - Auch sie schlafen jetzt wieder ein."
                        } else {
                            binding.description.text =
                                "Als erstes erwacht der Amor und zeigt auf zwei Personen..."
                        }
                    } else {
                        binding.description.text = "Das ganze Dorf schläft ein."
                        langerText = !langerText
                    }
                    1 -> if (langerText) {
                        binding.description.text =
                            "Ich tippe alle Freunde kurz an. - Jetzt erwachen die Freunde und schauen sich an, um sich später wiederzuerkennen. - Die Freunde schlafen beruhigt wieder ein, da sie wisssen, dass sie nicht alleine sind."
                    } else {
                        binding.description.text =
                            "Ich tippe alle Freunde kurz an. - Jetzt erwachen die Freunde..."
                    }
                    2 -> if (binding.personen.visibility == View.VISIBLE) {
                        if (langerText) {
                            binding.description.text =
                                "Jetzt erwacht der Dieb und sucht sich eine Person aus, bei der er oder sie die Nacht verbringen möchten. - Er zeigt auf diese Person und schläft danach wieder ein."
                        } else {
                            binding.description.text =
                                "Jetzt erwacht der Dieb und sucht sich eine Person aus..."
                        }
                    } else {
                        if (langerText) {
                            binding.description.text =
                                "Ich tippe alle Freunde kurz an. - Jetzt erwachen die Freunde und schauen sich an, um sich später wiederzuerkennen. - Die Freunde schlafen beruhigt wieder ein, da sie wisssen, dass sie nicht alleine sind."
                        } else {
                            binding.description.text =
                                "Ich tippe alle Freunde kurz an. - Jetzt erwachen die Freunde..."
                        }
                    }
                    3 -> if (langerText) {
                        binding.description.text =
                            "Der Wächter wählt eine Person, die er diese Nacht beschützen möchte. - Der Wächter schläft wieder ein."
                    } else {
                        binding.description.text =
                            "Der Wächter wählt eine Person, die er diese Nacht beschützen..."
                    }
                    4 -> if (langerText) {
                        binding.description.text =
                            "Das Werwolfjunge erwacht und sucht sich ein Vorbild aus. Sollte dieses Vorbild sterben, wirst du auch ein Werwolf und wachst gemeinsam mit ihnen auf. -\n Das Junge schläft wieder."
                    } else {
                        binding.description.text =
                            "Das Werwolfjunge erwacht und sucht sich ein Vorbild aus..."
                    }
                    5 -> if (langerText) {
                        binding.description.text =
                            "Als nächstes wacht der Seher auf und zeigt auf eine Person, deren Karte er sehen möchte. Wenn er sie gesehen hat schläft er wieder ein."
                    } else {
                        binding.description.text =
                            "Als nächstes wacht der Seher auf und zeigt auf eine Person..."
                    }
                    6 -> if (langerText) {
                        binding.description.text =
                            "Zuletzt erwacht der bezaubernde Flötenspieler und darf eine Person seiner Wahl verzaubern. Hat er alle Mitspieler verzaubert gewinnt er."
                    } else {
                        binding.description.text =
                            "Zuletzt erwacht der bezaubernde Flötenspieler und darf..."
                    }
                    7 -> if (langerText) {
                        binding.description.text =
                            "Jetzt wachen die Werwölfe auf und suchen sich ihr Opfer für diese Nacht aus. Haben sie sich entschieden schlafen sie auch schon wieder ein."
                    } else {
                        binding.description.text =
                            "Jetzt wachen die Werwölfe auf und suchen sich ihr Opfer..."
                    }
                    8, 9 -> {}
                    10 -> if (langerText) {
                        binding.description.text =
                            "Als nächstes wacht die Hexe auf. Sie kann das Opfer -auf Opfer zeigen- retten, nichtstun, oder  eine weitere Person mit in den Tod reißen. -Dabei die drei Handzeichen für die Hexe sichtbar vormachen."
                    } else {
                        binding.description.text =
                            "Als nächstes wacht die Hexe auf. Sie kann das Opfer..."
                    }
                    20 -> {}
                    else -> {}
                }
            }
            else -> {}
        }
    }
}