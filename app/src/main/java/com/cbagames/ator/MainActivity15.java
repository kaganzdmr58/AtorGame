package com.cbagames.ator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity15 extends AppCompatActivity {
    private ImageView imageViewAvatarAnaKarakter
            ,imageViewAsık,imageViewBsık,imageViewCsık,imageViewDsık;
    private TextView textViewSoruCount,textViewTime,textViewSoru
            ,textViewAicerik,textViewBicerik,textViewCicerik,textViewDicerik
            ,textViewAsık,textViewBsık,textViewCsık,textViewDsık;
    private CardView cardViewA,cardViewB,cardViewC,cardViewD;
    private Button buttonSonraki;
    private Resources res ;
    private Boolean durumA ,durumB ,durumC ,durumD ;
    private int cevap,cevapSecim,skor = 0,canSayisi = 5;
    private Soru soru;
    private CardViewSiklar cardViewSiklarA,cardViewSiklarB,cardViewSiklarC,cardViewSiklarD;
    private CountDownTimer countDownTimer,countDownTimerX13;
    public static ArrayList<Soru> soruList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main15);

        imageViewAvatarAnaKarakter = findViewById(R.id.imageViewAvatarAnaKarakter);
        imageViewAsık = findViewById(R.id.imageViewAsık);
        imageViewBsık = findViewById(R.id.imageViewBsık);
        imageViewCsık = findViewById(R.id.imageViewCsık);
        imageViewDsık = findViewById(R.id.imageViewDsık);
        textViewSoruCount = findViewById(R.id.textViewSoruCount);
        textViewTime = findViewById(R.id.textViewTime);
        textViewSoru = findViewById(R.id.textViewSoru);
        textViewAicerik = findViewById(R.id.textViewAicerik);
        textViewBicerik = findViewById(R.id.textViewBicerik);
        textViewCicerik = findViewById(R.id.textViewCicerik);
        textViewDicerik = findViewById(R.id.textViewDicerik);
        textViewAsık = findViewById(R.id.textViewAsık);
        textViewBsık = findViewById(R.id.textViewBsık);
        textViewCsık = findViewById(R.id.textViewCsık);
        textViewDsık = findViewById(R.id.textViewDsık);
        cardViewA = findViewById(R.id.cardViewA);
        cardViewB = findViewById(R.id.cardViewB);
        cardViewC = findViewById(R.id.cardViewC);
        cardViewD = findViewById(R.id.cardViewD);
        buttonSonraki = findViewById(R.id.buttonSonraki);

        canSayisi = getIntent().getIntExtra("can",5);

        res = getResources();

        cardViewSiklarA = new CardViewSiklar(cardViewA,textViewAicerik,textViewAsık,imageViewAsık);
        cardViewSiklarB = new CardViewSiklar(cardViewB,textViewBicerik,textViewBsık,imageViewBsık);
        cardViewSiklarC = new CardViewSiklar(cardViewC,textViewCicerik,textViewCsık,imageViewCsık);
        cardViewSiklarD = new CardViewSiklar(cardViewD,textViewDicerik,textViewDsık,imageViewDsık);

        durumA = choiceButton(false,cardViewSiklarA);
        durumB = choiceButton(false,cardViewSiklarB);
        durumC = choiceButton(false,cardViewSiklarC);
        durumD = choiceButton(false,cardViewSiklarD);

        //https://fatihdemirag.net/android-sayac-yapimi-timer/
        countDownTimerX13=new CountDownTimer(70000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTime.setText(zamanCevirme(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                gecis(skor,canSayisi);
            }
        }.start();





        soruDownload();
        soruYukleme(1);


        cardViewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonCrate(1);
            }
        });

        cardViewB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonCrate(2);
            }
        });

        cardViewC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonCrate(3);
            }
        });

        cardViewD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonCrate(4);
            }
        });


        buttonSonraki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogruCevapGoster();
            }
        });




    }

    private void radioButtonCrate(int buttonNu){
        switch (buttonNu){
            case 1:
                durumA = choiceButton(durumA,cardViewSiklarA);
                durumB = choiceButton(false,cardViewSiklarB);
                durumC = choiceButton(false,cardViewSiklarC);
                durumD = choiceButton(false,cardViewSiklarD);
                if (!durumA){
                    cevapSecim = 1;
                }else {
                    cevapSecim = 0;
                }
                break;
            case 2:
                durumA = choiceButton(false,cardViewSiklarA);
                durumB = choiceButton(durumB,cardViewSiklarB);
                durumC = choiceButton(false,cardViewSiklarC);
                durumD = choiceButton(false,cardViewSiklarD);
                if (!durumB){
                    cevapSecim = 2;
                }else {
                    cevapSecim = 0;
                }
                break;
            case 3:
                durumA = choiceButton(false,cardViewSiklarA);
                durumB = choiceButton(false,cardViewSiklarB);
                durumC = choiceButton(durumC,cardViewSiklarC);
                durumD = choiceButton(false,cardViewSiklarD);
                if (!durumC){
                    cevapSecim = 3;
                }else {
                    cevapSecim = 0;
                }
                break;
            case 4:
                durumA = choiceButton(false,cardViewSiklarA);
                durumB = choiceButton(false,cardViewSiklarB);
                durumC = choiceButton(false,cardViewSiklarC);
                durumD = choiceButton(durumD,cardViewSiklarD);
                if (!durumD){
                    cevapSecim = 4;
                }else {
                    cevapSecim = 0;
                }
                break;
            default:
                cevapSecim = 0;
                break;
        }
    }

    private Boolean choiceButton(Boolean durum, MainActivity15.CardViewSiklar cardViewSiklar){
        if (durum) {
            cevapGosterSari(cardViewSiklar);
            durum = false;
        }else {
            cevapGosterBeyaz(cardViewSiklar);
            durum = true;
        }
        return durum;
        // seçim alınırken tersini almak gerekecek, durumun true false kalan değerleri farklı olduğuna dikkat.
    }
    private void cevapGosterSari(CardViewSiklar cardViewSiklar){
        cardViewSiklar.cardView.setCardBackgroundColor(res.getColor(R.color.text_yellow));
        cardViewSiklar.textViewIcerik.setTextColor(res.getColor(R.color.white));
        cardViewSiklar.textViewSık.setTextColor(res.getColor(R.color.text_yellow));
        cardViewSiklar.imageViewSık.setImageResource(R.drawable.ic_baseline_brightness_2_24);
    }
    private void cevapGosterBeyaz(CardViewSiklar cardViewSiklar){
        cardViewSiklar.cardView.setCardBackgroundColor(res.getColor(R.color.white));
        cardViewSiklar.textViewIcerik.setTextColor(res.getColor(R.color.text_yellow));
        cardViewSiklar.textViewSık.setTextColor(res.getColor(R.color.white));
        cardViewSiklar.imageViewSık.setImageResource(R.drawable.ic_baseline_brightness_1_24_yellow);
    }
    private void cevapGosterYesil(CardViewSiklar cardViewSiklar){
        cardViewSiklar.cardView.setCardBackgroundColor(res.getColor(R.color.yesil));
        cardViewSiklar.textViewIcerik.setTextColor(res.getColor(R.color.white));
        cardViewSiklar.textViewSık.setTextColor(res.getColor(R.color.yesil));
        cardViewSiklar.imageViewSık.setImageResource(R.drawable.ic_baseline_brightness_2_24);
    }
    private void cevapGosterKirmizi(CardViewSiklar cardViewSiklar){
        cardViewSiklar.cardView.setCardBackgroundColor(res.getColor(R.color.avatar_kirmizi));
        cardViewSiklar.textViewIcerik.setTextColor(res.getColor(R.color.white));
        cardViewSiklar.textViewSık.setTextColor(res.getColor(R.color.avatar_kirmizi));
        cardViewSiklar.imageViewSık.setImageResource(R.drawable.ic_baseline_brightness_2_24);
    }
    //cevap alınacak cevapla seçilen durum karşılaştırılacak eğer seçilen durum farklı ise

    private void dogruCevapGoster(){
        switch (soru.getSoruCevap()){
            case 1:
                cevapGosterYesil(cardViewSiklarA);
                break;
            case 2:
                cevapGosterYesil(cardViewSiklarB);
                break;
            case 3:
                cevapGosterYesil(cardViewSiklarC);
                break;
            case 4:
                cevapGosterYesil(cardViewSiklarD);
                break;
        }
        if (soru.getSoruCevap() != cevapSecim){
            switch (cevapSecim){
                case 1:
                    cevapGosterKirmizi(cardViewSiklarA);
                    break;
                case 2:
                    cevapGosterKirmizi(cardViewSiklarB);
                    break;
                case 3:
                    cevapGosterKirmizi(cardViewSiklarC);
                    break;
                case 4:
                    cevapGosterKirmizi(cardViewSiklarD);
                    break;
            }
        }

        if (soru.getSoruCevap() == cevapSecim){
            // doğru yanıtladınız
            skor += 5;
        }else  {
            if (cevapSecim == 0){
                // boş
            }else {
                // yalnış cevap
                skor -= 1;
            }
        }

        //https://fatihdemirag.net/android-sayac-yapimi-timer/
        countDownTimer=new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                soruYukleme(soru.getSoruId()+1);
            }
        }.start();

    }


    private void soruDownload(){
        soruList = new ArrayList<>();
        Soru soru1 = new Soru(1,3,1,"merhaba bu ilk sorumuz","A şıkkı","B şıkkı","C şıkkı","D şıkkı");
        Soru soru2 = new Soru(2,2,1,"merhaba bu İkinci sorumuz","A şıkkı","B şıkkı","C şıkkı","D şıkkı");
        soruList.add(soru1);
        soruList.add(soru2);
    }



    private void soruYukleme(int id){

        if (soruList.size() >= id) {
            soru = soruList.get(id - 1);
        }else {
            // sonuc actvitiye geçiş
            gecis(skor,canSayisi);
        }

        if (soru != null) {
            // soruları yükleme
            textViewSoru.setText(soru.getSoru());
            textViewAicerik.setText(soru.getSoruSıkA());
            textViewBicerik.setText(soru.getSoruSıkB());
            textViewCicerik.setText(soru.getSoruSıkC());
            textViewDicerik.setText(soru.getSoruSıkD());
            textViewSoruCount.setText(soru.getSoruId() + " / " + soruList.size());
        }
        durumA = choiceButton(false,cardViewSiklarA);
        durumB = choiceButton(false,cardViewSiklarB);
        durumC = choiceButton(false,cardViewSiklarC);
        durumD = choiceButton(false,cardViewSiklarD);
    }









    private class CardViewSiklar{
        private CardView cardView;
        private TextView textViewIcerik;
        private TextView textViewSık;
        private ImageView imageViewSık;

        public CardViewSiklar() {
        }

        public CardViewSiklar(CardView cardView,
                              TextView textViewIcerik, TextView textViewSık, ImageView imageViewSık) {
            this.cardView = cardView;
            this.textViewIcerik = textViewIcerik;
            this.textViewSık = textViewSık;
            this.imageViewSık = imageViewSık;
        }

        public CardView getCardView() {
            return cardView;
        }

        public void setCardView(CardView cardView) {
            this.cardView = cardView;
        }

        public TextView getTextViewIcerik() {
            return textViewIcerik;
        }

        public void setTextViewIcerik(TextView textViewIcerik) {
            this.textViewIcerik = textViewIcerik;
        }

        public TextView getTextViewSık() {
            return textViewSık;
        }

        public void setTextViewSık(TextView textViewSık) {
            this.textViewSık = textViewSık;
        }

        public ImageView getImageViewSık() {
            return imageViewSık;
        }

        public void setImageViewSık(ImageView imageViewSık) {
            this.imageViewSık = imageViewSık;
        }
    }


    public void gecis(int gidecekPuan,int can){
        // timer durduruluyor
        countDownTimerX13.cancel();

        //titresim(400);

        Intent intent = new Intent(getApplicationContext(), SonucEkraniActivity.class);
        intent.putExtra("skor",gidecekPuan);
        intent.putExtra("can",can);
        startActivity(intent);
        finish();
    }


    private String zamanCevirme(long millisUntilFinished){
        millisUntilFinished = millisUntilFinished /1000;
        String dakika = String.valueOf(millisUntilFinished/60);
        String saniye = String.valueOf(millisUntilFinished%60);
        if (dakika.length()==1)
            dakika = "0"+dakika;
        if (saniye.length() ==1)
            saniye = "0"+saniye;
        String saat = dakika+" : "+saniye+" min";
        return saat;
    }



}