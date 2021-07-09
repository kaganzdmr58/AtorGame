package com.cbagames.ator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

public class istekGonderActivity extends AppCompatActivity {
    private EditText editTextTextİstekGirisi;
    private RatingBar ratingBar;
    private Button buttonIstekGonder;
    private TextView textViewIstekIptal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istek_gonder);


        editTextTextİstekGirisi = findViewById(R.id.editTextTextİstekGirisi);
        ratingBar = findViewById(R.id.ratingBar);
        buttonIstekGonder = findViewById(R.id.buttonIstekGonder);
        textViewIstekIptal = findViewById(R.id.textViewIstekIptal);

        buttonIstekGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webIstekInsert(editTextTextİstekGirisi.getText().toString(),(int)ratingBar.getRating());
            }
        });

        textViewIstekIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ayarlar sayfası geçişi ve reklam açma
                Intent intent = new Intent(getApplicationContext(),AyarlarActivity.class);
                intent.putExtra("reklamDurum",false);
                startActivity(intent);
                finish();
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


    public void webIstekInsert(String istek,int rate){
        Resources res = getResources();
        // skor 0 geldiğinde çalışacak
        // önce internet bağlantısı var mı kontrol edilecek
        if (!internetKontrol()){
            Toast.makeText(this, res.getText(R.string.so_internet_baglantiniz_yok), Toast.LENGTH_SHORT).show();
            // Ayarlar sayfası geçişi ve reklam açma
            Intent intent = new Intent(getApplicationContext(),AyarlarActivity.class);
            intent.putExtra("reklamDurum",false);
            startActivity(intent);
            finish();
            return;
        }
        // en yüksek skor oy_03_skor listesine kayıt edilecek (yeni)

        String url = "https://callbellapp.xyz/project/oy_03_bize_ulasin/oy_03_bize_ulasin_insert_1.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //burada player id numarası girilecek.
                Log.e("response", response);

                Resources res = getResources();

                Toast.makeText(istekGonderActivity.this,
                        res.getText(R.string.isteginiz_gonderildi), Toast.LENGTH_LONG).show();

                // Ayarlar sayfası geçişi ve reklam açma
                Intent intent = new Intent(getApplicationContext(),AyarlarActivity.class);
                intent.putExtra("reklamDurum",false);
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                startActivity(new Intent(getApplicationContext(),AyarlarActivity.class));
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("per_id",String.valueOf(1));// per id
                params.put("ulasin_istek",istek);
                params.put("ulasin_rate",String.valueOf(rate));
                return params;
            }
        };
        Volley.newRequestQueue(istekGonderActivity.this).add(stringRequest);


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




}