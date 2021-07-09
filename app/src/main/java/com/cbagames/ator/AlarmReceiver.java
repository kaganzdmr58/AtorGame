package com.cbagames.ator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.multidex.MultiDex;

public class AlarmReceiver extends BroadcastReceiver {

    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;
    public static int canSayisi , sayi = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        MultiDex.install(context);
        // burası alarm çalıştığında yapılcak işlemin yazılacağı yer.

        //Toast.makeText(context,"Alarm ayarlandı."+ new Date().toString(),Toast.LENGTH_SHORT).show();

        //bildirimOlusturma(context,sayi);
        sp = context.getSharedPreferences("alarm", Context.MODE_PRIVATE);
        editor = sp.edit();

        Resources res = context.getResources();

        sayi = sp.getInt("canSuresiCount",0);
        sayi += 1;
        editor.putInt("canSuresiCount",sayi);
        editor.commit();

        canSayisi = sp.getInt("canSayisi",0);

        //Toast.makeText(context, "Can sayisi : "+canSayisi+ " can Süresi Count : "+ sayi, Toast.LENGTH_SHORT).show();

        
        if (sayi == 2){
            if (canSayisi < 1){
                bildirimOlusturma(context,res.getString(R.string.alarm_1_canınız_geri_geldi));
                canSayisi = 1;
            }
        }

        if (sayi == 5){
            //bildirimOlusturma(context,sayi);
            if (canSayisi <= 1){
                canSayisi = 2;
            }
        }

        if (sayi == 15){
            //bildirimOlusturma(context,sayi);
            if (canSayisi <= 2){
                canSayisi = 3;
            }
        }

        if (sayi == 30){
            //bildirimOlusturma(context,sayi);
            if (canSayisi <= 3){
                canSayisi = 4;
            }
        }

        if (sayi == 60){
            bildirimOlusturma(context,res.getString(R.string.alarm_canlarınız_fullendi));
            if (canSayisi <= 4){
                canSayisi = 5;
            }
        }




        if (sayi > 61){
            editor.putInt("canSuresiCount",0);
            editor.commit();

            if (SonucEkraniActivity.alarmManager != null){
                SonucEkraniActivity.alarmManager.cancel(SonucEkraniActivity.pendingIntent);
                //Toast.makeText(context, "iptal edildi", Toast.LENGTH_SHORT).show();
                //bildirimOlusturma(context,"Canlarınız fullendi.");
                sayi = 0;
            }

        }

        editor.putInt("canSayisi",canSayisi);
        editor.commit();


        //SonucEkraniActivity.canSayisi = canSayisi;
        //SonucEkraniActivity.textViewCanSayisiSonuc.setText(String.valueOf(canSayisi));
        //KarsilamaEkraniActivity.setCanSayisi(canSayisi);
        //MainActivity.textViewCanSayisi.setText(String.valueOf(canSayisi));
        //KarsilamaEkraniActivity.canSayisi = canSayisi;
        //AyarlarActivity.canSayisi = canSayisi;



    }

    public static void setCanSayisi(Context context,int canSayisiX){

        //bildirimOlusturma(context,sayi);
        sp = context.getSharedPreferences("alarm", Context.MODE_PRIVATE);
        editor = sp.edit();

        editor.putInt("canSuresiCount",0);
        editor.putInt("canSayisi",canSayisiX);
        editor.commit();

    }

    public void bildirimOlusturma(Context context,String mesaj){
        NotificationCompat.Builder builder;

        NotificationManager bildirimYoneticisi =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context,KarsilamaEkraniActivity.class);

        PendingIntent gidilecekIntent = PendingIntent.getActivity(context
                ,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String kanalId = "kanalId";
            String kanalAd = "kanalAd";
            String kanalTanım = "kanalTanım";
            int kanalOnceligi = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel kanal = bildirimYoneticisi.getNotificationChannel(kanalId);

            if(kanal==null){
                kanal = new NotificationChannel(kanalId,kanalAd,kanalOnceligi);
                kanal.setDescription(kanalTanım);
                bildirimYoneticisi.createNotificationChannel(kanal);
            }

            builder = new NotificationCompat.Builder(context,kanalId);

            builder.setContentTitle("Ator Game")
                    .setContentText(mesaj)
                    .setSmallIcon(R.mipmap.icon_kirmizi_top)
                    .setAutoCancel(true)
                    .setContentIntent(gidilecekIntent);
            /*// ayarlar ekranından bilgi alma
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            String bildirimTonu = sp.getString("PREF_BILDIRIM_TONU",null);
            if (!TextUtils.isEmpty(bildirimTonu))
                builder.setSound(Uri.parse(bildirimTonu));*/

        }else {

            builder = new NotificationCompat.Builder(context);

            builder.setContentTitle("Ator Game")
                    .setContentText(mesaj)
                    .setSmallIcon(R.mipmap.icon_kirmizi_top)
                    .setAutoCancel(true)
                    .setContentIntent(gidilecekIntent);
           /* // ayarlar ekranından bilgi alma
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            String bildirimTonu = sp.getString("PREF_BILDIRIM_TONU",null);
            if (!TextUtils.isEmpty(bildirimTonu))
                builder.setSound(Uri.parse(bildirimTonu));*/

        }

        bildirimYoneticisi.notify(1,builder.build());
    }
}