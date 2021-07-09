package com.cbagames.ator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity11 extends AppCompatActivity {

    private ImageView kirmiziUcgen,anaKarakter,sariDaire,siyahKare;
    private TextView textViewBaslamakİcinDokun,textViewSkor,textViewTimer;
    private ConstraintLayout cl;

    //pozisyonlar
    private int anakarakterX;
    private int anakarakterY;
    private int siyahkareX;
    private int siyahkareY;
    private int saridaireX;
    private int saridaireY;
    private int kirmiziucgenX;
    private int kirmiziucgenY;

    // Boyutlara
    private int ekranGenisliği;
    private int ekranYuksekligi;
    private int anakarakterGenisligi;
    private int anakarakterYuksekligi;


    // Hız
    private int anakarakterHiz;
    private int saridaireHız;
    private int kirmiziucgenHız;
    private int siyahkareHiz;

    private int skor = 0;
    //Kontroller
    private boolean dokunmaKontrol = false;
    private boolean baslangicKontrol = false;

    private Timer timer = new Timer();
    private Handler handler = new Handler();
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);

        cl = findViewById(R.id.cl);
        kirmiziUcgen = findViewById(R.id.imageViewKirmiziUcgen);
        anaKarakter = findViewById(R.id.imageViewAnaKarakter);
        sariDaire = findViewById(R.id.imageViewSariDaire);
        siyahKare = findViewById(R.id.imageViewSiyahKare);
        textViewBaslamakİcinDokun = findViewById(R.id.textViewBaslamakİcinDokun);
        textViewSkor = findViewById(R.id.textViewSkor);
        textViewTimer = findViewById(R.id.textViewTimer);

        siyahKare.setY(-100);
        siyahKare.setX(-100);
        sariDaire.setY(-100);
        sariDaire.setX(-100);
        kirmiziUcgen.setY(-100);
        kirmiziUcgen.setX(-100);

        //https://fatihdemirag.net/android-sayac-yapimi-timer/
        countDownTimer=new CountDownTimer(35000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                gecis(skor);
            }
        }.start();

        cl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (baslangicKontrol){
                    if (event.getAction() == MotionEvent.ACTION_DOWN){
                        Log.e("MOTION EVENT","Ekrana Dokundu");
                        dokunmaKontrol = true;
                    }

                    if (event.getAction() == MotionEvent.ACTION_UP){
                        Log.e("MOTION EVENT","Ekranı Bıraktı");
                        dokunmaKontrol = false;
                    }

                }else {
                    baslangicKontrol = true;

                    textViewBaslamakİcinDokun.setVisibility(View.INVISIBLE);

                    anakarakterX = (int) anaKarakter.getX();
                    anakarakterY = (int) anaKarakter.getY();

                    anakarakterGenisligi = anaKarakter.getWidth();
                    anakarakterYuksekligi = anaKarakter.getHeight();
                    ekranGenisliği = cl.getWidth();
                    ekranYuksekligi = cl.getHeight();

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    anakarakterHareketEttirme();
                                    cisimleriHareketEttir();
                                    carpismaKontrol();
                                }
                            });
                        }
                    },0,20);


                }

                return true;
            }
        });


    }

    public void anakarakterHareketEttirme(){

        anakarakterHiz = Math.round(ekranYuksekligi/60);  // 1280 / 60 = 20

        if (dokunmaKontrol){
            anakarakterY -=anakarakterHiz;
        }else {
            anakarakterY +=anakarakterHiz;
        }

        if (anakarakterY <= 0){
            anakarakterY = 0;
        }

        if (anakarakterY >= ekranYuksekligi - anakarakterYuksekligi){
            anakarakterY = ekranYuksekligi - anakarakterYuksekligi;
        }

        anaKarakter.setY(anakarakterY);
    }

    public void cisimleriHareketEttir(){

        saridaireHız = Math.round(ekranGenisliği/100);  // 760 / 60 = 26
        siyahkareHiz = Math.round(ekranGenisliği/100);  // 760 / 60 = 26
        kirmiziucgenHız = Math.round(ekranGenisliği/100);  // 760 / 30 = 13


        // siyah kare için
        siyahkareX -= siyahkareHiz;
        if (siyahkareX < 0 ){
            siyahkareX = ekranGenisliği + 20;
            siyahkareY = (int) Math.floor(Math.random() * ekranYuksekligi);
        }
        siyahKare.setX(siyahkareX);
        siyahKare.setY(siyahkareY);


        // kirmizi üçgen için
        kirmiziucgenX -= kirmiziucgenHız;
        if (kirmiziucgenX < 0 ) {
            kirmiziucgenX = ekranGenisliği + 20;
            kirmiziucgenY = (int) Math.floor(Math.random() * ekranYuksekligi);
        }
        kirmiziUcgen.setX(kirmiziucgenX);
        kirmiziUcgen.setY(kirmiziucgenY);

        // sari daire
        saridaireX -= saridaireHız;
        if (saridaireX < 0 ){
            saridaireX = ekranGenisliği + 20;
            saridaireY = (int) Math.floor(Math.random() * ekranYuksekligi);
        }
        sariDaire.setX(saridaireX);
        sariDaire.setY(saridaireY);



    }

    public void carpismaKontrol(){

        int saridaireMerkezX = saridaireX + sariDaire.getWidth()/2;
        int saridaireMerkezY = saridaireY + sariDaire.getHeight()/2;

        if (0 <= saridaireMerkezX && saridaireMerkezX <= anakarakterGenisligi
                && anakarakterY <= saridaireMerkezY && saridaireMerkezY <=anakarakterY+anakarakterYuksekligi){
            skor += 3;
            saridaireX = -10;
        }

        int kirmiziucgenMerkezX = kirmiziucgenX + kirmiziUcgen.getWidth()/2;
        int kirmiziucgenMerkezY = kirmiziucgenY + kirmiziUcgen.getHeight()/2;

        if (0 <= kirmiziucgenMerkezX && kirmiziucgenMerkezX <= anakarakterGenisligi
                && anakarakterY <= kirmiziucgenMerkezY && kirmiziucgenMerkezY <=anakarakterY+anakarakterYuksekligi){
            skor += 2;
            kirmiziucgenX = -10;
        }

        int siyahkareMerkezX = siyahkareX + siyahKare.getWidth()/2;
        int siyahkareMerkezY = siyahkareY + siyahKare.getHeight()/2;

        if (0 <= siyahkareMerkezX && siyahkareMerkezX <= anakarakterGenisligi
                && anakarakterY <= siyahkareMerkezY && siyahkareMerkezY <=anakarakterY+anakarakterYuksekligi){
            //skor += 1;

           gecis(0);

        }


        textViewSkor.setText(String.valueOf(skor));
    }



    public void gecis(int skor){
        // timer durduruluyor
        countDownTimer.cancel();
        timer.cancel();
        timer = null;

        Intent intent = new Intent(getApplicationContext(),SonucEkraniActivity.class);
        intent.putExtra("skor",skor);
        startActivity(intent);
    }
}