package com.example.tobias.werwolf_v1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import maes.tech.intentanim.CustomIntent;

public class PlayerManagement extends AppCompatActivity implements View.OnClickListener {

    private EditText nameText;
    private TextView anzahlPersoenenText;
    private int anzahlPersonen;
    private DatabaseHelper mDatabaseHelper;
    private Button weiter;
    private CustomAdapter customAdapter;
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
    // private String karten;

    private Vibrator vibrator;
    private int vibrationsdauer;

    private boolean lastPersonAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_playermanagement);

        mDatabaseHelper = new DatabaseHelper(this);
        data = mDatabaseHelper.getData();

        anzahlPersonen = 0;
        ListView listePers = findViewById(R.id.listePers);


        customAdapter = new CustomAdapter();

        listePers.setAdapter(customAdapter);
        listePers.setOnItemClickListener((parent, view, position, id) -> {

            vibrator.vibrate(vibrationsdauer);
            data.moveToPosition(position);
            String name = data.getString(1);
            Cursor hilfedata = mDatabaseHelper.getItemID(name);
            int itemID = -1;

            while (hilfedata.moveToNext()) {
                itemID = hilfedata.getInt(0);
            }

            if (itemID > -1) {
                mDatabaseHelper.deleteName("" + itemID);
            }
            listeAktualisieren();
            anzahlAktualisieren();
            if (gesamtPer == anzahlPersonen) {
                lastPersonAdded = true;
                weiter.setText(R.string.weiter);
                nameText.setClickable(false);
            } else {
                lastPersonAdded = false;
                weiter.setText("Person einfügen");
                nameText.setClickable(true);
            }
        });

        vibrationsdauer = 8;
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        weiter = findViewById(R.id.weiter);
        weiter.setOnClickListener(PlayerManagement.this);

        nameText = findViewById(R.id.nameText);
        nameText.setTextColor(Color.WHITE);


        charakterDatenHolen();

        anzahlPersoenenText = findViewById(R.id.anzahlPersoenenText);

        listeAktualisieren();
        anzahlAktualisieren();

        if (gesamtPer == anzahlPersonen) {

            lastPersonAdded = true;
            weiter.setText(R.string.weiter);
            nameText.setClickable(false);
        }
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
    }

    private void listeAktualisieren() {
        data = mDatabaseHelper.getData();
        customAdapter.notifyDataSetChanged();
    }

    public void anzahlAktualisieren() {
        data.moveToFirst();

        int anzahl = 1;
        while (data.moveToNext()) {
            anzahl++;
        }
        anzahlPersonen = anzahl;
        anzahlPersoenenText.setText("Personen: " + anzahl + " von " + gesamtPer);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.weiter) {
            anzahlAktualisieren();

            if (gesamtPer > anzahlPersonen) {
                String name = nameText.getText().toString();
                if (name.length() == 0) {
                    Toast.makeText(PlayerManagement.this, "Namen eingegeben", Toast.LENGTH_SHORT).show();
                    vibrator.vibrate(vibrationsdauer);
                } else {
                    nameText.setText("");

                    data = mDatabaseHelper.getData();

                    boolean nameDoppelt = false;

                    while (data.moveToNext() && !(nameDoppelt)) {
                        String hilfe = data.getString(1);
                        if (0 == hilfe.compareTo(name)) {
                            nameDoppelt = true;
                        }
                    }

                    if (nameDoppelt) {
                        Toast.makeText(PlayerManagement.this, "Dieser Name ist schon vergeben", Toast.LENGTH_LONG).show();
                        vibrator.vibrate(vibrationsdauer);
                    } else {
                        boolean insertData = mDatabaseHelper.addData(name);

                        if (!insertData) {
                            Toast.makeText(PlayerManagement.this, "unbekannter Fehler, Person konnte nicht eingfügt werden.", Toast.LENGTH_LONG).show();
                        }

                    }
                    listeAktualisieren();
                    anzahlAktualisieren();
                }
            } else if (gesamtPer < anzahlPersonen) {
                vibrator.vibrate(vibrationsdauer);
                Toast.makeText(PlayerManagement.this, "Mehr Charakterkarten als Personen", Toast.LENGTH_SHORT).show();
            }

            if (gesamtPer == anzahlPersonen) {
                if (lastPersonAdded) {
                    Intent intent;
                    intent = new Intent(this, CardsToPlayerMatching.class);

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
                    intent.putExtra("gesamtPer", gesamtPer);
                    startActivity(intent);

                    CustomIntent.customType(this, "left-to-right");
                } else {
                    lastPersonAdded = true;
                    weiter.setText(R.string.weiter);
                    nameText.setClickable(false);
                }
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }


    class CustomAdapter extends BaseAdapter {

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
            convertView = getLayoutInflater().inflate(R.layout.mylistitemwhitetext, null);
            TextView pers = convertView.findViewById(R.id.textPer);

            data.moveToPosition(position);

            pers.setText("" + data.getString(1));

            return convertView;
        }
    }


}
