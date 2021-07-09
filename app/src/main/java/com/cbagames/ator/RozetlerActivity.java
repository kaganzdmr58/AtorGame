package com.cbagames.ator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class RozetlerActivity extends AppCompatActivity {

    private TextView textViewTamam;

    private ImageView imageViewDoubleBest, imageViewSampiyon, imageViewIslemDehasi,
            imageViewUsta,imageViewKarakterSahibi;

    private Resources res ;
    private Boolean karanlikMod;

    private int rozetSayisiCount = 0;

    // sayfa açıldığında web üzerinden per_rozet_sayisi güncellemesi yapılacak.

    // karanlık modda 512 X 512 olduğunda hata verdiği için picasso ile çözünürlğü 150'ye düşürdüm.

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), BolumSecActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rozetler);
        MultiDex.install(this);


        textViewTamam = findViewById(R.id.textViewTamam);

        imageViewDoubleBest = findViewById(R.id.imageViewDoubleBest);
        imageViewSampiyon = findViewById(R.id.imageViewSampiyon);
        imageViewIslemDehasi = findViewById(R.id.imageViewDeha);
        imageViewUsta = findViewById(R.id.imageViewUsta);
        imageViewKarakterSahibi = findViewById(R.id.imageViewKarakter);

        textViewTamam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ayarlar sayfası geçişi ve reklam açma
                Intent intent = new Intent(getApplicationContext(),AyarlarActivity.class);
                intent.putExtra("reklamDurum",false);
                startActivity(intent);
                finish();
            }
        });


        res = getResources();

        karanlikMod = true;//KarsilamaEkraniActivity.sp.getBoolean("karanlikMod",false);
        karanlikModKontrol(karanlikMod);



        Boolean ustaRozeti = KarsilamaEkraniActivity.spRozet.getBoolean("ustaRozeti",false);
        if (ustaRozeti){
            if (karanlikMod){
                Picasso.get().load(R.drawable.usta_sari_2).resize(150, 150).centerCrop().into(imageViewUsta);
                //imageViewUsta.setImageDrawable(res.getDrawable(R.drawable.usta_sari_2));
            }/*else {
                //Picasso.get().load(R.drawable.usta_sari).into(imageViewUsta);
                imageViewUsta.setImageDrawable(res.getDrawable(R.drawable.usta_sari_2));
            }*/
            rozetSayisiCount +=1;
        }

        Boolean islemRozeti = KarsilamaEkraniActivity.spRozet.getBoolean("islemRozeti",false);
        if (islemRozeti){
            if (karanlikMod){
                Picasso.get().load(R.drawable.islem_sari_2).resize(150, 150).centerCrop().into(imageViewIslemDehasi);
                //imageViewIslemDehasi.setImageDrawable(res.getDrawable(R.drawable.islem_sari_2));
            }/*else {
                //Picasso.get().load(R.drawable.islem_sari).resize(150, 150).centerCrop().into(imageViewIslemDehasi);
                imageViewIslemDehasi.setImageDrawable(res.getDrawable(R.drawable.islem_sari_2));
            }*/
            rozetSayisiCount +=1;
        }


        // burada rozetlerin durumu sorgulanacak rozet alınmış olanlara farklı resim ve yazı rengi ÖRN: altın rengine büründürme gibi (duolingo)
        Boolean karakterSahibi = KarsilamaEkraniActivity.spRozet.getBoolean("karakterSahibi",false);
        if (karakterSahibi){
            if (karanlikMod){
                Picasso.get().load(R.drawable.karakter_sari_2).resize(150, 150).centerCrop().into(imageViewKarakterSahibi);
                //imageViewKarakterSahibi.setImageDrawable(res.getDrawable(R.drawable.karakter_sari_2));
            }/*else {
                //Picasso.get().load(R.drawable.karakter_sari).resize(150, 150).centerCrop().into(imageViewKarakterSahibi);
                imageViewKarakterSahibi.setImageDrawable(res.getDrawable(R.drawable.karakter_sari_2));
            }*/
            rozetSayisiCount +=1;
        }



        Boolean DoubleBestModeSkor = KarsilamaEkraniActivity.spRozet.getBoolean("DoubleBestModeSkor",false);
        Boolean DoubleBestModePlayer = KarsilamaEkraniActivity.spRozet.getBoolean("DoubleBestModePlayer",false);

        if (DoubleBestModeSkor && DoubleBestModePlayer){
            if (karanlikMod){
                Picasso.get().load(R.drawable.db_sari_2).resize(150, 150).centerCrop().into(imageViewDoubleBest);

                //imageViewDoubleBest.setImageDrawable(res.getDrawable(R.drawable.db_sari_2));
            }/*else {
                //Picasso.get().load(R.drawable.db_sari).resize(150, 150).centerCrop().into(imageViewDoubleBest);
                imageViewDoubleBest.setImageDrawable(res.getDrawable(R.drawable.db_sari_2));
            }*/
            rozetSayisiCount +=1;
        }


        Boolean SampiyonMode = KarsilamaEkraniActivity.spRozet.getBoolean("SampiyonMode",false);
        if (SampiyonMode){
            if (karanlikMod){
                Picasso.get().load(R.drawable.sampiyon_sari_2).resize(150, 150).centerCrop().into(imageViewSampiyon);
                //imageViewSampiyon.setImageDrawable(res.getDrawable(R.drawable.sampiyon_sari_2));
            }/*else {
                //Picasso.get().load(R.drawable.sampiyon_sari).resize(150, 150).centerCrop().into(imageViewSampiyon);
                imageViewSampiyon.setImageDrawable(res.getDrawable(R.drawable.sampiyon_sari_2));
            }*/
            rozetSayisiCount +=1;
        }


        // Rozet Sayisi Güncelleme
        int rozetSayisi = KarsilamaEkraniActivity.spRozet.getInt("rozetSayisi",0);

        if (rozetSayisi != rozetSayisiCount){
            webPersonelRozetSayisiGuncelleme(rozetSayisiCount);
        }

        KarsilamaEkraniActivity.editorRozet.putInt("rozetSayisi",rozetSayisiCount);
        KarsilamaEkraniActivity.editorRozet.commit();





        imageViewDoubleBest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alerOlusturucu(1);

            }
        });

        imageViewSampiyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alerOlusturucu(2);

            }
        });

        imageViewKarakterSahibi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alerOlusturucu(3);

            }
        });

        imageViewIslemDehasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alerOlusturucu(4);

            }
        });

        imageViewUsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alerOlusturucu(5);

            }
        });




        // burada card view e dokunulunca bilgilendirme alerti çıkacak ufak bilgilerverecek tamam ile kapatılacak.









    }



    private void karanlikModKontrol(Boolean karanlikMod){
        if (karanlikMod){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            Picasso.get().load(R.drawable.usta_gri_2).resize(150, 150).centerCrop().into(imageViewUsta);
            Picasso.get().load(R.drawable.islem_gri_2).resize(150, 150).centerCrop().into(imageViewIslemDehasi);
            Picasso.get().load(R.drawable.karakter_gri_2).resize(150, 150).centerCrop().into(imageViewKarakterSahibi);
            Picasso.get().load(R.drawable.db_gri_2).resize(150, 150).centerCrop().into(imageViewDoubleBest);
            Picasso.get().load(R.drawable.sampiyon_gri_2).resize(150, 150).centerCrop().into(imageViewSampiyon);
            
/*
            imageViewDoubleBest.setImageDrawable(res.getDrawable(R.drawable.db_gri_2));
            imageViewIslemDehasi.setImageDrawable(res.getDrawable(R.drawable.islem_gri_2));
            imageViewKarakterSahibi.setImageDrawable(res.getDrawable(R.drawable.karakter_gri_2));
            imageViewSampiyon.setImageDrawable(res.getDrawable(R.drawable.sampiyon_gri_2));
            imageViewUsta.setImageDrawable(res.getDrawable(R.drawable.usta_gri_2));
*/

        }/*else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

           *//* Picasso.get().load(R.drawable.usta_sari).resize(150, 150).centerCrop().into(imageViewUsta);
            Picasso.get().load(R.drawable.islem_sari).resize(150, 150).centerCrop().into(imageViewIslemDehasi);
            Picasso.get().load(R.drawable.karakter_sari).resize(150, 150).centerCrop().into(imageViewKarakterSahibi);
            Picasso.get().load(R.drawable.db_sari).resize(150, 150).centerCrop().into(imageViewDoubleBest);
            Picasso.get().load(R.drawable.sampiyon_sari).resize(150, 150).centerCrop().into(imageViewSampiyon);
*//*
            imageViewDoubleBest.setImageDrawable(res.getDrawable(R.drawable.db_gri));
            imageViewIslemDehasi.setImageDrawable(res.getDrawable(R.drawable.islem_gri));
            imageViewKarakterSahibi.setImageDrawable(res.getDrawable(R.drawable.karakter_gri));
            imageViewSampiyon.setImageDrawable(res.getDrawable(R.drawable.sampiyon_gri));
            imageViewUsta.setImageDrawable(res.getDrawable(R.drawable.usta_gri));
        }*/
    }


    public void alerOlusturucu(int kupa){
        LayoutInflater inflater = LayoutInflater.from(RozetlerActivity.this);
        View alert_tasarim = inflater.inflate(R.layout.alertview_tasarim, null);

        ImageView alertImageView = (ImageView) alert_tasarim.findViewById(R.id.imageViewAlertRozet);
        TextView alertTextView = (TextView) alert_tasarim.findViewById(R.id.textViewAlertAciklama);
        TextView textViewBaslik = (TextView) alert_tasarim.findViewById(R.id.textViewBaslik);
        textViewBaslik.setVisibility(View.INVISIBLE);

        Resources resources = RozetlerActivity.this.getResources();

        AlertDialog.Builder alertDialogOlusturucu = new AlertDialog.Builder(RozetlerActivity.this);

        alertDialogOlusturucu.setMessage(res.getText(R.string.ro_kupa_aciklamasi));
        alertDialogOlusturucu.setView(alert_tasarim);

        if (karanlikMod){
            switch (kupa){
                case 1:     //  db
                    Picasso.get().load(R.drawable.db_sari_2).resize(150, 150).centerCrop().into(alertImageView);
                    //alertImageView.setImageDrawable(resources.getDrawable(R.drawable.db_sari_2));
                    alertTextView.setText(res.getText(R.string.ro_db));
                    break;
                case 2:     //  şampiyon
                    Picasso.get().load(R.drawable.sampiyon_sari_2).resize(150, 150).centerCrop().into(alertImageView);
                    //alertImageView.setImageDrawable(resources.getDrawable(R.drawable.sampiyon_sari_2));
                    alertTextView.setText(res.getText(R.string.ro_sampiyon));
                    break;
                case 3:     //  karakter sahibi
                    Picasso.get().load(R.drawable.karakter_sari_2).resize(150, 150).centerCrop().into(alertImageView);
                    //alertImageView.setImageDrawable(resources.getDrawable(R.drawable.karakter_sari_2));
                    alertTextView.setText(res.getText(R.string.ro_karakter));
                    break;
                case 4:     //  Deha
                    Picasso.get().load(R.drawable.islem_sari_2).resize(150, 150).centerCrop().into(alertImageView);
                    //alertImageView.setImageDrawable(resources.getDrawable(R.drawable.islem_sari_2));
                    alertTextView.setText(res.getText(R.string.ro_deha));
                    break;
                case 5:     //  usta
                    Picasso.get().load(R.drawable.usta_sari_2).resize(150, 150).centerCrop().into(alertImageView);
                    //alertImageView.setImageDrawable(resources.getDrawable(R.drawable.usta_sari_2));
                    alertTextView.setText(res.getText(R.string.ro_usta));
                    break;
                default:
                    break;
            }
        }/*else {
            switch (kupa){
                case 1:     //  db
                    //Picasso.get().load(R.drawable.db_sari).resize(150, 150).centerCrop().into(alertImageView);
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.db_sari));
                    alertTextView.setText(res.getText(R.string.ro_db));
                    break;
                case 2:     //  şampiyon
                    //Picasso.get().load(R.drawable.sampiyon_sari).resize(150, 150).centerCrop().into(alertImageView);
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.sampiyon_sari));
                    alertTextView.setText(res.getText(R.string.ro_sampiyon));
                    break;
                case 3:     //  karakter sahibi
                    //Picasso.get().load(R.drawable.karakter_sari).resize(150, 150).centerCrop().into(alertImageView);
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.karakter_sari));
                    alertTextView.setText(res.getText(R.string.ro_karakter));
                    break;
                case 4:     //  Deha
                    //Picasso.get().load(R.drawable.islem_sari).resize(150, 150).centerCrop().into(alertImageView);
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.islem_sari));
                    alertTextView.setText(res.getText(R.string.ro_deha));
                    break;
                case 5:     //  usta
                    //Picasso.get().load(R.drawable.usta_sari).resize(150, 150).centerCrop().into(alertImageView);
                    alertImageView.setImageDrawable(resources.getDrawable(R.drawable.usta_sari));
                    alertTextView.setText(res.getText(R.string.ro_usta));
                    break;
                default:
                    break;
            }
        }*/





        alertDialogOlusturucu.setPositiveButton(res.getText(R.string.Tamam), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
       /* alertDialogOlusturucu.setNegativeButton("Paylaş", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });*/
        alertDialogOlusturucu.create().show();
    }


    public void webPersonelRozetSayisiGuncelleme(int per_rozet_sayisi){
        // skor 0 geldiğinde çalışacak
        // en yüksek skor oy_03_skor listesine kayıt edilecek (yeni)
        if (!internetKontrol()){
            Toast.makeText(this, res.getText(R.string.ro_internet_baglantısı_olmadıgı_icin), Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://callbellapp.xyz/project/oy_03_personel/oy_03_personal_rozet_sayisi_update.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RozetlerActivity.this, res.getText(R.string.ro_rozet_sayiniz_basariyla_guncellendi), Toast.LENGTH_SHORT).show();
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
                params.put("per_rozet_sayisi",String.valueOf(per_rozet_sayisi));
                return params;
            }
        };
        Volley.newRequestQueue(RozetlerActivity.this).add(stringRequest);

    }


    protected boolean internetKontrol() {
        //interneti kontrol eden method
        // TODO Auto-generated method stub
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


    public void uygulamaDisiRozetiPaylas(){
        // diğer uygulamalarla paylaş
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=com.cbagames.ator");
        sendIntent.setType("text/*");
        startActivity(sendIntent);
    }


}