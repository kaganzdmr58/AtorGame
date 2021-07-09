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

public class MainActivity12 extends AppCompatActivity {
    private ImageView eksi,anaKarakter,arti,sifir,carpi,bol,bonus;
    private TextView avatarSkor,textViewSkor,textViewArti,textViewEksi,textViewSifir,
            textViewCarp,textViewBol,textViewMukemmel;
    private ConstraintLayout cl;

    private TextView textViewCanSayisi;

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

    private Timer timerx1 = new Timer();
    private Handler handler = new Handler();

    // Sayilar
    private int sayix = 0;
    private int hedefSayi=0;
    private int artiSayi = 0;
    private int eksiSayi = 0;
    private int bonusSayi = 1;
    private int bonusZamanlayici = 1;
    private int carpiSayi;
    private int boluSayi;

    private int levelHiz1 = 0;
    private int levelHiz2 = 0;

    private Resources res ;
    private String hedefSayiString;
    private int canSayisi;

    private TextView textViewTimer;
    private CountDownTimer countDownTimer,countDownTimer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);
        MultiDex.install(this);

        cl = findViewById(R.id.cl);
        eksi = findViewById(R.id.imageViewEksi);
        anaKarakter = findViewById(R.id.imageViewAnaKarakter);
        arti = findViewById(R.id.imageViewArti);
        sifir = findViewById(R.id.imageViewSifir);
        avatarSkor = findViewById(R.id.avatarSkor);
        textViewSkor = findViewById(R.id.textViewHedefSayi);
        textViewSifir = findViewById(R.id.textViewSifir);
        textViewArti = findViewById(R.id.textViewArti);
        textViewEksi = findViewById(R.id.textViewEksi);
        bonus = findViewById(R.id.imageViewBonus);
        textViewMukemmel = findViewById(R.id.textViewMukemmel);
        textViewCanSayisi = findViewById(R.id.textViewCanSayisi);
        textViewTimer = findViewById(R.id.textViewTimer);
        carpi = findViewById(R.id.imageViewCarpi);
        bol = findViewById(R.id.imageViewBolu);
        textViewBol = findViewById(R.id.textViewBolu);
        textViewCarp = findViewById(R.id.textViewCarpi);

        res = getResources();

        bonusZamanlayici =  (int) Math.floor(Math.random() * 10);

        eksiX = xKonumlandır();
        artiX = xKonumlandır();
        sifirX = xKonumlandır();
        bonusX = xKonumlandır();
        carpiX = xKonumlandır();
        bolX = xKonumlandır();


        sifir.setY(-250);
        sifir.setX(sifirX);
        textViewSifir.setY(-250);
        textViewSifir.setX(sifirX);


        arti.setY(-250);
        arti.setX(artiX);
        textViewArti.setY(-250);
        textViewArti.setX(artiX);

        eksi.setY(-250);
        eksi.setX(eksiX);
        textViewEksi.setY(-250);
        textViewEksi.setX(eksiX);


        carpi.setY(-250);
        carpi.setX(carpiX);
        textViewCarp.setY(-250);
        textViewCarp.setX(carpiX);

        bol.setY(-250);
        bol.setX(bolX);
        textViewBol.setY(-250);
        textViewBol.setX(bolX);


        bonus.setY(-250);
        bonus.setX(eksiX);


        hedefSayi = (int) Math.floor(Math.random() * 20);
        hedefSayi += 1;
        hedefSayiString = res.getString(R.string.main_hedef_sayi);
        textViewSkor.setText( "0");

        artiSayi = puanUret(9,1);
        eksiSayi = puanUret(9,1);
        boluSayi = puanUret(2,2);
        carpiSayi = puanUret(2,2);
        textViewArti.setText("+ "+artiSayi);
        textViewEksi.setText("- "+eksiSayi);
        textViewBol.setText("/ "+boluSayi);
        textViewCarp.setText("* "+carpiSayi);


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
        bonusSayi = (int) Math.floor(Math.random() * 2)+2;



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
                    toplarGenisligi = arti.getWidth();
                    toplarYuksekligi = sifir.getHeight();

                    //imageViewYonOku.setVisibility(View.INVISIBLE);

                    eksiX = xKonumlandır();
                    artiX = xKonumlandır();
                    sifirX = xKonumlandır();
                    bonusX = xKonumlandır();
                    carpiX = xKonumlandır();
                    bolX = xKonumlandır();

                    //https://fatihdemirag.net/android-sayac-yapimi-timer/
                    countDownTimer=new CountDownTimer(45000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            textViewTimer.setText(String.valueOf(millisUntilFinished/1000));
                        }

                        @Override
                        public void onFinish() {
                            gecis(skor,canSayisi);
                        }
                    }.start();

                    timerx1.schedule(new TimerTask() {
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
                                    avatarSkor.setX(sayifloat);
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

    private void yemeSesi(){
        // sesler
        Boolean sesDurum = KarsilamaEkraniActivity.sp.getBoolean("sesDurum",false);
        final MediaPlayer sesYeme = MediaPlayer.create(MainActivity12.this, R.raw.eat_2_potato);
        if (sesDurum) {
            sesYeme.start();
        }
    }

    private void karanlikModKontrol(Boolean karanlikMod){
        if (!karanlikMod){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
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
        textViewSifir.setX(sifirX+(sifir.getWidth()/4));
        textViewSifir.setY(sifirY+(sifir.getHeight()/4));

        //sifir.setRotation(sifir.getRotation() + 5);
        sifir.setRotation(0);


        // kirmizi eksi
        eksiY += eksiHız;
        if (eksiY > ekranYuksekligi ) {
            eksiSayi = puanUret(9,1);
            textViewEksi.setText("- "+eksiSayi);
            eksiY = -40;
            eksiX = xKonumlandır();
        }
        eksi.setX(eksiX);
        eksi.setY(eksiY);
        textViewEksi.setX(eksiX+(eksi.getWidth()/3));
        textViewEksi.setY(eksiY+(eksi.getHeight()/4));

        //eksi.setRotation(eksi.getRotation() + 4);
        eksi.setRotation(0);

        // sari arti
        artiY += artiHız;
        if (artiY > ekranYuksekligi){
            artiSayi = puanUret(9,1);
            textViewArti.setText("+ "+artiSayi);
            artiY = -40;
            artiX = xKonumlandır();

        }

        arti.setX(artiX);
        arti.setY(artiY);
        textViewArti.setX(artiX+(arti.getWidth()/4));
        textViewArti.setY(artiY+(arti.getHeight()/4));

        //arti.setRotation(arti.getRotation() + 3);
        arti.setRotation(0);

      /*  // sari carpi
        carpiY += carpiHiz;
        if (carpiY > ekranYuksekligi){
            carpiSayi = puanUret(3,2);
            textViewCarp.setText("* "+carpiSayi);
            carpiY = -40;
            carpiX = xKonumlandır();

        }

        carpi.setX(carpiX);
        carpi.setY(carpiY);
        textViewCarp.setX(carpiX+(carpi.getWidth()/4));
        textViewCarp.setY(carpiY+(carpi.getHeight()/4));

        //arti.setRotation(arti.getRotation() + 3);
        //carpi.setRotation(0);
*/

        // sari bölü
        bolY += bolHiz;
        if (bolY > ekranYuksekligi){
            boluSayi = puanUret(3,2);
            textViewBol.setText("/ "+boluSayi);
            bolY = -40;
            bolX = xKonumlandır();

        }

        bol.setX(bolX);
        bol.setY(bolY);
        textViewBol.setX(bolX+(bol.getWidth()/4));
        textViewBol.setY(bolY+(bol.getHeight()/4));

        //arti.setRotation(arti.getRotation() + 3);


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

    }


    public void carpismaKontrol(){

        int saridaireMerkezX = artiX + arti.getWidth()/2;
        int saridaireMerkezY = artiY + arti.getHeight()/2;

        if (    saridaireMerkezX >= sayix  && saridaireMerkezX <= sayix + anakarakterGenisligi &&
                saridaireMerkezY >= anakarakterY &&  saridaireMerkezY <= anakarakterY+anakarakterYuksekligi){
            // arti sayi
            skor += artiSayi;
            artiY = -40;
            artiX = xKonumlandır();
            artiSayi = puanUret(9,1);
            textViewArti.setText("+ "+artiSayi);
            carpismaIslemi();
        }

        int kirmiziucgenMerkezX = eksiX + eksi.getWidth()/2;
        int kirmiziucgenMerkezY = eksiY + eksi.getHeight()/2;

        if (    kirmiziucgenMerkezX >= sayix  && kirmiziucgenMerkezX <= sayix + anakarakterGenisligi &&
                kirmiziucgenMerkezY >= anakarakterY &&  kirmiziucgenMerkezY <= anakarakterY+anakarakterYuksekligi){
            // eksi sayi
            skor -= eksiSayi;
            eksiY = -40;
            eksiX = xKonumlandır();
            eksiSayi = puanUret(9,1);
            textViewEksi.setText("- "+eksiSayi);
            carpismaIslemi();
        }


        // carpi
        int carpiMerkezX = carpiX + carpi.getWidth()/2;
        int carpinMerkezY = carpiY + carpi.getHeight()/2;

        if (    carpiMerkezX >= sayix  && carpiMerkezX <= sayix + anakarakterGenisligi &&
                carpinMerkezY >= anakarakterY &&  carpinMerkezY <= anakarakterY+anakarakterYuksekligi){
            // carpi sayi
            skor *= carpiSayi;
            carpiY = -40;
            carpiX = xKonumlandır();
            //textViewSkor.setText("Kırmızı Çarpışma gerçekleşti.");
            carpiSayi = puanUret(3,2);
            textViewCarp.setText("* "+carpiSayi);
            carpismaIslemi();
        }



        // bol
        int bolMerkezX = bolX + bol.getWidth()/2;
        int bolMerkezY = bolY + bol.getHeight()/2;

        if (    bolMerkezX >= sayix  && bolMerkezX <= sayix + anakarakterGenisligi &&
                bolMerkezY >= anakarakterY &&  bolMerkezY <= anakarakterY+anakarakterYuksekligi){
            // carpi sayi
            skor /= boluSayi;
            bolY = -40;
            bolX = xKonumlandır();
            //textViewSkor.setText("Kırmızı Çarpışma gerçekleşti.");
            boluSayi = puanUret(3,2);
            textViewBol.setText("/ "+boluSayi);
            carpismaIslemi();

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

            bonusSayi = (int) Math.floor(Math.random() * 2)+2;
            skor *= bonusSayi;
            carpismaIslemi();



        }


    }

    public void carpismaIslemi(){

        textViewSkor.setText(String.valueOf(skor));
        if (skor>=200 ){
            textViewMukemmel.setText("+ 1 can kazandınız.");
            textViewMukemmel.setVisibility(View.VISIBLE);

            timerx1.cancel();
            timerx1 = null;

            //https://fatihdemirag.net/android-sayac-yapimi-timer/
            countDownTimer2=new CountDownTimer(1500,1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }
                @Override
                public void onFinish() {
                    gecis(skor,canSayisi+1);
                }
            }.start();

        }

        titresim(100);
        yemeSesi();

    }
    public void gecis(int skor,int can){
        // timer durduruluyor
        countDownTimer.cancel();
        if (countDownTimer2 !=null)
            countDownTimer2.cancel();

       if (timerx1!=null){
           timerx1.cancel();
           timerx1 = null;
       }

        titresim(400);

        if (can > 5){
            can = 5;
        }

        Intent intent = new Intent(getApplicationContext(), SonucEkraniActivity.class);
        intent.putExtra("skor",skor);
        intent.putExtra("can",can);
        startActivity(intent);
        finish();
    }

    public int puanUret(int carpan,int artan){
        bonusSayac += 1;

        if ((bonusSayac % 10) == bonusZamanlayici){
            bonuskontrol = true;
        }
        /*if ((bonusSayac % 10) == (bonusZamanlayici + 3) ){
            textViewMukemmel.setVisibility(View.INVISIBLE);
            bonusYazıKontrol = false;
        }*/

        int sayix = (int) Math.floor(Math.random() * carpan) +artan;
        return sayix;
    }

    public int xKonumlandır(){
        int x = (int) Math.floor(Math.random() * (ekranGenisliği - toplarGenisligi));
        if(x <= 50){
            x += 50;
        }
        return x;
    }


    public void avatarSecimiUygula(){
        int secim = KarsilamaEkraniActivity.sp.getInt("avatarSecim",1);
        Resources res = getResources();
        switch (secim){
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
            case 5:
                anaKarakter.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s5));
                break;
            case 6:
                anaKarakter.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s6));
                break;
            default:
                break;
        }
    }



}