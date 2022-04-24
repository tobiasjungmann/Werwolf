package com.example.tobias.werwolf_v1;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ListNight extends AppCompatActivity implements View.OnClickListener {

    private TextView beschreibung;
    private ListView personen;
    //  private ArrayList<String> arrayList;
    private CustomAdapter customAdapter;
    private DatabaseHelper mDatabaseHelper;
    private Cursor data;
    private TextView textSpielstand;
    private LinearLayout layoutSpielstand;
    private Button weiterNacht;

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


    private int liebenderEinsID;
    private int liebenderZweiID;
    private int schlafplatzDiebID;
    private int schlafplatzWaechterID;
    private int vorbildID;
    private int werwolfOpferID;
    private int weisserWerwolfOpferID;
    private int hexeOpferID;
    private int urwolfVeto;
    private int buergerOpfer;
    private String tot;
    private boolean liebespaarEntdeckt;
    private boolean ritterletzteRundeGetoetet;
    private boolean CharakterpositionErstInkementieren;
    private int listeAuswahlGenuegend;
    private int verzaubertAktuell;
    private String verzaubertCharakter;
    private String verzaubertName;


    private int charakterPosition;
    private boolean wwletzteRundeAktiv;
    private boolean langerText;

    //für Hexe
    private CheckBox opferRettenHexe;
    private CheckBox personToetenHexe;
    private TextView textViewOpferRetten;
    private TextView textViewPersonToeten;
    private LinearLayout layoutHexeNacht;
    private LinearLayout rettenLayoutHexe;
    private LinearLayout toetenLayoutHexe;
    private boolean trankLebenEinsetzbar;
    private boolean trankTodEinsetzbar;
    private boolean hexeToetenGedrueckt = false;
    private boolean hexeRettenGedrueckt = false;
    private int werwolfOpferIDBackupHexe = -1;

    //Urwolf
    private LinearLayout layoutUrwolf;
    private CheckBox checkboxUrwolfVeto;
    private int werwolfDurchUrwolfID;

    //Jaeger
    private int charakterPositionJaegerBackup;
    private int jaegerOpfer;
    private boolean jaegerAktiv;

    //Ritter
    private boolean ritterAktiv;
    private int ritterOpfer;
    private String nameRitterOpfer;
    private String charakterRitterOpfer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nightlist);

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

        trankLebenEinsetzbar = true;
        trankTodEinsetzbar = true;
        liebenderEinsID = -1;
        liebenderZweiID = -1;
        schlafplatzDiebID = -1;
        schlafplatzWaechterID = -1;
        vorbildID = -1;
        werwolfOpferID = -1;
        weisserWerwolfOpferID = -1;
        hexeOpferID = -1;
        buergerOpfer = -1;
        liebespaarEntdeckt = false;
        ritterletzteRundeGetoetet = false;
        listeAuswahlGenuegend = 0;
        CharakterpositionErstInkementieren = false;
        verzaubertAktuell = -1;
        verzaubertCharakter = "";
        verzaubertName = "";
        wwletzteRundeAktiv = true;

        langerText = false;
        charakterPosition = 0;
        tot = "";


        //Leiste oben
        textSpielstand = findViewById(R.id.textSpielstand);
        layoutSpielstand = findViewById(R.id.layoutSpielstand);


        //UI Allgemein
        weiterNacht = findViewById(R.id.weiterNacht);
        weiterNacht.setOnClickListener(ListNight.this);
        beschreibung = findViewById(R.id.beschreibung);
        beschreibung.setOnClickListener(ListNight.this);
        personen = findViewById(R.id.personen);
        personen.setVisibility(View.INVISIBLE);


        //Hexe
        opferRettenHexe = findViewById(R.id.opferRettenHexe);
        personToetenHexe = findViewById(R.id.personToetenHexe);
        textViewOpferRetten = findViewById(R.id.textViewOpferRetten);
        textViewPersonToeten = findViewById(R.id.textViewPersonToeten);
        layoutHexeNacht = findViewById(R.id.layoutHexeNacht);
        rettenLayoutHexe = findViewById(R.id.layoutRettenHexe);
        toetenLayoutHexe = findViewById(R.id.toetenLayoutHexe);
        opferRettenHexe.setOnClickListener(this);
        personToetenHexe.setOnClickListener(this);
        textViewOpferRetten.setOnClickListener(this);
        textViewPersonToeten.setOnClickListener(this);


        //Urwolf
        layoutUrwolf = findViewById(R.id.layoutUrwolf);
        TextView textViewUrwolfVeto = findViewById(R.id.textViewUrwolfVeto);
        checkboxUrwolfVeto = findViewById(R.id.checkboxUrwolfVeto);
        textViewUrwolfVeto.setOnClickListener(this);
        checkboxUrwolfVeto.setOnClickListener(this);
        if (anzahlUrwolf > 0)
            urwolfVeto = 0;
        else
            urwolfVeto = -1;
        werwolfDurchUrwolfID = -1;

        //Jaeger
        jaegerAktiv = false;

        //Ritter
        ritterAktiv = false;
        ritterOpfer = -1;
        nameRitterOpfer = "";
        charakterRitterOpfer = "";

        //Liste Initialisieren
        mDatabaseHelper = new DatabaseHelper(this);
        data = mDatabaseHelper.getData();

        // arrayList = new ArrayList<String>();

        customAdapter = new CustomAdapter();
        personen.setAdapter(customAdapter);
        personen.setOnItemClickListener(new AdapterView.OnItemClickListener() {            //Hier kmmt eine Möglichkeit zum Löschen eines Eintrages
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemID = -1;
                data.moveToPosition(position);
                String name = data.getString(1);
                String charakter = data.getString(2);
                Cursor hilfedata = mDatabaseHelper.getItemID(name);


                while (hilfedata.moveToNext()) {
                    itemID = hilfedata.getInt(0);
                }

                if (itemID > -1) {
                    switch (charakterPosition) {
                        case 0:                         //Amor
                            if (listeAuswahlGenuegend == 0) {
                                if (liebenderEinsID == -1) {
                                    liebenderEinsID = itemID;
                                } else {
                                    liebenderZweiID = itemID;
                                    CharakterpositionErstInkementieren = true;
                                    weiterNacht.setClickable(true);
                                    weiterNacht.setBackground(getResources().getDrawable(R.drawable.buttonblue));
                                    listeAuswahlGenuegend = 1;
                                }
                            } else {
                                if (listeAuswahlGenuegend % 2 == 1) {
                                    if (itemID != liebenderZweiID) {
                                        liebenderEinsID = itemID;
                                        customAdapter.notifyDataSetChanged();
                                        listeAuswahlGenuegend++;
                                    }
                                } else {
                                    if (itemID != liebenderEinsID) {
                                        liebenderZweiID = itemID;
                                        customAdapter.notifyDataSetChanged();
                                        listeAuswahlGenuegend++;
                                    }
                                }
                            }
                            break;

                        case 2:                         //Dieb
                            schlafplatzDiebID = itemID;
                            CharakterpositionErstInkementieren = true;
                            weiterNacht.setClickable(true);
                            weiterNacht.setBackground(getResources().getDrawable(R.drawable.buttonblue));
                            break;

                        case 3:                         //Wächter
                            schlafplatzWaechterID = itemID;
                            CharakterpositionErstInkementieren = true;
                            weiterNacht.setClickable(true);
                            weiterNacht.setBackground(getResources().getDrawable(R.drawable.buttonblue));
                            break;

                        case 4:                         //Junges
                            vorbildID = itemID;
                            CharakterpositionErstInkementieren = true;
                            weiterNacht.setClickable(true);
                            weiterNacht.setBackground(getResources().getDrawable(R.drawable.buttonblue));
                            break;


                        case 6:                         //Flötenspieler
                            verzaubertAktuell = itemID;
                            verzaubertCharakter = charakter;
                            verzaubertName = name;
                            CharakterpositionErstInkementieren = true;
                            weiterNacht.setClickable(true);
                            weiterNacht.setBackground(getResources().getDrawable(R.drawable.buttonblue));
                            break;


                        case 7:                         //Werwolf
                            werwolfOpferID = itemID;
                            CharakterpositionErstInkementieren = true;
                            weiterNacht.setClickable(true);
                            weiterNacht.setBackground(getResources().getDrawable(R.drawable.buttonblue));
                            break;

                        case 8:                         //Weisser Werwolf
                            weisserWerwolfOpferID = itemID;
                            CharakterpositionErstInkementieren = true;
                            weiterNacht.setClickable(true);
                            weiterNacht.setBackground(getResources().getDrawable(R.drawable.buttonblue));
                            break;


                        case 10:                         //Hexe (töten)
                            hexeOpferID = itemID;
                            CharakterpositionErstInkementieren = true;
                            weiterNacht.setClickable(true);
                            weiterNacht.setBackground(getResources().getDrawable(R.drawable.buttonblue));
                            break;


                        case 12:                         //Opfer dorf auswerten
                            buergerOpfer = itemID;
                            CharakterpositionErstInkementieren = true;
                            weiterNacht.setClickable(true);
                            weiterNacht.setBackground(getResources().getDrawable(R.drawable.buttonblue));
                            break;

                        case 20:
                            jaegerOpfer = itemID;
                            weiterNacht.setClickable(true);
                            weiterNacht.setBackground(getResources().getDrawable(R.drawable.buttonblue));
                            break;

                        case 21:            //Ritter
                            ritterOpfer = itemID;
                            nameRitterOpfer = name;
                            charakterRitterOpfer = charakter;
                            //ritterDialogToeten();
                            weiterNacht.setClickable(true);
                            weiterNacht.setBackground(getResources().getDrawable(R.drawable.buttonblue));
                            break;

                    }
                    customAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(ListNight.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(ListNight.this);
        }
        builder.setTitle("Warnung")
                .setMessage("Möchtest du die App wirklich verlassen? Der gesamte Spielstand geht verloren.")
                .setNegativeButton("Ja, App verlassen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                })
                .setPositiveButton("weiterspielen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();


    }


    private void charakterPositionBestimmen() {

        Log.d(TAG, "charakterposition  case " + charakterPosition);
        switch (charakterPosition) {
            case 0:                         //Amor
                if (anzahlAmor > 0) {
                    textSpielstand.setText(R.string.amor);
                    layoutSpielstand.setBackgroundResource(R.drawable.leiste_amor);

                    if (langerText) {
                        beschreibung.setText("Als erstes erwacht der Amor und zeigt auf zwei Personen, die sich augenblicklich unsterblich ineinander verlieben. - Der Amor schläft wieder ein. Ich tippe die beiden verliebten jetzt an un sie schauen sich tief in die Augen. - Auch sie schlafen jetzt wieder ein.");
                    } else {
                        beschreibung.setText("Als erstes erwacht der Amor und zeigt auf zwei Personen...");
                    }
                    weiterNacht.setClickable(false);
                    weiterNacht.setBackground(getResources().getDrawable(R.drawable.knopf_blau_unclickable));
                    customAdapter.notifyDataSetChanged();
                    personen.setVisibility(View.VISIBLE);
                    anzahlAmor = 0;
                    anzahlBuerger++;
                } else {
                    charakterPosition++;
                    charakterPositionBestimmen();
                }
                break;

            case 1:                         //Freunde
                if (anzahlFreunde > 0) {
                    textSpielstand.setText(R.string.freunde);
                    layoutSpielstand.setBackgroundResource(R.drawable.leiste_freunde);
                    if (langerText) {
                        beschreibung.setText("Ich tippe alle Freunde kurz an. - Jetzt erwachen die Freunde und schauen sich an, um sich später wiederzuerkennen. - Die Freunde schlafen beruhigt wieder ein, da sie wisssen, dass sie nicht alleine sind.");
                    } else {
                        beschreibung.setText("Ich tippe alle Freunde kurz an. - Jetzt erwachen die Freunde...");
                    }
                    anzahlBuerger = anzahlBuerger + anzahlFreunde;
                    anzahlFreunde = 0;      //todo Bürgerzahl +2

                    charakterPosition++;
                    personen.setVisibility(View.INVISIBLE);
                } else {
                    charakterPosition++;
                    charakterPositionBestimmen();
                }
                break;

            case 2:                         //Dieb
                if (anzahlDieb > 0) {
                    textSpielstand.setText(R.string.dieb);
                    layoutSpielstand.setBackgroundResource(R.drawable.leiste_dieb);
                    if (langerText) {
                        beschreibung.setText("Jetzt erwacht der Dieb und sucht sich eine Person aus, bei der er oder sie die Nacht verbringen möchten. - Er zeigt auf diese Person und schläft danach wieder ein.");
                    } else {
                        beschreibung.setText("Jetzt erwacht der Dieb und sucht sich eine Person aus...");
                    }
                    weiterNacht.setClickable(false);
                    weiterNacht.setBackground(getResources().getDrawable(R.drawable.knopf_blau_unclickable));
                    customAdapter.notifyDataSetChanged();
                    personen.setVisibility(View.VISIBLE);
                } else {
                    charakterPosition++;
                    charakterPositionBestimmen();
                }
                break;

            case 3:                         //Wächter
                if (anzahlWaechter > 0) {
                    textSpielstand.setText(R.string.waechter);
                    layoutSpielstand.setBackgroundResource(R.drawable.leiste_waechter);
                    if (langerText) {
                        beschreibung.setText("Der Wächter wählt eine Person, die er diese Nacht beschützen möchte. - Der Wächter schläft wieder ein.");
                    } else {
                        beschreibung.setText("Der Wächter wählt eine Person, die er diese Nacht beschützen...");
                    }
                    weiterNacht.setClickable(false);
                    weiterNacht.setBackground(getResources().getDrawable(R.drawable.knopf_blau_unclickable));
                    customAdapter.notifyDataSetChanged();
                    personen.setVisibility(View.VISIBLE);
                } else {
                    charakterPosition++;
                    charakterPositionBestimmen();
                }
                break;

            case 4:                         //Junges
                if (anzahlJunges > 0) {
                    textSpielstand.setText(R.string.junges);
                    layoutSpielstand.setBackgroundResource(R.drawable.leiste_junges);
                    if (langerText) {
                        beschreibung.setText("Das Werwolfjunge erwacht und sucht sich ein Vorbild aus. Sollte dieses Vorbild sterben, wirst du auch ein Werwolf und wachst gemeinsam mit ihnen auf. -\n Das Junge schläft wieder.");
                    } else {
                        beschreibung.setText("Das Werwolfjunge erwacht und sucht sich ein Vorbild aus...");
                    }
                    anzahlBuerger = anzahlBuerger + anzahlJunges;
                    anzahlJunges = 0;

                    weiterNacht.setClickable(false);
                    weiterNacht.setBackground(getResources().getDrawable(R.drawable.knopf_blau_unclickable));
                    customAdapter.notifyDataSetChanged();
                    personen.setVisibility(View.VISIBLE);
                } else {
                    charakterPosition++;
                    charakterPositionBestimmen();
                }
                break;

            case 5:                         //Seher
                if (anzahlSeher > 0) {
                    textSpielstand.setText(R.string.seher);
                    layoutSpielstand.setBackgroundResource(R.drawable.leiste_seher);
                    if (langerText) {
                        beschreibung.setText("Als nächstes wacht der Seher auf und zeigt auf eine Person, deren Karte er sehen möchte. Wenn er sie gesehen hat schläft er wieder ein.");
                    } else {
                        beschreibung.setText("Als nächstes wacht der Seher auf und zeigt auf eine Person...");
                    }
                    charakterPosition++;
                    personen.setVisibility(View.INVISIBLE);
                } else {
                    charakterPosition++;
                    charakterPositionBestimmen();
                }
                break;


            case 6:         //Flötenspieler
                if (anzahlFloetenspieler > 0) {
                    textSpielstand.setText(R.string.floetenspieler);
                    layoutSpielstand.setBackgroundResource(R.drawable.leiste_floetenspieler);
                    if (langerText) {
                        beschreibung.setText("Zuletzt erwacht der bezaubernde Flötenspieler und darf eine Person seiner Wahl verzaubern. Hat er alle Mitspieler verzaubert gewinnt er.");
                    } else {
                        beschreibung.setText("Zuletzt erwacht der bezaubernde Flötenspieler und darf...");
                    }
                    weiterNacht.setClickable(false);
                    weiterNacht.setBackground(getResources().getDrawable(R.drawable.knopf_blau_unclickable));
                    customAdapter.notifyDataSetChanged();
                    personen.setVisibility(View.VISIBLE);
                } else {
                    charakterPosition++;
                    charakterPositionBestimmen();
                }
                break;


            case 7:         //Werwölfe
                if (ritterAktiv) {
                    ritterDialogVorbereiten();
                } else {
                    if (anzahlWerwolf + anzahlUrwolf + anzahlWeisserWerwolf > 0) {
                        textSpielstand.setText(R.string.werwolf);
                        layoutSpielstand.setBackgroundResource(R.drawable.leiste_werwolf);
                        if (langerText) {
                            beschreibung.setText("Jetzt wachen die Werwölfe auf und suchen sich ihr Opfer für diese Nacht aus. Haben sie sich entschieden schlafen sie auch schon wieder ein.");
                        } else {
                            beschreibung.setText("Jetzt wachen die Werwölfe auf und suchen sich ihr Opfer...");
                        }
                        weiterNacht.setClickable(false);
                        weiterNacht.setBackground(getResources().getDrawable(R.drawable.knopf_blau_unclickable));
                        customAdapter.notifyDataSetChanged();
                        personen.setVisibility(View.VISIBLE);
                    } else {
                        beschreibung.setText("Fehler: Es sind keine Werwölfe mehr im Spiel!");
                    }
                }
                break;

            case 8:                     //Weisser Werwolf
                if (anzahlWeisserWerwolf > 0 && !wwletzteRundeAktiv) {
                    textSpielstand.setText(R.string.weisser_werwolf);
                    layoutSpielstand.setBackgroundResource(R.drawable.leiste_weisserwerwolf);
                    if (langerText) {
                        beschreibung.setText("Text Weißer Werwolf");
                    } else {
                        beschreibung.setText("Text Weißer Werwolf...");
                    }
                    weiterNacht.setClickable(false);
                    weiterNacht.setBackground(getResources().getDrawable(R.drawable.knopf_blau_unclickable));
                    customAdapter.notifyDataSetChanged();
                    personen.setVisibility(View.VISIBLE);
                    wwletzteRundeAktiv = true;
                } else {
                    wwletzteRundeAktiv = false;
                    charakterPosition++;
                    charakterPositionBestimmen();
                }
                break;

            case 9:                         //Urwolf
                if (anzahlUrwolf > 0 && urwolfVeto != -1) {
                    personen.setVisibility(View.GONE);
                    layoutUrwolf.setVisibility(View.VISIBLE);
                    textSpielstand.setText(R.string.urwolf);
                    layoutSpielstand.setBackgroundResource(R.drawable.leiste_urwolf);
                    if (langerText) {
                        beschreibung.setText("Text Urwolf");
                    } else {
                        beschreibung.setText("Text Urwolf...");
                    }
                    charakterPosition++;

                } else {
                    charakterPosition++;
                    charakterPositionBestimmen();
                }
                break;


            case 10:                    //Hexe
                layoutUrwolf.setVisibility(View.GONE);
                if (anzahlHexe > 0) {
                    if (trankLebenEinsetzbar || trankTodEinsetzbar) {
                        textSpielstand.setText(R.string.hexe);
                        werwolfOpferIDBackupHexe = werwolfOpferID;
                        layoutSpielstand.setBackgroundResource(R.drawable.leiste_hexe);
                        if (langerText) {
                            beschreibung.setText("Als nächstes wacht die Hexe auf. Sie kann das Opfer -auf Opfer zeigen- retten, nichtstun, oder  eine weitere Person mit in den Tod reißen. -Dabei die drei Handzeichen für die Hexe sichtbar vormachen.");
                        } else {
                            beschreibung.setText("Als nächstes wacht die Hexe auf. Sie kann das Opfer...");
                        }

                        personen.setVisibility(View.INVISIBLE);

                        //todo, wenn nur eine Option übrig ist eien entsprechenden Rand einfügen

                        if (trankLebenEinsetzbar || trankTodEinsetzbar) {
                            layoutHexeNacht.setVisibility(View.VISIBLE);
                            if (!(trankLebenEinsetzbar && trankTodEinsetzbar)) {
                                if (trankLebenEinsetzbar)        //leben anzaeigen, sonst nur tod
                                {
                                    rettenLayoutHexe.setVisibility(View.VISIBLE);
                                    toetenLayoutHexe.setVisibility(View.GONE);
                                } else {
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    params.setMargins(16, 16, 16, 16);

                                    toetenLayoutHexe.setLayoutParams(params);
                                    rettenLayoutHexe.setVisibility(View.GONE);
                                    toetenLayoutHexe.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        charakterPosition++;
                    } else {
                        charakterPosition++;
                        charakterPositionBestimmen();
                    }
                } else {
                    charakterPosition++;
                    charakterPositionBestimmen();
                }
                break;


            case 11:
                nachtAuswerten();

                //Opfer des weißen werwolfes töten
                if (weisserWerwolfOpferID != -1 && weisserWerwolfOpferID != werwolfOpferID) {
                    weisserWerwolfAuswerten();
                    weisserWerwolfOpferID = -1;
                }

                //Opfer des Ritters töten
                if (ritterOpfer != -1) {
                    tot = "";
                    sicherToeten(ritterOpfer, nameRitterOpfer, charakterRitterOpfer);
                    String s = beschreibung.getText().toString();
                    beschreibung.setText(s + "\n" + tot);
                    ritterOpfer = -1;
                    nameRitterOpfer = "";
                    charakterRitterOpfer = "";
                }

                auswerten();
                if (tot.compareTo("") == 0) {
                    beschreibung.setText("Das ganze Dorf erwacht, alle haben überlebt");
                } else {
                    beschreibung.setText("Das ganze Dorf erwacht außer: " + tot);
                }
                tot = "";

                //wenn Jäger gestorben, entsprechende charakterposition
                if (!jaegerAktiv) {
                    layoutHexeNacht.setVisibility(View.GONE);
                    weiterNacht.setClickable(true);
                    weiterNacht.setBackground(getResources().getDrawable(R.drawable.buttonblue));
                    personen.setVisibility(View.INVISIBLE);
                    textSpielstand.setText(R.string.village);
                    layoutSpielstand.setBackgroundResource(R.drawable.leiste_hellgruen);
                    charakterPosition++;

                } else {
                    String s = beschreibung.getText().toString();
                    beschreibung.setText(s + "Der Jäger ist gestorben. Er darf eine weitere Person töten:");
                }

                if (ritterAktiv) {
                    String s = beschreibung.getText().toString();
                    beschreibung.setText(s + "\n\nDer Ritter ist verstorben. In der nächsten Nacht stirbt der Nächste Werwolf zur Rechten des Ritters.");
                }
                break;


            case 12:
                auswerten();
                beschreibung.setText("Abstimmphase, wen wählt das Dorf als Schuldigen aus? ");
                //todo: Möglichkeit keinen zu töten, weiter immer klickbar, aber dann dialog der nachfrägt
                weiterNacht.setClickable(false);
                weiterNacht.setBackground(getResources().getDrawable(R.drawable.knopf_blau_unclickable));

                customAdapter.notifyDataSetChanged();
                personen.setVisibility(View.VISIBLE);


                //was macht der folgende Abschnitt????
                if (verzaubertAktuell != -1) {
                    mDatabaseHelper.deleteName("" + verzaubertAktuell);
                    mDatabaseHelper.addVerzaubert(verzaubertName, verzaubertCharakter);
                    data = mDatabaseHelper.getData();

                    if (verzaubertAktuell == vorbildID) {
                        data.moveToFirst();
                        boolean vorbildGefunden = false;
                        if (data.getString(1).compareTo(verzaubertName) == 0) {
                            vorbildGefunden = true;
                            vorbildID = data.getInt(0);
                        }
                        while (data.moveToNext() && !(vorbildGefunden)) {
                            if (data.getString(1).compareTo(verzaubertName) == 0) {
                                vorbildGefunden = true;
                                vorbildID = data.getInt(0);
                            }
                        }
                    }

                    if (liebenderEinsID == verzaubertAktuell) {
                        data.moveToFirst();
                        boolean vorbildGefunden = false;
                        if (data.getString(1).compareTo(verzaubertName) == 0) {
                            vorbildGefunden = true;
                            liebenderEinsID = data.getInt(0);
                        }
                        while (data.moveToNext() && !(vorbildGefunden)) {
                            if (data.getString(1).compareTo(verzaubertName) == 0) {
                                vorbildGefunden = true;
                                liebenderEinsID = data.getInt(0);
                            }
                        }
                    }

                    if (liebenderZweiID == verzaubertAktuell) {
                        data.moveToFirst();
                        boolean vorbildGefunden = false;
                        if (data.getString(1).compareTo(verzaubertName) == 0) {
                            vorbildGefunden = true;
                            liebenderZweiID = data.getInt(0);
                        }
                        while (data.moveToNext() && !(vorbildGefunden)) {
                            if (data.getString(1).compareTo(verzaubertName) == 0) {
                                vorbildGefunden = true;
                                liebenderZweiID = data.getInt(0);
                            }
                        }
                    }

                    verzaubertAktuell = -1;
                    verzaubertCharakter = "";
                    verzaubertName = "";
                }
                break;

            case 13:
                buergeropferToeten();
                if (!jaegerAktiv) {
                    personen.setVisibility(View.GONE);
                    beschreibung.setText("Das ganze Dorf schläft ein.\n\nHinweis: Die Reihenfolge der Personen in der Liste hat sich geändert.");
                    charakterPosition = 1;
                    werwolfOpferID = -1;
                    weisserWerwolfOpferID = -1;
                    schlafplatzWaechterID = -1;
                    schlafplatzDiebID = -1;
                    tot = "";
                    // totErweiterungWW = "";
                } else {
                    String s = beschreibung.getText().toString();
                    beschreibung.setText(s + "\n\nDer Jäger ist gestorben. Er darf eine weitere Person töten:");
                }
                break;
        }

    }


    private void nachtAuswerten() {
        int opferZweiID = -1;
        boolean werwolfopferGefunden = false;
        String werwolfOpferName = "";
        String werwolfOpferVerzaubert = "";
        String werwolfOpferCharakter = "";

        if (werwolfOpferID != -1)                                      //Werwolf existiert
        {
            //Werte des Opfers ermitteln:
            data.moveToFirst();                                         //Daten des Werwolfopfers werden ermittelt


            if (data.getInt(0) == werwolfOpferID) {
                werwolfopferGefunden = true;
                werwolfOpferName = data.getString(1);
                werwolfOpferCharakter = data.getString(2);
                werwolfOpferVerzaubert = data.getString(4);
            }
            while (data.moveToNext() && !(werwolfopferGefunden)) {
                if (data.getInt(0) == werwolfOpferID) {
                    werwolfopferGefunden = true;
                    werwolfOpferName = data.getString(1);
                    werwolfOpferCharakter = data.getString(2);
                    werwolfOpferVerzaubert = data.getString(4);
                }
            }

            if (urwolfVeto == 1)                 //Von Urwolf gerettet -> verwandelt sich in einen Werwolf
            {
                werwolfDurchUrwolfID = werwolfOpferID;
                urwolfVeto = -1;
                anzahlWerwolf++;
                werwolfOpferID = -1;

            } else {            //Auswertung aller Opfer
                int waechterID = -1;
                int diebID = -1;
                boolean waechterGefunden = false;
                boolean diebGefunden = false;


                //Werte wächter bestimmen
                if (anzahlWaechter > 0) {
                    data.moveToFirst();
                    if (data.getString(2).compareTo("waechter") == 0) {
                        waechterGefunden = true;
                        waechterID = data.getInt(0);
                    }
                    while (data.moveToNext() && !(waechterGefunden)) {
                        if (data.getString(2).compareTo("waechter") == 0) {
                            waechterGefunden = true;
                            waechterID = data.getInt(0);
                        }
                    }
                }

                //werte Dieb bestimmen
                if (anzahlDieb > 0)                                  //todo auswetung das Diebes kann aussetzen, wenn der Wächter das eigentliche Werwolfopfer beschützt, sodass dieses überlebt.
                {
                    data.moveToFirst();
                    if (data.getString(2).compareTo("dieb") == 0) {
                        diebGefunden = true;
                        diebID = data.getInt(0);
                    }
                    while (data.moveToNext() && !(diebGefunden)) {
                        if (data.getString(2).compareTo("dieb") == 0) {
                            diebGefunden = true;
                            diebID = data.getInt(0);
                        }
                    }

                }

                //wächter ist das Ofer
                if (werwolfOpferCharakter.compareTo("waechter") == 0) {

                    if (!(schlafplatzWaechterID == waechterID))             //hier muss noch geprüft werden ob der dieb beim wächter schläft
                    {
                        if (!(waechterID == schlafplatzDiebID)) {
                            werwolfOpferID = -1;
                            Log.d(TAG, "Werwolf tötet wächter, aber niemand daheim");
                        } else {
                            werwolfOpferID = diebID;
                            Log.d(TAG, "dieb bei wächter wächter aber nicht daheim");
                        }
                    }
                }

                //wächter rettet das Opfer
                if (werwolfOpferID != -1) {
                    if (schlafplatzWaechterID == werwolfOpferID) {
                        werwolfOpferID = waechterID;
                        werwolfOpferID = -1;
                        Log.d(TAG, "Wächter hat das Opfer gerettet");
                    }
                }


                //Hat der Dieb was mit dem Opfer zu tun?
                if (werwolfOpferID != -1 && anzahlDieb > 0) {
                    if (!(diebID == schlafplatzDiebID))             //Dieb ist nicht daheim -> gilt als nicht normaler bürger
                    {
                        if (schlafplatzDiebID == werwolfOpferID) {  //dieb ist beim Opfer -> muss auch sterben
                            opferZweiID = diebID;
                        }
                        if (anzahlWaechter > 0) {
                            if (schlafplatzDiebID == waechterID) {
                                werwolfOpferID = diebID;
                            }
                        }
                        if (diebID == werwolfOpferID) {
                            werwolfOpferID = -1;
                        }
                    }
                }

                //Attribute des Finalen Opfers bestimmen, dann töten
                if (werwolfOpferID != -1) {
                    data.moveToFirst();                                             //Die werte müssen neu bestimmt werden, da der Dieb jetzt zum Opfergeworden sein kann.
                    werwolfopferGefunden = false;

                    if (data.getInt(0) == werwolfOpferID) {
                        werwolfopferGefunden = true;
                        werwolfOpferName = data.getString(1);
                        werwolfOpferCharakter = data.getString(2);
                    }
                    while (data.moveToNext() && !(werwolfopferGefunden)) {
                        if (data.getInt(0) == werwolfOpferID) {
                            werwolfopferGefunden = true;
                            werwolfOpferName = data.getString(1);
                            werwolfOpferCharakter = data.getString(2);
                        }
                    }
                    if (werwolfOpferID == hexeOpferID) {
                        hexeOpferID = -1;
                    }
                    sicherToeten(werwolfOpferID, werwolfOpferName, werwolfOpferCharakter);
                }

                //Opfer 2 wird ermittelt und getötet
                if (opferZweiID != -1) {
                    data.moveToFirst();
                    werwolfopferGefunden = false;

                    if (data.getInt(0) == opferZweiID) {
                        werwolfopferGefunden = true;
                        werwolfOpferName = data.getString(1);
                        werwolfOpferCharakter = data.getString(2);
                    }
                    while (data.moveToNext() && !(werwolfopferGefunden)) {
                        if (data.getInt(0) == opferZweiID) {
                            werwolfopferGefunden = true;
                            werwolfOpferName = data.getString(1);
                            werwolfOpferCharakter = data.getString(2);
                        }
                    }
                    if (opferZweiID == hexeOpferID) {
                        hexeOpferID = -1;
                    }
                    sicherToeten(opferZweiID, werwolfOpferName, werwolfOpferCharakter);

                }

                //Schlafplätze spielen dabei keien Rolle mehr sollte es ein hexen opfer geben wird es hier getötet
            }
        }

        if (hexeOpferID != -1) {
            data.moveToFirst();
            werwolfopferGefunden = false;

            if (data.getInt(0) == hexeOpferID) {
                werwolfopferGefunden = true;
                werwolfOpferName = data.getString(1);
                werwolfOpferCharakter = data.getString(2);
            }
            while (data.moveToNext() && !(werwolfopferGefunden)) {
                if (data.getInt(0) == hexeOpferID) {
                    werwolfopferGefunden = true;
                    werwolfOpferName = data.getString(1);
                    werwolfOpferCharakter = data.getString(2);
                }
            }
            sicherToeten(hexeOpferID, werwolfOpferName, werwolfOpferCharakter);
        }
    }


    private void weisserWerwolfAuswerten() {
        int opferZweiID = -1;

        if (weisserWerwolfOpferID != -1)                                      //Werwolf existiert
        {
            //Werte des Opfers ermitteln:
            data.moveToFirst();                                         //Daten des Werwolfopfers werden ermittelt
            boolean werwolfopferGefunden = false;
            String werwolfOpferName = "";
            String werwolfOpferVerzaubert = "";
            String werwolfOpferCharakter = "";

            if (data.getInt(0) == werwolfOpferID) {
                werwolfopferGefunden = true;
                werwolfOpferName = data.getString(1);
                werwolfOpferCharakter = data.getString(2);
                werwolfOpferVerzaubert = data.getString(4);
            }
            while (data.moveToNext() && !(werwolfopferGefunden)) {
                if (data.getInt(0) == werwolfOpferID) {
                    werwolfopferGefunden = true;
                    werwolfOpferName = data.getString(1);
                    werwolfOpferCharakter = data.getString(2);
                    werwolfOpferVerzaubert = data.getString(4);
                }
            }


            int waechterID = -1;
            int diebID = -1;
            boolean waechterGefunden = false;
            boolean diebGefunden = false;


            //Werte wächter bestimmen
            if (anzahlWaechter > 0) {
                data.moveToFirst();
                if (data.getString(2).compareTo("waechter") == 0) {
                    waechterGefunden = true;
                    waechterID = data.getInt(0);
                }
                while (data.moveToNext() && !(waechterGefunden)) {
                    if (data.getString(2).compareTo("waechter") == 0) {
                        waechterGefunden = true;
                        waechterID = data.getInt(0);
                    }
                }
            }

            //werte Dieb bestimmen
            if (anzahlDieb > 0) {
                data.moveToFirst();
                if (data.getString(2).compareTo("dieb") == 0) {
                    diebGefunden = true;
                    diebID = data.getInt(0);
                }
                while (data.moveToNext() && !(diebGefunden)) {
                    if (data.getString(2).compareTo("dieb") == 0) {
                        diebGefunden = true;
                        diebID = data.getInt(0);
                    }
                }

            }

            //wächter ist das Ofer
            if (werwolfOpferCharakter.compareTo("waechter") == 0) {

                if (!(schlafplatzWaechterID == waechterID))             //hier muss noch geprüft werden ob der dieb beim wächter schläft
                {
                    if (!(waechterID == schlafplatzDiebID)) {
                        weisserWerwolfOpferID = -1;
                        Log.d(TAG, "Werwolf tötet wächter, aber niemand daheim");
                    } else {
                        werwolfOpferID = diebID;
                        Log.d(TAG, "dieb bei wächter wächter aber nicht daheim");
                    }
                }
            }

            //wächter rettet das Opfer
            if (weisserWerwolfOpferID != -1) {
                if (schlafplatzWaechterID == werwolfOpferID) {

                    weisserWerwolfOpferID = -1;
                    Log.d(TAG, "Wächter hat das Opfer gerettet");
                }
            }


            //Hat der Dieb was mit dem Opfer zu tun?
            if (weisserWerwolfOpferID != -1 && anzahlDieb > 0) {
                if (!(diebID == schlafplatzDiebID))             //Dieb ist nicht daheim -> gilt als nicht normaler bürger
                {
                    if (schlafplatzDiebID == weisserWerwolfOpferID) {  //dieb ist beim Opfer -> muss auch sterben
                        opferZweiID = diebID;
                    }
                    if (anzahlWaechter > 0) {
                        if (schlafplatzDiebID == waechterID) {
                            weisserWerwolfOpferID = diebID;
                        }
                    }
                    if (diebID == weisserWerwolfOpferID) {
                        weisserWerwolfOpferID = -1;
                    }

                }
            }

            //Attribute des Finalen Opfers bestimmen, dann töten
            if (weisserWerwolfOpferID != -1) {
                data.moveToFirst();                                             //Die werte müssen neu bestimmt werden, da der Dieb jetzt zum Opfergeworden sein kann.
                werwolfopferGefunden = false;

                if (data.getInt(0) == weisserWerwolfOpferID) {
                    werwolfopferGefunden = true;
                    werwolfOpferName = data.getString(1);
                    werwolfOpferCharakter = data.getString(2);
                }
                while (data.moveToNext() && !(werwolfopferGefunden)) {
                    if (data.getInt(0) == weisserWerwolfOpferID) {
                        werwolfopferGefunden = true;
                        werwolfOpferName = data.getString(1);
                        werwolfOpferCharakter = data.getString(2);
                    }
                }
                if (weisserWerwolfOpferID == hexeOpferID) {
                    hexeOpferID = -1;
                }
                sicherToeten(weisserWerwolfOpferID, werwolfOpferName, werwolfOpferCharakter);
            }

            //Opfer 2 wird ermittelt und getötet
            if (opferZweiID != -1) {
                data.moveToFirst();
                werwolfopferGefunden = false;

                if (data.getInt(0) == opferZweiID) {
                    werwolfopferGefunden = true;
                    werwolfOpferName = data.getString(1);
                    werwolfOpferCharakter = data.getString(2);
                }
                while (data.moveToNext() && !(werwolfopferGefunden)) {
                    if (data.getInt(0) == opferZweiID) {
                        werwolfopferGefunden = true;
                        werwolfOpferName = data.getString(1);
                        werwolfOpferCharakter = data.getString(2);
                    }
                }
                if (opferZweiID == hexeOpferID) {
                    hexeOpferID = -1;
                }
                sicherToeten(opferZweiID, werwolfOpferName, werwolfOpferCharakter);

            }
        }
    }


    private void auswerten() {
        data = mDatabaseHelper.getData();

        data.moveToFirst();

        //Liebespar lebt; nur noch drei persoenn übrig
        int verliebtGefundenAnzahl = 1;
        while (data.moveToNext()) {
            verliebtGefundenAnzahl++;
        }
        if (liebenderEinsID != -1) {
            if (verliebtGefundenAnzahl <= 3 && !(liebespaarEntdeckt)) {
                siegbildschirmOeffnen("liebespaar");
            }
        }

        int anzahlNichtWerwolf = anzahlRitter + anzahlDieb + anzahlMaedchen + anzahlJaeger + anzahlWaechter + anzahlFreunde + anzahlHexe + anzahlSeher + anzahlBuerger + anzahlFloetenspieler + anzahlAmor + anzahlJunges;
        if (werwolfDurchUrwolfID != -1) {
            anzahlNichtWerwolf--;
        }
        if (anzahlNichtWerwolf < anzahlWeisserWerwolf + anzahlUrwolf + anzahlWerwolf) {
            if (anzahlUrwolf + anzahlWerwolf > 0) {
                siegbildschirmOeffnen("werwoelfe");
            } else {
                siegbildschirmOeffnen("ww");
            }
        }

        int anzahltest = anzahlWeisserWerwolf + anzahlUrwolf + anzahlWerwolf;
        //keine Werwölfe mehr da
        if (anzahlWeisserWerwolf + anzahlUrwolf + anzahlWerwolf == 0) {
            siegbildschirmOeffnen("buerger");
        }


        data.moveToFirst();
        boolean nichtverzaubertGefunden = false;
        if (data.getString(4).compareTo("ja") != 0) {
            nichtverzaubertGefunden = true;
        }
        while (data.moveToNext() && !(nichtverzaubertGefunden)) {
            if (data.getString(4).compareTo("ja") != 0) {
                nichtverzaubertGefunden = true;
            }
        }

        if (!(nichtverzaubertGefunden)) {
            siegbildschirmOeffnen("floetenspieler");
        }
    }


    private void siegbildschirmOeffnen(String sieger) {
        Intent intent = new Intent(this, VictoryActivity.class);
        intent.putExtra("sieger", sieger);
        startActivity(intent);
    }


    private void sicherToeten(int ID, String nameOpfer, String charakterOpfer) {
        boolean jaegerGefunden = false;
        String charaktername = "";
        int charakterID = -1;

        if ((ID == liebenderEinsID || ID == liebenderZweiID) && !(liebespaarEntdeckt))                //Das ganze auch für sich nicht liebende
        {
            if (hexeOpferID == liebenderEinsID || hexeOpferID == liebenderZweiID) {
                hexeOpferID = -1;
            }
            //todo: muss hier auch opfer 2 beachtet werden???


            boolean nameGefunden = false;
            data.moveToFirst();
            if (data.getInt(0) == liebenderZweiID) {
                nameGefunden = true;
                tot = tot + "\n" + data.getString(1);
                charaktername = data.getString(2);

                anzahlMindern(charaktername, data.getInt(0));
            }
            while (data.moveToNext() && !(nameGefunden)) {
                if (data.getInt(0) == liebenderZweiID) {
                    nameGefunden = true;
                    tot = tot + "\n" + data.getString(1);
                    charaktername = data.getString(2);

                    anzahlMindern(charaktername, data.getInt(0));
                }
            }
            if (nameGefunden) {
                nameGefunden = false;
                data.moveToFirst();

                if (data.getInt(0) == liebenderEinsID) {
                    nameGefunden = true;
                    charaktername = data.getString(2);
                    tot = tot + "\n" + data.getString(1);
                    anzahlMindern(charaktername, data.getInt(0));
                }

                while (data.moveToNext() && !(nameGefunden)) {
                    if (data.getInt(0) == liebenderEinsID) {
                        nameGefunden = true;
                        charaktername = data.getString(2);
                        tot = tot + "\n" + data.getString(1);
                        anzahlMindern(charaktername, data.getInt(0));
                    }
                }
                if (nameGefunden) {
                    liebespaarEntdeckt = true;
                    if (vorbildID == liebenderEinsID || vorbildID == liebenderZweiID) {
                        vorbildGestorbenDialog();
                    }
                    mDatabaseHelper.deleteName("" + liebenderEinsID);
                }
                mDatabaseHelper.deleteName("" + liebenderZweiID);
            }
        } else                    //keiner des Liebespaares getötet
        {
            //Attribute des zu tötenden ermitteln
            boolean nameGefunden = false;
            data.moveToFirst();
            if (data.getInt(0) == ID) {
                nameGefunden = true;
                charaktername = data.getString(2);
                tot = tot + "\n" + data.getString(1);
                charakterID = data.getInt(0);
            }
            while (data.moveToNext() && !(nameGefunden)) {
                if (data.getInt(0) == ID) {
                    nameGefunden = true;
                    charaktername = data.getString(2);
                    tot = tot + "\n" + data.getString(1);
                    charakterID = data.getInt(0);
                }
            }

            if (nameGefunden) {
                anzahlMindern(charaktername, charakterID);

                //sollte das Opfer das Vorbild es Werwolfjungen sein; Dialog entsprechend anpassen
                if (vorbildID == ID) {
                    vorbildGestorbenDialog();
                }
                mDatabaseHelper.deleteName("" + ID);
            }
        }
        data = mDatabaseHelper.getData();
    }


    private void anzahlMindern(String charaktername, int charakterID) {
        if (charakterID == werwolfDurchUrwolfID) {
            anzahlWerwolf--;
        }

        switch (charaktername) {
            case "werwolf":
                if (anzahlWerwolf > 0) {
                    anzahlWerwolf--;
                    //Toast.makeText(Nacht_Liste.this, "mindern anzahlWerwolf:" + anzahlWerwolf, Toast.LENGTH_SHORT).show();
                }
                break;
            case "buerger":
                if (anzahlBuerger > 0) {
                    anzahlBuerger--;
                    //Toast.makeText(Nacht_Liste.this, "mindern anzahlBuerger:" + anzahlBuerger, Toast.LENGTH_SHORT).show();
                }
                break;
            case "seher":
                if (anzahlSeher > 0) {
                    anzahlSeher--;
                }
                break;
            case "hexe":
                if (anzahlHexe > 0) {
                    anzahlHexe--;
                    //Toast.makeText(Nacht_Liste.this, "mindern anzahlHexe:" + anzahlHexe, Toast.LENGTH_SHORT).show();
                }
                break;
            case "floetenspieler":
                if (anzahlFloetenspieler > 0) {
                    anzahlFloetenspieler--;
                }
                break;
            case "freunde":
                if (anzahlFreunde > 0) {
                    anzahlFreunde--;
                }
                break;
            case "amor":
                if (anzahlAmor > 0) {
                    anzahlAmor--;
                }
                break;
            case "urwolf":
                if (anzahlUrwolf > 0) {
                    anzahlUrwolf--;
                }
                break;
            case "weisserwerwolf":
                if (anzahlWeisserWerwolf > 0) {
                    anzahlWeisserWerwolf--;
                }
                break;
            case "waechter":
                if (anzahlWaechter > 0) {
                    anzahlWaechter--;
                }
                break;
            case "junges":
                if (anzahlJunges > 0) {
                    anzahlJunges--;
                }
                break;
            case "jaeger":
                if (anzahlJaeger > 0) {
                    Log.d(TAG, "jager inkrementiert");
                    jaegerDialog();
                    anzahlJaeger--;
                }
                break;
            case "maedchen":
                if (anzahlMaedchen > 0) {
                    anzahlMaedchen--;
                }
                break;
            case "dieb":
                if (anzahlDieb > 0) {
                    anzahlDieb--;
                }
                break;
            case "ritter":
                if (anzahlRitter > 0) {
                    anzahlRitter--;
                    ritterDialog();
                    ritterletzteRundeGetoetet = true;
                }
                break;
        }
    }


    private void ritterDialog() {
        ritterAktiv = true;
    }


    private void ritterDialogVorbereiten() {
        charakterPosition = 21;


        beschreibung.setText("Der Ritter ist letzte Nacht gestorben. Wähle jetzt den nächsten Werwolf zur rechten das verstorbenen Jägers aus.");
        textSpielstand.setText("Ritter");
        layoutSpielstand.setBackgroundResource(R.drawable.leiste_ritter);

        weiterNacht.setClickable(false);
        weiterNacht.setBackground(getResources().getDrawable(R.drawable.knopf_blau_unclickable));
        customAdapter.notifyDataSetChanged();
        personen.setVisibility(View.VISIBLE);
    }


    private void ritterDialogToeten() {
        ritterAktiv = false;
        charakterPosition = 7;
        charakterPositionBestimmen();
    }


    private void jaegerDialog() {
        charakterPositionJaegerBackup = charakterPosition;
        charakterPosition = 20;
        jaegerAktiv = true;

        beschreibung.setText("Der Jäger ist gestorben. Er darf eine weitere Person töten:");
        textSpielstand.setText("Jäger");
        layoutSpielstand.setBackgroundResource(R.drawable.leiste_jaeger);


        weiterNacht.setClickable(false);
        weiterNacht.setBackground(getResources().getDrawable(R.drawable.knopf_blau_unclickable));
        customAdapter.notifyDataSetChanged();
        personen.setVisibility(View.VISIBLE);
    }

    private void jaegerToeten() {
        personen.setVisibility(View.GONE);
        jaegerAktiv = false;

        boolean jaegerOpferGefunden = false;
        String jaegerOpferName = "";
        String jaegerOpferCharakter = "";

        if (data.getInt(0) == jaegerOpfer) {
            jaegerOpferGefunden = true;
            jaegerOpferName = data.getString(1);
            jaegerOpferCharakter = data.getString(2);
        }
        while (data.moveToNext() && !(jaegerOpferGefunden)) {
            if (data.getInt(0) == hexeOpferID) {
                jaegerOpferGefunden = true;
                jaegerOpferName = data.getString(1);
                jaegerOpferCharakter = data.getString(2);
            }
        }
        sicherToeten(jaegerOpfer, jaegerOpferName, jaegerOpferCharakter);

        charakterPosition = charakterPositionJaegerBackup;
        // charakterPosition++;
        charakterPositionBestimmen();
    }

    private void vorbildGestorbenDialog() {
        String s = beschreibung.getText().toString();
        beschreibung.setText(s + "Das Vorbild ist verstorben. Ab der nächsten Nacht wacht das Junge mit den Werwölfen gemeinsam auf.\n\n");


        boolean jungesGefunden = false;
        int jungesID = -1;
        String jungesName = "";
        String jungesVerzaubert = "";
        anzahlWerwolf++;
        anzahlJunges--;

        //junges als Eintrag finden und durch einen werwolf ersetzten
        data.moveToFirst();
        if (data.getString(2).compareTo("junges") == 0) {
            jungesGefunden = true;
            jungesID = data.getInt(0);
            jungesName = data.getString(1);
            jungesVerzaubert = data.getString(4);
            mDatabaseHelper.deleteName("" + jungesID);
            mDatabaseHelper.addjungesExtra(jungesName, jungesVerzaubert);
        }

        while (data.moveToNext() && !(jungesGefunden)) {
            if (data.getString(2).compareTo("junges") == 0) {
                jungesGefunden = true;
                jungesID = data.getInt(0);
                jungesName = data.getString(1);
                jungesVerzaubert = data.getString(4);
                mDatabaseHelper.deleteName("" + jungesID);
                mDatabaseHelper.addjungesExtra(jungesName, jungesVerzaubert);
            }
        }

        //Wenn jungesID gleich mit einer der liebenden ID, so muss diese die neue ID des Elementes erhalten Wird anhand des Namen ermittelt
        if (liebenderEinsID == jungesID) {
            data.moveToFirst();
            boolean vorbildGefunden = false;
            if (data.getString(1).compareTo(jungesName) == 0) {
                vorbildGefunden = true;
                liebenderEinsID = data.getInt(0);
            }
            while (data.moveToNext() && !(vorbildGefunden)) {
                if (data.getString(1).compareTo(jungesName) == 0) {
                    vorbildGefunden = true;
                    liebenderEinsID = data.getInt(0);
                }
            }
        }

        if (liebenderZweiID == jungesID) {
            data.moveToFirst();
            boolean vorbildGefunden = false;
            if (data.getString(1).compareTo(jungesName) == 0) {
                vorbildGefunden = true;
                liebenderZweiID = data.getInt(0);
            }
            while (data.moveToNext() && !(vorbildGefunden)) {
                if (data.getString(1).compareTo(jungesName) == 0) {
                    vorbildGefunden = true;
                    liebenderZweiID = data.getInt(0);
                }
            }
        }
    }

    private void buergeropferToeten() {
        String charaktername = "";
        String persname = "";
        boolean persGefunden = false;

        data = mDatabaseHelper.getData();
        data.moveToFirst();

        if (data.getInt(0) == buergerOpfer) {
            persGefunden = true;
            charaktername = data.getString(2);
            persname = data.getString(1);
        }

        while (data.moveToNext() && !(persGefunden)) {
            if (data.getInt(0) == buergerOpfer) {
                persGefunden = true;
                charaktername = data.getString(2);
                persname = data.getString(1);
            }
        }
        sicherToeten(buergerOpfer, persname, charaktername);
        auswerten();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //für die Hexe
            case R.id.personToetenHexe:
            case R.id.textViewPersonToeten:     //opfer Toeten
                personToetenHexe.setChecked(!hexeToetenGedrueckt);
                hexeToetenGedrueckt = !hexeToetenGedrueckt;
                if (hexeToetenGedrueckt) {
                    personen.setVisibility(View.VISIBLE);
                    customAdapter.notifyDataSetChanged();
                    weiterNacht.setClickable(false);
                    weiterNacht.setBackground(getResources().getDrawable(R.drawable.knopf_blau_unclickable));
                    trankTodEinsetzbar = false;
                    CharakterpositionErstInkementieren = true;
                    charakterPosition--;
                } else {
                    personen.setVisibility(View.INVISIBLE);
                    customAdapter.notifyDataSetChanged();
                    weiterNacht.setClickable(true);
                    weiterNacht.setBackground(getResources().getDrawable(R.drawable.buttonblue));
                    trankTodEinsetzbar = true;
                    CharakterpositionErstInkementieren = false;
                    charakterPosition++;
                }
                break;

            case R.id.opferRettenHexe:
            case R.id.textViewOpferRetten:     //opfer Toeten
                opferRettenHexe.setChecked(!hexeRettenGedrueckt);
                hexeRettenGedrueckt = !hexeRettenGedrueckt;

                if (hexeRettenGedrueckt) {
                    trankLebenEinsetzbar = false;
                    werwolfOpferID = -1;
                } else {
                    trankLebenEinsetzbar = true;
                    werwolfOpferID = werwolfOpferIDBackupHexe;
                }
                break;


            case R.id.weiterNacht:
                Log.d(TAG, "weiternacht Position: " + charakterPosition);
                if (charakterPosition == 20) {
                    jaegerToeten();
                }
                if (charakterPosition == 21) {
                    ritterDialogToeten();
                } else {
                    if (CharakterpositionErstInkementieren) {
                        charakterPosition++;
                        CharakterpositionErstInkementieren = false;
                    }
                    charakterPositionBestimmen();
                }
                break;

            case R.id.textViewUrwolfVeto:
            case R.id.checkboxUrwolfVeto:
                if (urwolfVeto == 1) {
                    checkboxUrwolfVeto.setChecked(false);
                    urwolfVeto = 0;
                } else {
                    checkboxUrwolfVeto.setChecked(true);
                    urwolfVeto = 1;
                }
                break;


            //schaltet zwischen den Beiden zusatztezten um
            case R.id.beschreibung:
                langerText = !langerText;
                switch (charakterPosition) {
                    case 0:     //Amor
                        if (personen.getVisibility() == View.VISIBLE) {
                            if (langerText) {
                                beschreibung.setText("Als erstes erwacht der Amor und zeigt auf zwei Personen, die sich augenblicklich unsterblich ineinander verlieben. - Amor schläft wieder ein. Ich tippe die beiden verliebten jetzt an un sie schauen sich tief in die Augen. - Auch sie schlafen jetzt wieder ein.");
                            } else {
                                beschreibung.setText("Als erstes erwacht der Amor und zeigt auf zwei Personen...");
                            }
                        } else {
                            beschreibung.setText("Das ganze Dorf schläft ein.");
                            langerText = !langerText;

                        }
                        break;

                    //------- Freunde
                    case 1:
                        if (langerText) {
                            beschreibung.setText("Ich tippe alle Freunde kurz an. - Jetzt erwachen die Freunde und schauen sich an, um sich später wiederzuerkennen. - Die Freunde schlafen beruhigt wieder ein, da sie wisssen, dass sie nicht alleine sind.");
                        } else {
                            beschreibung.setText("Ich tippe alle Freunde kurz an. - Jetzt erwachen die Freunde...");
                        }
                        break;

                    //------- Dieb
                    case 2:
                        if (personen.getVisibility() == View.VISIBLE) {
                            if (langerText) {
                                beschreibung.setText("Jetzt erwacht der Dieb und sucht sich eine Person aus, bei der er oder sie die Nacht verbringen möchten. - Er zeigt auf diese Person und schläft danach wieder ein.");
                            } else {
                                beschreibung.setText("Jetzt erwacht der Dieb und sucht sich eine Person aus...");
                            }
                        } else {
                            if (langerText) {
                                beschreibung.setText("Ich tippe alle Freunde kurz an. - Jetzt erwachen die Freunde und schauen sich an, um sich später wiederzuerkennen. - Die Freunde schlafen beruhigt wieder ein, da sie wisssen, dass sie nicht alleine sind.");
                            } else {
                                beschreibung.setText("Ich tippe alle Freunde kurz an. - Jetzt erwachen die Freunde...");
                            }
                        }
                        break;

                    //------- Wächter
                    case 3:
                        if (langerText) {
                            beschreibung.setText("Der Wächter wählt eine Person, die er diese Nacht beschützen möchte. - Der Wächter schläft wieder ein.");
                        } else {
                            beschreibung.setText("Der Wächter wählt eine Person, die er diese Nacht beschützen...");
                        }
                        break;

                    //------- Junges
                    case 4:
                        if (langerText) {
                            beschreibung.setText("Das Werwolfjunge erwacht und sucht sich ein Vorbild aus. Sollte dieses Vorbild sterben, wirst du auch ein Werwolf und wachst gemeinsam mit ihnen auf. -\n Das Junge schläft wieder.");
                        } else {
                            beschreibung.setText("Das Werwolfjunge erwacht und sucht sich ein Vorbild aus...");
                        }
                        break;

                    //------- Seher
                    case 5:
                        if (langerText) {
                            beschreibung.setText("Als nächstes wacht der Seher auf und zeigt auf eine Person, deren Karte er sehen möchte. Wenn er sie gesehen hat schläft er wieder ein.");
                        } else {
                            beschreibung.setText("Als nächstes wacht der Seher auf und zeigt auf eine Person...");
                        }
                        break;

                    //------- Flötenspieler
                    case 6:
                        if (langerText) {
                            beschreibung.setText("Zuletzt erwacht der bezaubernde Flötenspieler und darf eine Person seiner Wahl verzaubern. Hat er alle Mitspieler verzaubert gewinnt er.");
                        } else {
                            beschreibung.setText("Zuletzt erwacht der bezaubernde Flötenspieler und darf...");
                        }
                        break;

                    //------- Werwölfe
                    case 7:
                        if (langerText) {
                            beschreibung.setText("Jetzt wachen die Werwölfe auf und suchen sich ihr Opfer für diese Nacht aus. Haben sie sich entschieden schlafen sie auch schon wieder ein.");
                        } else {
                            beschreibung.setText("Jetzt wachen die Werwölfe auf und suchen sich ihr Opfer...");
                        }
                        break;

                    case 8:
                    case 9:
                        break;

                    case 10:
                        if (langerText) {
                            beschreibung.setText("Als nächstes wacht die Hexe auf. Sie kann das Opfer -auf Opfer zeigen- retten, nichtstun, oder  eine weitere Person mit in den Tod reißen. -Dabei die drei Handzeichen für die Hexe sichtbar vormachen.");
                        } else {
                            beschreibung.setText("Als nächstes wacht die Hexe auf. Sie kann das Opfer...");
                        }
                        break;

                    case 20:
                        break;

                    default:
                        break;
                }
                break;


            default:
                break;
        }
    }


    class CustomAdapter extends BaseAdapter {

        private TextView pers;
        private ImageView icon;

        @Override
        public int getCount() {
            return data.getCount();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.mylistitemicon, null);
            pers = (TextView) convertView.findViewById(R.id.textPer);
            icon = (ImageView) convertView.findViewById(R.id.iconListe);
            data.moveToPosition(position);

            switch (charakterPosition) {
                case 0:                                     //Amor
                    if (data.getInt(0) == liebenderEinsID) {
                        icon.setImageResource(R.drawable.herz_v1);
                    }
                    if (data.getInt(0) == liebenderZweiID) {
                        icon.setImageResource(R.drawable.herz_v1);
                    }
                    break;

                case 2:                                   //dieb
                    if (data.getInt(0) == schlafplatzDiebID) {
                        icon.setImageResource(R.drawable.dieb_icon_v1);
                    }
                    break;

                case 3:                                     //wächter
                    if (data.getInt(0) == schlafplatzWaechterID) {
                        icon.setImageResource(R.drawable.schild_v1);
                    }
                    break;

                case 4:                                     //Junges
                    if (data.getInt(0) == vorbildID) {
                        icon.setImageResource(R.drawable.werwolf_icon_v1);
                    }
                    break;


                case 6:                                     //flötenspieler
                    if (data.getString(4).compareTo("ja") == 0) {
                        icon.setImageResource(R.drawable.verzaubert_v1);
                    }
                    if (data.getInt(0) == verzaubertAktuell) {
                        icon.setImageResource(R.drawable.verzaubert_v1);
                    }
                    break;


                case 7:                                     //Werwölfe
                    if (data.getInt(0) == werwolfOpferID) {
                        icon.setImageResource(R.drawable.totenkopf_v1);
                    }
                    if (data.getInt(0) == werwolfDurchUrwolfID && werwolfDurchUrwolfID != -1) {
                        icon.setImageResource(R.drawable.werwolf_icon_v1);
                    }
                    if (data.getString(2).compareTo("werwolf") == 0) {
                        icon.setImageResource(R.drawable.werwolf_icon_v1);
                    }
                    if (data.getString(2).compareTo("weisserwerwolf") == 0) {
                        icon.setImageResource(R.drawable.werwolf_icon_v1);
                    }
                    if (data.getString(2).compareTo("urwolf") == 0) {
                        icon.setImageResource(R.drawable.werwolf_icon_v1);
                    }
                    break;


                case 8:                                     //Weisserwolf

                    if (data.getInt(0) == werwolfDurchUrwolfID && werwolfDurchUrwolfID != -1) {
                        icon.setImageResource(R.drawable.werwolf_icon_v1);
                    }
                    if (data.getString(2).compareTo("werwolf") == 0) {
                        icon.setImageResource(R.drawable.werwolf_icon_v1);
                    }
                    if (data.getString(2).compareTo("weisserwerwolf") == 0) {
                        icon.setImageResource(R.drawable.werwolf_icon_v1);
                    }
                    if (data.getString(2).compareTo("urwolf") == 0) {
                        icon.setImageResource(R.drawable.werwolf_icon_v1);
                    }
                    if (data.getInt(0) == weisserWerwolfOpferID) {
                        icon.setImageResource(R.drawable.totenkopf_v1);
                    }
                    break;

                case 10:                                    //Hexe
                    if (data.getInt(0) == hexeOpferID) {
                        icon.setImageResource(R.drawable.totenkopf_v1);
                    }
                    break;

                case 12:                                    //Bürgeropfer
                    if (data.getInt(0) == buergerOpfer) {
                        icon.setImageResource(R.drawable.kreuz_v1);
                    }
                    break;

                case 20:                                    //Bürgeropfer
                    if (data.getInt(0) == jaegerOpfer) {
                        icon.setImageResource(R.drawable.kreuz_v1);
                    }
                    break;

                case 21:                                     //Ritter töten
                    if (data.getInt(0) == ritterOpfer) {
                        icon.setImageResource(R.drawable.totenkopf_v1);
                    }
                    if (data.getInt(0) == werwolfDurchUrwolfID && werwolfDurchUrwolfID != -1) {
                        icon.setImageResource(R.drawable.werwolf_icon_v1);
                    }
                    if (data.getString(2).compareTo("werwolf") == 0) {
                        icon.setImageResource(R.drawable.werwolf_icon_v1);
                    }
                    if (data.getString(2).compareTo("weisserwerwolf") == 0) {
                        icon.setImageResource(R.drawable.werwolf_icon_v1);
                    }
                    if (data.getString(2).compareTo("urwolf") == 0) {
                        icon.setImageResource(R.drawable.werwolf_icon_v1);
                    }
                    break;

            }

            pers.setText("" + data.getString(1));
            return convertView;
        }
    }
}
