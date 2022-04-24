package com.example.tobias.werwolf_v1.multipleDevices;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HostToPlayerConnectionThread extends Thread {

    private final Socket client;
    private PrintWriter output;
    private final HostConnectWithPlayers spielleiter;
    private boolean spielbereit=false;
    private String name="";
    private String charakter="";


    public HostToPlayerConnectionThread(Socket client, HostConnectWithPlayers spielleiter) {
        this.client = client;
        this.spielleiter = spielleiter;
    }


    @Override
    public void run() {

        String stringData;
        try {
            output = new PrintWriter(client.getOutputStream());
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

            boolean alleVerbunden = false;
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
        } catch (IOException ignored) {
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
