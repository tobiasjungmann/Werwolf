package com.example.tobias.werwolf_v1.multipleDevices

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tobias.werwolf_v1.R
import com.example.tobias.werwolf_v1.StartScreenActivity
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import maes.tech.intentanim.CustomIntent
import java.io.*
import java.net.Socket

class PlayerConnectToHost : AppCompatActivity(), View.OnClickListener {
    private var square: LinearLayout? = null
    private var infoText: TextView? = null
    private var cameraSource: CameraSource? = null
    private var ipAdresseHost: String? = null
    private var bereit: Button? = null
    private var nameEingeben: EditText? = null
    private var statusVerbindung: TextView? = null
    private var wartenLayout: LinearLayout? = null
    private var nameLayout: LinearLayout? = null
    private var meineIp: String? = null
    private var statusBereit = 0
    private var einmalGedrueckt = false
    private var socket: Socket? = null
    private var out: OutputStream? = null
    private var output: PrintWriter? = null
    private var input: BufferedReader? = null
    private var empfangenAktiv = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_playerconnecttohost)
        einmalGedrueckt = false
        square = findViewById(R.id.square)
        infoText = findViewById(R.id.infoText)
        val wm = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        meineIp = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
        bereit = findViewById(R.id.bereit)
        bereit!!.setOnClickListener(this)
        nameEingeben = findViewById(R.id.nameEingeben)
        statusVerbindung = findViewById(R.id.statusVerbindung)
        // textWartenAnimation = findViewById(R.id.textWartenAnimation);
        wartenLayout = findViewById(R.id.wartenLayout)
        nameLayout = findViewById(R.id.nameLayout)
        val surfaceView = findViewById<SurfaceView>(R.id.cameraview)
        val barcodeDetector =
            BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build()
        cameraSource =
            CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).build()
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                if (ActivityCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling dafür sorgen dass nachkamera gefragt wird
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                try {
                    cameraSource!!.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource!!.stop()
            }
        })
        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {}
            override fun receiveDetections(detections: Detections<Barcode>) {
                val qrCodes = detections.detectedItems
                if (qrCodes.size() != 0) {
                    ipAdresseHost = qrCodes.valueAt(0).displayValue
                    Log.e("D", "qrCode erfasst: $ipAdresseHost")
                    val ipTest = ipAdresseHost!!.toCharArray()
                    var korrekterQRCode = true
                    if (ipAdresseHost!!.compareTo("0.0.0.0") == 0) {
                        korrekterQRCode = false
                    }
                    for (c in ipTest) {
                        if (!(c >= '0' && c <= '9' || c == '.')) {
                            korrekterQRCode = false
                        }
                    }
                    if (korrekterQRCode) {
                        // Toast.makeText(Spieler_Auswahl.this, "QR-Code enthält gültige IP-Adresse.", Toast.LENGTH_SHORT).show();
                        Log.e("D", "if: $ipAdresseHost")
                        object : Thread() {
                            override fun run() {
                                Log.e("D", "if: Thread$ipAdresseHost")
                                runOnUiThread {
                                    Log.e("D", "if runonui $ipAdresseHost")
                                    square!!.visibility = View.GONE
                                    infoText!!.visibility = View.GONE
                                    //  bereit.setVisibility(View.VISIBLE);
                                    nameLayout!!.visibility = View.VISIBLE
                                    statusVerbindung!!.visibility = View.VISIBLE
                                    //todo refreshen der UI, damit die elemente angezeigt werden
                                    cameraSource!!.stop()
                                }
                            }
                        }.start()
                    } else {
                        Log.e("D", "else $ipAdresseHost")
                        object : Thread() {
                            override fun run() {
                                Log.e("D", "else: thread$ipAdresseHost")
                                runOnUiThread {
                                    Log.e("D", "else: runonUI $ipAdresseHost")
                                    Toast.makeText(
                                        this@PlayerConnectToHost,
                                        "QR-Code enthält keine gültige IP-Adresse.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }.start()
                    }
                }
            }
        })
        if (!isNetworkAvailable) {
            dialogInternetConnection()
        }

        /*  wartenBeendet=false;
        threadWarten = new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        while (!wartenBeendet) {
                            switch (textWartenAnimation.getText().toString()) {
                                case "...":
                                    textWartenAnimation.setText(" ..");
                                    break;
                                case " ..":
                                    textWartenAnimation.setText("  .");
                                    break;
                                case "  .":
                                    textWartenAnimation.setText("   ");
                                    break;
                                case "   ":
                                    textWartenAnimation.setText(".  ");
                                    break;
                                case ".  ":
                                    textWartenAnimation.setText(".. ");
                                    break;
                                case ".. ":
                                    textWartenAnimation.setText("...");
                                    break;
                            }
                            try {
                                sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        };*/
    }

    //todo: dauerhaftes empfangenmit switch  das auch für namen sendeen arbeitet -> starts with...
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
                this@PlayerConnectToHost,
                android.R.style.Theme_Material_Dialog_Alert
            )
        } else {
            builder[0] = AlertDialog.Builder(this@PlayerConnectToHost)
        }
        builder[0]!!.setTitle("Hinweis")
            .setMessage("W-lan aktivieren um mit anderen Spielern zu verbinden.")
            .setPositiveButton("Okay.") { dialog, which ->
                // weiter[0] =true;
            }
            .show()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.bereit) {
            Log.e("D", "bereit gedrückt$ipAdresseHost")
            if (nameEingeben!!.text.toString().compareTo("") == 0) {
                statusVerbindung!!.text = "Du musst erst einen Namen eingeben."
                nameLayout!!.setBackgroundResource(R.drawable.button_rounded_corners)
                statusVerbindung!!.visibility = View.VISIBLE
            } else {
                nameEingeben!!.text.toString()
                statusVerbindung!!.text = "Daten werden gesendet..."
                nameSenden()
            }
        }
    }

    private fun empfangenThread() {
        empfangenAktiv = true
        val thread = Thread {
            while (empfangenAktiv) //todo auf empfangen warten, dass der name als gültig gesehen weren kann -> kein abbruch mehr
            {
                try {
                    val st = input!!.readLine()
                    Log.d("Verbindung", "empfangenThread empfangen: $st")
                    if (st.startsWith("spielbereit")) { //tod -> in die Karte übergehen
                        openCard(st.substring(11))
                    } else {
                        statusBereit = if (st.compareTo("frei") == 0) {
                            1
                            //threadWarten.start();
                        } else if (st.compareTo("verwendet") == 0) 0 else -1
                        runOnUiThread {
                            if (statusBereit == 1) {
                                Log.e("D", "bereit nach senden$ipAdresseHost")
                                if (einmalGedrueckt) {
                                    statusVerbindung!!.text = "Name geändert."
                                } else {
                                    bereit!!.text = "Namen Ändern"
                                    statusVerbindung!!.text = ""
                                    nameLayout!!.background?.setTint(
                                        ContextCompat.getColor(
                                            this,
                                            R.color.green
                                        )
                                    )
                                    //  threadWarten.start();
                                    wartenLayout!!.visibility = View.VISIBLE
                                    einmalGedrueckt = true
                                }
                            } else if (statusBereit == 0) {
                                nameLayout!!.setBackgroundResource(R.drawable.button_rounded_corners)
                                statusVerbindung!!.text =
                                    "Der Name " + nameEingeben!!.text.toString() + " wird bereits von einem deiner Mitspieler verwendet."
                            } else {
                                nameLayout!!.setBackgroundResource(R.drawable.button_rounded_corners)
                                statusVerbindung!!.text =
                                    "Verbindung fehlgeschlagen. Überprüfe die Internetverbindung beider Geräte."
                            }
                        }
                    }
                } catch (e: IOException) {
                    Log.d("Verbindung exception", "in exception")
                    e.printStackTrace()
                }
            }
        }
        thread.start()
    }

    private fun nameSenden(): Thread {
        val thread = Thread {
            try {
                if (socket == null) {
                    socket = Socket(ipAdresseHost, 9002)
                    out = socket!!.getOutputStream()
                    output = PrintWriter(out)
                    input = BufferedReader(InputStreamReader(socket!!.getInputStream()))
                }
                output!!.println(meineIp + "/EndeIP" + nameEingeben!!.text.toString())
                output!!.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            output!!.println(meineIp + "/EndeIP" + nameEingeben!!.text.toString())
            output!!.flush()
            if (!empfangenAktiv) {
                empfangenThread()
            }
            /*
                          final String st = input.readLine();
      
                          if (st.compareTo("frei") == 0) {
                              statusBereit = 1;
                              //threadWarten.start();
                          } else if (st.compareTo("verwendet") == 0)
                              statusBereit = 0;
                          else
                              statusBereit = -1;
      
      
                          //  output.close();
                          //  out.close();
                          //  socket.close();*/
            /*catch (ConnectException e) {
      
                          }*/
        }
        thread.start()
        return thread
    }

    override fun finish() {
        super.finish()
        try {
            out!!.close()
            output!!.close()
            socket!!.close()
            input!!.close()
            empfangenAktiv = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        CustomIntent.customType(this, "right-to-left")
    }

    fun openCard(rolle: String?) {
        val intent = Intent(this, PlayerCard::class.java)
        intent.putExtra("rolle", rolle)
        startActivity(intent)
        CustomIntent.customType(this, "left-to-right")
    }

    override fun onBackPressed() {
        val intent = Intent(this, StartScreenActivity::class.java)
        startActivity(intent)
    }
}