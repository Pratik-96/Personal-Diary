package com.example.firebase_login;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent1) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Channel_id");
        builder.setSmallIcon(R.drawable.google_play_books);


        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivities(context,1, new Intent[]{intent},PendingIntent.FLAG_IMMUTABLE);
        builder.setContentTitle("Reminder..!!");
        builder.setContentText("It's time to write your memo...!!");
        builder.setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "Channel_id";
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(channelId,"Channel human readable title", NotificationManager.IMPORTANCE_HIGH);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }
        builder.setChannelId(channelId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!notificationManager.isNotificationPolicyAccessGranted()) {
                Intent intent2 = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            }
        }


        notificationManager.notify(100,builder.build());

    }
}
