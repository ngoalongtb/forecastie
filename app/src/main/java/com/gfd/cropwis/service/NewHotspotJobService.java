package com.gfd.cropwis.service;

import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.gfd.cropwis.Constants;
import com.gfd.cropwis.R;
import com.gfd.cropwis.configs.AppConfig;
import com.gfd.cropwis.hotspotsarea.HotspotsAreaActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class NewHotspotJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("ZXC", "on start job");

        AndroidNetworking.get(Constants.NEW_HOTSPOT)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean rs = response.optBoolean("rs");
                            JSONObject jsonObject = response.getJSONObject("ms");

                            int selectedWeek = jsonObject.optInt("week");
                            int selectedYear = jsonObject.optInt("year");

                            SharedPreferences preferences = PreferenceManager.
                                    getDefaultSharedPreferences(NewHotspotJobService.this);

                            String key = String.format(Locale.getDefault(), "%d/%d", selectedYear, selectedWeek);

                            if(!preferences.getBoolean(key, false)){
                                //schedule the job only once.

                                PendingIntent pendingIntent = PendingIntent.getActivity(NewHotspotJobService.this, 0, new Intent(NewHotspotJobService.this, HotspotsAreaActivity.class), 0);
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(NewHotspotJobService.this, "CropWISNotification")
                                        .setContentTitle("New Hotspots data")
                                        .setSmallIcon(R.drawable.maize)
                                        .setAutoCancel(true)
                                        .setContentText(key)
                                        .setContentIntent(pendingIntent);
                                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(NewHotspotJobService.this);
                                managerCompat.notify(999, builder.build());


                                //update shared preference
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean(key, true);
                                editor.apply();
                            } else {
                                PendingIntent pendingIntent = PendingIntent.getActivity(NewHotspotJobService.this, 0, new Intent(NewHotspotJobService.this, HotspotsAreaActivity.class), 0);
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(NewHotspotJobService.this, "CropWISNotification")
                                        .setContentTitle("Old Hotspots data")
                                        .setSmallIcon(R.drawable.maize)
                                        .setAutoCancel(true)
                                        .setContentText(key)
                                        .setContentIntent(pendingIntent);
                                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(NewHotspotJobService.this);
                                managerCompat.notify(999, builder.build());
                            }
                        } catch (Exception e) {
                            Log.e(AppConfig.LOG_KEY, e.getMessage());
                        }
                        Log.i(AppConfig.LOG_KEY, response.toString());
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
