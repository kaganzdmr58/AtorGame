package com.cbagames.ator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
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
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity6 extends AppCompatActivity {

    private ImageView imageViewRenkliCember,imageViewGriTop,imageViewPota,imageViewAvatar;
    private TextView textViewShow1,textViewShow2,textViewSkor;
    private ConstraintLayout cl;

    private Animation animation;
    private ObjectAnimator animator;

    //Kontroller
    private boolean baslangicKontrol = false;
    private Timer timerx7 = new Timer();
    private Handler handler = new Handler();

    private Timer timer1 ;
    private Handler handler1 ;

    private CountDownTimer countDownTimerx7;
    private float baslangicTopY;
    private int skor = 0;
    private int sayac=0;
    private int canSayisi = 5;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(countDownTimerx7 != null)
        countDownTimerx7.cancel();
        if (timerx7 != null) {
            timerx7.cancel();
            timerx7 = null;
        }
        if (timer1 != null) {
            timer1.cancel();
            timer1 = null;
        }

        startActivity(new Intent(getApplicationContext(), BolumSecActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);



        cl = findViewById(R.id.cl);
        imageViewRenkliCember = findViewById(R.id.imageViewRenkliCember);
        imageViewGriTop = findViewById(R.id.imageViewGriTop);
        imageViewPota = findViewById(R.id.imageViewPota);
        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        textViewShow1 = findViewById(R.id.textViewShow1);
        textViewShow2 = findViewById(R.id.textViewShow2);
        textViewSkor = findViewById(R.id.textViewSkor);
        textViewShow2.setVisibility(View.INVISIBLE);

        textViewShow1.setText(sayac+" / 10");
        canSayisi = getIntent().getIntExtra("can",5);

        cl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!baslangicKontrol) {

                    baslangicKontrol = true;
                    baslangicTopY = imageViewGriTop.getY();

                    timerx7.schedule(new TimerTask() {
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


                timer1 = new Timer();
                handler1 = new Handler();

                imageViewAvatar.setClickable(false);

                if (baslangicTopY == 0){
                    baslangicTopY = imageViewGriTop.getY();
                }
                timer1.schedule(new TimerTask() {
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
                startActivity(new Intent(getApplicationContext(),MainActivity6.class));
                finish();
            }
        });

        karanlikModKontrol();
    }

    public void topYukariCikarma(){
        imageViewGriTop.setY(imageViewGriTop.getY()-50);

        if (imageViewGriTop.getY() <= imageViewPota.getY()+50){
            // timer durduruluyor
            timer1.cancel();
            timer1 = null;

            titresim(100);
            tadaSesi();

            imageViewGriTop.setY(baslangicTopY);
            Resources res = getResources();
            imageViewGriTop.setColorFilter(res.getColor(R.color.gray));
            textViewSkor.setText("Skor : "+ skor);
            imageViewAvatar.setClickable(true);

            sayac += 1;
            textViewShow1.setText(sayac+" / 10");

            if (sayac >= 10 ){
                timerx7.cancel();
                timerx7 = null;
                textViewShow2.setText("Oyun bitti puanınız : "+skor);
                textViewShow2.setVisibility(View.VISIBLE);
                imageViewAvatar.setClickable(false);
                //https://fatihdemirag.net/android-sayac-yapimi-timer/
                countDownTimerx7=new CountDownTimer(2000,1000) {
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
            }
            if (90<rotatioanCember && rotatioanCember <= 180) {
                imageViewGriTop.setColorFilter(res.getColor(R.color.xmor));
                skor += 2;
            }
            if (180<rotatioanCember && rotatioanCember <= 270) {
                imageViewGriTop.setColorFilter(res.getColor(R.color.xyesil));
                skor += 3;
            }
            if (270<rotatioanCember && rotatioanCember <= 360) {
                imageViewGriTop.setColorFilter(res.getColor(R.color.xsari));
                skor += 4;
            }
        }

    }


    public void gecis(int gidecekPuan,int can){
        // timer durduruluyor
        countDownTimerx7.cancel();

        Intent intent = new Intent(getApplicationContext(), SonucEkraniActivity.class);
        intent.putExtra("skor",gidecekPuan);
        intent.putExtra("can",can);
        startActivity(intent);
        finish();
    }


    private void tadaSesi(){
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

