package com.example.tobias.werwolf_v1;

import android.util.Log;

import com.google.android.gms.common.api.Api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Spielleiter_Verbindung extends Thread {

    private Socket client;
    private PrintWriter output;
    private BufferedReader input;
    private Spielleiter_Verbinden spielleiter;
    private boolean alleVerbunden = false;
    private boolean spielbereit=false;
    private String name="";
    private String charakter="";


    public Spielleiter_Verbindung(Socket client, Spielleiter_Verbinden spielleiter) {
        this.client = client;
        this.spielleiter = spielleiter;
    }


    @Override
    public void run() {

        String stringData;
        try {
            output = new PrintWriter(client.getOutputStream());
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));

            while (!alleVerbunden) {
                stringData = input.readLine();
                if(stringData!=null) {
                    final String ipSender = stringData.substring(0, stringData.indexOf("/EndeIP"));
                    String nameNeu = stringData.substring(stringData.indexOf("/EndeIP") + 7);

                    if (spielleiter.iPToNameContainsName(nameNeu))        //name in der liste bereits enthalten
                    {
                        //todo: daten prüfen, ob schon als Objekt erzeugt, dafür Alle Ip Adresen überprüfen lamda expression

                        if (spielleiter.iPToNameContainsKey(ipSender) && spielleiter.getNameIPToName(ipSender).compareTo(nameNeu) == 0) {
                            output.println("frei");     //muss nicht eingefügt werden, ist schon so...
                            spielbereit = true;
                        } else {
                            output.println("verwendet");
                            spielbereit = false;
                        }
                    } else        //name noch nicht in der Map
                    {
                        if (spielleiter.iPToNameContainsKey(ipSender)) {
                            spielleiter.ipToNamePut(ipSender, nameNeu);
                            name=nameNeu;
                        } else {
                            spielleiter.ipToNamePut(ipSender, nameNeu);
                            spielleiter.ipAdressenAdd(ipSender);
                            name=nameNeu;
                        }
                        output.println("frei");
                        Log.d("VerbindungThread", "Senden abgeschlossen");
                        spielbereit = true;
                    }

                    output.flush();
                }
            }
        } catch (IOException e) {
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

public void bereitSenden()
{
    output.println("spielbereit"+charakter);
    output.flush();
}


    public void setCharakter(String charakter) {
        this.charakter = charakter;
    }
    public String getCharakter() {
        return charakter;
    }

    public boolean getSpielbereit()
    {
        return spielbereit;
    }

    public String getNameNichtThread()
    {
        return name;
    }
}
