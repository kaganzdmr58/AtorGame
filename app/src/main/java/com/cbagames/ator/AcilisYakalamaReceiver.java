package com.cbagames.ator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.multidex.MultiDex;

import java.util.Date;

public class AcilisYakalamaReceiver extends BroadcastReceiver {

    public static AlarmManager alarmManager;
    public static PendingIntent pendingIntent;


    @Override
    public void onReceive(Context context, Intent intent) {
        MultiDex.install(context);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        final Intent alarmIntent = new Intent(
                context,AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(context
                ,0,alarmIntent,0);

        long neZamanTetiklenecek = SystemClock.elapsedRealtime();
        long tekrarlamaSuresi = 1000 * 60 * 1; //1 dk


        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP
                ,neZamanTetiklenecek,tekrarlamaSuresi, pendingIntent);

        //Toast.makeText(context, "Cihaz açılışı yakalandı : " + new Date().toString(), Toast.LENGTH_SHORT).show();

    }
}