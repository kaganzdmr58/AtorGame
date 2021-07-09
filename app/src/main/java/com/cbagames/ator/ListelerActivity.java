package com.cbagames.ator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListelerActivity extends AppCompatActivity {

    // personel

    private ArrayList<Personel> personelArrayList ;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView rv;
    private listelerRVAdapter adapter;
    private TextView textViewTamam3;
    private AdView mAdView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), BolumSecActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listeler);
        Resources res = getResources();
        MultiDex.install(this);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        rv = findViewById(R.id.rv);
        textViewTamam3 = findViewById(R.id.textViewTamam3);

        // seçim belirtiyoruz.
        bottomNavigationView.setSelectedItemId(R.id.action_birinci);




       /* // ADMOB banner
        MobileAds.initialize(this,"ca-app-pub-2183039164562504~3326495559");
        //cihaz admob ID
        final AdView banner = findViewById(R.id.adViewbanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);
        banner.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                // Reklam yüklendiğinde çalışır
                Log.e("Banner"," onAdLoaded yüklendi");
            }
            @Override
            public void onAdFailedToLoad(int i) {
                // Hata oluştuğunda çalışır.
                Log.e("Banner","onAdFailedToLoad yüklendi : " + i);

                // banner.loadAd( new AdRequest.Builder().build());
            }
            @Override
            public void onAdOpened() {
                // Reklam tıklanarak açılma
                Log.e("Banner","onAdOpened yüklendi");
            }

            @Override
            public void onAdLeftApplication() {
                //Uygulamadan ayrılınca
                Log.e("Banner","onAdLeftApplication çalıştı");
            }

            @Override
            public void onAdClosed() {
                //Reklamdan geri dönünce çalışır.
                Log.e("Banner","onAdClosed çalıştı");
            }
        });

*/




        //Navigasyonlar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.action_birinci:


                        //Toast.makeText(ListelerActivity.this, "1. tıklandı", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_ikinci:
                        //Toast.makeText(ListelerActivity.this, "2. tıklandı", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),Listeler2Activity.class));
                        finish();
                        break;

                    default:
                }
                return true;
            }
        });



        rv.setHasFixedSize(true);
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));







        webListeGetir();

        textViewTamam3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ayarlar sayfası geçişi ve reklam açma
                Intent intent = new Intent(getApplicationContext(),AyarlarActivity.class);
                intent.putExtra("reklamDurum",false);
                startActivity(intent);
                finish();
            }
        });


        Boolean karanlikMod = false;//KarsilamaEkraniActivity.sp.getBoolean("karanlikMod",false);
        karanlikModKontrol(karanlikMod);





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



    private void karanlikModKontrol(Boolean karanlikMod){
        if (!karanlikMod){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }



    public void webListeGetir(){
        Resources res = getResources();
        // skor 0 geldiğinde çalışacak
        // önce internet bağlantısı var mı kontrol edilecek

        if (!internetKontrol()){
            Toast.makeText(this, res.getText(R.string.li_internet_baglantiniz_yok), Toast.LENGTH_SHORT).show();
            return;
        }
        // en yüksek skor oy_03_skor listesine kayıt edilecek (yeni)
        personelArrayList = new ArrayList<>();

        /*// liste Başlığı
        Personel perBaslik = new Personel(0,"EN İYİ OYUNCULAR",100,1);
        personelArrayList.add(perBaslik);
*/


        String url = "https://callbellapp.xyz/project/oy_03_personel/oy_03_personel_all_table_30_person.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray dizi = jsonObject.getJSONArray("tablo1");

                    for (int i = 0; i<dizi.length(); i++ ) {

                        JSONObject q = dizi.getJSONObject(i);

                        int per_id = q.getInt("per_id");
                        String per_name = q.getString("per_name");
                        int per_total_skor = q.getInt("per_total_skor");
                        int per_icon_nu = q.getInt("per_icon_nu");
                        int per_rozet_sayisi = q.getInt("per_rozet_sayisi");

                        Personel personel = new Personel(per_id,per_name,per_total_skor,per_icon_nu,per_rozet_sayisi);
                        personelArrayList.add(personel);

                        int personel_genelID = Integer.valueOf(KarsilamaEkraniActivity.personelIDString);
                        if (i < 3){
                            if (per_id == personel_genelID){    // ilk üç içerisne girmişse

                                Boolean DoubleBestModePlayer = KarsilamaEkraniActivity.spRozet.getBoolean("DoubleBestModePlayer",false);
                                Boolean DoubleBestModeSkor = KarsilamaEkraniActivity.spRozet.getBoolean("DoubleBestModeSkor",false);
                                if (!DoubleBestModePlayer && DoubleBestModeSkor){
                                    alerOlusturucuTebriklerRozetKazandınız(2);
                                }

                                KarsilamaEkraniActivity.editorRozet.putBoolean("DoubleBestModePlayer",true);
                                KarsilamaEkraniActivity.editor.commit();
                            }
                        }

                        if (i == 0){        // personel 1. olduysa
                            if (per_id == personel_genelID){

                                Boolean SampiyonMode = KarsilamaEkraniActivity.spRozet.getBoolean("SampiyonMode",false);
                                if (!SampiyonMode){
                                    alerOlusturucuTebriklerRozetKazandınız(2);
                                }

                                KarsilamaEkraniActivity.editorRozet.putBoolean("SampiyonMode",true);
                                KarsilamaEkraniActivity.editorRozet.commit();
                            }
                        }

                    }
                    adapter = new listelerRVAdapter(ListelerActivity.this,personelArrayList);
                    rv.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(ListelerActivity.this).add(stringRequest);


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

    public void alerOlusturucuTebriklerRozetKazandınız(int kupa){
        Resources res = getResources();

        sesNewLevel();

        Boolean karanlikMod = KarsilamaEkraniActivity.sp.getBoolean("karanlikMod",false);

        LayoutInflater inflater = LayoutInflater.from(ListelerActivity.this);
        View alert_tasarim = inflater.inflate(R.layout.alertview_tasarim, null);

        ImageView alertImageView = (ImageView) alert_tasarim.findViewById(R.id.imageViewAlertRozet);
        TextView alertTextView = (TextView) alert_tasarim.findViewById(R.id.textViewAlertAciklama);

        Resources resources = ListelerActivity.this.getResources();

        AlertDialog.Builder alertDialogOlusturucu = new AlertDialog.Builder(ListelerActivity.this);

        alertDialogOlusturucu.setMessage("   ");
        alertDialogOlusturucu.setView(alert_tasarim);

        if (karanlikMod){
            switch (kupa){
                case 1:     //  db
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.db_sari_2));
                    alertTextView.setText(res.getText(R.string.li_tebrikler_double_best));
                    break;
                case 2:     //  şampiyon
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.sampiyon_sari_2));
                    alertTextView.setText(res.getText(R.string.li_tebrikler_sampiyon));
                    break;
                case 3:     //  karakter sahibi
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.karakter_sari_2));
                    alertTextView.setText(res.getText(R.string.li_tebrikler_karakter));
                    break;
                case 4:     //  Deha
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.islem_sari_2));
                    alertTextView.setText(res.getText(R.string.li_tebrikler_islem));
                    break;
                case 5:     //  usta
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.usta_sari_2));
                    alertTextView.setText(res.getText(R.string.li_tebrikler_usta));
                    break;
                default:
                    break;
            }


        }else {

            switch (kupa){
                case 1:     //  db
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.db_sari));
                    alertTextView.setText(res.getText(R.string.li_tebrikler_double_best));
                    break;
                case 2:     //  şampiyon
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.sampiyon_sari));
                    alertTextView.setText(res.getText(R.string.li_tebrikler_sampiyon));
                    break;
                case 3:     //  karakter sahibi
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.karakter_sari));
                    alertTextView.setText(res.getText(R.string.li_tebrikler_karakter));
                    break;
                case 4:     //  Deha
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.islem_sari));
                    alertTextView.setText(res.getText(R.string.li_tebrikler_islem));
                    break;
                case 5:     //  usta
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.usta_sari));
                    alertTextView.setText(res.getText(R.string.li_tebrikler_usta));
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


    public void sesNewLevel(){
        // sesler
        Boolean sesDurum = KarsilamaEkraniActivity.sp.getBoolean("sesDurum",false);
        final MediaPlayer sesNewLevel = MediaPlayer.create(ListelerActivity.this, R.raw.level_completed_1);
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

}