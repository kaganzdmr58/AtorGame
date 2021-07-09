package com.cbagames.ator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cbagames.ator.AlarmReceiver;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Calendar;

public class KarsilamaEkraniActivity extends AppCompatActivity {

    private Button buttonBasla;
    private ImageView imageViewGoster;
    private TextView textViewSonraki;

    private int karsilamaResimGostermeSirasi = 1;

    public static SharedPreferences sp,spRozet;
    public static SharedPreferences.Editor editor,editorRozet;

    public static String personelIDString;
    private AdView mAdView;
    //public static int canSayisi;
    public static AlarmManager alarmManager;
    public static PendingIntent pendingIntent;

    /*public static int getCanSayisi() {
        canSayisi = sp.getInt("canSayisi",5);
        return canSayisi;
    }

    public static void setCanSayisi(int canSayisi) {
        KarsilamaEkraniActivity.canSayisi = canSayisi;
        editor.putInt("canSayisi",canSayisi);
        editor.commit();
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karsilama_ekrani);
        MultiDex.install(this);

        sp = getSharedPreferences("Ayarlar", Context.MODE_PRIVATE);
        editor = sp.edit();

        spRozet = getSharedPreferences("Rozetler", Context.MODE_PRIVATE);
        editorRozet = spRozet.edit();


        personelIDString = sp.getString("personelID","nul");

        buttonBasla = findViewById(R.id.buttonBasla);
        imageViewGoster = findViewById(R.id.imageViewGoster);
        textViewSonraki = findViewById(R.id.textViewSonraki);


        bildirimOlusturmaGunluk();


        //canSayisi = KarsilamaEkraniActivity.sp.getInt("canSayisi",5);

        /*if (AlarmReceiver.canSayisi > canSayisi){
            //AlarmReceiver da can sayısı sürekli olarak güncelleniyor onu alıyoruz ve buradaki shared ile kayıt yapıyoruz
            setCanSayisi(AlarmReceiver.canSayisi);
        }


        if(canSayisi <5){
            canSayisi += 1;
            setCanSayisi(canSayisi);
        }
*/


        Resources res = getResources();
        buttonBasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BolumSecActivity.class);
                intent.putExtra("karsilamaCanEkle",true);
                startActivity(intent);
                finish();
            }
        });


        Boolean karanlikMod = KarsilamaEkraniActivity.sp.getBoolean("karanlikMod",false);
        karanlikModKontrol(karanlikMod);

        karsilamaResimGostermeSirasi = sp.getInt("karsilamaResimGostermeSirasi",1);
        resimGosterme(karsilamaResimGostermeSirasi + 1);

        //startActivity(new Intent(getApplicationContext(),AyarlarActivity.class));

        textViewSonraki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimGosterme(karsilamaResimGostermeSirasi);
            }
        });


        imageViewGoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimGosterme(karsilamaResimGostermeSirasi);
            }
        });



        // Reklam
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2183039164562504~3326495559");
        // TODO: Add adView to your view hierarchy.

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adViewbanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

/*

        // ilk karsilama kontrol ve uygulama
        Boolean ilkKarsilama = sp.getBoolean("ilkKarsilama3",true);
        if (ilkKarsilama){

            editor.putBoolean("ilkKarsilama3",false);
            editor.commit();

            startActivity(new Intent(getApplicationContext(),IlkKarsilamaActivity.class));
            finish();
        }
*/


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

        karsilamaResimGostermeSirasi = sira;
        editor.putInt("karsilamaResimGostermeSirasi",sira);
        editor.commit();

        Resources res = getResources();
        switch (sira){
            case 1:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme1));
                break;
            case 2:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme2));
                break;
            case 3:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme3));
                break;
            case 4:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme4));
                break;
            case 5:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme5));
                break;
            case 6:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme6));
                break;
            case 7:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme7));
                break;
            case 8:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme8));
                break;
            case 9:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme9));
                break;
            case 10:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme10));
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

        karsilamaResimGostermeSirasi = sira;
        editor.putInt("karsilamaResimGostermeSirasi",sira);
        editor.commit();

        Resources res = getResources();
        switch (sira){
            case 1:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme1_e));
                break;
            case 2:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme2_e));
                break;
            case 3:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme3_e));
                break;
            case 4:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme4_e));
                break;
            case 5:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme5_e));
                break;
            case 6:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme6_e));
                break;
            case 7:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme7_e));
                break;
            case 8:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme8_e));
                break;
            case 9:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme9_e));
                break;
            case 10:
                imageViewGoster.setImageDrawable(res.getDrawable(R.drawable.bilgilendirme10_e));
                break;
            default:
                break;
        }
    }




    private void bildirimOlusturmaGunluk(){
        AlarmManager alarmManager2;
        PendingIntent pendingIntent2;
        //RTC açılış yakalama
        alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        final Intent alarmIntent2 = new Intent(
                KarsilamaEkraniActivity.this, AlarmReceiver2RTC.class);

        pendingIntent2 = PendingIntent.getBroadcast(KarsilamaEkraniActivity.this
                ,0,alarmIntent2,0);



        // Alarm çalışması ayarlanması
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,19);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        long neZamanTetiklenecek2 = calendar.getTimeInMillis();
        long tekrarlamaSuresi2 = 1000*60*1;  // 1 dk

        alarmManager2.setInexactRepeating(
                AlarmManager.RTC_WAKEUP
                ,neZamanTetiklenecek2
                ,alarmManager2.INTERVAL_DAY  // günün aynı saatinde çalışıyor.
                ,pendingIntent2);
        //Toast.makeText(context,"Açılış yakalandı.",Toast.LENGTH_SHORT).show();



    }




}