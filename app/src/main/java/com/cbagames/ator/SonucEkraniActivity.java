package com.cbagames.ator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cbagames.ator.AlarmReceiver;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.HashMap;
import java.util.Map;

public class SonucEkraniActivity extends AppCompatActivity {

    private TextView textViewBildirimYazisi,textViewEnYuksekSkor,textViewAyarlar,textViewListe
            ,textViewRozet,textViewBilgilendirmeBaslik,buttonTekrarBasla;
    public static TextView textViewCanSayisiSonuc;
    private ImageView imageViewAvatarSembol,imageViewAyarlar,imageViewListe,imageViewRozet,
            imageViewKirikKalp;
    public static ImageView imageViewKalp;
    private int canSayisi = 5;
    private int bestSkor = 0 ,enYuksekSkor = 0 ,skor = 0 ,level = 1;
    private AdView mAdView;
    private InterstitialAd interstitialAd;

    public static AlarmManager alarmManager;
    public static PendingIntent pendingIntent;
    private Animation animation;

    private Boolean reklamDurum = true;
    Class bolum ;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), BolumSecActivity.bolum);
        intent.putExtra("can",canSayisi);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc_ekrani);
        Resources res = getResources();
        MultiDex.install(this);

        textViewBildirimYazisi = findViewById(R.id.textViewBildirimYazisi);
        textViewEnYuksekSkor = findViewById(R.id.textViewEnYuksekSkor);
        buttonTekrarBasla = findViewById(R.id.buttonTekrarBasla);
        imageViewAvatarSembol = findViewById(R.id.imageViewAvatarSembol);
        textViewAyarlar = findViewById(R.id.textViewAyarlar);
        imageViewAyarlar = findViewById(R.id.imageViewAyarlar);
        imageViewListe = findViewById(R.id.imageViewListe);
        imageViewRozet = findViewById(R.id.imageViewRozet);
        textViewListe = findViewById(R.id.textViewListe);
        textViewRozet = findViewById(R.id.textViewRozet);
        textViewBilgilendirmeBaslik = findViewById(R.id.textViewBilgilendirmeBaslik);
        textViewCanSayisiSonuc = findViewById(R.id.textViewCanSayisiSonuc);
        imageViewKirikKalp = findViewById(R.id.imageViewKirikKalp);
        imageViewKalp = findViewById(R.id.imageViewKalp);

        if (KarsilamaEkraniActivity.personelIDString == "nul"){
            webPlayerkayit();
        }


        if (canSayisi <5 ){
            alarmManagerCanSuresi();
        }
        try {
            bestSkor = KarsilamaEkraniActivity.sp.getInt("bestSkor",0);
            enYuksekSkor = KarsilamaEkraniActivity.sp.getInt("enYuksekSkor",0);
            level = KarsilamaEkraniActivity.sp.getInt("level",1);
        }catch (Exception e){

        }

        canSayisi = getIntent().getIntExtra("can",5);
        BolumSecActivity.setCanSayisi(canSayisi);
        textViewCanSayisiSonuc.setText(String.valueOf(canSayisi));



        if(AlarmReceiver.canSayisi > canSayisi){
            canSayisi = AlarmReceiver.canSayisi;
            BolumSecActivity.setCanSayisi(canSayisi);
            textViewCanSayisiSonuc.setText(String.valueOf(canSayisi));
        }

        if (AlarmReceiver.canSayisi < canSayisi) {
            AlarmReceiver.setCanSayisi(this,canSayisi);
        }



        String tebrikler2 = res.getString(R.string.so_tebrikler2);
        String puan_kazandın = res.getString(R.string.so_puan_kazandın);
        String uzgunuz_hic_puan_kazanamadın = res.getString(R.string.so_uzgunuz_hic_puan_kazanamadın);
        String cok_iyi_gidiyorsun = res.getString(R.string.so_cok_iyi_gidiyorsun);

        skor = getIntent().getIntExtra("skor",0);
        if (skor == 0 ){
            skor0();
        }else {
            textViewBilgilendirmeBaslik.setText(res.getText(R.string.so_OYUN_DEVAM_EDİYOR));
            textViewBilgilendirmeBaslik.setTextColor(res.getColor(R.color.yesil));
            //imageViewAvatarSembol.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu));
            avatarSecimiUygulaMutlu();
            bestSkor = bestSkor + skor;
            textViewEnYuksekSkor.setText(String.valueOf(bestSkor));
            textViewBildirimYazisi.setText(cok_iyi_gidiyorsun+skor+puan_kazandın);
            buttonTekrarBasla.setText(res.getText(R.string.so_oyuna_devam_et));
        }



        Boolean ustaRozeti = KarsilamaEkraniActivity.spRozet.getBoolean("ustaRozeti",false);

        if (bestSkor >= 1000){
            KarsilamaEkraniActivity.editorRozet.putBoolean("ustaRozeti",true);
            KarsilamaEkraniActivity.editorRozet.commit();

            if (!ustaRozeti) {
                alerOlusturucuTebriklerRozetKazandınız(5);
            }
        }



        Boolean islemRozeti = KarsilamaEkraniActivity.spRozet.getBoolean("islemRozeti",false);

        if (bestSkor >= 2500) {
            KarsilamaEkraniActivity.editorRozet.putBoolean("islemRozeti", true);
            KarsilamaEkraniActivity.editorRozet.commit();

            if (!islemRozeti) {
                alerOlusturucuTebriklerRozetKazandınız(4);
            }
        }




        enYuksekSkor = enYuksekSkor + skor;
        // en yüksek skor her defasında personel numarasına kayıt edilecek.
        webPersonelSkorKayitIslemleri(String.valueOf(enYuksekSkor));

        KarsilamaEkraniActivity.editor.putInt("enYuksekSkor",enYuksekSkor);
        KarsilamaEkraniActivity.editor.putInt("bestSkor",bestSkor);
        KarsilamaEkraniActivity.editor.commit();


        int yeniLevel = (enYuksekSkor/1500)+1;
        if ( yeniLevel > level){
            // levet atladınız
            alerOlusturucuTebriklerNewLevel(yeniLevel);

            KarsilamaEkraniActivity.editor.putInt("level",yeniLevel);
            KarsilamaEkraniActivity.editor.commit();
        }


        /*if (canSayisi <= 0 ){
            imageViewKalp.setImageDrawable(res.getDrawable(R.drawable.kirik_kalp_2));
        }else {
            imageViewKalp.setImageDrawable(res.getDrawable(R.drawable.ic_baseline_favorite_24));
        }*/



        buttonTekrarBasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (canSayisi > 0){
                    reklamDurum = false;
                    Intent intent = new Intent(getApplicationContext(), BolumSecActivity.bolum);
                    intent.putExtra("can",canSayisi);
                    startActivity(intent);
                    finish();
               }else {
                    animation = AnimationUtils.loadAnimation(getApplicationContext()
                            ,R.anim.kirik_kalp_sonuc);
                    imageViewKirikKalp.setVisibility(View.VISIBLE);
                    imageViewKirikKalp.setAnimation(animation);

                    Toast.makeText(SonucEkraniActivity.this,
                            res.getText(R.string.hicCanKalmadı), Toast.LENGTH_SHORT).show();

                }

            }
        });

        imageViewAyarlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reklamDurum = false;

                // Ayarlar sayfası geçişi ve reklam açma
                Intent intent = new Intent(getApplicationContext(),AyarlarActivity.class);
                intent.putExtra("reklamDurum",false);
                startActivity(intent);
                finish();
            }
        });

        textViewAyarlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reklamDurum = false;

                // Ayarlar sayfası geçişi ve reklam açma
                Intent intent = new Intent(getApplicationContext(),AyarlarActivity.class);
                intent.putExtra("reklamDurum",false);
                startActivity(intent);
                finish();
            }
        });

        imageViewListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reklamDurum = false;

                startActivity(new Intent(getApplicationContext(),ListelerActivity.class));
                finish();
            }
        });

        textViewListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reklamDurum = false;

                startActivity(new Intent(getApplicationContext(),ListelerActivity.class));
                finish();
            }
        });

        imageViewRozet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reklamDurum = false;

                startActivity(new Intent(getApplicationContext(),RozetlerActivity.class));
                finish();
            }
        });

        textViewRozet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reklamDurum = false;

                startActivity(new Intent(getApplicationContext(),RozetlerActivity.class));
                finish();
            }
        });

        Boolean karanlikMod = KarsilamaEkraniActivity.sp.getBoolean("karanlikMod",false);
        karanlikModKontrol(karanlikMod);


        sesler();


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









    }

    private void interReklam(){
        if (canSayisi != 0 || reklamDurum == false){
            return;
        }
        //  Reklam yanma gösterme

        interstitialAd = new InterstitialAd(SonucEkraniActivity.this);
        interstitialAd.setAdUnitId("ca-app-pub-2183039164562504/8948140287");
        // reklam id'si
        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.e("Sonuc","Reklam Çalıştı");
                if (reklamDurum) {
                    interstitialAd.show();
                    reklamDurum = false;
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.e("Sonuc","Reklam Hata oluşturdu");
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Log.e("Sonuc","Reklam ekranı kapladı");
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                Log.e("Sonuc","Reklama tıklandı");
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Log.e("Sonuc","Reklamdan geri gelindi");
                interstitialAd.loadAd(new AdRequest.Builder().build());

                // burada canı bittiğinde fulleme için alert
                //alerOlusturucuCanFullemeIstermisiniz();
            }
        });
    }

    private void skor0(){
        Resources res = getResources();
        String tebrikler2 = res.getString(R.string.so_tebrikler2);
        String puan_kazandın = res.getString(R.string.so_puan_kazandın);
        String uzgunuz_hic_puan_kazanamadın = res.getString(R.string.so_uzgunuz_hic_puan_kazanamadın);

        textViewBilgilendirmeBaslik.setText(res.getText(R.string.so_oyun_bitti));
        if (bestSkor > 0){
            textViewBildirimYazisi.setText(tebrikler2+bestSkor+puan_kazandın);
        }else {
            textViewBildirimYazisi.setText(uzgunuz_hic_puan_kazanamadın);
        }
        //imageViewAvatarSembol.setImageDrawable(res.getDrawable(R.drawable.avatar_uzgun));
        avatarSecimiUygulaMutsuz();

        // internet işlemleri
        //webSkorKayitIslemleri(String.valueOf(bestSkor));
        textViewEnYuksekSkor.setText(String.valueOf(bestSkor));
        buttonTekrarBasla.setText(res.getText(R.string.so_tekrar_basla));

        /*canSayisi = KarsilamaEkraniActivity.getCanSayisi();

        canSayisi -= 1;
        if (canSayisi<0){
            canSayisi = 0;
        }
        KarsilamaEkraniActivity.setCanSayisi(canSayisi);
        textViewCanSayisiSonuc.setText(String.valueOf(canSayisi));*/

            /*if (alarmManager != null){
                alarmManager.cancel(pendingIntent);
                Toast.makeText(SonucEkraniActivity.this,
                        "eski can süresi iptal edildi", Toast.LENGTH_SHORT).show();
            }*/
        alarmManagerCanSuresi();

        bestSkor = 0;

        //interReklam();

        /*if (interstitialAd.isLoaded()){
            interstitialAd.show();
        }else {
            Log.e("Sonuc","Reklam gösterime hazır değil");
        }*/

    }

    private void sesler(){
        // sesler
        Boolean sesDurum = KarsilamaEkraniActivity.sp.getBoolean("sesDurum",false);
        final MediaPlayer sesTik = MediaPlayer.create(SonucEkraniActivity.this, R.raw.click_1);
        final MediaPlayer sesGameOver = MediaPlayer.create(SonucEkraniActivity.this, R.raw.game_over_2);
        final MediaPlayer sesCorrect = MediaPlayer.create(SonucEkraniActivity.this, R.raw.correct_2);

        if (sesDurum) {
            if (skor == 0) {
                sesGameOver.start();
            }
            if (skor > 0) {
                sesCorrect.start();
            }
        }
    }

    private void karanlikModKontrol(Boolean karanlikMod){
        if (!karanlikMod){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }



    public void sesNewLevel(){
        // sesler
        Boolean sesDurum = KarsilamaEkraniActivity.sp.getBoolean("sesDurum",false);
        final MediaPlayer sesNewLevel = MediaPlayer.create(SonucEkraniActivity.this, R.raw.level_completed_1);
        if (sesDurum){
            sesNewLevel.start();
        }
        titresim(400);
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

    public void webSkorKayitIslemleri(String bestSkor){
        Resources res = getResources();
        // skor 0 geldiğinde çalışacak
        // önce internet bağlantısı var mı kontrol edilecek
        if (!internetKontrol()){
            Toast.makeText(this,
                    res.getText(R.string.ro_internet_baglantısı_olmadıgı_icin), Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.valueOf(bestSkor) == 0 ) {
            return;
        }

        // en yüksek skor oy_03_skor listesine kayıt edilecek (yeni)
        String url = "https://callbellapp.xyz/project/oy_03_best_skor/oy_03_best_score_insert.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Log.e("response",response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("per_id",KarsilamaEkraniActivity.personelIDString);
                params.put("skor_best",bestSkor);
                return params;
            }
        };
        Volley.newRequestQueue(SonucEkraniActivity.this).add(stringRequest);


    }

    public void webPersonelSkorKayitIslemleri(String guncelEnYuksekSkor){
        // skor 0 geldiğinde çalışacak
        // önce internet bağlantısı var mı kontrol edilecek
        if (!internetKontrol()){
            //Toast.makeText(this, "İnternet bağlantınız olmadığı için,
            // Puanınız kayıt edilemiyor.", Toast.LENGTH_SHORT).show();
            return;
        }
        // personel listesi skor güncellenecek.


        String url = "https://callbellapp.xyz/project/oy_03_personel/oy_03_personal_total_skor_update.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Log.e("response",response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("per_id",KarsilamaEkraniActivity.personelIDString);
                params.put("per_total_skor",guncelEnYuksekSkor);
                return params;
            }
        };
        Volley.newRequestQueue(SonucEkraniActivity.this).add(stringRequest);



    }

    public void webPlayerkayit(){
        Resources res = getResources();
        // skor 0 geldiğinde çalışacak
        // önce internet bağlantısı var mı kontrol edilecek
        if (!internetKontrol()){
            Toast.makeText(this, res.getText(R.string.so_internet_baglantiniz_yok), Toast.LENGTH_SHORT).show();
            return;
        }
        // en yüksek skor oy_03_skor listesine kayıt edilecek (yeni)

        String url = "https://callbellapp.xyz/project/oy_03_personel/oy_03_personel_insert_1.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //burada player id numarası girilecek.
                Log.e("response", response);
                String[] cevap = response.split(":");

                // personel ID kaydedilecek.
                KarsilamaEkraniActivity.editor.putString("personelID",cevap[1]);
                KarsilamaEkraniActivity.editor.commit();

                KarsilamaEkraniActivity.personelIDString = cevap[1];

                Toast.makeText(SonucEkraniActivity.this, res.getText(R.string.so_yeni_oyuncu_kaydı_yapıldı), Toast.LENGTH_SHORT).show();

                webPlayerNameUpdate("player_"+cevap[1],cevap[1]);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("per_name","player");
                return params;
            }
        };
        Volley.newRequestQueue(SonucEkraniActivity.this).add(stringRequest);


    }


    public void webPlayerNameUpdate(String ad,String ID){
        Resources res = getResources();
        // skor 0 geldiğinde çalışacak
        // önce internet bağlantısı var mı kontrol edilecek
        if (!internetKontrol()){
            Toast.makeText(this, res.getText(R.string.so_internet_baglantiniz_yok), Toast.LENGTH_SHORT).show();
            return;
        }
        // en yüksek skor oy_03_skor listesine kayıt edilecek (yeni)

        String url = "https://callbellapp.xyz/project/oy_03_personel/oy_03_personal_name_update.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //burada player id numarası girilecek.
                Log.e("response", response);

                //Toast.makeText(SonucEkraniActivity.this, "İsim değiştirildi.", Toast.LENGTH_SHORT).show();

                KarsilamaEkraniActivity.editor.putString("name",ad);
                KarsilamaEkraniActivity.editor.commit();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("per_name",ad);
                params.put("per_id",ID);
                return params;
            }
        };
        Volley.newRequestQueue(SonucEkraniActivity.this).add(stringRequest);


    }

    protected boolean internetKontrol() { //interneti kontrol eden method
        // TODO Auto-generated method stub
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


    public void avatarSecimiUygulaMutlu(){
        int secim = KarsilamaEkraniActivity.sp.getInt("avatarSecim",1);
        Resources res = getResources();
        switch (secim){
            case 1:
                imageViewAvatarSembol.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu));
                //buttonTekrarBasla.setBackgroundColor(res.getColor(R.color.avatar_mavi));
                break;
            case 2:
                imageViewAvatarSembol.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s2));
                //buttonTekrarBasla.setBackgroundColor(res.getColor(R.color.avatar_sari));
                break;
            case  3:
                imageViewAvatarSembol.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s3));
                //buttonTekrarBasla.setBackgroundColor(res.getColor(R.color.avatar_pembe));
                break;
            case 4:
                imageViewAvatarSembol.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s4));
                //buttonTekrarBasla.setBackgroundColor(res.getColor(R.color.avatar_yesil));
                break;
            case 5:
                imageViewAvatarSembol.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s5));
                //buttonTekrarBasla.setBackgroundColor(res.getColor(R.color.avatar_acik_mavi));
                break;
            case 6:
                imageViewAvatarSembol.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s6));
                //buttonTekrarBasla.setBackgroundColor(res.getColor(R.color.avatar_kirmizi));
                break;
            default:
                break;
        }
    }

    public void avatarSecimiUygulaMutsuz(){
        int secim = KarsilamaEkraniActivity.sp.getInt("avatarSecim",1);
        Resources res = getResources();
        switch (secim){
            case 1:
                imageViewAvatarSembol.setImageDrawable(res.getDrawable(R.drawable.avatar_uzgun));
                //buttonTekrarBasla.setBackgroundColor(res.getColor(R.color.avatar_mavi));
                break;
            case 2:
                imageViewAvatarSembol.setImageDrawable(res.getDrawable(R.drawable.avatar_uzgun_s2));
                //buttonTekrarBasla.setBackgroundColor(res.getColor(R.color.avatar_sari));
                break;
            case  3:
                imageViewAvatarSembol.setImageDrawable(res.getDrawable(R.drawable.avatar_uzgun_s3));
                //buttonTekrarBasla.setBackgroundColor(res.getColor(R.color.avatar_pembe));
                break;
            case 4:
                imageViewAvatarSembol.setImageDrawable(res.getDrawable(R.drawable.avatar_uzgun_s4));
                //buttonTekrarBasla.setBackgroundColor(res.getColor(R.color.avatar_yesil));
                break;
            case 5:
                imageViewAvatarSembol.setImageDrawable(res.getDrawable(R.drawable.avatar_uzgun_s5));
                //buttonTekrarBasla.setBackgroundColor(res.getColor(R.color.avatar_acik_mavi));
                break;
            case 6:
                imageViewAvatarSembol.setImageDrawable(res.getDrawable(R.drawable.avatar_uzgun_s6));
                //buttonTekrarBasla.setBackgroundColor(res.getColor(R.color.avatar_kirmizi));
                break;
            default:
                break;
        }
    }

    public void alerOlusturucuTebriklerRozetKazandınız(int kupa){
        Resources res = getResources();

        sesNewLevel();

        Boolean karanlikMod = KarsilamaEkraniActivity.sp.getBoolean("karanlikMod",false);

        LayoutInflater inflater = LayoutInflater.from(SonucEkraniActivity.this);
        View alert_tasarim = inflater.inflate(R.layout.alertview_tasarim, null);

        ImageView alertImageView = (ImageView) alert_tasarim.findViewById(R.id.imageViewAlertRozet);
        TextView alertTextView = (TextView) alert_tasarim.findViewById(R.id.textViewAlertAciklama);

        Resources resources = SonucEkraniActivity.this.getResources();

        AlertDialog.Builder alertDialogOlusturucu = new AlertDialog.Builder(SonucEkraniActivity.this);

        alertDialogOlusturucu.setMessage("   ");
        alertDialogOlusturucu.setView(alert_tasarim);

        if (karanlikMod){
            switch (kupa){
                case 1:     //  db
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.db_sari_2));
                    alertTextView.setText(res.getText(R.string.so_db));
                    break;
                case 2:     //  şampiyon
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.sampiyon_sari_2));
                    alertTextView.setText(res.getText(R.string.so_sampiyon));
                    break;
                case 3:     //  karakter sahibi
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.karakter_sari_2));
                    alertTextView.setText(res.getText(R.string.so_karakter));
                    break;
                case 4:     //  Deha
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.islem_sari_2));
                    alertTextView.setText(res.getText(R.string.so_deha));
                    break;
                case 5:     //  usta
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.usta_sari_2));
                    alertTextView.setText(res.getText(R.string.so_usta));
                    break;
                default:
                    break;
            }


        }else {

            switch (kupa){
                case 1:     //  db
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.db_sari));
                    alertTextView.setText(res.getText(R.string.so_db));
                    break;
                case 2:     //  şampiyon
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.sampiyon_sari));
                    alertTextView.setText(res.getText(R.string.so_sampiyon));
                    break;
                case 3:     //  karakter sahibi
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.karakter_sari));
                    alertTextView.setText(res.getText(R.string.so_karakter));
                    break;
                case 4:     //  Deha
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.islem_sari));
                    alertTextView.setText(res.getText(R.string.so_deha));
                    break;
                case 5:     //  usta
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.usta_sari));
                    alertTextView.setText(res.getText(R.string.so_usta));
                    break;
                default:
                    break;
            }



        }

        alertDialogOlusturucu.setPositiveButton(res.getText(R.string.Tamam), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialogOlusturucu.create().show();
    }




    public void alerOlusturucuTebriklerNewLevel(int level){

        sesNewLevel();

        Boolean karanlikMod = KarsilamaEkraniActivity.sp.getBoolean("karanlikMod",false);

        LayoutInflater inflater = LayoutInflater.from(SonucEkraniActivity.this);
        View alert_tasarim = inflater.inflate(R.layout.alertview_tasarim_new_level, null);

        ImageView alertImageView = (ImageView) alert_tasarim.findViewById(R.id.imageViewNewLevel);
        TextView alertTextView = (TextView) alert_tasarim.findViewById(R.id.textViewNewLevelSayisi);

        Resources resources = SonucEkraniActivity.this.getResources();

        AlertDialog.Builder alertDialogOlusturucu = new AlertDialog.Builder(
                SonucEkraniActivity.this);

        alertDialogOlusturucu.setMessage("  ");
        alertDialogOlusturucu.setView(alert_tasarim);

        if (!karanlikMod){
            alertImageView.setImageDrawable(resources.getDrawable(R.drawable.new_level_rozet));
        }else {
            alertImageView.setImageDrawable(resources.getDrawable(R.drawable.new_level_rozet_night));
        }

        alertTextView.setText(level+".");

        alertDialogOlusturucu.setPositiveButton(R.string.Tamam, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialogOlusturucu.create().show();
    }




    public void alarmManagerCanSuresi(){
        if (alarmManager != null){
            alarmManager.cancel(pendingIntent);
            //Toast.makeText(SonucEkraniActivity.this,"eski can süresi iptal edildi", Toast.LENGTH_SHORT).show();
            /*KarsilamaEkraniActivity.editor.putInt("canSuresiCount",0);
            KarsilamaEkraniActivity.editor.commit();*/

        }

        AlarmReceiver.setCanSayisi(this,canSayisi);


        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        final Intent alarmIntent = new Intent(
                SonucEkraniActivity.this,AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(SonucEkraniActivity.this
                ,0,alarmIntent,0);

        long neZamanTetiklenecek = SystemClock.elapsedRealtime();
        long tekrarlamaSuresi = 1000 * 60 * 1; //1 dk


        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP
                ,neZamanTetiklenecek,tekrarlamaSuresi, pendingIntent);

        //Toast.makeText(this, "başlatıldı", Toast.LENGTH_SHORT).show();



    }



    public void alerOlusturucuCanFullemeIstermisiniz(){

        LayoutInflater inflater = LayoutInflater.from(SonucEkraniActivity.this);

        AlertDialog.Builder alertDialogOlusturucu = new AlertDialog.Builder(SonucEkraniActivity.this);
        Resources resources = SonucEkraniActivity.this.getResources();

        alertDialogOlusturucu.setIcon(resources.getDrawable(R.drawable.can_ekleme));
        alertDialogOlusturucu.setTitle(R.string.articankazan);
        alertDialogOlusturucu.setMessage(R.string.kisabirvideoyuizleyin);

        alertDialogOlusturucu.setPositiveButton(R.string.Tamam, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Ayarlar sayfası geçişi ve reklam açma
                Intent intent = new Intent(getApplicationContext(),AyarlarActivity.class);
                intent.putExtra("reklamDurum",true);
                startActivity(intent);
            }
        });
        alertDialogOlusturucu.setNegativeButton(R.string.ki_iptal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialogOlusturucu.create().show();
    }



}