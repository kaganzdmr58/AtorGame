package com.cbagames.ator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity9 extends AppCompatActivity {

    private ImageView imageViewRenkliCember,imageViewGriTop,imageViewAvatar,toplarıRenkendirme
            ,imageViewPota,imageViewPota2,imageViewPota3,imageViewPota4,imageViewPota5,imageViewPota6,imageViewPota7;
    private TextView textViewShow1,textViewShow2,textViewSkor;
    private ConstraintLayout cl;

    private Animation animation;
    private ObjectAnimator animator;

    //Kontroller
    private boolean baslangicKontrol = false;
    private Timer timerx10 = new Timer();
    private Handler handler = new Handler();

    private Timer timerx101 ;
    private Handler handler1 ;

    private CountDownTimer countDownTimerx10;
    private float baslangicTopY;
    private int skor = 0;
    private int sayac=0;
    private Color color;
    private int canSayisi = 5;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(countDownTimerx10 != null)
            countDownTimerx10.cancel();
        if (timerx10 != null) {
            timerx10.cancel();
            timerx10 = null;
        }
        if (timerx101 != null) {
            timerx101.cancel();
            timerx101 = null;
        }

        startActivity(new Intent(getApplicationContext(), BolumSecActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);


        cl = findViewById(R.id.cl);
        imageViewRenkliCember = findViewById(R.id.imageViewRenkliCember);
        imageViewGriTop = findViewById(R.id.imageViewGriTop);
        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        textViewShow1 = findViewById(R.id.textViewShow1);
        textViewShow2 = findViewById(R.id.textViewShow2);
        textViewSkor = findViewById(R.id.textViewSkor);
        textViewShow2.setVisibility(View.INVISIBLE);

        // topların sırasına göre burada ayarlandı
        imageViewPota = findViewById(R.id.imageViewPota7);
        imageViewPota2 = findViewById(R.id.imageViewPota6);
        imageViewPota3 = findViewById(R.id.imageViewPota3);
        imageViewPota4 = findViewById(R.id.imageViewPota);
        imageViewPota5 = findViewById(R.id.imageViewPota2);
        imageViewPota6 = findViewById(R.id.imageViewPota4);
        imageViewPota7 = findViewById(R.id.imageViewPota5);


        canSayisi = getIntent().getIntExtra("can",5);
        textViewShow1.setText(sayac+" / 7");

        karanlikModKontrol();

        cl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!baslangicKontrol) {

                    baslangicKontrol = true;
                    baslangicTopY = imageViewGriTop.getY();

                    timerx10.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                    imageViewRenkliCember.setRotation(imageViewRenkliCember.getRotation() + 5);

                                }
                            });
                        }
                    }, 0, 20);


                }

                return true;
            }
        });


        imageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!baslangicKontrol)
                    return;


                timerx101 = new Timer();
                handler1 = new Handler();

                imageViewAvatar.setClickable(false);

                if (baslangicTopY == 0){
                    baslangicTopY = imageViewGriTop.getY();
                }
                timerx101.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler1.post(new Runnable() {
                            @Override
                            public void run() {

                                topYukariCikarma();

                            }
                        });
                    }
                }, 0, 50);


            }
        });

        textViewShow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity6.class));
                finish();
            }
        });

    }

    public void topYukariCikarma(){
        imageViewGriTop.setY(imageViewGriTop.getY()-50);

        if (imageViewGriTop.getY() <= imageViewPota.getY()+50){
            // timer durduruluyor
            timerx101.cancel();
            timerx101 = null;

            imageViewGriTop.setY(baslangicTopY);
            Resources res = getResources();
            imageViewGriTop.setColorFilter(res.getColor(R.color.gray));
            textViewSkor.setText("Skor : "+ skor);
            imageViewAvatar.setClickable(true);

            sayac += 1;
            textViewShow1.setText(sayac+" / 7");

            if (sayac >= 7 ){
                timerx10.cancel();
                timerx10 = null;
                textViewShow2.setText("Oyun bitti puanınız : "+skor);
                textViewShow2.setVisibility(View.VISIBLE);
                imageViewAvatar.setClickable(false);
                //https://fatihdemirag.net/android-sayac-yapimi-timer/
                countDownTimerx10=new CountDownTimer(2000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        gecis(skor,canSayisi);
                    }
                }.start();

            }
        }

        if (imageViewGriTop.getY() <= imageViewRenkliCember.getY()+(imageViewRenkliCember.getHeight() - 50)
                && imageViewGriTop.getY() >= imageViewRenkliCember.getY()+(imageViewRenkliCember.getHeight() - 101)){

            Resources res = getResources();

            float rotatioanCember = imageViewRenkliCember.getRotation();
            rotatioanCember = rotatioanCember % 360;

            if (0<=rotatioanCember && rotatioanCember <= 90) {
                imageViewGriTop.setColorFilter(res.getColor(R.color.xkirmizi));
                skor += 1;
                toplarıRenklendir(1);
            }
            if (90<rotatioanCember && rotatioanCember <= 180) {
                imageViewGriTop.setColorFilter(res.getColor(R.color.xmor));
                skor += 2;
                toplarıRenklendir(2);
            }
            if (180<rotatioanCember && rotatioanCember <= 270) {
                imageViewGriTop.setColorFilter(res.getColor(R.color.xyesil));
                skor += 3;
                toplarıRenklendir(3);
            }
            if (270<rotatioanCember && rotatioanCember <= 360) {
                imageViewGriTop.setColorFilter(res.getColor(R.color.xsari));
                skor += 4;
                toplarıRenklendir(4);
            }
        }

    }


    public void gecis(int gidecekPuan,int can){
        // timer durduruluyor
        countDownTimerx10.cancel();

        titresim(400);

        Intent intent = new Intent(getApplicationContext(), SonucEkraniActivity.class);
        intent.putExtra("skor",gidecekPuan);
        intent.putExtra("can",can);
        startActivity(intent);
        finish();
    }


    private void toplarıRenklendir(int color){
        switch (sayac){
            case 0:
                renklendir(imageViewPota,color);
                break;
            case 1:
                renklendir(imageViewPota2,color);
                break;
            case 2:
                renklendir(imageViewPota3,color);
                break;
            case 3:
                renklendir(imageViewPota4,color);
                break;
            case 4:
                renklendir(imageViewPota5,color);
                break;
            case 5:
                renklendir(imageViewPota6,color);
                break;
            case 6:
                renklendir(imageViewPota7,color);
                break;
        }



    }


    private void renklendir(ImageView toplarıRenkendirme ,int color){
        Resources res = getResources();
        switch (color){
            case 1:
                toplarıRenkendirme.setColorFilter(res.getColor(R.color.xkirmizi));
                break;
            case 2:
                toplarıRenkendirme.setColorFilter(res.getColor(R.color.xmor));
                break;
            case 3:
                toplarıRenkendirme.setColorFilter(res.getColor(R.color.xyesil));
                break;
            case 4:
                toplarıRenkendirme.setColorFilter(res.getColor(R.color.xsari));
                break;
        }
        titresim(100);
        tadaSesi();
    }

    private void tadaSesi(){
        // sesler
        Resources res = getResources();
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
