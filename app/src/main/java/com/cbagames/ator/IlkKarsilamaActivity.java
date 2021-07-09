package com.cbagames.ator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class IlkKarsilamaActivity extends AppCompatActivity {

    private ImageView imageViewIlkKarsilama;
    private TextView textViewIleri,textViewGeri,textViewDurum;
    private int resimSirasi = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilk_karsilama);
        MultiDex.install(this);

        imageViewIlkKarsilama = findViewById(R.id.imageViewIlkKarsilama);
        textViewIleri = findViewById(R.id.textViewIleri);
        textViewGeri = findViewById(R.id.textViewGeri);
        textViewDurum = findViewById(R.id.textViewDurum);

        resimGosterme(0);

        textViewIleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resimSirasi == 10){
                    startActivity(new Intent(getApplicationContext(),KarsilamaEkraniActivity.class));
                    finish();
                }else {
                    resimGosterme(resimSirasi);
                }
            }
        });
        textViewGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimGosterme(resimSirasi - 2);
                textViewIleri.setText(R.string.k_ileri);
            }
        });

        imageViewIlkKarsilama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimGosterme(resimSirasi);
            }
        });




        Boolean karanlikMod = KarsilamaEkraniActivity.sp.getBoolean("karanlikMod",false);
        karanlikModKontrol(karanlikMod);

    }


    private void karanlikModKontrol(Boolean karanlikMod){
        if (!karanlikMod){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }



    private void resimGosterme(int sira){
        Resources res = getResources();
        String dil = res.getString(R.string.dil);
        int dilInt = Integer.valueOf(dil);
        if (dilInt == 1){
            // ingilizce
            resimGostermeEn(sira);
        }
        if (dilInt == 2){
            // türkçe
            resimGostermeTr(sira);
        }

    }

    private void resimGostermeTr(int sira){
        sira = sira +1;
        if (sira >10){
            sira = sira % 10;
        }

        resimSirasi = sira;
        textViewDurum.setText(sira+" / 10");

        Resources res = getResources();
        switch (sira){
            case 1:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama));
                textViewGeri.setVisibility(View.INVISIBLE);
                break;
            case 2:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama2));
                textViewGeri.setVisibility(View.VISIBLE);
                break;
            case 3:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama3));
                break;
            case 4:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama4));
                break;
            case 5:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama5));
                break;
            case 6:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama6));
                break;
            case 7:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama7));
                break;
            case 8:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama8));
                break;
            case 9:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama9));
                break;
            case 10:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama10));
                textViewIleri.setText( R.string.k_oyuna_basla);
                break;

            default:
                break;
        }
    }


    private void resimGostermeEn(int sira){
        sira = sira +1;
        if (sira >10){
            sira = sira % 10;
        }


        resimSirasi = sira;
        textViewDurum.setText(sira+" / 10");

        Resources res = getResources();
        switch (sira){
            case 1:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama_e));
                textViewGeri.setVisibility(View.INVISIBLE);
                break;
            case 2:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama2_e));
                textViewGeri.setVisibility(View.VISIBLE);
                break;
            case 3:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama3_e));
                break;
            case 4:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama4_e));
                break;
            case 5:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama5_e));
                break;
            case 6:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama6_e));
                break;
            case 7:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama7_e));
                break;
            case 8:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama8_e));
                break;
            case 9:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama9_e));
                break;
            case 10:
                imageViewIlkKarsilama.setImageDrawable(res.getDrawable(R.drawable.karsilama10_e));
                textViewIleri.setText( R.string.k_oyuna_basla);

                break;

            default:
                break;
        }
    }



}