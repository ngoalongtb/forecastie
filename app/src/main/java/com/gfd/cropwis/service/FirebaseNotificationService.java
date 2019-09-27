package com.gfd.cropwis.service;

import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.gfd.cropwis.R;
import com.gfd.cropwis.configs.AppConstant;
import com.gfd.cropwis.hotspotsarea.HotspotsAreaActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseNotificationService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, AppConstant.CHANEL_ID)
                .setContentTitle("New token")
                .setSmallIcon(R.drawable.maize)
                .setAutoCancel(true)
                .setContentText(s);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        showNotification(remoteMessage.getNotification());

    }

    private void showNotification(RemoteMessage.Notification notification) {
        String title = notification.getTitle();
        String body = notification.getBody();

        PendingIntent pendingIntent = PendingIntent.getActivity(FirebaseNotificationService.this, 0, new Intent(FirebaseNotificationService.this, HotspotsAreaActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, AppConstant.CHANEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.maize)
                .setAutoCancel(true)
                .setContentText(body)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }
}
