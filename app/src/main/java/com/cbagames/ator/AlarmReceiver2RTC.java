package com.cbagames.ator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.cbagames.ator.KarsilamaEkraniActivity;
import com.cbagames.ator.R;

public class AlarmReceiver2RTC extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "RTC ALARM AYARLANDI.", Toast.LENGTH_SHORT).show();
        Resources res = context.getResources();
        bildirimOlusturma(context,res.getString(R.string.GunlukAlarmBildirimMesajı));
    }




    public void bildirimOlusturma(Context context,String mesaj){
        NotificationCompat.Builder builder;

        NotificationManager bildirimYoneticisi =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, KarsilamaEkraniActivity.class);

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