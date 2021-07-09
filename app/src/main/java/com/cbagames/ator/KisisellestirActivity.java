package com.cbagames.ator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class KisisellestirActivity extends AppCompatActivity {
    private ImageView imageViewAvatarShow,imageViewS1,imageViewS2,imageViewS3
            ,imageViewS4,imageViewS5,imageViewS6;
    private EditText editTextTextPersonName;
    private Button buttonKaydet;
    private TextView textViewIptal;
    private int perIconNu = 1;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Ayarlar sayfası geçişi ve reklam açma
        Intent intent = new Intent(getApplicationContext(),AyarlarActivity.class);
        intent.putExtra("reklamDurum",false);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisisellestir);
        Resources res = getResources();
        MultiDex.install(this);

        imageViewAvatarShow = findViewById(R.id.imageViewAvatarShow);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        buttonKaydet = findViewById(R.id.buttonKaydet);
        textViewIptal = findViewById(R.id.textViewIptal);
        imageViewS1 = findViewById(R.id.imageViewS1);
        imageViewS2 = findViewById(R.id.imageViewS2);
        imageViewS3 = findViewById(R.id.imageViewS3);
        imageViewS4 = findViewById(R.id.imageViewS4);
        imageViewS5 = findViewById(R.id.imageViewS5);
        imageViewS6 = findViewById(R.id.imageViewS6);

        avatarSecimiUygula();



        String name = KarsilamaEkraniActivity.sp.getString("name","Player");
        editTextTextPersonName.setText(name);

        perIconNu = KarsilamaEkraniActivity.sp.getInt("avatarSecim",1);

        buttonKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // personel name alınacak ve kayıt edilecek ayarlar activity isim kayıt edilecek.
                String name = editTextTextPersonName.getText().toString().trim();
                KarsilamaEkraniActivity.editor.putString("name",name);
                KarsilamaEkraniActivity.editor.commit();

                if (!internetKontrol()){
                    Boolean karakterSahibi = KarsilamaEkraniActivity.spRozet.getBoolean("karakterSahibi",false);
                    if (karakterSahibi){
                        Toast.makeText(KisisellestirActivity.this,
                                res.getText(R.string.ki_internet_baglantiniz_yok_uzun), Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(KisisellestirActivity.this,
                                res.getText(R.string.ki_internet_baglantiniz_yok), Toast.LENGTH_SHORT).show();
                    }
                    // Ayarlar sayfası geçişi ve reklam açma
                    Intent intent = new Intent(getApplicationContext(),AyarlarActivity.class);
                    intent.putExtra("reklamDurum",false);
                    startActivity(intent);
                    finish();
                }else {
                    webPersonelNameveIconGuncelleme(name,String.valueOf(perIconNu));
                    // personel icon nu değişikliğideğişecek.
                }
                // avatar seçimi kayıt edilecek aynı anda uygulanacak

                // arka plan rengi veya deseni kayıt edilecek aynı anda uygulanacak


            }
        });

        textViewIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ayarlar sayfası geçişi ve reklam açma
                Intent intent = new Intent(getApplicationContext(),AyarlarActivity.class);
                intent.putExtra("reklamDurum",false);
                startActivity(intent);
                finish();
            }
        });

        imageViewS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewAvatarShow.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu));
                avatarSecimKayit(1);
            }
        });

        imageViewS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewAvatarShow.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s2));
                avatarSecimKayit(2);
            }
        });

        imageViewS3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewAvatarShow.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s3));
                avatarSecimKayit(3);
            }
        });


        imageViewS4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewAvatarShow.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s4));
                avatarSecimKayit(4);
            }
        });

        imageViewS5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewAvatarShow.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s5));
                avatarSecimKayit(5);
            }
        });

        imageViewS6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewAvatarShow.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s6));
                avatarSecimKayit(6);
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


    public void webPersonelNameveIconGuncelleme(String name,String per_icon_nu){
        // skor 0 geldiğinde çalışacak
        // en yüksek skor oy_03_skor listesine kayıt edilecek (yeni)

        String url = "https://callbellapp.xyz/project/oy_03_personel/oy_03_personal_name_and_icon_nu_update.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //burada player id numarası girilecek.

                Boolean karakterSahibi = KarsilamaEkraniActivity.spRozet.getBoolean("karakterSahibi",false);

                KarsilamaEkraniActivity.editorRozet.putBoolean("karakterSahibi",true);
                KarsilamaEkraniActivity.editorRozet.commit();

                if (!karakterSahibi) {
                    alerOlusturucuTebriklerKarakterSahibiRozetiKazandınız();
                }else {
                    // Ayarlar sayfası geçişi ve reklam açma
                    Intent intent = new Intent(getApplicationContext(),AyarlarActivity.class);
                    intent.putExtra("reklamDurum",false);
                    startActivity(intent);
                    finish();
                }

                // burada ilk defasında karakter sahibi rozeti kazandınız uyarısı ve rozet iconu gösteren alert

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Resources res = getResources();
                Toast.makeText(KisisellestirActivity.this,res.getText(R.string.ki_guncelleme_isleminde_sorun_olustu) , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("per_id",KarsilamaEkraniActivity.personelIDString);
                params.put("per_name",name);
                params.put("per_icon_nu",String.valueOf(per_icon_nu));
                return params;
            }
        };
        Volley.newRequestQueue(KisisellestirActivity.this).add(stringRequest);

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

    private void avatarSecimKayit(int secim){

        KarsilamaEkraniActivity.editor.putInt("avatarSecim",secim);
        KarsilamaEkraniActivity.editor.commit();

        perIconNu = secim;
    }


    public void avatarSecimiUygula(){
        int secim = KarsilamaEkraniActivity.sp.getInt("avatarSecim",1);
        Resources res = getResources();
        switch (secim){
            case 1:
                imageViewAvatarShow.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu));
                break;
            case 2:
                imageViewAvatarShow.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s2));
                break;
            case  3:
                imageViewAvatarShow.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s3));
                break;
            case 4:
                imageViewAvatarShow.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s4));
                break;
            case 5:
                imageViewAvatarShow.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s5));
                break;
            case 6:
                imageViewAvatarShow.setImageDrawable(res.getDrawable(R.drawable.avatar_mutlu_s6));
                break;
            default:
                break;
        }
    }

    public void alerOlusturucuTebriklerKarakterSahibiRozetiKazandınız(){
        Resources res = getResources();

        sesNewLevel();

        Boolean karanlikMod = KarsilamaEkraniActivity.sp.getBoolean("karanlikMod",false);

        LayoutInflater inflater = LayoutInflater.from(KisisellestirActivity.this);
        View alert_tasarim = inflater.inflate(R.layout.alertview_tasarim, null);

        ImageView alertImageView = (ImageView) alert_tasarim.findViewById(R.id.imageViewAlertRozet);
        TextView alertTextView = (TextView) alert_tasarim.findViewById(R.id.textViewAlertAciklama);

        Resources resources = KisisellestirActivity.this.getResources();

        AlertDialog.Builder alertDialogOlusturucu = new AlertDialog.Builder(KisisellestirActivity.this);

        alertDialogOlusturucu.setMessage("   ");
        alertDialogOlusturucu.setView(alert_tasarim);

        if (karanlikMod){
            alertImageView.setImageDrawable(resources.getDrawable(R.drawable.karakter_sari_2));
            alertTextView.setText(res.getText(R.string.ki_tebrikler_karakter_sahibi_rozeti_kazandiniz));
        }else {
            alertImageView.setImageDrawable(resources.getDrawable(R.drawable.karakter_sari));
            alertTextView.setText(res.getText(R.string.ki_tebrikler_karakter_sahibi_rozeti_kazandiniz));

        }

        alertDialogOlusturucu.setPositiveButton(res.getText(R.string.Tamam), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // Ayarlar sayfası geçişi ve reklam açma
                Intent intent = new Intent(getApplicationContext(),AyarlarActivity.class);
                intent.putExtra("reklamDurum",false);
                startActivity(intent);
                finish();

            }
        });
       /* alertDialogOlusturucu.setNegativeButton("iptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(RozetlerActivity.this,res.getString("İptal edildi."),Toast.LENGTH_SHORT).show();
            }
        });*/
        alertDialogOlusturucu.create().show();
    }


    public void sesNewLevel(){
        // sesler
        Boolean sesDurum = KarsilamaEkraniActivity.sp.getBoolean("sesDurum",false);
        final MediaPlayer sesNewLevel = MediaPlayer.create(KisisellestirActivity.this, R.raw.level_completed_1);
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