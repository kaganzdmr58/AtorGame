package com.cbagames.ator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class BolumSecActivity extends AppCompatActivity {

    private ImageView imageViewBolIlk,imageViewBol1,imageViewBol2,imageViewBolX1,imageViewBolX4
            ,imageViewBolX5,imageViewBolX8,imageViewbolX12;

    public static Class bolum;

    public static SharedPreferences spCan;
    public static SharedPreferences.Editor editorCan;

    public static int canSayisi;


    public static int getCanSayisi() {
        canSayisi = spCan.getInt("canSayisi",5);
        return canSayisi;
    }

    public static void setCanSayisi(int canSayisi) {
        BolumSecActivity.canSayisi = canSayisi;
        editorCan.putInt("canSayisi",canSayisi);
        editorCan.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolum_sec);

        imageViewBolIlk = findViewById(R.id.imageViewBolIlk);
        imageViewBol1 = findViewById(R.id.imageViewBol1);
        imageViewBol2 = findViewById(R.id.imageViewBol2);
        imageViewBolX1 = findViewById(R.id.imageViewBolX1);
        imageViewBolX4 = findViewById(R.id.imageViewBolX4);
        imageViewBolX5 = findViewById(R.id.imageViewBolX5);
        imageViewBolX8 = findViewById(R.id.imageViewBolX8);
        imageViewbolX12 = findViewById(R.id.imageViewbolX12);



        spCan = getSharedPreferences("canSayisi", Context.MODE_PRIVATE);
        editorCan = spCan.edit();

        canSayisi = getCanSayisi();

        // Karşılama ekranından gelirken 1 can ekleniyor
        boolean canEkle = getIntent().getBooleanExtra("karsilamaCanEkle",false);
        if (canEkle){
            if(canSayisi < 5){
                canSayisi += 1;
                setCanSayisi(canSayisi);
            }
        }

        // Alarm Receiver deki can sayısı alınıyor ve güncelleniyor.
        if (AlarmReceiver.canSayisi > canSayisi){
            //AlarmReceiver da can sayısı sürekli olarak güncelleniyor 
            // onu alıyoruz ve buradaki shared ile kayıt yapıyoruz
            setCanSayisi(AlarmReceiver.canSayisi);
            canSayisi = AlarmReceiver.canSayisi;
        }
        
        

        imageViewBolIlk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bolum = MainActivityIlk.class;
                Intent intent = new Intent(getApplicationContext(), MainActivityIlk.class);
                intent.putExtra("can",canSayisi);
                startActivity(intent);
                finish();
            }
        });


        imageViewBol1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bolum = MainActivity.class;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("can",canSayisi);
                startActivity(intent);
                finish();
            }
        });

        imageViewBol2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bolum = MainActivity2.class;
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                intent.putExtra("can",canSayisi);
                startActivity(intent);
                finish();
            }
        });

        imageViewBolX1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bolum = MainActivity3.class;
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                intent.putExtra("can",canSayisi);
                startActivity(intent);
                finish();
            }
        });

        imageViewBolX4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bolum = MainActivity4.class;
                Intent intent = new Intent(getApplicationContext(), MainActivity4.class);
                intent.putExtra("can",canSayisi);
                startActivity(intent);
                finish();
            }
        });

        imageViewBolX5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bolum = MainActivity5.class;
                Intent intent = new Intent(getApplicationContext(), MainActivity5.class);
                intent.putExtra("can",canSayisi);
                startActivity(intent);
                finish();
            }
        });


        imageViewBolX8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bolum = MainActivity8.class;
                Intent intent = new Intent(getApplicationContext(), MainActivity8.class);
                intent.putExtra("can",canSayisi);
                startActivity(intent);
                finish();
            }
        });





        imageViewbolX12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bolum = MainActivity12.class;
                Intent intent = new Intent(getApplicationContext(), MainActivity12.class);
                intent.putExtra("can",canSayisi);
                startActivity(intent);
                finish();
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


}