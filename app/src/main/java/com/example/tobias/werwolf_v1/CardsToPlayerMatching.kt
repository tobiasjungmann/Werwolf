package com.example.tobias.werwolf_v1

import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import maes.tech.intentanim.CustomIntent

class CardsToPlayerMatching : AppCompatActivity() {
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
    private var AmorId = 0
    private var WerwolfId = 0
    private var HexeId = 0
    private var DiebId = 0
    private var SeherId = 0
    private var JungesId = 0
    private var JaegerId = 0
    private var BuergerId = 0
    private var WaechterId = 0
    private var WeisserWerwolfId = 0
    private var MaedchenId = 0
    private var FloetenspielerId = 0
    private var UrwolfId = 0
    private var RitterId = 0
    private var FreundeId = 0
    private var nameAkt: String? = null
    private var mDatabaseHelper: DatabaseHelper? = null
    private var data: Cursor? = null
    private var persTxt: TextView? = null
    private var customAdapter: CustomAdapter? = null
    private var intent: Intent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_cardstoplayermatching)
        anzahlAmor = getIntent().extras!!.getInt("anzahlAmor")
        anzahlBuerger = getIntent().extras!!.getInt("anzahlBuerger")
        anzahlWaechter = getIntent().extras!!.getInt("anzahlWaechter")
        anzahlDieb = getIntent().extras!!.getInt("anzahlDieb")
        anzahlHexe = getIntent().extras!!.getInt("anzahlHexe")
        anzahlJaeger = getIntent().extras!!.getInt("anzahlJaeger")
        anzahlJunges = getIntent().extras!!.getInt("anzahlJunges")
        anzahlSeher = getIntent().extras!!.getInt("anzahlSeher")
        anzahlWerwolf = getIntent().extras!!.getInt("anzahlWerwolf")
        anzahlWeisserWerwolf = getIntent().extras!!.getInt("anzahlWeisserWerwolf")
        anzahlRitter = getIntent().extras!!.getInt("anzahlRitter")
        anzahlFloetenspieler = getIntent().extras!!.getInt("anzahlFloetenspieler")
        anzahlFreunde = getIntent().extras!!.getInt("anzahlFreunde")
        anzahlMaedchen = getIntent().extras!!.getInt("anzahlMaedchen")
        anzahlUrwolf = getIntent().extras!!.getInt("anzahlUrwolf")
        intent = Intent(this, ListNight::class.java)
        intent!!.putExtra("anzahlAmor", anzahlAmor)
        intent!!.putExtra("anzahlBuerger", anzahlBuerger)
        intent!!.putExtra("anzahlWaechter", anzahlWaechter)
        intent!!.putExtra("anzahlDieb", anzahlDieb)
        intent!!.putExtra("anzahlHexe", anzahlHexe)
        intent!!.putExtra("anzahlJaeger", anzahlJaeger)
        intent!!.putExtra("anzahlJunges", anzahlJunges)
        intent!!.putExtra("anzahlSeher", anzahlSeher)
        intent!!.putExtra("anzahlWerwolf", anzahlWerwolf)
        intent!!.putExtra("anzahlWeisserWerwolf", anzahlWeisserWerwolf)
        intent!!.putExtra("anzahlRitter", anzahlRitter)
        intent!!.putExtra("anzahlFloetenspieler", anzahlFloetenspieler)
        intent!!.putExtra("anzahlFreunde", anzahlFreunde)
        intent!!.putExtra("anzahlMaedchen", anzahlMaedchen)
        intent!!.putExtra("anzahlUrwolf", anzahlUrwolf)
        AmorId = -1
        WerwolfId = -1
        HexeId = -1
        DiebId = -1
        SeherId = -1
        JungesId = -1
        JaegerId = -1
        BuergerId = -1
        WaechterId = -1
        WeisserWerwolfId = -1
        MaedchenId = -1
        FloetenspielerId = -1
        UrwolfId = -1
        RitterId = -1
        FreundeId = -1
        mDatabaseHelper = DatabaseHelper(this)
        data = mDatabaseHelper!!.data
        persTxt = findViewById(R.id.persTxt)
        persTxt?.setTextColor(Color.WHITE)
        namenSchreiben()
        bilderFeldErstellen()
        val rollen = findViewById<ListView>(R.id.rollen)
        customAdapter = CustomAdapter()
        rollen.adapter = customAdapter
        rollen.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                val charakterHilfe = determineCharacter(position)
                Log.e("D", "Name:    $nameAkt")
                mDatabaseHelper!!.deleteOnlyName(nameAkt!!)
                mDatabaseHelper!!.addCharakter(nameAkt!!, charakterHilfe)
                namenSchreiben()
            }
    }

    fun namenSchreiben() {
        if (data!!.moveToNext()) {
            nameAkt = data!!.getString(1)
            persTxt!!.text = getString(R.string.nameWrapper, nameAkt)
        } else {
            startActivity(intent)
            CustomIntent.customType(this, "left-to-right")
        }
    }

    fun determineCharacter(position: Int): String {
        var retValue = ""
        if (position == WaechterId) {
            retValue = "waechter"
            anzahlWaechter--
        } else if (position == WerwolfId) {
            retValue = "werwolf"
            anzahlWerwolf--
        } else if (position == JaegerId) {
            retValue = "jaeger"
            anzahlJaeger--
        } else if (position == JungesId) {
            retValue = "junges"
            anzahlJunges--
        } else if (position == SeherId) {
            retValue = ""
            anzahlSeher--
        } else if (position == AmorId) {
            retValue = "amor"
            anzahlAmor--
        } else if (position == BuergerId) {
            retValue = "buerger"
            anzahlBuerger--
        } else if (position == DiebId) {
            retValue = "dieb"
            anzahlDieb--
        } else if (position == HexeId) {
            retValue = "hexe"
            anzahlHexe--
        } else if (position == WeisserWerwolfId) {
            retValue = "weisserwerwolf"
            anzahlWeisserWerwolf--
        } else if (position == FloetenspielerId) {
            retValue = "floetenspieler"
            anzahlFloetenspieler--
        } else if (position == UrwolfId) {
            retValue = "urwolf"
            anzahlUrwolf--
        } else if (position == RitterId) {
            retValue = "ritter"
            anzahlRitter--
        } else if (position == MaedchenId) {
            retValue = "maedchen"
            anzahlMaedchen--
        } else if (position == FreundeId) {
            retValue = "freunde"
            anzahlFreunde--
        }
        bilderFeldErstellen()
        customAdapter!!.notifyDataSetChanged()
        return retValue
    }

    fun bilderFeldErstellen() {
        AmorId = -1
        WerwolfId = -1
        HexeId = -1
        DiebId = -1
        SeherId = -1
        JungesId = -1
        JaegerId = -1
        BuergerId = -1
        WaechterId = -1
        WeisserWerwolfId = -1
        MaedchenId = -1
        FloetenspielerId = -1
        UrwolfId = -1
        RitterId = -1
        FreundeId = -1
        var position = 0
        if (anzahlWerwolf > 0) {
            WerwolfId = position
            position++
        }
        if (anzahlBuerger > 0) {
            BuergerId = position
            position++
        }
        if (anzahlAmor > 0) {
            AmorId = position
            position++
        }
        if (anzahlHexe > 0) {
            HexeId = position
            position++
        }
        if (anzahlWaechter > 0) {
            WaechterId = position
            position++
        }
        if (anzahlMaedchen > 0) {
            MaedchenId = position
            position++
        }
        if (anzahlSeher > 0) {
            SeherId = position
            position++
        }
        if (anzahlDieb > 0) {
            DiebId = position
            position++
        }
        if (anzahlJaeger > 0) {
            JaegerId = position
            position++
        }
        if (anzahlRitter > 0) {
            RitterId = position
            position++
        }
        if (anzahlFloetenspieler > 0) {
            FloetenspielerId = position
            position++
        }
        if (anzahlFreunde > 0) {
            FreundeId = position
            position++
        }
        if (anzahlJunges > 0) {
            JungesId = position
            position++
        }
        if (anzahlWeisserWerwolf > 0) {
            WeisserWerwolfId = position
            position++
        }
        if (anzahlUrwolf > 0) {
            UrwolfId = position
            position++
        }
    }

    internal inner class CustomAdapter : BaseAdapter() {
        override fun getCount(): Int {
            var summe = 0
            if (anzahlAmor > 0) {
                summe++
            }
            if (anzahlWerwolf > 0) {
                summe++
            }
            if (anzahlSeher > 0) {
                summe++
            }
            if (anzahlJunges > 0) {
                summe++
            }
            if (anzahlJaeger > 0) {
                summe++
            }
            if (anzahlHexe > 0) {
                summe++
            }
            if (anzahlDieb > 0) {
                summe++
            }
            if (anzahlWaechter > 0) {
                summe++
            }
            if (anzahlBuerger > 0) {
                summe++
            }
            if (anzahlWeisserWerwolf > 0) {
                summe++
            }
            if (anzahlMaedchen > 0) {
                summe++
            }
            if (anzahlFloetenspieler > 0) {
                summe++
            }
            if (anzahlUrwolf > 0) {
                summe++
            }
            if (anzahlRitter > 0) {
                summe++
            }
            if (anzahlFreunde > 0) {
                summe++
            }
            return summe
        }

        override fun getItem(position: Int): Any? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
            var convertView = convertView
            convertView = layoutInflater.inflate(R.layout.mylistitem, null)
            val charakter = convertView.findViewById<TextView>(R.id.charakter)
            val layoutcharakterRolle =
                convertView.findViewById<LinearLayout>(R.id.layoutcharakterRolle)
            if (position == WaechterId) {
                charakter.setText(R.string.waechter)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_waechter))
            } else if (position == WerwolfId) {
                charakter.setText(R.string.werwolf)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_werwolf))
            } else if (position == DiebId) {
                charakter.setText(R.string.dieb)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_dieb))
            } else if (position == SeherId) {
                charakter.setText(R.string.seher)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_seher))
            } else if (position == JaegerId) {
                charakter.setText(R.string.jaeger)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_jaeger))
            } else if (position == JungesId) {
                charakter.setText(R.string.junges)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_junges))
            } else if (position == BuergerId) {
                charakter.setText(R.string.buerger)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_buerger))
            } else if (position == HexeId) {
                charakter.setText(R.string.hexe)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_hexe))
            } else if (position == AmorId) {
                charakter.setText(R.string.amor)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_amor))
            } else if (position == MaedchenId) {
                charakter.setText(R.string.maedchen)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_maedchen))
            } else if (position == WeisserWerwolfId) {
                charakter.setText(R.string.weisser_werwolf)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_weisserwolf))
            } else if (position == FloetenspielerId) {
                charakter.setText(R.string.floetenspieler)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_floetenspieler))
            } else if (position == FreundeId) {
                charakter.setText(R.string.freunde)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_freunde))
            } else if (position == RitterId) {
                charakter.setText(R.string.ritter)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_ritter))
            } else if (position == UrwolfId) {
                charakter.setText(R.string.urwolf)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_urwolf))
            }
            return convertView
        }
    }

    override fun finish() {
        super.finish()
        CustomIntent.customType(this, "right-to-left")
    }
}