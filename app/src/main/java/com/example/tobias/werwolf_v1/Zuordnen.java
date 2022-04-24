package com.example.tobias.werwolf_v1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;

public class Zuordnen extends AppCompatActivity {


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

    private int AmorId;
    private int WerwolfId;
    private int HexeId;
    private int DiebId;
    private int SeherId;
    private int JungesId;
    private int JaegerId;
    private int BuergerId;
    private int WaechterId;
    private int WeisserWerwolfId;
    private int MaedchenId;
    private int FloetenspielerId;
    private int UrwolfId;
    private int RitterId;
    private int FreundeId;

    private int bildPos;

    private String nameAkt;

    private ListView rollen;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private DatabaseHelper mDatabaseHelper;
    private Cursor data;
    private TextView persTxt;
    private CustomAdapter customAdapter;
    private Intent intent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_zuordnen);

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

        AmorId = -1;
        WerwolfId = -1;
        HexeId = -1;
        DiebId = -1;
        SeherId = -1;
        JungesId = -1;
        JaegerId = -1;
        BuergerId = -1;
        WaechterId = -1;
        WeisserWerwolfId = -1;
        MaedchenId = -1;
        FloetenspielerId = -1;
        UrwolfId = -1;
        RitterId = -1;
        FreundeId = -1;

        bildPos = 0;
        mDatabaseHelper = new DatabaseHelper(this);
        data = mDatabaseHelper.getData();

        persTxt = (TextView) findViewById(R.id.persTxt);
        persTxt.setTextColor(Color.WHITE);
        namenSchreiben();

        bilderFeldErstellen();

        rollen = (ListView) findViewById(R.id.rollen);
        customAdapter = new CustomAdapter();

        rollen.setAdapter(customAdapter);
        rollen.setOnItemClickListener(new AdapterView.OnItemClickListener() {            //Hier kmmt eine Möglichkeit zum Löschen eine s Eintrages
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String charakterHilfe = charakterErmitteln(position);
                Log.e("D", "Name:    " + nameAkt);
                mDatabaseHelper.deleteNurName(nameAkt);
                mDatabaseHelper.addCharakter(nameAkt, charakterHilfe);
                // Toast.makeText(Zuordnen.this, ""+nameAkt+"  |  "+charakterHilfe, Toast.LENGTH_SHORT).show();
                namenSchreiben();
            }
        });
    }

    public void namenSchreiben() {
        if (data.moveToNext()) {
            nameAkt = data.getString(1);
            persTxt.setText(" " + nameAkt);
        } else {

            startActivity(intent);
            CustomIntent.customType(this, "left-to-right");
        }

    }

    public String charakterErmitteln(int position) {
        String retValue = "";
        int menge = 0;

        if (position == WaechterId) {
            retValue = "waechter";
            anzahlWaechter--;
        } else if (position == WerwolfId) {
            retValue = "werwolf";
            anzahlWerwolf--;
        } else if (position == JaegerId) {
            retValue = "jaeger";
            anzahlJaeger--;
        } else if (position == JungesId) {
            retValue = "junges";
            anzahlJunges--;
        } else if (position == SeherId) {
            retValue = "";
            anzahlSeher--;
        } else if (position == AmorId) {
            retValue = "amor";
            anzahlAmor--;
        } else if (position == BuergerId) {
            retValue = "buerger";
            anzahlBuerger--;
        } else if (position == DiebId) {
            retValue = "dieb";
            anzahlDieb--;
        } else if (position == HexeId) {
            retValue = "hexe";
            anzahlHexe--;
        } else if (position == WeisserWerwolfId) {
            retValue = "weisserwerwolf";
            anzahlWeisserWerwolf--;
        } else if (position == FloetenspielerId) {
            retValue = "floetenspieler";
            anzahlFloetenspieler--;
        } else if (position == UrwolfId) {
            retValue = "urwolf";
            anzahlUrwolf--;
        } else if (position == RitterId) {
            retValue = "ritter";
            anzahlRitter--;
        } else if (position == MaedchenId) {
            retValue = "maedchen";
            anzahlMaedchen--;
        } else if (position == FreundeId) {
            retValue = "freunde";
            anzahlFreunde--;
        }
        bilderFeldErstellen();


        customAdapter.notifyDataSetChanged();
        return retValue;
    }

    public void bilderFeldErstellen() {
        AmorId = -1;
        WerwolfId = -1;
        HexeId = -1;
        DiebId = -1;
        SeherId = -1;
        JungesId = -1;
        JaegerId = -1;
        BuergerId = -1;
        WaechterId = -1;
        WeisserWerwolfId = -1;
        MaedchenId = -1;
        FloetenspielerId = -1;
        UrwolfId = -1;
        RitterId = -1;
        FreundeId = -1;

        int position = 0;

        if (anzahlWerwolf > 0) {
            WerwolfId = position;
            position++;
        }

        if (anzahlBuerger > 0) {
            BuergerId = position;
            position++;
        }

        if (anzahlAmor > 0) {
            AmorId = position;
            position++;
        }

        if (anzahlHexe > 0) {
            HexeId = position;
            position++;
        }

        if (anzahlWaechter > 0) {
            WaechterId = position;
            position++;
        }

        if (anzahlMaedchen > 0) {
            MaedchenId = position;
            position++;
        }

        if (anzahlSeher > 0) {
            SeherId = position;
            position++;
        }

        if (anzahlDieb > 0) {
            DiebId = position;
            position++;
        }

        if (anzahlJaeger > 0) {
            JaegerId = position;
            position++;
        }

        if (anzahlRitter > 0) {
            RitterId = position;
            position++;
        }

        if (anzahlFloetenspieler > 0) {
            FloetenspielerId = position;
            position++;
        }

        if (anzahlFreunde > 0) {
            FreundeId = position;
            position++;
        }


        if (anzahlJunges > 0) {
            JungesId = position;
            position++;
        }

        if (anzahlWeisserWerwolf > 0) {
            WeisserWerwolfId = position;
            position++;
        }

        if (anzahlUrwolf > 0) {
            UrwolfId = position;
            position++;
        }
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            int summe = 0;

            if (anzahlAmor > 0) {
                summe++;
            }
            if (anzahlWerwolf > 0) {
                summe++;
            }
            if (anzahlSeher > 0) {
                summe++;
            }
            if (anzahlJunges > 0) {
                summe++;
            }
            if (anzahlJaeger > 0) {
                summe++;
            }
            if (anzahlHexe > 0) {
                summe++;
            }
            if (anzahlDieb > 0) {
                summe++;
            }
            if (anzahlWaechter > 0) {
                summe++;
            }
            if (anzahlBuerger > 0) {
                summe++;
            }
            if (anzahlWeisserWerwolf > 0) {
                summe++;
            }
            if (anzahlMaedchen > 0) {
                summe++;
            }
            if (anzahlFloetenspieler > 0) {
                summe++;
            }
            if (anzahlUrwolf > 0) {
                summe++;
            }
            if (anzahlRitter > 0) {
                summe++;
            }
            if (anzahlFreunde > 0) {
                summe++;
            }
            return summe;
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
            convertView = getLayoutInflater().inflate(R.layout.mein_list_item, null);
            TextView charakter = (TextView) convertView.findViewById(R.id.charakter);
            LinearLayout layoutcharakterRolle = convertView.findViewById(R.id.layoutcharakterRolle);

            if (position == WaechterId) {
                charakter.setText(R.string.waechter);
                layoutcharakterRolle.setBackgroundColor(getResources().getColor(R.color.farbe_waechter));
            } else if (position == WerwolfId) {
                charakter.setText(R.string.werwolf);
                layoutcharakterRolle.setBackgroundColor(getResources().getColor(R.color.farbe_werwolf));
            } else if (position == DiebId) {
                charakter.setText(R.string.dieb);
                layoutcharakterRolle.setBackgroundColor(getResources().getColor(R.color.farbe_dieb));
            } else if (position == SeherId) {
                charakter.setText(R.string.seher);
                layoutcharakterRolle.setBackgroundColor(getResources().getColor(R.color.farbe_seher));
            } else if (position == JaegerId) {
                charakter.setText(R.string.jaeger);
                layoutcharakterRolle.setBackgroundColor(getResources().getColor(R.color.farbe_jaeger));
            } else if (position == JungesId) {
                charakter.setText(R.string.junges);
                layoutcharakterRolle.setBackgroundColor(getResources().getColor(R.color.farbe_junges));
            } else if (position == BuergerId) {
                charakter.setText(R.string.buerger);
                layoutcharakterRolle.setBackgroundColor(getResources().getColor(R.color.farbe_buerger));
            } else if (position == HexeId) {
                charakter.setText(R.string.hexe);
                layoutcharakterRolle.setBackgroundColor(getResources().getColor(R.color.farbe_hexe));
            } else if (position == AmorId) {
                charakter.setText(R.string.amor);
                layoutcharakterRolle.setBackgroundColor(getResources().getColor(R.color.farbe_amor));
            } else if (position == MaedchenId) {
                charakter.setText(R.string.maedchen);
                layoutcharakterRolle.setBackgroundColor(getResources().getColor(R.color.farbe_maedchen));
            } else if (position == WeisserWerwolfId) {
                charakter.setText(R.string.weisser_werwolf);
                layoutcharakterRolle.setBackgroundColor(getResources().getColor(R.color.farbe_weisserwolf));
            } else if (position == FloetenspielerId) {
                charakter.setText(R.string.floetenspieler);
                layoutcharakterRolle.setBackgroundColor(getResources().getColor(R.color.farbe_floetenspieler));
            } else if (position == FreundeId) {
                charakter.setText(R.string.freunde);
                layoutcharakterRolle.setBackgroundColor(getResources().getColor(R.color.farbe_freunde));
            } else if (position == RitterId) {
                charakter.setText(R.string.ritter);
                layoutcharakterRolle.setBackgroundColor(getResources().getColor(R.color.farbe_ritter));
            } else if (position == UrwolfId) {
                charakter.setText(R.string.urwolf);
                layoutcharakterRolle.setBackgroundColor(getResources().getColor(R.color.farbe_urwolf));
            }
            return convertView;
        }
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }
}
