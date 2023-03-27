package com.example.tobias.werwolf_v1.multipleDevices

import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.format.Formatter
import android.view.*
import android.widget.*
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AppCompatActivity
import com.example.tobias.werwolf_v1.database.models.DatabaseHelper
import com.example.tobias.werwolf_v1.ListNight
import com.example.tobias.werwolf_v1.R
//import com.google.zxing.WriterException
import maes.tech.intentanim.CustomIntent
import java.io.IOException
import java.net.ServerSocket
import java.util.*

class HostConnectWithPlayers : AppCompatActivity() {
    private var qrImage: ImageView? = null
    private var qrgEncoder: QRGEncoder? = null
    private var end = false
    val handler = Handler()
    private var ipAdressen: ArrayList<String>? = null
    private var clients: ArrayList<HostToPlayerConnectionThread>? = null
    private var listViewPersonen: ListView? = null
    private var adapter: CustomAdapter? = null
    private var mDatabaseHelper: DatabaseHelper? = null
    private var data: Cursor? = null
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
    private var anzahlAmorZuweisung = 0
    private var anzahlWerwolfZuweisung = 0
    private var anzahlHexeZuweisung = 0
    private var anzahlDiebZuweisung = 0
    private var anzahlSeherZuweisung = 0
    private var anzahlJungesZuweisung = 0
    private var anzahlJaegerZuweisung = 0
    private var anzahlBuergerZuweisung = 0
    private var anzahlWaechterZuweisung = 0
    private var anzahlWeisserWerwolfZuweisung = 0
    private var anzahlMaedchenZuweisung = 0
    private var anzahlFloetenspielerZuweisung = 0
    private var anzahlUrwolfZuweisung = 0
    private var anzahlRitterZuweisung = 0
    private var anzahlFreundeZuweisung = 0
    private var gesamtPerZuweisung = 0
    private var anzahlMitspielerText: TextView? = null
    private var ss: ServerSocket? = null
    private var ichselbst: HostConnectWithPlayers? = null
    private var pruefenThread: Thread? = null
    private var alleSpielbereit = false
    private var intent: Intent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_hostconnectwithplayers)
        if (!isNetworkAvailable) {
            dialogInternetConnection()
        }
        charakterDatenHolen()
        anzahlMitspielerText = findViewById(R.id.anzahlMitspielerText)
        anzahlMitspielerText?.text = "Mitspieler: 0 von $gesamtPer"
        qrImage = findViewById(R.id.imageQR)
        val manager = getSystemService(WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val width = point.x
        val height = point.y
        val smallerDimension = if (width < height) width else height
        val wm = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val ip = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
        qrgEncoder = QRGEncoder(ip, null, QRGContents.Type.TEXT, smallerDimension)
    /*    try {
            val bitmap = qrgEncoder!!.encodeAsBitmap()
            qrImage!!.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Log.v("d", e.toString())
        }*/ // todo add again
        ichselbst = this
        try {
            ss = ServerSocket(9002)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        startServerSocket()
        ipToName = TreeMap()
        ipAdressen = ArrayList()
        clients = ArrayList()
        listViewPersonen = findViewById(R.id.listViewPersonen)
        adapter = CustomAdapter(ipAdressen!!, ipToName!!)
        listViewPersonen?.adapter = adapter
        mDatabaseHelper = DatabaseHelper(this)
        mDatabaseHelper!!.clearDatabase()
        data = mDatabaseHelper!!.data

//todo:Datenbank leeren
        verbindungAuswerten()
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
    }

    private val isNetworkAvailable: Boolean
        private get() {
            val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    private fun dialogInternetConnection() {
        val builder = arrayOfNulls<AlertDialog.Builder>(1)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder[0] = AlertDialog.Builder(
                this@HostConnectWithPlayers,
                android.R.style.Theme_Material_Dialog_Alert
            )
        } else {
            builder[0] = AlertDialog.Builder(this@HostConnectWithPlayers)
        }
        builder[0]!!.setTitle("Hinweis")
            .setMessage("Stelle sicher, dass das Gerät it dem W-lan verbunden ist. Eine Verbindung mit den Spielern ist sonst nicht möglich")
            .setPositiveButton("Okay.") { dialog, which ->
                // weiter[0] =true;
            }
            .show()
    }

    override fun finish() {
        super.finish()
        try {
            ss!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        end = true
        CustomIntent.customType(this, "right-to-left")
    }

    private fun startServerSocket() {
        val thread = Thread(object : Runnable {
            private val stringData: String? = null
            override fun run() {
                while (!end) {
                    try {
                        val client = ss!!.accept()
                        val sv = HostToPlayerConnectionThread(client, ichselbst)
                        sv.start()
                        clients!!.add(sv)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        })
        thread.start()
    }

    fun verbindungAuswerten() {
        pruefenThread = Thread {
            while (!end) {
                if (ipAdressen!!.size == gesamtPer) {
                    alleSpielbereit = true
                    for (i in clients!!.indices) {
                        if (clients!![i].spielbereit == false) {
                            alleSpielbereit = false
                        }
                    }
                    if (alleSpielbereit == true) {
                        for (i in clients!!.indices) {
                            charakterZuweisen(clients!![i])
                            clients!![i].bereitSenden()
                        }
                        end = true
                        data = mDatabaseHelper!!.data
                        startActivity(intent)
                    }
                }
                runOnUiThread {
                    adapter!!.notifyDataSetChanged()
                    anzahlMitspielerText!!.text =
                        "Mitspieler: " + ipAdressen!!.size + " von " + gesamtPer
                }
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        pruefenThread!!.start()
    }

    private fun charakterDatenHolen() {
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
        gesamtPer = getIntent().extras!!.getInt("gesamtPer")
        anzahlAmorZuweisung = getIntent().extras!!.getInt("anzahlAmor")
        anzahlBuergerZuweisung = getIntent().extras!!.getInt("anzahlBuerger")
        anzahlWaechterZuweisung = getIntent().extras!!.getInt("anzahlWaechter")
        anzahlDiebZuweisung = getIntent().extras!!.getInt("anzahlDieb")
        anzahlHexeZuweisung = getIntent().extras!!.getInt("anzahlHexe")
        anzahlJaegerZuweisung = getIntent().extras!!.getInt("anzahlJaeger")
        anzahlJungesZuweisung = getIntent().extras!!.getInt("anzahlJunges")
        anzahlSeherZuweisung = getIntent().extras!!.getInt("anzahlSeher")
        anzahlWerwolfZuweisung = getIntent().extras!!.getInt("anzahlWerwolf")
        anzahlWeisserWerwolfZuweisung = getIntent().extras!!.getInt("anzahlWeisserWerwolf")
        anzahlRitterZuweisung = getIntent().extras!!.getInt("anzahlRitter")
        anzahlFloetenspielerZuweisung = getIntent().extras!!.getInt("anzahlFloetenspieler")
        anzahlFreundeZuweisung = getIntent().extras!!.getInt("anzahlFreunde")
        anzahlMaedchenZuweisung = getIntent().extras!!.getInt("anzahlMaedchen")
        anzahlUrwolfZuweisung = getIntent().extras!!.getInt("anzahlUrwolf")
        gesamtPerZuweisung = getIntent().extras!!.getInt("gesamtPer")
    }

    private fun charakterZuweisen(verbindung: HostToPlayerConnectionThread): String {
        val retValue = ""
        if (gesamtPerZuweisung > 0) {
            val position = Math.random().toInt() * gesamtPerZuweisung - 1
            if (position < anzahlWerwolfZuweisung && 0 < anzahlWerwolfZuweisung) {
                verbindung.charakter = "werwolf"
                anzahlWerwolfZuweisung--
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung && 0 < anzahlBuergerZuweisung) {
                verbindung.charakter = "buerger"
                anzahlBuergerZuweisung--
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung && 0 < anzahlAmorZuweisung) {
                verbindung.charakter = "amor"
                anzahlAmorZuweisung--
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung && 0 < anzahlHexeZuweisung) {
                verbindung.charakter = "hexe"
                anzahlHexeZuweisung--
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung + anzahlWaechterZuweisung && 0 < anzahlWaechterZuweisung) {
                verbindung.charakter = "waechter"
                anzahlWaechterZuweisung--
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung + anzahlWaechterZuweisung + anzahlMaedchenZuweisung && 0 < anzahlMaedchenZuweisung) {
                verbindung.charakter = "maedchen"
                anzahlMaedchenZuweisung--
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung + anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung && 0 < anzahlSeherZuweisung) {
                verbindung.charakter = "seher"
                anzahlSeherZuweisung--
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung + anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung + anzahlDiebZuweisung && 0 < anzahlDiebZuweisung) {
                verbindung.charakter = "dieb"
                anzahlDiebZuweisung--
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung +
                anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung + anzahlDiebZuweisung + anzahlJaegerZuweisung && 0 < anzahlJaegerZuweisung
            ) {
                verbindung.charakter = "jaeger"
                anzahlJaegerZuweisung--
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung +
                anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung + anzahlDiebZuweisung + anzahlJaegerZuweisung + anzahlRitterZuweisung && 0 < anzahlRitterZuweisung
            ) {
                verbindung.charakter = "ritter"
                anzahlRitterZuweisung--
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung +
                anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung + anzahlDiebZuweisung + anzahlJaegerZuweisung + anzahlRitterZuweisung + anzahlFloetenspielerZuweisung && 0 < anzahlFloetenspielerZuweisung
            ) {
                verbindung.charakter = "floetenspieler"
                anzahlFloetenspielerZuweisung--
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung +
                anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung + anzahlDiebZuweisung + anzahlJaegerZuweisung + anzahlRitterZuweisung + anzahlFloetenspielerZuweisung + anzahlFreundeZuweisung && 0 < anzahlFreundeZuweisung
            ) {
                verbindung.charakter = "freunde"
                anzahlFreundeZuweisung--
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung +
                anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung + anzahlDiebZuweisung +
                anzahlJaegerZuweisung + anzahlRitterZuweisung + anzahlFloetenspielerZuweisung + anzahlFreundeZuweisung + anzahlWeisserWerwolfZuweisung && 0 < anzahlWeisserWerwolfZuweisung
            ) {
                verbindung.charakter = "weisserwerwolf"
                anzahlWeisserWerwolfZuweisung--
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung +
                anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung + anzahlDiebZuweisung +
                anzahlJaegerZuweisung + anzahlRitterZuweisung + anzahlFloetenspielerZuweisung + anzahlFreundeZuweisung +
                anzahlWeisserWerwolfZuweisung + anzahlJungesZuweisung && 0 < anzahlJungesZuweisung
            ) {
                verbindung.charakter = "junges"
                anzahlJungesZuweisung--
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung +
                anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung + anzahlDiebZuweisung +
                anzahlJaegerZuweisung + anzahlRitterZuweisung + anzahlFloetenspielerZuweisung + anzahlFreundeZuweisung +
                anzahlWeisserWerwolfZuweisung + anzahlJungesZuweisung + anzahlUrwolfZuweisung && 0 < anzahlUrwolfZuweisung
            ) {
                verbindung.charakter = "urwolf"
                anzahlUrwolfZuweisung--
            }
        }
        gesamtPerZuweisung--
        mDatabaseHelper!!.addCharakter(verbindung.nameNichtThread, verbindung.charakter)
        return retValue
    }

    internal inner class CustomAdapter(
        private val ipAdressen: ArrayList<String>,
        private val ipToName: Map<String, String>
    ) : BaseAdapter() {
        private var pers: TextView? = null
        override fun getCount(): Int {
            return ipAdressen.size
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View, parent: ViewGroup): View {

            //charakter.setImageResource(R.drawable.amor_v3);
            var convertView = convertView
            convertView = layoutInflater.inflate(R.layout.mylistitemwhitetext, null)
            pers = convertView.findViewById<View>(R.id.textViewLabel) as TextView
            pers!!.text = ipAdressen[position] + "   " + ipToName[ipAdressen[position]]
            return convertView
        }
    }

    fun iPToNameContainsName(name: String): Boolean {
        return ipToName!!.containsValue(name)
    }

    fun iPToNameContainsKey(ipAdresse: String): Boolean {
        return ipToName!!.containsKey(ipAdresse)
    }

    fun getNameIPToName(ipAdresse: String): String? {
        return ipToName!![ipAdresse]
    }

    fun ipToNamePut(ipSender: String, name: String) {
        ipToName!![ipSender] = name
    }

    fun ipAdressenAdd(ipSender: String) {
        ipAdressen!!.add(ipSender)
    }

    companion object {
        private var ipToName: MutableMap<String, String>? = null
    }
}