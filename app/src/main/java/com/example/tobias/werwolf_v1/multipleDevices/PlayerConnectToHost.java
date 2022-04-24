package com.example.tobias.werwolf_v1.multipleDevices;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.tobias.werwolf_v1.R;
import com.example.tobias.werwolf_v1.SquareLayout;
import com.example.tobias.werwolf_v1.StartScreen;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import maes.tech.intentanim.CustomIntent;

public class PlayerConnectToHost extends AppCompatActivity implements View.OnClickListener {


    private SquareLayout square;
    private TextView infoText;
    private CameraSource cameraSource;
    private String ipAdresseHost;
    private Button bereit;
    private EditText nameEingeben;
    private TextView statusVerbindung;

    private LinearLayout wartenLayout;
    private LinearLayout nameLayout;
    private String meineIp;
    private int statusBereit = 0;
    private boolean einmalGedrueckt;
    private Socket socket;
    private OutputStream out;
    private PrintWriter output;
    private BufferedReader input;
    private boolean empfangenAktiv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_playerconnecttohost);

        einmalGedrueckt = false;

        square = findViewById(R.id.square);
        infoText = findViewById(R.id.infoText);

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        meineIp = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        bereit = findViewById(R.id.bereit);
        bereit.setOnClickListener(this);
        nameEingeben = findViewById(R.id.nameEingeben);
        statusVerbindung = findViewById(R.id.statusVerbindung);
       // textWartenAnimation = findViewById(R.id.textWartenAnimation);
        wartenLayout = findViewById(R.id.wartenLayout);
        nameLayout = findViewById(R.id.nameLayout);
        SurfaceView surfaceView = findViewById(R.id.cameraview);


        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling dafür sorgen dass nachkamera gefragt wird
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if (qrCodes.size() != 0) {
                    ipAdresseHost = qrCodes.valueAt(0).displayValue;
                    Log.e("D", "qrCode erfasst: " + ipAdresseHost);

                    char[] ipTest = ipAdresseHost.toCharArray();
                    boolean korrekterQRCode = true;
                    if (ipAdresseHost.compareTo("0.0.0.0") == 0) {
                        korrekterQRCode = false;
                    }
                    for (char c : ipTest) {
                        if (!((c >= '0' && c <= '9') || c == '.')) {
                            korrekterQRCode = false;
                        }
                    }

                    if (korrekterQRCode) {
                        // Toast.makeText(Spieler_Auswahl.this, "QR-Code enthält gültige IP-Adresse.", Toast.LENGTH_SHORT).show();

                        Log.e("D", "if: " + ipAdresseHost);
                        new Thread() {
                            public void run() {

                                Log.e("D", "if: Thread" + ipAdresseHost);
                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        Log.e("D", "if runonui " + ipAdresseHost);
                                        square.setVisibility(View.GONE);
                                        infoText.setVisibility(View.GONE);
                                        //  bereit.setVisibility(View.VISIBLE);
                                        nameLayout.setVisibility(View.VISIBLE);
                                        statusVerbindung.setVisibility(View.VISIBLE);
                                        //todo refreshen der UI, damit die elemente angezeigt werden

                                        cameraSource.stop();


                                    }
                                });
                            }
                        }.start();

                    } else {

                        Log.e("D", "else " + ipAdresseHost);
                        new Thread() {
                            public void run() {

                                Log.e("D", "else: thread" + ipAdresseHost);
                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        Log.e("D", "else: runonUI " + ipAdresseHost);
                                        Toast.makeText(PlayerConnectToHost.this, "QR-Code enthält keine gültige IP-Adresse.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }.start();
                    }
                }
            }
        });

        if (!isNetworkAvailable()) {
            dialogInternetConnection();
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void dialogInternetConnection() {
        AlertDialog.Builder[] builder = new AlertDialog.Builder[1];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder[0] = new AlertDialog.Builder(PlayerConnectToHost.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder[0] = new AlertDialog.Builder(PlayerConnectToHost.this);
        }
        builder[0].setTitle("Hinweis")
                .setMessage("W-lan aktivieren um mit anderen Spielern zu verbinden.")
                .setPositiveButton("Okay.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // weiter[0] =true;
                    }

                })
                .show();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bereit) {
            Log.e("D", "bereit gedrückt" + ipAdresseHost);


            if (nameEingeben.getText().toString().compareTo("") == 0) {
                statusVerbindung.setText("Du musst erst einen Namen eingeben.");
                nameLayout.setBackgroundResource(R.drawable.knopf_orange);
                statusVerbindung.setVisibility(View.VISIBLE);
            } else {
                nameEingeben.getText().toString();
                statusVerbindung.setText("Daten werden gesendet...");

                nameSenden();


            }
        }
    }

    private void empfangenThread() {
        empfangenAktiv = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (empfangenAktiv)         //todo auf empfangen warten, dass der name als gültig gesehen weren kann -> kein abbruch mehr
                {
                    try {
                        String st = input.readLine();
                        Log.d("Verbindung", "empfangenThread empfangen: " + st);

                        if (st.startsWith("spielbereit")) {//tod -> in die Karte übergehen
                            openCard(st.substring(11));
                        } else {
                            if (st.compareTo("frei") == 0) {
                                statusBereit = 1;
                                //threadWarten.start();
                            } else if (st.compareTo("verwendet") == 0)
                                statusBereit = 0;
                            else
                                statusBereit = -1;


                            runOnUiThread(new Runnable() {
                                public void run() {
                                    if (statusBereit == 1) {
                                        Log.e("D", "bereit nach senden" + ipAdresseHost);
                                        if (einmalGedrueckt) {
                                            statusVerbindung.setText("Name geändert.");
                                        } else {
                                            bereit.setText("Namen Ändern");
                                            statusVerbindung.setText("");
                                            nameLayout.setBackgroundResource(R.drawable.knopf_hellgruen);
                                            //  threadWarten.start();
                                            wartenLayout.setVisibility(View.VISIBLE);
                                            einmalGedrueckt = true;
                                        }

                                    } else if (statusBereit == 0) {
                                        nameLayout.setBackgroundResource(R.drawable.knopf_orange);
                                        statusVerbindung.setText("Der Name " + nameEingeben.getText().toString() + " wird bereits von einem deiner Mitspieler verwendet.");
                                    } else {
                                        nameLayout.setBackgroundResource(R.drawable.knopf_orange);
                                        statusVerbindung.setText("Verbindung fehlgeschlagen. Überprüfe die Internetverbindung beider Geräte.");
                                    }
                                }
                            });
                        }

                    } catch (IOException e) {
                        Log.d("Verbindung exception", "in exception");
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private Thread nameSenden() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    if (socket == null) {
                        socket = new Socket(ipAdresseHost, 9002);
                        out = socket.getOutputStream();

                        output = new PrintWriter(out);
                        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    }
                    output.println(meineIp + "/EndeIP" + nameEingeben.getText().toString());
                    output.flush();


                } catch (IOException e) {
                    e.printStackTrace();
                }

                output.println(meineIp + "/EndeIP" + nameEingeben.getText().toString());
                output.flush();
                if (!empfangenAktiv) {
                    empfangenThread();
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
        });
        thread.start();
        return thread;
    }



    @Override
    public void finish() {
        super.finish();
        try {
            out.close();
            output.close();
            socket.close();
            input.close();
            empfangenAktiv = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        CustomIntent.customType(this, "right-to-left");
    }


    public void openCard(String rolle) {
        Intent intent = new Intent(this, PlayerCard.class);

        intent.putExtra("rolle", rolle);
        startActivity(intent);

        CustomIntent.customType(this, "left-to-right");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StartScreen.class);
        startActivity(intent);
    }

}
