package com.example.tobias.werwolf_v1;

import static java.lang.Thread.sleep;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import maes.tech.intentanim.CustomIntent;

public class Spielleiter_Verbinden extends AppCompatActivity {

    private ImageView qrImage;
    private QRGEncoder qrgEncoder;
    private boolean end = false;
    final Handler handler = new Handler();
    private static Map<String, String> ipToName;
    private ArrayList<String> ipAdressen;
    private ArrayList<Spielleiter_Verbindung> clients;
    private ListView listViewPersonen;
    private CustomAdapter adapter;
    private DatabaseHelper mDatabaseHelper;
    private Cursor data;


    private int anzahlAmor;
    private int anzahlWerwolf;
    private int anzahlHexe;
    private int anzahlDieb;
    private int anzahlSeher;
    private int anzahlJunges;
    private int anzahlJaeger;
    private int anzahlBuerger;
    private int anzahlWaechter;
    private int anzahlWeisserWerwolf;
    private int anzahlMaedchen;
    private int anzahlFloetenspieler;
    private int anzahlUrwolf;
    private int anzahlRitter;
    private int anzahlFreunde;
    private int gesamtPer;

    private int anzahlAmorZuweisung;
    private int anzahlWerwolfZuweisung;
    private int anzahlHexeZuweisung;
    private int anzahlDiebZuweisung;
    private int anzahlSeherZuweisung;
    private int anzahlJungesZuweisung;
    private int anzahlJaegerZuweisung;
    private int anzahlBuergerZuweisung;
    private int anzahlWaechterZuweisung;
    private int anzahlWeisserWerwolfZuweisung;
    private int anzahlMaedchenZuweisung;
    private int anzahlFloetenspielerZuweisung;
    private int anzahlUrwolfZuweisung;
    private int anzahlRitterZuweisung;
    private int anzahlFreundeZuweisung;
    private int gesamtPerZuweisung;

    private TextView anzahlMitspielerText;
    private ServerSocket ss;
    private Spielleiter_Verbinden ichselbst;
    private Thread pruefenThread;
    private boolean alleSpielbereit = false;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spielleiter__verbinden);

        if (!isNetworkAvailable()) {
            dialogInternetConnection();
        }


        charakterDatenHolen();

        anzahlMitspielerText = findViewById(R.id.anzahlMitspielerText);
        anzahlMitspielerText.setText("Mitspieler: 0 von " + gesamtPer);
        qrImage = findViewById(R.id.imageQR);

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        qrgEncoder = new QRGEncoder(ip, null, QRGContents.Type.TEXT, smallerDimension);

        try {
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
            Log.v("d", e.toString());
        }


        ichselbst = this;
        try {
            ss = new ServerSocket(9002);
        } catch (IOException e) {
            e.printStackTrace();
        }
        startServerSocket();

        ipToName = new TreeMap<>();
        ipAdressen = new ArrayList<>();
        clients = new ArrayList<>();

        listViewPersonen = findViewById(R.id.listViewPersonen);
        adapter = new CustomAdapter(ipAdressen, ipToName);
        listViewPersonen.setAdapter(adapter);

        mDatabaseHelper = new DatabaseHelper(this);
        mDatabaseHelper.clearDatabase();
        data = mDatabaseHelper.getData();

//todo:Datenbank leeren
        verbindungAuswerten();
         intent = new Intent(this, Nacht_Liste.class);
        intent.putExtra("anzahlAmor", anzahlAmor);
        intent.putExtra("anzahlBuerger", anzahlBuerger);
        intent.putExtra("anzahlWaechter", anzahlWaechter);
        intent.putExtra("anzahlDieb", anzahlDieb);
        intent.putExtra("anzahlHexe", anzahlHexe);
        intent.putExtra("anzahlJaeger", anzahlJaeger);
        intent.putExtra("anzahlJunges", anzahlJunges);
        intent.putExtra("anzahlSeher", anzahlSeher);
        intent.putExtra("anzahlWerwolf", anzahlWerwolf);
        intent.putExtra("anzahlWeisserWerwolf", anzahlWeisserWerwolf);
        intent.putExtra("anzahlRitter", anzahlRitter);
        intent.putExtra("anzahlFloetenspieler", anzahlFloetenspieler);
        intent.putExtra("anzahlFreunde", anzahlFreunde);
        intent.putExtra("anzahlMaedchen", anzahlMaedchen);
        intent.putExtra("anzahlUrwolf", anzahlUrwolf);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void dialogInternetConnection() {
        AlertDialog.Builder[] builder = new AlertDialog.Builder[1];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder[0] = new AlertDialog.Builder(Spielleiter_Verbinden.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder[0] = new AlertDialog.Builder(Spielleiter_Verbinden.this);
        }
        builder[0].setTitle("Hinweis")
                .setMessage("Stelle sicher, dass das Gerät it dem W-lan verbunden ist. Eine Verbindung mit den Spielern ist sonst nicht möglich")
                .setPositiveButton("Okay.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // weiter[0] =true;
                    }

                })
                .show();
    }


    @Override
    public void finish() {
        super.finish();
        try {
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        end = true;
        CustomIntent.customType(this, "right-to-left");
    }


    private void startServerSocket() {
        Thread thread = new Thread(new Runnable() {

            private String stringData = null;

            @Override
            public void run() {
                while (!end) {
                    try {
                        Socket client = ss.accept();
                        Spielleiter_Verbindung sv = new Spielleiter_Verbindung(client, ichselbst);
                        sv.start();
                        clients.add(sv);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();


    }


    public void verbindungAuswerten() {
        pruefenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!end) {
                    if (ipAdressen.size() == gesamtPer) {
                        alleSpielbereit = true;
                        for (int i = 0; i < clients.size(); i++) {
                            if (clients.get(i).getSpielbereit() == false) {
                                alleSpielbereit = false;
                            }
                        }

                        if (alleSpielbereit == true) {
                            for (int i = 0; i < clients.size(); i++) {
                                charakterZuweisen(clients.get(i));
                                clients.get(i).bereitSenden();
                            }
                            end=true;
                            data=mDatabaseHelper.getData();
                            startActivity(intent);
                        }
                    }

                    runOnUiThread(new Runnable() {
                        public void run() {
                            adapter.notifyDataSetChanged();
                            anzahlMitspielerText.setText("Mitspieler: " + ipAdressen.size() + " von " + gesamtPer);
                        }
                    });

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        pruefenThread.start();
    }


    private void charakterDatenHolen() {
        anzahlAmor = getIntent().getExtras().getInt("anzahlAmor");
        anzahlBuerger = getIntent().getExtras().getInt("anzahlBuerger");
        anzahlWaechter = getIntent().getExtras().getInt("anzahlWaechter");
        anzahlDieb = getIntent().getExtras().getInt("anzahlDieb");
        anzahlHexe = getIntent().getExtras().getInt("anzahlHexe");
        anzahlJaeger = getIntent().getExtras().getInt("anzahlJaeger");
        anzahlJunges = getIntent().getExtras().getInt("anzahlJunges");
        anzahlSeher = getIntent().getExtras().getInt("anzahlSeher");
        anzahlWerwolf = getIntent().getExtras().getInt("anzahlWerwolf");
        anzahlWeisserWerwolf = getIntent().getExtras().getInt("anzahlWeisserWerwolf");
        anzahlRitter = getIntent().getExtras().getInt("anzahlRitter");
        anzahlFloetenspieler = getIntent().getExtras().getInt("anzahlFloetenspieler");
        anzahlFreunde = getIntent().getExtras().getInt("anzahlFreunde");
        anzahlMaedchen = getIntent().getExtras().getInt("anzahlMaedchen");
        anzahlUrwolf = getIntent().getExtras().getInt("anzahlUrwolf");
        gesamtPer = getIntent().getExtras().getInt("gesamtPer");

        anzahlAmorZuweisung = getIntent().getExtras().getInt("anzahlAmor");
        anzahlBuergerZuweisung = getIntent().getExtras().getInt("anzahlBuerger");
        anzahlWaechterZuweisung = getIntent().getExtras().getInt("anzahlWaechter");
        anzahlDiebZuweisung = getIntent().getExtras().getInt("anzahlDieb");
        anzahlHexeZuweisung = getIntent().getExtras().getInt("anzahlHexe");
        anzahlJaegerZuweisung = getIntent().getExtras().getInt("anzahlJaeger");
        anzahlJungesZuweisung = getIntent().getExtras().getInt("anzahlJunges");
        anzahlSeherZuweisung = getIntent().getExtras().getInt("anzahlSeher");
        anzahlWerwolfZuweisung = getIntent().getExtras().getInt("anzahlWerwolf");
        anzahlWeisserWerwolfZuweisung = getIntent().getExtras().getInt("anzahlWeisserWerwolf");
        anzahlRitterZuweisung = getIntent().getExtras().getInt("anzahlRitter");
        anzahlFloetenspielerZuweisung = getIntent().getExtras().getInt("anzahlFloetenspieler");
        anzahlFreundeZuweisung = getIntent().getExtras().getInt("anzahlFreunde");
        anzahlMaedchenZuweisung = getIntent().getExtras().getInt("anzahlMaedchen");
        anzahlUrwolfZuweisung = getIntent().getExtras().getInt("anzahlUrwolf");
        gesamtPerZuweisung = getIntent().getExtras().getInt("gesamtPer");
    }


    private String charakterZuweisen(Spielleiter_Verbindung verbindung) {
        String retValue = "";
        if (gesamtPerZuweisung > 0) {
            int position = ((int) Math.random() * gesamtPerZuweisung - 1);

            if (position < anzahlWerwolfZuweisung && 0<anzahlWerwolfZuweisung) {
                verbindung.setCharakter("werwolf");
                anzahlWerwolfZuweisung--;
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung&& 0<anzahlBuergerZuweisung) {
                verbindung.setCharakter("buerger");
                anzahlBuergerZuweisung--;
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung&& 0<anzahlAmorZuweisung) {
                verbindung.setCharakter("amor");
                anzahlAmorZuweisung--;
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung && 0<anzahlHexeZuweisung) {
                verbindung.setCharakter("hexe");
                anzahlHexeZuweisung--;
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung + anzahlWaechterZuweisung&& 0<anzahlWaechterZuweisung) {
                verbindung.setCharakter("waechter");
                anzahlWaechterZuweisung--;
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung + anzahlWaechterZuweisung + anzahlMaedchenZuweisung && 0< anzahlMaedchenZuweisung) {
                verbindung.setCharakter("maedchen");
                anzahlMaedchenZuweisung--;
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung + anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung && 0<anzahlSeherZuweisung) {
                verbindung.setCharakter("seher");
                anzahlSeherZuweisung--;
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung + anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung + anzahlDiebZuweisung && 0<anzahlDiebZuweisung) {
                verbindung.setCharakter("dieb");
                anzahlDiebZuweisung--;
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung +
                    anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung + anzahlDiebZuweisung + anzahlJaegerZuweisung && 0<anzahlJaegerZuweisung) {
                verbindung.setCharakter("jaeger");
                anzahlJaegerZuweisung--;
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung +
                    anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung + anzahlDiebZuweisung + anzahlJaegerZuweisung + anzahlRitterZuweisung&& 0<anzahlRitterZuweisung) {
                verbindung.setCharakter("ritter");
                anzahlRitterZuweisung--;
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung +
                    anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung + anzahlDiebZuweisung + anzahlJaegerZuweisung + anzahlRitterZuweisung + anzahlFloetenspielerZuweisung && 0<anzahlFloetenspielerZuweisung) {
                verbindung.setCharakter("floetenspieler");
                anzahlFloetenspielerZuweisung--;
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung +
                    anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung + anzahlDiebZuweisung + anzahlJaegerZuweisung + anzahlRitterZuweisung + anzahlFloetenspielerZuweisung + anzahlFreundeZuweisung&& 0< anzahlFreundeZuweisung) {
                verbindung.setCharakter("freunde");
                anzahlFreundeZuweisung--;
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung +
                    anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung + anzahlDiebZuweisung +
                    anzahlJaegerZuweisung + anzahlRitterZuweisung + anzahlFloetenspielerZuweisung + anzahlFreundeZuweisung +
                    anzahlWeisserWerwolfZuweisung && 0<anzahlWeisserWerwolfZuweisung) {
                verbindung.setCharakter("weisserwerwolf");
                anzahlWeisserWerwolfZuweisung--;
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung +
                    anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung + anzahlDiebZuweisung +
                    anzahlJaegerZuweisung + anzahlRitterZuweisung + anzahlFloetenspielerZuweisung + anzahlFreundeZuweisung +
                    anzahlWeisserWerwolfZuweisung + anzahlJungesZuweisung && 0<anzahlJungesZuweisung) {
                verbindung.setCharakter("junges");
                anzahlJungesZuweisung--;
            } else if (position < anzahlWerwolfZuweisung + anzahlBuergerZuweisung + anzahlAmorZuweisung + anzahlHexeZuweisung +
                    anzahlWaechterZuweisung + anzahlMaedchenZuweisung + anzahlSeherZuweisung + anzahlDiebZuweisung +
                    anzahlJaegerZuweisung + anzahlRitterZuweisung + anzahlFloetenspielerZuweisung + anzahlFreundeZuweisung +
                    anzahlWeisserWerwolfZuweisung + anzahlJungesZuweisung + anzahlUrwolfZuweisung && 0<anzahlUrwolfZuweisung) {
                verbindung.setCharakter("urwolf");
                anzahlUrwolfZuweisung--;
            }

        }
        gesamtPerZuweisung--;
        mDatabaseHelper.addCharakter(verbindung.getNameNichtThread(), verbindung.getCharakter());
        return retValue;
    }


    class CustomAdapter extends BaseAdapter {

        private TextView pers;
        private ArrayList<String> ipAdressen;
        private Map<String, String> ipToName;

        public CustomAdapter(ArrayList<String> ipAdressen, Map<String, String> ipToName) {
            this.ipAdressen = ipAdressen;
            this.ipToName = ipToName;
        }

        @Override
        public int getCount() {
            return ipAdressen.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //charakter.setImageResource(R.drawable.amor_v3);
            convertView = getLayoutInflater().inflate(R.layout.mein_list_item_text_weis, null);
            pers = (TextView) convertView.findViewById(R.id.textPer);

            pers.setText(ipAdressen.get(position) + "   " + ipToName.get(ipAdressen.get(position)));


            return convertView;
        }
    }

    public boolean iPToNameContainsName(String name) {
        return ipToName.containsValue(name);
    }

    public boolean iPToNameContainsKey(String ipAdresse) {
        return ipToName.containsKey(ipAdresse);
    }

    public String getNameIPToName(String ipAdresse) {
        return ipToName.get(ipAdresse);
    }

    public void ipToNamePut(String ipSender, String name) {
        ipToName.put(ipSender, name);
    }


    public void ipAdressenAdd(String ipSender) {
        ipAdressen.add(ipSender);
    }
}
