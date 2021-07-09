package com.cbagames.ator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.multidex.MultiDex;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity8 extends AppCompatActivity {
    private ImageView sari,anaKarakter,mavi,sifir,pembe,yesil,bonus;
    private TextView textViewSkor,textViewMukemmel;
    private ConstraintLayout cl;

    public static TextView textViewCanSayisi;

    //pozisyonlar
    private int anakarakterX;
    private int anakarakterY;
    private int sifirX;
    private int sifirY;
    private int artiX;
    private int artiY;
    private int eksiX;
    private int eksiY;
    private int bonusX;
    private int bonusY;
    private int carpiX;
    private int carpiY;
    private int bolX;
    private int bolY;

    // Boyutlara
    private int ekranGenisliği;
    private int ekranYuksekligi;
    private int anakarakterGenisligi;
    private int anakarakterYuksekligi;
    private int toplarGenisligi;
    private int toplarYuksekligi;


    // Hız
    private int anakarakterHiz;
    private int artiHız;
    private int eksiHız;
    private int sifirHiz;
    private int bonusHiz;
    private int carpiHiz;
    private int bolHiz;


    //Kontroller
    private boolean baslangicKontrol = false;
    private int skor = 0;
    private Boolean bonuskontrol = false;
    private int bonusSayac = 0;

    private Timer timerx9 = new Timer();
    private Handler handler = new Handler();

    // Sayilar
    private int sayix = 0;
    //private int hedefSayi=0;
    private int bonusSayi = 1;
    private int bonusZamanlayici = 1;


    private int levelHiz1 = 0;
    private int levelHiz2 = 0;

    private Resources res ;
    private String hedefSayiString;
    public static int canSayisi;

    private TextView textViewTimer;
    private CountDownTimer countDownTimerx9;

    private int avatarRenk = 1;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        countDownTimerx9.cancel();
        if (timerx9 != null) {
            timerx9.cancel();
            timerx9 = null;
        }

        startActivity(new Intent(getApplicationContext(), BolumSecActivity.class));
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        MultiDex.install(this);

        cl = findViewById(R.id.cl);
        sari = findViewById(R.id.imageViewEksi);
        anaKarakter = findViewById(R.id.imageViewAnaKarakter);
        mavi = findViewById(R.id.imageViewArti);
        sifir = findViewById(R.id.imageViewSifir);
        textViewSkor = findViewById(R.id.textViewHedefSayi);
        bonus = findViewById(R.id.imageViewBonus);
        textViewMukemmel = findViewById(R.id.textViewMukemmel);
        textViewCanSayisi = findViewById(R.id.textViewCanSayisi);
        textViewTimer = findViewById(R.id.textViewTimer);
        pembe = findViewById(R.id.imageViewCarpi);
        yesil = findViewById(R.id.imageViewBolu);

        res = getResources();

        bonusZamanlayici =  (int) Math.floor(Math.random() * 17);

        eksiX = xKonumlandır();
        artiX = xKonumlandır();
        sifirX = xKonumlandır();
        bonusX = xKonumlandır();
        carpiX = xKonumlandır();
        bolX = xKonumlandır();


        sifir.setY(-250);
        sifir.setX(sifirX);

        mavi.setY(-250);
        mavi.setX(artiX);

        sari.setY(-250);
        sari.setX(eksiX);

        pembe.setY(-250);
        pembe.setX(carpiX);

        yesil.setY(-250);
        yesil.setX(bolX);


        bonus.setY(-250);
        bonus.setX(eksiX);

       /* hedefSayi = (int) Math.floor(Math.random() * 50);
        hedefSayi += 1;
        hedefSayiString = res.getString(R.string.main_hedef_sayi);
        textViewSkor.setText( hedefSayiString + hedefSayi);*/


        avatarSecimiUygula();

        /*levelHiz1 = KarsilamaEkraniActivity.sp.getInt("level",1);       // normal
        levelHiz2 = KarsilamaEkraniActivity.sp.getInt("level",1);       // Bonus
        if (levelHiz1 > 300){
            levelHiz1 = 300;
        }*/
        levelHiz1 = 50;
        levelHiz2 = levelHiz1;
        //levelHiz1 = levelHiz1 / 3;
        levelHiz2 = levelHiz2 / 30;

        canSayisi = getIntent().getIntExtra("can",5);
        //canSayisi = KarsilamaEkraniActivity.sp.getInt("canSayisi",5);
        textViewCanSayisi.setText(String.valueOf(canSayisi));
        // eğer can sayısı 0 ise 1 dk beklet.
        textViewSkor.setText(res.getString(R.string.a_skor) + 0);

        /*
        //https://fatihdemirag.net/android-sayac-yapimi-timer/
        countDownTimerx9=new CountDownTimer(42000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                gecis(skor);
            }
        }.start();
        */

        /*imageViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AyarlarActivity.class));
                finish();
            }
        });*/


        cl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!baslangicKontrol) {

                    baslangicKontrol = true;

                    anakarakterX = (int) anaKarakter.getX();
                    anakarakterY = (int) anaKarakter.getY();

                    anakarakterGenisligi = anaKarakter.getWidth();
                    anakarakterYuksekligi = anaKarakter.getHeight();

                    ekranGenisliği = cl.getWidth();
                    ekranYuksekligi = cl.getHeight();
                    toplarGenisligi = mavi.getWidth();
                    toplarYuksekligi = sifir.getHeight();

                    //imageViewYonOku.setVisibility(View.INVISIBLE);

                    eksiX = xKonumlandır();
                    artiX = xKonumlandır();
                    sifirX = xKonumlandır();
                    bonusX = xKonumlandır();
                    carpiX = xKonumlandır();
                    bolX = xKonumlandır();


                    //https://fatihdemirag.net/android-sayac-yapimi-timer/
                    countDownTimerx9=new CountDownTimer(42000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            textViewTimer.setText(String.valueOf(millisUntilFinished/1000));
                        }

                        @Override
                        public void onFinish() {
                            gecis(skor,canSayisi);
                        }
                    }.start();


                    timerx9.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // ana karakter hareketi
                                    float sayifloat = event.getX();
                                    if (sayifloat >= ekranGenisliği - anakarakterGenisligi){
                                        sayifloat = ekranGenisliği - anakarakterGenisligi;
                                    }
                                    anaKarakter.setX(sayifloat);
                                    sayix = (int) sayifloat;
                                    //textViewSkor.setText("Ana karakter X : "+sayix+ "\nAna karakter genişliği : "+anakarakterGenisligi+"\nToplar Genişliği : "+toplarGenisligi);

                                    cisimleriHareketEttir();
                                    carpismaKontrol();

                                    if (bonuskontrol){
                                        bonusHareketEttir();
                                    }

                                }
                            });
                        }
                    }, 0, 20);


                }

                return true;
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


    private void yemeSesi(){
        // sesler
        Boolean sesDurum = KarsilamaEkraniActivity.sp.getBoolean("sesDurum",false);
        final MediaPlayer sesYeme = MediaPlayer.create(MainActivity8.this, R.raw.eat_2_potato);
        if (sesDurum) {
            sesYeme.start();
        }
    }



    private void titresim(int sure){
        // Titreşim Modu
        Boolean titresimMod = KarsilamaEkraniActivity.sp.getBoolean("titresimMod",false);

        if (titresimMod) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(sure, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(sure);
            }
        }
    }


    public void cisimleriHareketEttir(){


        artiHız = Math.round(ekranYuksekligi/(155 - levelHiz1));
        sifirHiz = Math.round(ekranYuksekligi/(140 - levelHiz1));
        eksiHız = Math.round(ekranYuksekligi/(170 - levelHiz1));
        bolHiz = Math.round(ekranYuksekligi/(200 - levelHiz1));
        carpiHiz = Math.round(ekranYuksekligi/(150 - levelHiz1));


        // mavi X 0
        sifirY += sifirHiz;
        if (sifirY > ekranYuksekligi){
            sifirY = - 40;
            sifirX = xKonumlandır();
        }

        sifir.setX(sifirX);
        sifir.setY(sifirY);



        // kirmizi eksi
        eksiY += eksiHız;
        if (eksiY > ekranYuksekligi ) {
            eksiY = -40;
            eksiX = xKonumlandır();

        }

        sari.setX(eksiX);
        sari.setY(eksiY);



        // sari arti
        artiY += artiHız;
        if (artiY > ekranYuksekligi){
            artiY = -40;
            artiX = xKonumlandır();

        }

        mavi.setX(artiX);
        mavi.setY(artiY);


        // sari carpi
        carpiY += carpiHiz;
        if (carpiY > ekranYuksekligi){
            carpiY = -40;
            carpiX = xKonumlandır();

        }

        pembe.setX(carpiX);
        pembe.setY(carpiY);



        // sari bölü
        bolY += bolHiz;
        if (bolY > ekranYuksekligi){
            bolY = -40;
            bolX = xKonumlandır();

        }

        yesil.setX(bolX);
        yesil.setY(bolY);



    }

    public void bonusHareketEttir(){

        bonusHiz = Math.round(ekranYuksekligi / (50 - levelHiz2));  // 760 / 60 = 26

        // sari arti
        bonusY += bonusHiz;
        if (bonusY > ekranYuksekligi){
            bonuskontrol = false;
            bonusY = -400;
            bonusX = xKonumlandır();
        }

        bonus.setX(bonusX);
        bonus.setY(bonusY);

        //arti.setRotation(arti.getRotation() + 3);
        bonus.setRotation(0);


    }


    public void carpismaKontrol(){

        int saridaireMerkezX = artiX + mavi.getWidth()/2;
        int saridaireMerkezY = artiY + mavi.getHeight()/2;

        if (    saridaireMerkezX >= sayix  && saridaireMerkezX <= sayix + anakarakterGenisligi &&
                saridaireMerkezY >= anakarakterY &&  saridaireMerkezY <= anakarakterY+anakarakterYuksekligi){
            // arti sayi  //mavi
            if (avatarRenk == 1){
                skor += 1;
                textViewSkor.setText(res.getString(R.string.a_skor) + skor);
                avatarSecimiUygula();

            }
            artiY = -40;
            artiX = xKonumlandır();
            titresim(100);
            bonusUret();
            yemeSesi();

        }

        int kirmiziucgenMerkezX = eksiX + sari.getWidth()/2;
        int kirmiziucgenMerkezY = eksiY + sari.getHeight()/2;

        if (    kirmiziucgenMerkezX >= sayix  && kirmiziucgenMerkezX <= sayix + anakarakterGenisligi &&
                kirmiziucgenMerkezY >= anakarakterY &&  kirmiziucgenMerkezY <= anakarakterY+anakarakterYuksekligi){
            // eksi sayi  -- sari
            if (avatarRenk == 2){
                skor += 1;
                textViewSkor.setText(res.getString(R.string.a_skor) + skor);
                avatarSecimiUygula();
            }
            eksiY = -40;
            eksiX = xKonumlandır();
            //textViewSkor.setText("Kırmızı Çarpışma gerçekleşti.");
            //textViewSkor.setText(hedefSayiString+hedefSayi);

            titresim(100);
            bonusUret();
            yemeSesi();
        }


        // carpi  -- pembe
        int carpiMerkezX = carpiX + pembe.getWidth()/2;
        int carpinMerkezY = carpiY + pembe.getHeight()/2;

        if (    carpiMerkezX >= sayix  && carpiMerkezX <= sayix + anakarakterGenisligi &&
                carpinMerkezY >= anakarakterY &&  carpinMerkezY <= anakarakterY+anakarakterYuksekligi){
            // carpi sayi
            if (avatarRenk == 3){
                skor += 1;
                textViewSkor.setText(res.getString(R.string.a_skor) + skor);
                avatarSecimiUygula();
            }
            carpiY = -40;
            carpiX = xKonumlandır();

            titresim(100);
            bonusUret();
            yemeSesi();
        }


        // bol  -- yesil
        int bolMerkezX = bolX + yesil.getWidth()/2;
        int bolMerkezY = bolY + yesil.getHeight()/2;

        if (    bolMerkezX >= sayix  && bolMerkezX <= sayix + anakarakterGenisligi &&
                bolMerkezY >= anakarakterY &&  bolMerkezY <= anakarakterY+anakarakterYuksekligi){

            if (avatarRenk == 4){
                skor += 1;
                textViewSkor.setText(res.getString(R.string.a_skor) + skor);
                avatarSecimiUygula();
            }
            bolY = -40;
            bolX = xKonumlandır();

            titresim(100);
            bonusUret();
            yemeSesi();
        }





        int maviMerkezX = sifirX + sifir.getWidth()/2;
        int maviMerkezY = sifirY + sifir.getHeight()/2;

        if (    maviMerkezX >= sayix  && maviMerkezX <= sayix + anakarakterGenisligi &&
                maviMerkezY >= anakarakterY &&  maviMerkezY <= anakarakterY+anakarakterYuksekligi){


            gecis(0,canSayisi-1);

        }


        // Bonus çarpışması
        int bonusMerkezX = bonusX + bonus.getWidth()/2;
        int bonusMerkezY = bonusY + bonus.getHeight()/2;

        if (    bonusMerkezX >= sayix  && bonusMerkezX <= sayix + anakarakterGenisligi &&
                bonusMerkezY >= anakarakterY &&  bonusMerkezY <= anakarakterY+anakarakterYuksekligi){

            bonuskontrol = false;

            bonusY = - 400;
            bonusX = xKonumlandır();
            bonus.setX(bonusX);
            bonus.setY(bonusY);

            int bonusSayiEkle = (int) Math.floor(Math.random() * 2);
            bonusSayiEkle += 2;

            bonusSayi = bonusSayi + bonusSayiEkle;

            textViewMukemmel.setText("X "+ bonusSayi+ res.getText(R.string.main_bonus_kazandiniz));
            textViewMukemmel.setVisibility(View.VISIBLE);

            yemeSesi();

        }




    }

    public void gecis(int skor,int can){
        // timer durduruluyor

        countDownTimerx9.cancel();
        if (timerx9 != null) {
            timerx9.cancel();
            timerx9 = null;
        }

        titresim(400);

        int gidenSayi = skor * bonusSayi;
        bonusSayi = 1;

        Intent intent = new Intent(getApplicationContext(), SonucEkraniActivity.class);
        intent.putExtra("skor",gidenSayi);
        intent.putExtra("can",can);
        startActivity(intent);
        finish();
    }

    public void bonusUret(){
        bonusSayac += 1;

        if ((bonusSayac % 17) == bonusZamanlayici){
            bonuskontrol = true;
        }
        if (bonusSayac % 17 == (bonusZamanlayici + 3) ){
            textViewMukemmel.setVisibility(View.INVISIBLE);
        }

    }

    public int xKonumlandır(){
        int x = (int) Math.floor(Math.random() * (ekranGenisliği - toplarGenisligi));
        if(x <= 50){
            x += 50;
        }
        return x;
    }


    public void avatarSecimiUygula(){
        int avatarRenkGecici = (int) Math.floor(Math.random() * 4)+1;
        if (avatarRenk != avatarRenkGecici){
            avatarRenk = avatarRenkGecici;
        }else {
            avatarRenk = (int) Math.floor(Math.random() * 4)+1;
        }
        Resources res = getResources();
        switch (avatarRenk){
            case 1:
                anaKarakter.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu));
                break;
            case 2:
                anaKarakter.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s2));
                break;
            case  3:
                anaKarakter.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s3));
                break;
            case 4:
                anaKarakter.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s4));
                break;
            /*case 5:
                anaKarakter.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s5));
                break;
            case 6:
                anaKarakter.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s6));
                break;*/
            default:
                break;
        }
    }



}