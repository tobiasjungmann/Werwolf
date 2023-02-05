package com.example.tobias.werwolf_v1.multipleDevices

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class HostToPlayerConnectionThread(
    private val client: Socket,
    private val spielleiter: HostConnectWithPlayers?
) : Thread() {
    private var output: PrintWriter? = null
    var spielbereit = false
        private set
    var nameNichtThread = ""
        private set
    var charakter = ""

    override fun run() {
        var stringData: String
        try {
            output = PrintWriter(client.getOutputStream())
            val input = BufferedReader(
                InputStreamReader(
                    client.getInputStream()
                )
            )
            val alleVerbunden = false
            while (!alleVerbunden) {
                stringData = input.readLine()
                if (stringData != null) {
                    val ipSender = stringData.substring(0, stringData.indexOf("/EndeIP"))
                    val nameNeu = stringData.substring(stringData.indexOf("/EndeIP") + 7)
                    if (spielleiter!!.iPToNameContainsName(nameNeu)) //name in der liste bereits enthalten
                    {
                        //todo: daten prüfen, ob schon als Objekt erzeugt, dafür Alle Ip Adresen überprüfen lamda expression
                        spielbereit =
                            if (spielleiter.iPToNameContainsKey(ipSender) && spielleiter.getNameIPToName(
                                    ipSender
                                )!!
                                    .compareTo(nameNeu) == 0
                            ) {
                                output!!.println("frei") //muss nicht eingefügt werden, ist schon so...
                                true
                            } else {
                                output!!.println("verwendet")
                                false
                            }
                    } else  //name noch nicht in der Map
                    {
                        if (spielleiter.iPToNameContainsKey(ipSender)) {
                            spielleiter.ipToNamePut(ipSender, nameNeu)
                            nameNichtThread = nameNeu
                        } else {
                            spielleiter.ipToNamePut(ipSender, nameNeu)
                            spielleiter.ipAdressenAdd(ipSender)
                            nameNichtThread = nameNeu
                        }
                        output!!.println("frei")
                        Log.d("VerbindungThread", "Senden abgeschlossen")
                        spielbereit = true
                    }
                    output!!.flush()
                }
            }
        } catch (ignored: IOException) {
        } finally {
            try {
                client.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun bereitSenden() {
        output!!.println("spielbereit$charakter")
        output!!.flush()
    }
}