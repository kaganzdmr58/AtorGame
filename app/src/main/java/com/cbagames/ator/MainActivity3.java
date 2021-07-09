package com.cbagames.ator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbagames.ator.BolumSecActivity;
import com.cbagames.ator.KarsilamaEkraniActivity;
import com.cbagames.ator.R;
import com.cbagames.ator.SonucEkraniActivity;

public class MainActivity3 extends AppCompatActivity {

    private TextView textViewHedefSayi,textViewAnlikSkor,textViewTimer,textViewIslemGoster;
    private Button buttonArti,buttonEksi,buttonCarpi,buttonBolu;
    private ImageView anaKarakter,imageViewCached;
    private int hedefSayi,skor,artiSayi,eksiSayi,bolumSayi,carpimSayi,canSayisi=5;
    private CountDownTimer countDownTimerx3,countDownTimerx31;
    private StringBuilder   stringTopla   =   new    StringBuilder ( ) ;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimerx3.cancel();
        startActivity(new Intent(getApplicationContext(), BolumSecActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Resources res = getResources();

        textViewHedefSayi = findViewById(R.id.textViewHedefSayi);
        textViewAnlikSkor = findViewById(R.id.textViewAnlikSkor);
        buttonArti = findViewById(R.id.buttonArti);
        buttonEksi = findViewById(R.id.buttonEksi);
        buttonCarpi = findViewById(R.id.buttonCarpi);
        buttonBolu = findViewById(R.id.buttonBolu);
        textViewTimer = findViewById(R.id.textViewTimer);
        anaKarakter = findViewById(R.id.imageViewAnaKarakter);
        imageViewCached = findViewById(R.id.imageViewCached);
        textViewIslemGoster = findViewById(R.id.textViewIslemGoster3);

        // textview scroll ederek kaydırma ekliyoruz
        textViewIslemGoster.setMovementMethod(new ScrollingMovementMethod());

        avatarSecimiUygula();

        canSayisi = getIntent().getIntExtra("can",5);
        hedefSayi = (int) Math.floor(Math.random() * 100);
        hedefSayi += 1;
        String hedefSayiString = res.getString(R.string.main_hedef_sayi);
        textViewHedefSayi.setText( hedefSayiString + hedefSayi);

        skor = puanUret();
        textViewAnlikSkor.setText(String.valueOf(skor));

        artiSayi = puanUret();
        eksiSayi = puanUret();
        carpimSayi = puanUret();
        bolumSayi = puanUret();
        buttonArti.setText(" + "+artiSayi);
        buttonEksi.setText(" - "+eksiSayi);
        buttonCarpi.setText(" X "+carpimSayi);
        buttonBolu.setText(" / "+bolumSayi);


        //https://fatihdemirag.net/android-sayac-yapimi-timer/
        countDownTimerx3=new CountDownTimer(45000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                gecis(0,canSayisi-1);
            }
        }.start();

        stringTopla.append("İşlemler : ");

        buttonArti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringTopla.append("\n"+skor+" + "+artiSayi + " = " +(skor+artiSayi));
                textViewIslemGoster.setText(stringTopla.toString());
                islemGosterScrollEnd();

                skor += artiSayi;
                textViewAnlikSkor.setText(String.valueOf(skor));

                titresim(100);
                clickSesi();
                if (skor == hedefSayi){
                    gecis(hedefSayi,canSayisi);
                }

            }
        });


        buttonEksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringTopla.append("\n"+skor+" - "+eksiSayi + " = " +(skor-eksiSayi));
                textViewIslemGoster.setText(stringTopla.toString());
                islemGosterScrollEnd();

                skor -= eksiSayi;
                textViewAnlikSkor.setText(String.valueOf(skor));

                titresim(100);
                clickSesi();
                if (skor == hedefSayi){
                    gecis(hedefSayi,canSayisi);
                }

            }
        });


        buttonCarpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringTopla.append("\n"+skor+" X "+carpimSayi + " = " +(skor*carpimSayi));
                textViewIslemGoster.setText(stringTopla.toString());
                islemGosterScrollEnd();

                skor *= carpimSayi;
                textViewAnlikSkor.setText(String.valueOf(skor));

                titresim(100);
                clickSesi();
                if (skor == hedefSayi){
                    gecis(hedefSayi,canSayisi);
                }

            }
        });


        buttonBolu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringTopla.append("\n"+skor+" / "+bolumSayi + " = " +(skor/bolumSayi));
                textViewIslemGoster.setText(stringTopla.toString());
                islemGosterScrollEnd();

                skor /= bolumSayi;
                textViewAnlikSkor.setText(String.valueOf(skor));
                titresim(100);
                clickSesi();

                if (skor == hedefSayi){
                    gecis(hedefSayi,canSayisi);
                }

            }
        });

        imageViewCached.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                artiSayi = puanUret();
                buttonArti.setText(" + "+artiSayi);

                eksiSayi = puanUret();
                buttonEksi.setText(" - "+eksiSayi);

                carpimSayi = puanUret();
                buttonCarpi.setText(" X "+carpimSayi);

                bolumSayi = puanUret();
                buttonBolu.setText(" / "+bolumSayi);
            }
        });


        karanlikModKontrol();

    }


    private int puanUret(){
        int puan = (int) Math.floor(Math.random() * 9) + 1;
        return puan;
    }


    private void islemGosterScrollEnd(){
        //https://stackoverflow.com/questions/3506696/auto-scrolling-textview-in-android-to-bring-text-into-view
        final int scrollAmount = textViewIslemGoster.getLayout().getLineTop(
                textViewIslemGoster.getLineCount()) - textViewIslemGoster.getHeight();
        // if there is no need to scroll, scrollAmount will be <=0
        if (scrollAmount > 0)
            textViewIslemGoster.scrollTo(0, scrollAmount);
        else
            textViewIslemGoster.scrollTo(0, 0);
    }


    public void gecis(int gidecekPuan,int can){
        // timer durduruluyor
        countDownTimerx3.cancel();
        titresim(400);

        //https://fatihdemirag.net/android-sayac-yapimi-timer/
        countDownTimerx31=new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                gecis2(gidecekPuan,can);
            }
        }.start();
    }

    public void gecis2(int gidecekPuan,int can){
        // timer durduruluyor
        countDownTimerx31.cancel();

        Intent intent = new Intent(getApplicationContext(), SonucEkraniActivity.class);
        intent.putExtra("skor",gidecekPuan);
        intent.putExtra("can",can);
        startActivity(intent);
        finish();
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

    private void clickSesi(){
        // sesler
        Boolean sesDurum = KarsilamaEkraniActivity.sp.getBoolean("sesDurum",false);
        final MediaPlayer sesYeme = MediaPlayer.create(this, R.raw.tada_1);
        if (sesDurum) {
            sesYeme.start();
        }
    }

    private void karanlikModKontrol(){
        Boolean karanlikMod = KarsilamaEkraniActivity.sp.getBoolean("karanlikMod",false);
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
}