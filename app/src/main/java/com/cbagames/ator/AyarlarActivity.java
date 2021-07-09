package com.cbagames.ator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.multidex.MultiDex;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AyarlarActivity extends AppCompatActivity {
    private ImageView imageViewAvatarimIcon,imageViewRozetler, imageViewtitresim, imageViewSes,
            imageViewKaranlikMod, imageViewRenkSecimi,imageViewSiralama,
            imageViewNameEdit,imageViewRozetResimKucuk;

    private TextView textViewRozetler,textViewOyunaDon,textViewRenkSeçimi,textViewSiralama
            ,textViewName,textViewShowSkor, textViewLevel,textViewRozetSayisi;

    private Switch swichTitresim, switchSes, switchKaranlikMod;

    private Boolean karanlikMod;

    private ProgressBar progressBar2;
    private Resources res;
    private AdView mAdView;
    private Boolean sesDurum = false;
    private Toolbar toolbar;

    private int canSayisi;
    private RewardedAd rewardedAd;
    private RewardedAdLoadCallback yuklemeListener;
    private RewardedAdCallback calismaListener;
    private Boolean gelenReklamDurum =false;


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(getApplicationContext(), BolumSecActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        MultiDex.install(this);

        imageViewAvatarimIcon   = findViewById(R.id.imageViewAvatarimIcon);
        imageViewRozetler       = findViewById(R.id.imageViewRozetler);
        imageViewtitresim       = findViewById(R.id.imageViewtitresim);
        imageViewSes            = findViewById(R.id.imageViewSes);
        imageViewKaranlikMod    = findViewById(R.id.imageViewKaranlikMod);
        imageViewRenkSecimi     = findViewById(R.id.imageViewRenkSecimi);
        imageViewSiralama       = findViewById(R.id.imageViewSiralama);
        imageViewNameEdit       = findViewById(R.id.imageViewNameEdit);
        imageViewRozetResimKucuk = findViewById(R.id.imageViewRozetResimKucuk);

        textViewRozetler        = findViewById(R.id.textViewRozetler);
        textViewOyunaDon        = findViewById(R.id.textViewOyunaDon);
        textViewName            = findViewById(R.id.textViewName);
        textViewRenkSeçimi      = findViewById(R.id.textViewRenkSeçimi);
        textViewSiralama        = findViewById(R.id.textViewSiralama);
        textViewShowSkor        = findViewById(R.id.textViewShowSkor);
        textViewLevel           = findViewById(R.id.textViewLevel);
        textViewRozetSayisi     = findViewById(R.id.textViewRozetSayisi);

        swichTitresim           = findViewById(R.id.swichTitresim);
        switchSes               = findViewById(R.id.switchSes);
        switchKaranlikMod       = findViewById(R.id.switchKaranlikMod);

        progressBar2            = findViewById(R.id.progressBar2);

        res = getResources();



        toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle(R.string.a_ayarlar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.text_yellow));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setLogo(R.mipmap.icon_kirmizi_top);
        setSupportActionBar(toolbar);
        // toolbara tıklamada main activity açılışı
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canSayisi > 0){
                    startActivity(new Intent(getApplicationContext(),BolumSecActivity.bolum));
                    finish();
                }else {
                    Toast.makeText(AyarlarActivity.this,
                            res.getText(R.string.hicCanKalmadı), Toast.LENGTH_SHORT).show();
                }
            }
        });



        // Karanlık Mod
        karanlikMod = true;     //KarsilamaEkraniActivity.sp.getBoolean("karanlikMod",false);
        karanlikModKontrol(karanlikMod);
        switchKaranlikMod.setChecked(karanlikMod);

        // Titreşim Modu
        Boolean titresimMod = KarsilamaEkraniActivity.sp.getBoolean("titresimMod",false);
        swichTitresim.setChecked(titresimMod);

        canSayisi = BolumSecActivity.getCanSayisi();


        String name = KarsilamaEkraniActivity.sp.getString("name","Name");
        textViewName.setText(name);

        int skor = KarsilamaEkraniActivity.sp.getInt("enYuksekSkor",0);
        textViewShowSkor.setText("Skor : "+skor);

        int level = KarsilamaEkraniActivity.sp.getInt("level",1);
        textViewLevel.setText("Level : "+level);

        int levelSeviyesi = (skor % 1500)/10;
        progressBar2.setProgress(levelSeviyesi);
        progressBar2.setMax(150);

        
        // Rozet işlemleri
        int rozetSayisi = KarsilamaEkraniActivity.spRozet.getInt("rozetSayisi",0);
        if (rozetSayisi == 0){
            imageViewRozetResimKucuk.setVisibility(View.INVISIBLE);
            textViewRozetSayisi.setVisibility(View.INVISIBLE);
        }
        if (rozetSayisi == 1 ){
            imageViewRozetResimKucuk.setVisibility(View.VISIBLE);
            textViewRozetSayisi.setVisibility(View.INVISIBLE);
        }
        if (rozetSayisi > 1){
            imageViewRozetResimKucuk.setVisibility(View.VISIBLE);
            textViewRozetSayisi.setVisibility(View.VISIBLE);
            textViewRozetSayisi.setText(String.valueOf(rozetSayisi));
        }

        // sesler
        sesDurum = KarsilamaEkraniActivity.sp.getBoolean("sesDurum",false);
        switchSes.setChecked(sesDurum);


        MobileAds.initialize(AyarlarActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });


        reklamYukleme();
        reklamCalisma();

        avatarSecimiUygula();



        gelenReklamDurum = (Boolean) getIntent().getSerializableExtra("reklamDurum");

        if (gelenReklamDurum){
            if (rewardedAd.isLoaded()){
                rewardedAd.show(AyarlarActivity.this,calismaListener);
            }else {
                //Log.e("hata","reklam yüklenmedi");
                Resources res = getResources();
                Toast.makeText(this,
                        res.getText(R.string.reklam_yüklemede_hata), Toast.LENGTH_SHORT).show();

                reklamYukleme();
                reklamCalisma();
                if (rewardedAd.isLoaded()){
                    rewardedAd.show(AyarlarActivity.this,calismaListener);
                }
            }
        }



        switchKaranlikMod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                karanlikModKontrol(isChecked);
                KarsilamaEkraniActivity.editor.putBoolean("karanlikMod",true    /*Boolean.valueOf(isChecked)*/);
                KarsilamaEkraniActivity.editor.commit();

            }
        });

        swichTitresim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                KarsilamaEkraniActivity.editor.putBoolean("titresimMod",Boolean.valueOf(isChecked));
                KarsilamaEkraniActivity.editor.commit();

                titresim(500);
            }
        });



        switchSes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    KarsilamaEkraniActivity.editor.putBoolean("sesDurum",true);
                    KarsilamaEkraniActivity.editor.commit();
                    sesDurum = true;
                    //ses.start();
                }else {
                    KarsilamaEkraniActivity.editor.putBoolean("sesDurum",false);
                    KarsilamaEkraniActivity.editor.commit();
                    sesDurum = false;
                    //ses.stop();
                }
                sesTik();
            }
        });


        imageViewNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),KisisellestirActivity.class));
                finish();
            }
        });

        imageViewRenkSecimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),KisisellestirActivity.class));
                finish();
            }
        });

        textViewRenkSeçimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),KisisellestirActivity.class));
                finish();
            }
        });


        textViewOyunaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canSayisi > 0){
                    startActivity(new Intent(getApplicationContext(), BolumSecActivity.class));
                    finish();
                }else {
                    Toast.makeText(AyarlarActivity.this,
                            res.getText(R.string.hicCanKalmadı), Toast.LENGTH_SHORT).show();
                }
            }
        });


        imageViewSiralama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ListelerActivity.class));
                finish();
            }
        });

        textViewSiralama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ListelerActivity.class));
                finish();
            }
        });

        imageViewRozetler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RozetlerActivity.class));
                finish();
            }
        });


        textViewRozetler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RozetlerActivity.class));
                finish();
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



        if ((skor % 1000) == 0){
            alerOlusturucuElestir();
        }

    }


    private void karanlikModKontrol(Boolean karanlikMod){
        if (!karanlikMod){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            //imageViewRozetResimKucuk.setImageDrawable(res.getDrawable(R.drawable.kupa_night));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //imageViewRozetResimKucuk.setImageDrawable(res.getDrawable(R.drawable.kupa_night));
        }
    }


    public void avatarSecimiUygula(){
        int secim = KarsilamaEkraniActivity.sp.getInt("avatarSecim",1);
        Resources res = getResources();
        switch (secim){
            case 1:
                imageViewAvatarimIcon.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu));
                //textViewOyunaDon.setBackgroundColor(res.getColor(R.color.avatar_mavi));
                textViewName.setTextColor(res.getColor(R.color.avatar_mavi));
                break;
            case 2:
                imageViewAvatarimIcon.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s2));
                //textViewOyunaDon.setBackgroundColor(res.getColor(R.color.avatar_sari));
                textViewName.setTextColor(res.getColor(R.color.avatar_sari));
                break;
            case  3:
                imageViewAvatarimIcon.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s3));
                //textViewOyunaDon.setBackgroundColor(res.getColor(R.color.avatar_pembe));
                textViewName.setTextColor(res.getColor(R.color.avatar_pembe));
                break;
            case 4:
                imageViewAvatarimIcon.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s4));
                //textViewOyunaDon.setBackgroundColor(res.getColor(R.color.avatar_yesil));
                textViewName.setTextColor(res.getColor(R.color.avatar_yesil));
                break;
            case 5:
                imageViewAvatarimIcon.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s5));
                //textViewOyunaDon.setBackgroundColor(res.getColor(R.color.avatar_acik_mavi));
                textViewName.setTextColor(res.getColor(R.color.avatar_acik_mavi));
                break;
            case 6:
                imageViewAvatarimIcon.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s6));
                //textViewOyunaDon.setBackgroundColor(res.getColor(R.color.avatar_kirmizi));
                textViewName.setTextColor(res.getColor(R.color.avatar_kirmizi));
                break;
            default:
                break;
        }
    }


    public void sesTik(){
        // sesler
        Boolean sesDurum = KarsilamaEkraniActivity.sp.getBoolean("sesDurum",false);
        final MediaPlayer sesTik = MediaPlayer.create(AyarlarActivity.this, R.raw.click_1);
        if (sesDurum){
            sesTik.start();
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

    public void uygulamaDisiPaylas(){
        // diğer uygulamalarla paylaş
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TITLE, "Ator Game");
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=com.cbagames.ator");
        sendIntent.setType("text/*");
        startActivity(sendIntent);
        //https://developer.android.com/training/sharing/send
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_ayarlar_menu_3,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_paylas:
                //Toast.makeText(this, "paylaş", Toast.LENGTH_SHORT).show();
                /*startActivity(new Intent(getApplicationContext(), YoneticiMainActivity.class));
                finish();*/
                uygulamaDisiPaylas();
                return true;
            case R.id.action_duzenle:
                /*startActivity(new Intent(getApplicationContext(), IsletmeSecimActivity.class));
                finish();*/
                //Toast.makeText(this, "düzenle", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.action_avatar_renk_secimi:
                /*startActivity(new Intent(getApplicationContext(), CagrilarActivity.class));
                finish();*/
                //Toast.makeText(this, "renk seçimi", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.action_can_ekle:
                    if (rewardedAd.isLoaded()){
                        rewardedAd.show(AyarlarActivity.this,calismaListener);
                    }else {
                        //Log.e("hata","reklam yüklenmedi");
                        Resources res = getResources();
                        Toast.makeText(this,
                                res.getText(R.string.reklam_yüklemede_hata), Toast.LENGTH_SHORT).show();
                    }
                return true;

            case R.id.action_istek:
                startActivity(new Intent(getApplicationContext(), istekGonderActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void reklamYukleme(){
        rewardedAd = new RewardedAd(AyarlarActivity.this,
                "ca-app-pub-2183039164562504/5762816543");

        yuklemeListener = new RewardedAdLoadCallback(){
            @Override
            public void onRewardedAdLoaded() {
                super.onRewardedAdLoaded();
                //Log.e("hata","onRewardedAdLoaded çalıştı");
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError loadAdError) {
                super.onRewardedAdFailedToLoad(loadAdError);
                //Log.e("hata","onRewardedAdFailedToLoad çalıştı");
            }
        };

        rewardedAd.loadAd(new AdRequest.Builder().build(),yuklemeListener);
    }

    public void reklamCalisma(){

        calismaListener = new RewardedAdCallback() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                // ödül alınan yer.
                //Log.e("hata","hata değil onUserEarnedReward çalıştı");

                BolumSecActivity.setCanSayisi(5);

            }
            @Override
            public void onRewardedAdClosed() {
                super.onRewardedAdClosed();
                //Log.e("hata","hata değil onRewardedAdClosed çalıştı");
                reklamYukleme();

                Resources res = getResources();
                Toast.makeText(AyarlarActivity.this,
                        res.getText(R.string.canlarınız_tamamlandı), Toast.LENGTH_LONG).show();

            }

            /*
            @Override
            public void onRewardedAdOpened() {
                super.onRewardedAdOpened();
                //Log.e("hata","hata değil onRewardedAdOpened çalıştı");
            }
            @Override
            public void onRewardedAdFailedToShow(AdError adError) {
                super.onRewardedAdFailedToShow(adError);
                //Log.e("hata","hata oluştuğunda çalışan yer");
            }
            */
        };

    }


/*

private void alertCanTamamlandi(){
    LayoutInflater inflater = LayoutInflater.from(AyarlarActivity.this);
    View alert_tasarim = inflater.inflate(R.layout.alertview_tasarim, null);

    final EditText alert_edittext = (EditText) alert_tasarim.findViewById(R.id.editTextGirisBaslik);

    AlertDialog.Builder alertDialogOlusturucu = new AlertDialog.Builder(AyarlarActivity.this);
    alertDialogOlusturucu.setMessage(R.string.not);
    alertDialogOlusturucu.setView(alert_tasarim);
    alert_edittext.setHint(R.string.notunuzu_giriniz);

    alertDialogOlusturucu.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    });

    alertDialogOlusturucu.create().show();
}
*/




    public void alerOlusturucuElestir(){

        LayoutInflater inflater = LayoutInflater.from(AyarlarActivity.this);

        AlertDialog.Builder alertDialogOlusturucu = new AlertDialog.Builder(AyarlarActivity.this);
        Resources resources = AyarlarActivity.this.getResources();

        alertDialogOlusturucu.setIcon(resources.getDrawable(R.drawable.ic_baseline_edit_24));
        alertDialogOlusturucu.setTitle(R.string.bizieleştirin);
        alertDialogOlusturucu.setMessage(R.string.degerlifikirlerinizibizimlepaylaşmak);

        alertDialogOlusturucu.setPositiveButton(R.string.Tamam, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

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