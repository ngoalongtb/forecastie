package com.gfd.cropwis.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.gfd.cropwis.R;
import com.gfd.cropwis.hotspotsarea.HotspotsAreaActivity;

public class TestNewHotspotJobService extends JobIntentService {

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, TestNewHotspotJobService.class, 123, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

//        if (isStopped()) return;

        int i = 0;
        while (true) {
            i++;
            Log.i("ZXC", "on handle work");

            PendingIntent pendingIntent = PendingIntent.getActivity(TestNewHotspotJobService.this, 0, new Intent(TestNewHotspotJobService.this, HotspotsAreaActivity.class), 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(TestNewHotspotJobService.this, "CropWISNotification")
                .setContentTitle("Test Hotspots data")
                .setSmallIcon(R.drawable.maize)
                .setAutoCancel(true)
                .setContentText("alo " + i)
                .setContentIntent(pendingIntent);
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(TestNewHotspotJobService.this);
            managerCompat.notify(999, builder.build());

            SystemClock.sleep(5000);
        }


    }

    @Override
    public boolean onStopCurrentWork() {
        return false;
    }
}
